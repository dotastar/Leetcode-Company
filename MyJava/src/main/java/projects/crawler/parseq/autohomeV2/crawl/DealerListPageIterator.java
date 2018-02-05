package projects.crawler.parseq.autohomeV2.crawl;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import projects.crawler.data.model.City;
import projects.crawler.data.model.FetchResult;
import projects.crawler.parseq.autohomeV2.model.DealerPost;
import projects.crawler.parseq.autohomeV2.parser.DealerListPageParser;
import projects.crawler.parseq.engine.PageIterator;

import static com.google.common.truth.Truth.assertThat;


@Slf4j
public class DealerListPageIterator extends PageIterator {

  // The unique page element that identifies between a normal page and a page exceeds max page index
  private static final String NO_RESULT_PAGE_SELECTOR = ".dealer-list-wrap .no-result";
  private static final String QUERY_PARAMETER_KEY_PAGE_INDEX = "pageIndex=";

  public DealerListPageIterator(String baseUrl) {
    super(baseUrl, QUERY_PARAMETER_KEY_PAGE_INDEX, NO_RESULT_PAGE_SELECTOR);
  }

  // /beijing?countyId=0&brandId=173&seriesId=0&factoryId=0&pageIndex=1&kindId=0&orderType=0&isSales=0#pvareaid=2113614

  public static void main(String[] args) {
    String baseUrl = "https://dealer.autohome.com.cn/beijing";
    DealerListPageIterator pager = new DealerListPageIterator(baseUrl);
    DealerListPageParser dealerListPageParser = new DealerListPageParser();
    City city = new City("北京", "直辖市", baseUrl);
    while (pager.hasNext()) {
      System.out.println("Crawling page " + pager.getCurrentIndex());
      FetchResult fetchResult = pager.next();
      assertThat(fetchResult.getResult()).isEqualTo(FetchResult.Result.SUCCESS);
      List<DealerPost> dealerPosts = dealerListPageParser.extractPosts(fetchResult.getPage(), city);
      System.out.println(dealerPosts);
      System.out.println("===============================================================================\n");
    }
  }
}
