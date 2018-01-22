package projects.crawler.taskframework.autohome;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import projects.crawler.data.model.City;
import projects.crawler.taskframework.autohome.crawl.AutohomeCrawler;
import projects.crawler.taskframework.autohome.crawl.AutohomeParser;
import projects.crawler.taskframework.autohome.crawl.CityCrawler;
import projects.crawler.taskframework.task.BaseTask;
import projects.crawler.taskframework.task.TaskControl;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.stream.Collectors.toCollection;


/**
 * CrawlAutohomeDealerPostTask
 * <p>
 */
@Slf4j
public class CrawlAutohomeDealerPostTask extends BaseTask<String, City> {

  private final static int numThreads = 10;
  private final static int logFrequency = 5;
  private final static AtomicLong postsCnt = new AtomicLong();

  public CrawlAutohomeDealerPostTask(Iterator<? extends City> iterator) {
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
    List<City> cityQueue = convert(cityCrawler.extractCities(cityPage));
    Preconditions.checkState(!cityQueue.isEmpty());

    CrawlAutohomeDealerPostTask crawlTask = new CrawlAutohomeDealerPostTask(cityQueue.iterator());
    new TaskControl(crawlTask);
  }

  private static List<City> convert(Map<String, Set<City>> cityByProvince) {
    Preconditions.checkNotNull(cityByProvince);
    return cityByProvince.entrySet()
        .stream()
        .map(Map.Entry::getValue)
        .flatMap(Collection::stream)
        .collect(toCollection(ArrayList::new));
  }

  public static void main(String[] args) throws IOException {
    CrawlAutohomeDealerPostTask.startCrawling();
  }
}
