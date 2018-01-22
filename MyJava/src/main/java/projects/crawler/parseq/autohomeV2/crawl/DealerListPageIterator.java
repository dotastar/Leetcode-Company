package projects.crawler.parseq.autohomeV2.crawl;

import com.google.common.base.Verify;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import projects.crawler.data.model.City;
import projects.crawler.parseq.autohomeV2.model.DealerPost;
import projects.crawler.parseq.autohomeV2.parser.DealerListPageParser;
import projects.crawler.parseq.engine.PageIterator;


@Slf4j
public class DealerListPageIterator extends PageIterator {

  // The unique page element that identifies between a normal page and a page exceeds max page index
  private static final String NO_RESULT_PAGE_SELECTOR = ".dealer-list-wrap .no-result";
  private static final String QUERY_PARAMETER_PAGE_INDEX = "pageIndex=";

  public DealerListPageIterator(String baseUrl) {
    super(baseUrl, QUERY_PARAMETER_PAGE_INDEX, NO_RESULT_PAGE_SELECTOR);
  }

  public static void main(String[] args) throws IOException, ParseException {
    String baseUrl = "https://dealer.autohome.com.cn/beijing";
    DealerListPageIterator pager = new DealerListPageIterator(baseUrl);
    DealerListPageParser dealerListPageParser = new DealerListPageParser();
    City city = new City("北京", "直辖市", baseUrl);
    while (pager.hasNext()) {
      System.out.println("Crawling page " + pager.getCurrentIndex());
      Optional<Document> optionalPage = pager.next();
      Verify.verify(optionalPage.isPresent());
      List<DealerPost> dealerPosts = dealerListPageParser.parsePosts(optionalPage.get(), city);
      System.out.println(dealerPosts);
      System.out.println("===============================================================================\n");
    }
  }
}
