package projects.crawler.parseq.autohomeV2.parser;

import interview.AutoTestUtils;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import projects.crawler.network.SimpleHttpClient;

import static com.google.common.truth.Truth.assertThat;


@Slf4j
@Data
public class AutoBrandIdParser {

  private static final String BRAND_ITEM = ".data-brand-item.row-hide .item";
  public static final String QUERY_PARAMETER_KEY_BRAND_ID = "brandId=";
  private static final int ALL_BRANDS_ID = 0;

  /**
   * Extract a list of urls that contains BrandId
   * E.g. an extracted url:
   * /beijing?countyId=0&brandId=173&seriesId=0&factoryId=0&pageIndex=1&kindId=0&orderType=0&isSales=0#pvareaid=2113614
   */
  public List<Integer> extractBrandIds(Document page) {
    List<String> urls = extractBrandIdUrl(page);
    return extractBrandIds(urls);
  }

  private List<String> extractBrandIdUrl(Document page) {
    Elements items = page.select(BRAND_ITEM);
    // <a class="item" href="/beijing?countyId=0&amp;brandId=9&amp;seriesId=0&amp;factoryId=0&amp;pageIndex=1&amp;kindId=0&amp;orderType=0&amp;isSales=0#pvareaid=2113614">克莱斯勒</a>
    List<String> urls = items.stream().map(ele -> ele.attr("href")).distinct().collect(Collectors.toList());
    log.info("Found {} brand items (html), extracted {} urls", items.size(), urls.size());
    return urls;
  }

  private List<Integer> extractBrandIds(List<String> urls) {
    return urls.stream()
        .map(AutoBrandIdParser::extractBrandId)
        .distinct()
        .filter(Objects::nonNull)
        .filter(num -> num != ALL_BRANDS_ID)
        .collect(Collectors.toList());
  }

  private static Integer extractBrandId(String url) {
    int start = url.indexOf(QUERY_PARAMETER_KEY_BRAND_ID) + QUERY_PARAMETER_KEY_BRAND_ID.length();
    int end = url.indexOf('&', start);
    if (end == -1) {
      end = url.length();
    }
    try {
      return Integer.parseInt(url.substring(start, end));
    } catch (Exception e) {
      log.error("Failed to extractBrandId from {}", url, e);
      return null;
    }
  }

  public static class UnitTest {
    public static void main(String[] args) {
      AutoTestUtils.runTestClassAndPrint(UnitTest.class);
    }

    @Test
    public void extractBrandIds() {
      String url = "https://dealer.autohome.com.cn/beijing?pageIndex=1";
      Optional<Document> optionalDoc = SimpleHttpClient.get(url);
      assertThat(optionalDoc.isPresent()).isTrue();
      Document doc = optionalDoc.get();
      log.debug("{}", doc.html());
      AutoBrandIdParser parser = new AutoBrandIdParser();
      List<Integer> brandIds = parser.extractBrandIds(doc);
      brandIds.sort(Comparator.comparingInt(Integer::intValue));
      assertThat(brandIds).isNotEmpty();
      final int[] expectedBrands = { 173, 22, 94, 20, 114, 283, 60, 112 };
      for (int expectedBrandId : expectedBrands) {
        assertThat(brandIds).contains(expectedBrandId);
      }
      assertThat(brandIds).doesNotContain(ALL_BRANDS_ID);
    }
  }
}
