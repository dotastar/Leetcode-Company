package projects.crawler.parseq.autohomeV2.parser;

import interview.AutoTestUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import projects.crawler.data.model.City;
import projects.crawler.network.SimpleHttpClient;
import projects.crawler.parseq.autohomeV2.model.DealerPost;
import projects.crawler.utils.ReflectionUtils;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.truth.Truth.assertThat;


@Slf4j
@Data
public class DealerListPageParser {

  public List<DealerPost> extractPosts(Document page, City city) {
    List<DealerPost> res = new ArrayList<>();
    DealerListPageSchema schema = new DealerListPageSchema();
    Elements postElements = schema.getExtractPosts().apply(page.body());
    for (Element postElem : postElements) {
      DealerPost post = extractPost(postElem);
      post.setCity(city.getName());
      post.setProvince(city.getProvinceName());
      res.add(post);
    }
    log.debug("Extracted {} DealerPosts from {} for {}", res.size(), page.location(), city);
    return res;
  }

  public List<DealerPost> extractPosts(List<Document> pages, Map<String, City> cityMap) {
    List<DealerPost> dealers = pages.parallelStream()
        .map(page -> extractPosts(page, cityMap))
        .flatMap(List::stream)
        .collect(Collectors.toList());
    log.info("Extracted {} DealerPosts from {} pages for {}", dealers.size(), pages.size(), cityMap.values());
    return dealers;
  }

  public List<DealerPost> extractPosts(Document page, Map<String, City> cityMap) {
    String url = checkNotNull(page.location());
    String cityName = extractCityNameFromUrl(url);
    City city = cityMap.get(cityName);
    if (city == null) {
      log.error("Failed to extract page {}: city {} is missing in map {}", url, cityName, cityMap);
      return Collections.emptyList();
    }
    return extractPosts(page, city);
  }

  private DealerPost extractPost(Element postElem) {
    DealerPost post = new DealerPost();
    ReflectionUtils.extractAndApplyValues(post, postElem, DealerListPageSchema.class);
    return post;
  }

  public static String extractCityNameFromUrl(String url) {
    final String beginAnchor = "com.cn/";
    final String endAnchor = "?";
    int start = url.indexOf(beginAnchor) + beginAnchor.length();
    int end = url.indexOf(endAnchor, start);
    if (end < 0) {
      end = url.length();
    }
    return url.substring(start, end);
  }

  public static class UnitTest {
    public static void main(String[] args) {
      AutoTestUtils.runTestClassAndPrint(UnitTest.class);
    }

    @Test
    public void testExtractPosts() {
      String url = "https://dealer.autohome.com.cn/beijing?pageIndex=1";
      Optional<Document> optionalDoc = SimpleHttpClient.get(url);
      assertThat(optionalDoc.isPresent()).isTrue();
      Document doc = optionalDoc.get();
      log.debug("{}", doc.html());
      DealerListPageParser parser = new DealerListPageParser();
      List<DealerPost> posts = parser.extractPosts(doc, new City("北京", "直辖市", url));
      assertThat(posts).isNotEmpty();
      log.info("Extracted " + posts.size() + " posts");
      for (DealerPost post : posts) {
        log.debug("{}", post);
        assertThat(post.getWhereAtUrl()).isEqualTo(url);
      }
    }
  }
}
