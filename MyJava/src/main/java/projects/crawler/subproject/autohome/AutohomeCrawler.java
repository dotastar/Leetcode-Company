package projects.crawler.subproject.autohome;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import projects.crawler.subproject.autohome.model.DealerPost;

import javax.inject.Inject;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Slf4j
public class AutohomeCrawler {
  protected static final long WAIT_TIME = 500; // ms
  protected static final int TIMEOUT = 10_000; // ms

  protected static final int MAX_RETRY = 5;
  protected static final int MAX_PAGE = 100;
  protected static final String CITY_LIST = "http://www.58.com/yiyaodaibiao/changecity/";
  //    protected static final String ROOT_URL = "http://city.58.com/yiyaodaibiao/pn#/";
  protected static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";

  private static final String URL_TEMPLATE = "http://dealer.autohome.com.cn/%s/0_0_0_0_#_1_0.html";

  @Inject private DealerPost.Dao postDao;
  @Inject private AutohomeParser parser;

  public static void main(String[] args) {
//		AutoTestUtils.runTestClassAndPrint(WuBaCrawler.class);
    AutohomeCrawler crawler = new AutohomeModule().getInstance(AutohomeCrawler.class);
    crawler.crawlByCity("beijing");
  }

  public void crawlByCity(String city) {
    Preconditions.checkNotNull(city);
    log.info("Start crawling city: {}", city);

    int postsCount = 0;
    int pageIdx = 1;
    String baseUrl = String.format(URL_TEMPLATE, city);
    for (; pageIdx <= MAX_PAGE; pageIdx++) {
      String url = baseUrl.replace("#", Integer.toString(pageIdx));
      List<DealerPost> posts;
      try {
        log.info("=============== Page " + pageIdx + " =================");
        Document postsPage = Jsoup.connect(url).timeout(TIMEOUT).userAgent(USER_AGENT).get();
        if (AutohomeParser.isEndPage(postsPage)) {
          break;
        }
        posts = parser.parsePosts(postsPage, city);
      } catch (IOException | ParseException e) {
        log.error("Error({}) happened during crawling {}, page url {}\n{}", e.getMessage(), DealerPost.class.getSimpleName(), url, e);
        // TODO: save failed records
        continue;
      }

      log.info("Extracted " + posts.size() + " posts");
      postDao.insertMany(posts);
      postsCount += posts.size();
    }
    log.info("Finish crawling! Crawled " + (pageIdx - 1) + " pages, " + postsCount + " jobs.");
  }

  @Test
  public void test1() throws IOException {
    String url1 = "http://gz.58.com/yiyaodaibiao/pn1/";
    String url2 = "http://gz.58.com/yiyaodaibiao/pn2/";
    String url3 = "http://gz.58.com/yiyaodaibiao/pn3/";
    System.out.println(Jsoup.connect(url1).timeout(TIMEOUT).userAgent(USER_AGENT).get().hashCode());
    System.out.println(Jsoup.connect(url2).timeout(TIMEOUT).userAgent(USER_AGENT).get().hashCode());
    System.out.println(Jsoup.connect(url3).timeout(TIMEOUT).userAgent(USER_AGENT)
        .get().hashCode());
    assertEquals(true, true);
  }
}
