package projects.crawler.parseq.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import projects.crawler.network.SimpleHttpClient;


@Slf4j
@Data
public class PageIterator implements Iterator<Optional<Document>> {
  private static final int MAX_NO_RESULT_PAGE_COUNT = 2;
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

  public List<Document> crawl() {
    List<Document> pages = new ArrayList<>();
    while (hasNext()) {
      Optional<Document> optionalPage = next();
      if (!optionalPage.isPresent()) {
        continue;
      }
      Document page = optionalPage.get();

      pages.add(page);
    }
    return pages;
  }

  @Override
  public boolean hasNext() {
    return noResultPageCnt < MAX_NO_RESULT_PAGE_COUNT && failureCnt < MAX_FAILURE_COUNT;
  }

  @Override
  public Optional<Document> next() {
    ++currentIndex;
    String url = buildUrl(currentIndex);
    Optional<Document> optionalPage = SimpleHttpClient.get(url);

    if (!optionalPage.isPresent()) {
      log.warn("Failed to crawl page {}", url);
      failureCnt++;
    } else if (hasResult(optionalPage.get())) {
      log.info("Crawled page {} successfully.", url);
    } else { // isPresent && !hasResult
      log.info("Crawled page {}, reached NoResultPage!", url);
      noResultPageCnt++;
    }
    return optionalPage;
  }

  /**
   * Use css selector to find element(s) that can identify this page as a "no result" page
   */
  public boolean hasResult(Document document) {
    Elements noResultElems = document.select(noResultElemSelector);
    return noResultElems.isEmpty();
  }

  private String buildUrl(int i) {
    return baseUrl + "?" + queryParameterIndexName + i;
  }
}
