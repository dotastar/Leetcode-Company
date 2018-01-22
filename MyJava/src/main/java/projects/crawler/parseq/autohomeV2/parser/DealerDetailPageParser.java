package projects.crawler.parseq.autohomeV2.parser;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import java.util.List;
import java.util.function.BiConsumer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import projects.crawler.annotation.Extraction;
import projects.crawler.taskframework.autohome.model.BrandDetail;
import projects.crawler.taskframework.autohome.model.DealerPost;
import projects.crawler.utils.ReflectionUtils;


public class DealerDetailPageParser {
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

  public boolean parseDealerInfo(Document page, DealerPost dealerInfo) {
    Preconditions.checkNotNull(dealerInfo);
    return ReflectionUtils.extractAndApplyValues(dealerInfo, page.body(), DealerDetailPageParser.class);
  }

}
