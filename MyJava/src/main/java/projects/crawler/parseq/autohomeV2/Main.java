package projects.crawler.parseq.autohomeV2;

import com.linkedin.parseq.Task;
import com.linkedin.parseq.function.Tuple2;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import projects.crawler.data.model.City;
import projects.crawler.parseq.autohomeV2.crawl.CityReader;
import projects.crawler.parseq.autohomeV2.crawl.DealerListPageIterator;
import projects.crawler.parseq.autohomeV2.model.DealerPost;
import projects.crawler.parseq.autohomeV2.parser.DealerListPageParser;
import projects.crawler.parseq.engine.CrawlEngine;
import projects.crawler.parseq.engine.EngineConfig;

@Slf4j
public class Main {

  public static void main(String[] args) throws IOException {
    AutohomeV2Module autohomeV2Module = new AutohomeV2Module();
    DealerPost.Dao postDao = autohomeV2Module.getInstance(DealerPost.Dao.class);
    CrawlEngine crawlEngine = new CrawlEngine(new EngineConfig());
    DealerListPageParser dealerListPageParser = new DealerListPageParser();

    List<City> cityList = new CityReader().getAllCity();

    List<Tuple2<City, DealerListPageIterator>> tuple2s = cityList.stream()
        .map(city -> new Tuple2<>(city, new DealerListPageIterator(city.getUrl())))
        .collect(Collectors.toList());
    Collections.reverse(tuple2s);
    List<Task<Void>> insertTasks = new ArrayList<>();
    for (Tuple2<City, DealerListPageIterator> tuple2 : tuple2s) {
      City city = tuple2._1();
      DealerListPageIterator iterator = tuple2._2();
      iterator.setEngine(crawlEngine);
      log.info("Start crawling city {}", city.getName());
      List<DealerPost> dealers = new ArrayList<>();
      while (iterator.hasNext()) {
        Optional<Document> optionalPage = iterator.next();
        if (!optionalPage.isPresent()) {
          continue;
        }
        Document dealerList = optionalPage.get();
        List<DealerPost> dealerPosts = dealerListPageParser.parsePosts(dealerList, city);
        dealers.addAll(dealerPosts);
        log.info("Found {} posts on page {} for city {}", dealerPosts.size(), iterator.getCurrentIndex(), city.getName());
      }
      log.info("Finish crawling city {}, stopped at page {}.\n", city, iterator.getCurrentIndex());
      if (!dealers.isEmpty()) {
        Task<Void> insertTask = Task.action(() -> postDao.insertMany(dealers));
        crawlEngine.getEngine().blockingRun(insertTask);

        insertTasks.add(insertTask);
        if (insertTasks.size() == 5) {
          insertTasks.forEach(CrawlEngine::printTaskTrace);
          insertTasks.clear();
        }
      }
    }
  }

}
