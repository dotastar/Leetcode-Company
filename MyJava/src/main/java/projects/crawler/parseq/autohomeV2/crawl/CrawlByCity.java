package projects.crawler.parseq.autohomeV2.crawl;

import com.linkedin.parseq.Task;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import projects.crawler.data.model.City;
import projects.crawler.data.model.FetchResult;
import projects.crawler.data.model.RawPage;
import projects.crawler.parseq.autohomeV2.model.DealerPost;
import projects.crawler.parseq.autohomeV2.parser.AutoBrandIdParser;
import projects.crawler.parseq.autohomeV2.parser.DealerListPageParser;
import projects.crawler.parseq.engine.CrawlEngine;


@Slf4j
public class CrawlByCity {
  @Inject private DealerPost.Dao postDao;
  @Inject private RawPage.Dao rawPageDao;
  @Inject private FetchResult.Dao fetchResultDao;
  @Inject private CrawlEngine crawlEngine;
  private PageFetcher pageFetcher = new PageFetcher();
  private AutoBrandIdParser autoBrandIdParser = new AutoBrandIdParser();

  private DealerListPageParser dealerListPageParser = new DealerListPageParser();

  public void crawlAndSaveRawPageByCity(List<City> cities) {
    for (City city : cities) {
      log.info("Start crawling city {}", city);
      crawlAndSaveRawPageByCity(city);
      log.info("Finished crawling city {}", city);
    }
  }

  private void crawlAndSaveRawPageByCity(City city) {
    List<Integer> brandIds = extractBrandId(city);
    List<DealerListByBrandPageIterator> iteratorList = brandIds.stream()
        .map(id -> new DealerListByBrandPageIterator(city.getUrl(), id))
        .collect(Collectors.toList());

    iteratorList.parallelStream()
        .map(iter -> pageFetcher.fetch(iter))
        .filter(list -> !list.isEmpty())
        .peek(fetchResultDao::insertMany)
        .map(CrawlByCity::convert)
        .filter(list -> !list.isEmpty())
        .forEach(rawPageDao::insertMany);
  }

  private List<Integer> extractBrandId(City city) {
    String url = city.getUrl();
    FetchResult fetchResult = pageFetcher.fetchPage(url);
    if (fetchResult.getResult() != FetchResult.Result.SUCCESS) {
      fetchResultDao.insert(fetchResult);
    }
    Document page = fetchResult.getPage();
    return autoBrandIdParser.extractBrandIds(page);
  }

  private static List<RawPage> convert(List<FetchResult> results) {
    List<RawPage> pages = new ArrayList<>(results.size());
    for (FetchResult result : results) {
      pages.add(new RawPage(result.getUrl(), result.getHtml()));
    }
    return pages;
  }

  /**
   * Crawl cities in parallel
   */
  public List<DealerPost> crawlPostByCity(List<City> cities) {
    List<DealerPost> posts = cities.parallelStream()
        .map(this::crawlPosts)
        .peek(this::savePostsAsync)
        .flatMap(List::stream)
        .collect(Collectors.toList());
    log.info("Crawled {} cities and parsed in total {} posts", cities.size(), posts.size());
    return posts;
  }


  /**
   * Crawl every page of the city (follow pagination) and parse each page in parallel
   */
  private List<DealerPost> crawlPosts(City city) {
    DealerListPageIterator iterator = new DealerListPageIterator(city.getUrl());
    List<Document> pages = pageFetcher.fetchPages(iterator);
    return pages.stream()
        .map(page -> dealerListPageParser.extractPosts(page, city))
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  private void savePostsAsync(List<DealerPost> posts) {
    if (posts.isEmpty()) {
      return;
    }
    Task<Void> insertTask = Task.action(() -> postDao.insertMany(posts));
    crawlEngine.getEngine().blockingRun(insertTask);
  }
}
