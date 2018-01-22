package projects.crawler.parseq.autohomeV2.parser;

import interview.AutoTestUtils;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import projects.crawler.data.model.City;
import projects.crawler.parseq.autohomeV2.model.DealerPost;
import projects.crawler.network.SimpleHttpClient;
import projects.crawler.utils.ReflectionUtils;

import static com.google.common.truth.Truth.assertThat;


@Slf4j
@Data
public class DealerListPageParser {

  public List<DealerPost> parsePosts(Document page, City city) {
    List<DealerPost> res = new ArrayList<>();
    DealerListPageSchema schema = new DealerListPageSchema();
    Elements postElements = schema.getExtractPosts().apply(page.body());
    for (Element postElem : postElements) {
      DealerPost post = parsePost(postElem);
      post.setCity(city.getName());
      post.setProvince(city.getProvinceName());
      res.add(post);
    }
    return res;
  }

  private DealerPost parsePost(Element postElem) {
    DealerPost post = new DealerPost();
    ReflectionUtils.extractAndApplyValues(post, postElem, DealerListPageSchema.class);
    return post;
  }

  public static class UnitTest {
    public static void main(String[] args) {
      AutoTestUtils.runTestClassAndPrint(UnitTest.class);
    }

    @Test
    public void testExtractPosts() throws IOException, ParseException {
      String url = "https://dealer.autohome.com.cn/beijing?pageIndex=1";
      Optional<Document> optionalDoc = SimpleHttpClient.get(url);
      assertThat(optionalDoc.isPresent()).isTrue();
      Document doc = optionalDoc.get();
      log.debug("{}", doc.html());
      DealerListPageParser parser = new DealerListPageParser();
      List<DealerPost> posts = parser.parsePosts(doc, new City("北京", "直辖市", url));
      assertThat(posts).isNotEmpty();
      log.info("Extracted " + posts.size() + " posts");
      for (DealerPost post : posts) {
        log.debug("{}", post);
        assertThat(post.getWhereAtUrl()).isEqualTo(url);
      }
    }
  }
}
