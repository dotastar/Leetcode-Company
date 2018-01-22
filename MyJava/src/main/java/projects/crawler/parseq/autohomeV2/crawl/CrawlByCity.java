package projects.crawler.parseq.autohomeV2.crawl;

import com.linkedin.parseq.Task;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import projects.crawler.data.model.City;
import projects.crawler.parseq.autohomeV2.model.DealerPost;
import projects.crawler.parseq.autohomeV2.parser.DealerListPageParser;
import projects.crawler.parseq.engine.CrawlEngine;


@Slf4j
public class CrawlByCity {
  @Inject
  private DealerPost.Dao postDao;
  @Inject
  private CrawlEngine crawlEngine;
  private DealerListPageParser dealerListPageParser = new DealerListPageParser();

  /**
   * Crawl cities in parallel
   */
  public List<DealerPost> crawlByCity(List<City> cities) {
    List<DealerPost> posts = cities.parallelStream()
        .map(this::crawlCity)
        .peek(this::savePostsAsync)
        .flatMap(List::stream)
        .collect(Collectors.toList());
    log.info("Crawled {} cities and parsed in total {} posts", cities.size(), posts.size());
    return posts;
  }

  /**
   * Crawl every page of the city (follow pagination) and parse each page in parallel
   */
  private List<DealerPost> crawlCity(City city) {
    DealerListPageIterator iterator = new DealerListPageIterator(city.getUrl());
    List<Document> pages = iterator.crawl();
    log.info("Crawled {} pages for city {}", pages.size(), city.getName());

    List<DealerPost> dealers = pages.parallelStream()
        .filter(iterator::hasResult)
        .map(page -> dealerListPageParser.parsePosts(page, city))
        .flatMap(List::stream)
        .collect(Collectors.toList());
    log.info("Finish crawling city {}, stopped at page {}, found {} posts.\n", city, iterator.getCurrentIndex(),
        dealers.size());
    return dealers;
  }

  private void savePostsAsync(List<DealerPost> posts) {
    if (posts.isEmpty()) {
      return;
    }
    Task<Void> insertTask = Task.action(() -> postDao.insertMany(posts));
    crawlEngine.getEngine().blockingRun(insertTask);
  }
}
