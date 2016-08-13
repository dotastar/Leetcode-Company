package projects.crawler.subproject.autohome;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import projects.crawler.annotation.Extraction;
import projects.crawler.data.City;
import projects.crawler.subproject.autohome.model.DealerPost;
import projects.crawler.utils.Exporter;
import projects.crawler.utils.ReflectionUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
public class AutohomeParser {

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
//    AutoTestUtils.runTestClassAndPrint(AutohomeParser.class);
    Exporter exporter = new Exporter();
    DealerPost.Dao dao = new AutohomeModule().getInstance(DealerPost.Dao.class);
    String[] fields = { "title", "brand", "phone", "address", "city", "promotion" };
    exporter.exportToCsv("beijing.csv", dao, fields);
  }

  protected static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";

  @Data
  public static class ParsingSchema {
    private static String POSTS = "div.dealer-cont";
    // After extracting Posts
    private static String TITLE = " .dealer-cont-title > a:nth-child(2)";
    private static String PHONE = " .dealer-api-phone";
    private static String BRAND = " .telphone";
    private static String PROMOTION = ".pos-relative > a:nth-child(2)";

    private Function<Element, Elements> extractPosts = pageElem -> pageElem.select(POSTS);

    @Extraction
    private BiConsumer<Element, DealerPost> setTitle = (postElem, post) -> post.setTitle(getOnlyElement(postElem.select(TITLE)).text());
    @Extraction
    private BiConsumer<Element, DealerPost> setPhone = (postElem, post) -> post.setPhone(getOnlyElement(postElem.select(PHONE)).text());
    @Extraction
    private BiConsumer<Element, DealerPost> setAddress = (postElem, post) -> post.setAddress(
        getOnlyElement(postElem.select(PHONE)).parent().parent().nextElementSibling().ownText());
    @Extraction
    private BiConsumer<Element, DealerPost> setBrand = (postElem, post) -> post.setBrand(
        getOnlyElement(postElem.select(BRAND)).nextElementSibling().ownText());
    @Extraction
    private BiConsumer<Element, DealerPost> setPromotion = (postElem, post) -> {
      Element element = getOnlyElement(postElem.select(PROMOTION), null);
      if (element == null) {
        log.debug("No promotion found in Element {} for Post {}", postElem, post);
        return;
      }
      post.setPromotion(element.ownText());
    };

    @Extraction
    private BiConsumer<Element, DealerPost> setWhereAtUrl = (postElem, post) -> post.setWhereAtUrl(postElem.ownerDocument().location());
    @Extraction
    private BiConsumer<Element, DealerPost> setLinkToUrl = (postElem, post) -> post.setLinkToUrl(getOnlyElement(postElem.select(TITLE)).absUrl("href"));
    @Extraction
    private BiConsumer<Element, DealerPost> setCrawlDate = (postElem, post) -> post.setCrawlDate(new Date());

  }

  public List<DealerPost> parsePosts(Document page, City city) throws ParseException {
    List<DealerPost> res = new ArrayList<>();
    ParsingSchema schema = new ParsingSchema();
    Elements postElements = schema.getExtractPosts().apply(page.body());
    for (Element postElem : postElements) {
      DealerPost post = new DealerPost();
      post.setCity(city.getName());
      post.setProvince(city.getProvinceName());

      ReflectionUtil.extractAndApplyValues(post, postElem, schema);

      res.add(post);
    }
    return res;
  }

  public static boolean isEndPage(Document page) throws ParseException {
    ParsingSchema schema = new ParsingSchema();
    Elements postElements = schema.getExtractPosts().apply(page.body());
    return postElements.isEmpty();
  }

  @Test
  public void testExtractPosts() throws IOException, ParseException {
    String url = "http://dealer.autohome.com.cn/beijing/0_0_0_0_1.html";
    int timeout = 10_000;
    Document doc = Jsoup.connect(url).timeout(timeout).userAgent(USER_AGENT).get();
    List<DealerPost> posts = parsePosts(doc, new City("直辖市", "北京", url));
    System.out.println("Extracted " + posts.size() + " posts");
    for (DealerPost post : posts) {
      System.out.println(post);
      assertEquals(url, post.getWhereAtUrl());
    }
    assertTrue(posts.size() > 0);
  }
}
