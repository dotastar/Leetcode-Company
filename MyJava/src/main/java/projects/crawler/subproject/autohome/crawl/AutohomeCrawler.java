package projects.crawler.subproject.autohome.crawl;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import projects.crawler.data.model.City;
import projects.crawler.subproject.autohome.AutohomeModule;
import projects.crawler.subproject.autohome.model.DealerPost;

import javax.inject.Inject;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class AutohomeCrawler {
//  protected static final long WAIT_TIME = 500; // ms
  protected static final int TIMEOUT = 10_000; // ms

//  protected static final int MAX_RETRY = 5;
  protected static final int MAX_PAGE = 100;
  protected static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";

//  private static final String URL_TEMPLATE = "http://dealer.autohome.com.cn/%s/0_0_0_0_#_1_0.html";

  @Inject private DealerPost.Dao postDao;
  @Inject private AutohomeParser parser;

  public static void main(String[] args) {
//		AutoTestUtils.runTestClassAndPrint(WuBaCrawler.class);
    AutohomeCrawler crawler = new AutohomeModule().getInstance(AutohomeCrawler.class);
    String url = "http://dealer.autohome.com.cn/beijing/0_0_0_0_1.html";
    City city = new City("直辖市", "北京", url);
    crawler.crawlDealerPostByCity(city);
  }

  public int crawlDealerPostByCity(City city) {
    checkNotNull(city.getName());
    checkNotNull(city.getUrl());
    log.info("Start crawling city: {}", city);

    int postsCount = 0;
    int pageIdx = 1;
    String baseUrl = toBaseUrl(city.getUrl());

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

    log.info("Finish crawling {}!\nCrawled {} pages, {} posts.", city, (pageIdx - 1), postsCount);
    return postsCount;
  }

  /**
   * http://dealer.autohome.com.cn/linyi/0_0_0_0_1.html => http://dealer.autohome.com.cn/linyi/0_0_0_0_#.html
   */
  private String toBaseUrl(String url) {
    int i = url.lastIndexOf('1');
    Preconditions.checkState(i >= 0, "url:" + url);
    return url.substring(0, i) + '#' + url.substring(i + 1);
  }
}
