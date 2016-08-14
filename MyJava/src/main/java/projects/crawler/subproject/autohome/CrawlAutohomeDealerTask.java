package projects.crawler.subproject.autohome;

import com.google.common.base.Preconditions;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import projects.crawler.data.model.City;
import projects.crawler.subproject.autohome.crawl.AutohomeCrawler;
import projects.crawler.subproject.autohome.crawl.AutohomeParser;
import projects.crawler.subproject.autohome.crawl.CityCrawler;
import projects.crawler.task.BaseTask;
import projects.crawler.task.TaskControl;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * Created by yazhoucao on 10/28/15.
 */
@Slf4j
public class CrawlAutohomeDealerTask extends BaseTask<String, City> {

  private final static int numThreads = 10;
  private final static int logFrequency = 5;
  private final static AtomicLong postsCnt = new AtomicLong();

  public CrawlAutohomeDealerTask(Iterator<? extends City> iterator) {
    super(iterator, numThreads, logFrequency);
  }

  @Override
  public boolean operate(City city) {
    AutohomeCrawler crawler = new AutohomeModule().getInstance(AutohomeCrawler.class);
    int count = crawler.crawlDealerPostByCity(city);
    postsCnt.getAndAdd(count);
    return count > 0;
  }

  @Override
  public void after() {
    log.info("\n=============================================================\n");
    log.info("Task finished!\nCrawled total {} Posts.", postsCnt);
    log.info("\n=============================================================\n");
  }

  @SneakyThrows(IOException.class)
  public static void startCrawling() {
    Document cityPage = Jsoup.connect(CityCrawler.CITY_URL).timeout(10_000).userAgent(AutohomeParser.USER_AGENT).get();

    CityCrawler cityCrawler = new CityCrawler();
    List<City> cityQueue = cityCrawler.convert(cityCrawler.extractCities(cityPage));
    Preconditions.checkState(!cityQueue.isEmpty());

    CrawlAutohomeDealerTask crawlTask = new CrawlAutohomeDealerTask(cityQueue.iterator());
    new TaskControl(crawlTask);
  }

  public static void main(String[] args) throws IOException {
    CrawlAutohomeDealerTask.startCrawling();
  }
}
