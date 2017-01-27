package projects.crawler.subproject.autohome;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import projects.crawler.subproject.autohome.crawl.AutohomeParser;
import projects.crawler.subproject.autohome.model.DealerPost;
import projects.crawler.task.BaseTask;
import projects.crawler.task.TaskControl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CrawlAutohomeDealerInfoTask
 * <p>
 */
@Slf4j
public class CrawlAutohomeDealerInfoTask extends BaseTask<ObjectId, DealerPost> {

  private final static int numThreads = 8;
  private final static int logFrequency = 5;
  private final static int MAX_RETRY = 5;

  private AutohomeParser parser;
  private DealerPost.Dao dao;
  private AtomicInteger failures = new AtomicInteger();
  private AtomicInteger missingURls = new AtomicInteger();

  public CrawlAutohomeDealerInfoTask(Iterator<? extends DealerPost> iterator) {
    super(iterator, numThreads, logFrequency);
    AutohomeModule module = new AutohomeModule();
    parser = module.getInstance(AutohomeParser.class);
    dao = module.getInstance(DealerPost.Dao.class);
  }

  @Override
  public boolean operate(DealerPost dealerPost) {
    if (dealerPost.getLinkToUrl() == null) {
      log.warn("No linkToUrl found, {}", dealerPost);
      missingURls.incrementAndGet();
      return false;
    }
    int i = 0;
    boolean isSuccess;
    do {
      try {
        Document dealerInfoPage = Jsoup.connect(dealerPost.getLinkToUrl()).timeout(10_000).userAgent(AutohomeParser.USER_AGENT).get();
        Thread.sleep(200);
        isSuccess = parser.parseDealerInfo(dealerInfoPage, dealerPost);
      } catch (IOException e) {
        isSuccess = false;
        log.error("Error connect to {}, already failed {} times -- {}", dealerPost.getLinkToUrl(), i, e);
      } catch (InterruptedException e) {
        e.printStackTrace();
        isSuccess = false;
      }
    } while (!isSuccess && i++ < MAX_RETRY);

    if (!isSuccess) {
      failures.incrementAndGet();
    }
    if (isSuccess) {
      dao.updateById(dealerPost.getId(), ImmutableMap.of("brandDetailList", dealerPost.getBrandDetailList()));
    }

    return isSuccess;
  }

  public void after() {
    log.info("Total failures {}, missingUrls {}", failures, missingURls);
  }

  public static void startCrawling() {
    DealerPost.Dao dao = new AutohomeModule().getInstance(DealerPost.Dao.class);
    // TODO: fix DB Connection lost in a about 30 minutes issue (cause: auto finalize the DBCursor?)
    List<DealerPost> dealerList = new ArrayList<>();
    dao.find().forEach(dealerList::add);
    log.info("DealerList size: {}", dealerList.size());
    CrawlAutohomeDealerInfoTask crawlTask = new CrawlAutohomeDealerInfoTask(dealerList.iterator());
    new TaskControl(crawlTask);
  }

  public static void main(String[] args) throws IOException {
    CrawlAutohomeDealerInfoTask.startCrawling();
  }
}
