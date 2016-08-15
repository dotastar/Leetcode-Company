package projects.crawler.subproject.autohome.crawl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import projects.crawler.annotation.Extraction;
import projects.crawler.data.model.City;
import projects.crawler.subproject.autohome.AutohomeModule;
import projects.crawler.subproject.autohome.model.BrandDetail;
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

/**
 * Parse both dealer list page and dealer info page
 */
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

  public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
  private static final DealerListSchema DEALER_LIST_SCHEMA = new DealerListSchema();
  private static final DealerInfoSchema DEALER_INFO_SCHEMA = new DealerInfoSchema();

  /**
   * Dealer list page
   * Ex. http://dealer.autohome.com.cn/beijing/0_0_0_0_1_0.html
   */
  @Data
  public static class DealerListSchema {
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

  /**
   * Dealer info page
   * Ex. http://dealer.autohome.com.cn/124046/?pvareaid=103706
   */
  @Data
  public static class DealerInfoSchema {
    private static String SELLING_BRAND_DIV = "#zypp .brandtree-cont dl";

    @Extraction
    private BiConsumer<Element, DealerPost> addBrandDetails = (postElem, post) -> {
      List<BrandDetail> brandDetailList = post.getBrandDetailList();
      Elements brands = postElem.select(SELLING_BRAND_DIV);
      if (brands.isEmpty()) {
        return;
      }
      // it clears previous value,
      brandDetailList.clear();
      // TODO: instead, consider compare previous value and add it to list if it doesn't exist
      for (Element brandDiv : brands) {
        BrandDetail detail = new BrandDetail();
        String name = Iterables.getOnlyElement(brandDiv.select("dt")).text();
        detail.setBrandDetailedName(name);
        brandDiv.select("dd").stream()
            .map(Element::text)
            .forEach(detail.getSellingModels()::add);
        brandDetailList.add(detail);
      }
    };
  }

  public List<DealerPost> parsePosts(Document page, City city) throws ParseException {
    List<DealerPost> res = new ArrayList<>();
    Elements postElements = DEALER_LIST_SCHEMA.getExtractPosts().apply(page.body());
    for (Element postElem : postElements) {
      DealerPost post = new DealerPost();
      post.setCity(city.getName());
      post.setProvince(city.getProvinceName());

      ReflectionUtil.extractAndApplyValues(post, postElem, DEALER_LIST_SCHEMA);

      res.add(post);
    }
    return res;
  }

  public boolean parseDealerInfo(Document page, DealerPost dealerInfo) {
    Preconditions.checkNotNull(dealerInfo);
    return ReflectionUtil.extractAndApplyValues(dealerInfo, page.body(), DEALER_INFO_SCHEMA);
  }

  public static boolean isEndPage(Document page) throws ParseException {
    DealerListSchema schema = new DealerListSchema();
    Elements postElements = schema.getExtractPosts().apply(page.body());
    return postElements.isEmpty();
  }

  @Test
  public void testExtractPosts() throws IOException, ParseException {
    String url = "http://dealer.autohome.com.cn/beijing/0_0_0_0_1.html";
    int timeout = 10_000;
    AutohomeParser parser = new AutohomeModule().getInstance(AutohomeParser.class);
    Document doc = Jsoup.connect(url).timeout(timeout).userAgent(USER_AGENT).get();
    List<DealerPost> posts = parser.parsePosts(doc, new City("直辖市", "北京", url));
    log.info("Extracted " + posts.size() + " posts");
    for (DealerPost post : posts) {
      log.debug("{}", post);
      assertEquals(url, post.getWhereAtUrl());
    }
    assertTrue(posts.size() > 0);
  }

  @Test
  public void testParseDealerInfo_SellingBrandDetails() throws IOException {
    String url = "http://dealer.autohome.com.cn/124046/?pvareaid=103706";
    int timeout = 10_000;
    Document doc = Jsoup.connect(url).timeout(timeout).userAgent(USER_AGENT).get();
    DealerPost dealerPost = new DealerPost();
    AutohomeParser parser = new AutohomeModule().getInstance(AutohomeParser.class);
    assertTrue(parser.parseDealerInfo(doc, dealerPost));

    // smart, 北京奔驰, 奔驰(进口), 奔驰CLS级AMG, 梅赛德斯-迈巴赫
    assertEquals(5, dealerPost.getBrandDetailList().size());

    // 奔驰C级 奔驰E级 奔驰GLA 奔驰GLC
    List<String> sellingModels = dealerPost.getBrandDetailList().get(1).getSellingModels();
    assertEquals("Actual:" + sellingModels, 4, sellingModels.size());
  }

  @Test
  public void testParseDealerInfo_SellingBrandDetails2() throws IOException {
    String url = "http://dealer.autohome.com.cn/66641/?pvareaid=103706";
    int timeout = 10_000;
    Document doc = Jsoup.connect(url).timeout(timeout).userAgent(USER_AGENT).get();
    DealerPost dealerPost = new DealerPost();
    AutohomeParser parser = new AutohomeModule().getInstance(AutohomeParser.class);
    assertTrue(parser.parseDealerInfo(doc, dealerPost));

    // 长安马自达
    assertEquals(1, dealerPost.getBrandDetailList().size());

    // 马自达2, 马自达3 Axela昂克赛, 马自达3星骋, 马自达CX-5
    List<String> sellingModels = dealerPost.getBrandDetailList().get(0).getSellingModels();
    assertEquals("Actual:" + sellingModels, 4, sellingModels.size());
  }

}
