package projects.crawler.parseq.engine;

import java.util.Iterator;
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

  private CrawlEngine engine;

  public PageIterator(String baseUrl, String queryParameterIndexName, String noResultSelector) {
    this.baseUrl = baseUrl;
    this.queryParameterIndexName = queryParameterIndexName;
    this.noResultElemSelector = noResultSelector;
    this.currentIndex = 0;
    this.noResultPageCnt = 0;
  }

  @Override
  public boolean hasNext() {
    return noResultPageCnt < MAX_NO_RESULT_PAGE_COUNT && failureCnt < MAX_FAILURE_COUNT;
  }

  @Override
  public Optional<Document> next() {
    Optional<Document> optionalPage = getByPageIndex(++currentIndex);
    if (!optionalPage.isPresent()) {
      failureCnt++;
    } else if (isNoResultPage(optionalPage.get())) {
      noResultPageCnt++;
    }
    return optionalPage;
  }

  private boolean isNoResultPage(Document document) {
    Elements noResultDivs = document.select(noResultElemSelector);
    return !noResultDivs.isEmpty();
  }

  private Optional<Document> getByPageIndex(int i) {
    String url = baseUrl + "?" + queryParameterIndexName + i;
    return SimpleHttpClient.get(url);
  }
}
