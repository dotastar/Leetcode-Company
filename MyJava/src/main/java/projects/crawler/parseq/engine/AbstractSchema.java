package projects.crawler.parseq.engine;

import com.google.common.base.Verify;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

import static com.google.common.collect.Iterables.getOnlyElement;


public class AbstractSchema {

  public static String getOnlyElementText(Element postElem, String selector) {
    Element onlyElement = getOnlyElement(postElem.select(selector), null);
    return onlyElement == null ? StringUtils.EMPTY : onlyElement.text();
  }

  public static String getOnlyElementHref(Element postElem, String selector) {
    Element onlyElement = getOnlyElement(postElem.select(selector), null);
    if (onlyElement == null) {
      return StringUtils.EMPTY;
    }
    Verify.verify(onlyElement.hasAttr("href"));
    return onlyElement.absUrl("href");
  }

}
