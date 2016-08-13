package projects.crawler.subproject.autohome;

import com.google.common.collect.Iterables;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import projects.crawler.subproject.autohome.model.DealerPost;
import projects.crawler.utils.Exporter;
import projects.crawler.utils.ReflectionUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
public class AutohomeParser {

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

    // linkTo
    // whereAt

    private Function<Element, Elements> extractPosts = pageElem -> pageElem.select(POSTS);

    private Function<Element, String> extractTitle = postElem -> Iterables.getOnlyElement(postElem.select(TITLE)).text();
    private Function<Element, String> extractPhone = postElem -> Iterables.getOnlyElement(postElem.select(PHONE)).text();
    private Function<Element, String> extractAddress = postElem -> Iterables.getOnlyElement(postElem.select(PHONE)).parent().parent().nextElementSibling()
        .ownText();
    private Function<Element, String> extractBrand = postElem -> Iterables.getOnlyElement(postElem.select(BRAND)).nextElementSibling().ownText();
    private Function<Element, String> extractPromotion = postElem -> {
      Element element = Iterables.getOnlyElement(postElem.select(PROMOTION), null);
      return element == null ? "" : element.ownText();
    };

    @SuppressWarnings("unchecked")
    private Function<Element, String> getExtractionFunction(String fieldName) {
      Method method = ReflectionUtil.getGetter(ParsingSchema.class, fieldName);
      try {
        return (Function<Element, String>) method.invoke(this);
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
        throw new IllegalArgumentException(e);
      }
    }

    private void setValue(DealerPost post, String fieldName, Element elem) {
      try {
        Method method = ReflectionUtil.getSetter(DealerPost.class, fieldName);
        String value = getExtractionFunction(fieldName).apply(elem);
        method.invoke(post, value);
      } catch (Exception e) {
        log.error("Parse {} for Post({}) out of Element({}) error\n{}", fieldName, post, elem, e.getCause());
      }
    }

  }

  public List<DealerPost> parsePosts(Document page, String city) throws ParseException {
    List<DealerPost> res = new ArrayList<>();
    ParsingSchema schema = new ParsingSchema();
    Elements postElements = schema.getExtractPosts().apply(page.body());
    for (Element postElem : postElements) {
      DealerPost post = new DealerPost();
      post.setCity(city);
      post.setCrawlDate(new Date());
      schema.setValue(post, "title", postElem);
      schema.setValue(post, "phone", postElem);
      schema.setValue(post, "address", postElem);
      schema.setValue(post, "promotion", postElem);
      schema.setValue(post, "brand", postElem);
      post.setWhereAtUrl(page.location());
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
    String url = "http://dealer.autohome.com.cn/beijing/";
    int timeout = 10_000;
    Document doc = Jsoup.connect(url).timeout(timeout).userAgent(USER_AGENT).get();
    List<DealerPost> posts = parsePosts(doc, "Beijing");
    System.out.println("Extracted " + posts.size() + " job posts");
    for (DealerPost post : posts) {
      System.out.println(post);
      assertEquals(url, post.getWhereAtUrl());
    }
    assertTrue(posts.size() > 0);
  }
}
