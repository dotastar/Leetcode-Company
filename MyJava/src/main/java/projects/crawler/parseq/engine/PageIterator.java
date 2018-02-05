package projects.crawler.parseq.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import projects.crawler.data.model.FetchResult;
import projects.crawler.network.SimpleHttpClient;


@Slf4j
@Data
public class PageIterator implements Iterator<FetchResult> {
  private static final int MAX_NO_RESULT_PAGE_COUNT = 1;
  private static final int MAX_FAILURE_COUNT = 3;

  // e.g. https://dealer.autohome.com.cn/beijing?pageIndex=1
  private final String baseUrl;
  private final String queryParameterIndexName;
  private final String noResultElemSelector;

  private int currentIndex;
  private int noResultPageCnt;
  private int failureCnt;

  public PageIterator(String baseUrl, String queryParameterIndexName, String noResultSelector) {
    this.baseUrl = baseUrl;
    this.queryParameterIndexName = queryParameterIndexName;
    this.noResultElemSelector = noResultSelector;
    this.currentIndex = 0;
    this.noResultPageCnt = 0;
  }

  /**
   * Optional not present indicates a fetching failed request
   */
  public List<FetchResult> crawl() {
    List<FetchResult> pages = new ArrayList<>();
    while (hasNext()) {
      FetchResult optionalPage = next();
      pages.add(optionalPage);
    }
    return pages;
  }

  @Override
  public boolean hasNext() {
    return noResultPageCnt < MAX_NO_RESULT_PAGE_COUNT && failureCnt < MAX_FAILURE_COUNT;
  }

  @Override
  public FetchResult next() {
    ++currentIndex;
    String url = buildUrl(currentIndex);
    Optional<Document> optionalPage = SimpleHttpClient.get(url);
    FetchResult result;
    if (!optionalPage.isPresent()) {
      log.warn("Failed to crawl page {}", url);
      failureCnt++;
      result = FetchResult.failed(url);
    } else if (hasResult(optionalPage.get())) {
      log.info("Crawled page {} successfully.", url);
      result = FetchResult.success(url, optionalPage.get());
    } else { // isPresent && !hasResult
      log.info("Crawled page {}, reached NoResultPage!", url);
      noResultPageCnt++;
      result = FetchResult.noResult(url, optionalPage.get());
    }
    return result;
  }

  /**
   * Use css selector to find element(s) that can identify this page as a "no result" page
   */
  public boolean hasResult(Document document) {
    Elements noResultElems = document.select(noResultElemSelector);
    return noResultElems.isEmpty();
  }

  protected String buildUrl(int i) {
    return baseUrl + "?" + queryParameterIndexName + i;
  }
}
