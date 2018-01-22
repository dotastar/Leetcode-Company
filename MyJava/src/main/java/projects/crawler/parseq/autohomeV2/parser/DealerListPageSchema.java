package projects.crawler.parseq.autohomeV2.parser;

import java.util.Date;
import java.util.function.BiConsumer;
import java.util.function.Function;
import lombok.Data;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import projects.crawler.annotation.Extraction;
import projects.crawler.parseq.autohomeV2.model.DealerPost;
import projects.crawler.parseq.engine.AbstractSchema;


@Data
public class DealerListPageSchema extends AbstractSchema {
  private static String POSTS = "div.dealer-list-wrap ul.info-wrap";
  private Function<Element, Elements> extractPosts = pageElem -> pageElem.select(POSTS);

  /**
   * Apply after extracting Posts!!
   */
  private static String TITLE = " .tit-row a";
  private static String PHONE = " .tel";
  private static String BRAND = " em";
  private static String PROMOTION = " li:last-child a";
  private static String ADDRESS = " .info-addr";
  private static String DEALER_TYPE = " .tit-row > span:nth-child(2)";

  /**
   * Parse fields
   */
  @Extraction
  private BiConsumer<Element, DealerPost> setTitle = (postElem, post) -> post.setTitle(getOnlyElementText(postElem, TITLE));
  @Extraction
  private BiConsumer<Element, DealerPost> setPhone = (postElem, post) -> post.setPhone(getOnlyElementText(postElem, PHONE));
  @Extraction
  private BiConsumer<Element, DealerPost> setDealerType = (postElem, post) -> post.setDealerType(getOnlyElementText(postElem, DEALER_TYPE));
  @Extraction
  private BiConsumer<Element, DealerPost> setAddress = (postElem, post) -> post.setAddress(getOnlyElementText(postElem, ADDRESS));
  @Extraction
  private BiConsumer<Element, DealerPost> setBrand = (postElem, post) -> post.setBrand(getOnlyElementText(postElem, BRAND));
  @Extraction
  private BiConsumer<Element, DealerPost> setPromotion = (postElem, post) -> post.setPromotion(getOnlyElementText(postElem, PROMOTION));
  /**
   * Parse meta data
   */
  @Extraction
  private BiConsumer<Element, DealerPost> setWhereAtUrl = (postElem, post) -> post.setWhereAtUrl(postElem.ownerDocument().location());
  @Extraction
  private BiConsumer<Element, DealerPost> setLinkToUrl = (postElem, post) -> post.setLinkToUrl(getOnlyElementHref(postElem, TITLE));
  @Extraction
  private BiConsumer<Element, DealerPost> setCrawlDate = (postElem, post) -> post.setCrawlDate(new Date());
}
