package projects.crawler.parseq.autohomeV2;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.mongojack.DBCursor;
import projects.crawler.data.model.City;
import projects.crawler.data.model.FetchResult;
import projects.crawler.data.model.RawPage;
import projects.crawler.parseq.autohomeV2.crawl.CityReader;
import projects.crawler.parseq.autohomeV2.crawl.CrawlByCity;
import projects.crawler.parseq.autohomeV2.model.DealerPost;
import projects.crawler.parseq.autohomeV2.parser.DealerListPageParser;


@Slf4j
public class Main {
  private static AutohomeV2Module autohomeV2Module = new AutohomeV2Module();

  public static void main(String[] args) throws IOException {
    JobCenter jobCenter = autohomeV2Module.getInstance(JobCenter.class);

    jobCenter.parseAllRawPageTask();
  }


  public static class JobCenter {
    @Inject private CrawlByCity crawlByCity;
    @Inject private DealerPost.Dao dealerPostDao;
    @Inject private RawPage.Dao rawPageDao;
    @Inject private FetchResult.Dao fetchResultDao;
    private DealerListPageParser dealerListPageParser = new DealerListPageParser();

    public void parseAllRawPageTask() throws IOException {
      Map<String, City> cityMap = buildCityMap();
      DBCursor<RawPage> rawPageDBCursor = rawPageDao.find();
      while (rawPageDBCursor.hasNext()) {
        RawPage rawPage = rawPageDBCursor.next();
        if (rawPage.getHtml() == null) {
          log.warn("Skip {} because it has no html", rawPage);
          continue;
        }
        Document page = rawPage.getParsedPage();
        List<DealerPost> dealerPosts = dealerListPageParser.extractPosts(page, cityMap);
        if (dealerPosts.isEmpty()) {
          log.error("Unexpected: page {} has no results(DealerPost).", rawPage);
          continue;
        }
        dealerPostDao.insertMany(dealerPosts);
      }
    }

    private Map<String, City> buildCityMap() throws IOException {
      List<City> cityList = new CityReader().getAllCity();
      return cityList.stream().collect(Collectors.toMap(c -> DealerListPageParser.extractCityNameFromUrl(c.getUrl()), Function.identity()));
    }

//    public void rerunFailedJobs() {
//
//    }

    public void crawlAndSaveRawPagesByCitiesJob() throws IOException {
      CrawlByCity crawlByCity = autohomeV2Module.getInstance(CrawlByCity.class);
      List<City> cityList = new CityReader().getAllCity();
      Collections.reverse(cityList);
      crawlByCity.crawlAndSaveRawPageByCity(cityList);
      log.info("Finished crawling all the cities.");
    }
  }


}
