package projects.crawler.parseq.engine;

import interview.AutoTestUtils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.jsoup.nodes.Document;
import org.junit.Test;
import projects.crawler.data.model.FetchResult;
import projects.crawler.data.model.RawPage;
import projects.crawler.network.SimpleHttpClient;
import projects.crawler.parseq.autohomeV2.AutohomeV2Module;
import projects.crawler.utils.RawPageUtils;

import static com.google.common.truth.Truth.assertThat;
import static projects.crawler.data.model.FetchResult.Result.FAILED;
import static projects.crawler.data.model.FetchResult.Result.NO_RESULT;
import static projects.crawler.data.model.FetchResult.Result.SUCCESS;


/**
 * Refactor this class to a more general page fetcher
 *
 */
@Slf4j
public class PageFetcher {

  /**
   * Fetch pages, including both success and failed fetches, but filter out NO_RESULT fetches.
   */
  public List<FetchResult> fetch(PageIterator iterator) {
    List<FetchResult> pages = iterator.crawl()
        .stream()
        .filter(res -> res.getResult() != NO_RESULT)
        .collect(Collectors.toList());

    long successCount = pages.stream().filter(res -> res.getResult() == SUCCESS).count();
    long failCount = pages.stream().filter(res -> res.getResult() == FAILED).count();
    log.info("Finished fetching pages for baseUrl ({}), stopped at page {}, fetched {} pages successfully, failed fetching {} pages.\n",
        iterator.getBaseUrl(), iterator.getCurrentIndex(), successCount, failCount);
    return pages;
  }

  public FetchResult fetchPage(String url) {
    Optional<Document> page = SimpleHttpClient.get(url);
    return page.map(document -> FetchResult.success(url, document)).orElseGet(() -> FetchResult.failed(url));
  }

  public List<Document> fetchPages(PageIterator iterator) {
    return fetch(iterator).stream()
        .filter(res -> res.getResult() == FetchResult.Result.SUCCESS)
        .map(FetchResult::getPage)
        .collect(Collectors.toList());
  }

  public static class UnitTest {
    public static void main(String[] args) {
      AutoTestUtils.runTestClassAndPrint(UnitTest.class);
    }

    private AutohomeV2Module autohomeV2Module = new AutohomeV2Module();
    private RawPage.Dao rawPageDao = autohomeV2Module.getInstance(RawPage.Dao.class);
    private FetchResult.Dao fetchResultDao = autohomeV2Module.getInstance(FetchResult.Dao.class);

    @Test
    public void testSerializeDeserialize() {
      String url = "https://dealer.autohome.com.cn/beijing?pageIndex=2";
      Optional<Document> optionalDoc = SimpleHttpClient.get(url);
      assertThat(optionalDoc.isPresent()).isTrue();
      Document doc = optionalDoc.get();
      RawPage rawPage = RawPageUtils.from(doc);
//      RawPage rawPage = new RawPage("html",  "fromUrl");
      ObjectId savedId = rawPageDao.insert(rawPage).getSavedId();
      rawPage = rawPageDao.findById(savedId);
      System.out.println(rawPage);
    }

    @Test
    public void testJsonIgnore() {
      String url = "https://www.url.com";
      FetchResult fetchResult = FetchResult.success(url, Document.createShell(url));
      ObjectId savedId =fetchResultDao.insert(fetchResult).getSavedId();
      fetchResult = fetchResultDao.findById(savedId);
      System.out.println(fetchResult);
    }
  }

}
