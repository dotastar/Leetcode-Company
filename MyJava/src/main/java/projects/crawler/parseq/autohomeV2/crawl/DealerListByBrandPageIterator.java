package projects.crawler.parseq.autohomeV2.crawl;

import static projects.crawler.parseq.autohomeV2.parser.AutoBrandIdParser.QUERY_PARAMETER_KEY_BRAND_ID;


public class DealerListByBrandPageIterator extends DealerListPageIterator {

  private final String brandIdQueryParameter;

  public DealerListByBrandPageIterator(String baseUrl, int brandId) {
    super(baseUrl);
    this.brandIdQueryParameter = QUERY_PARAMETER_KEY_BRAND_ID + brandId;
  }

  @Override
  public String buildUrl(int i) {
    return super.buildUrl(i) + "&" + brandIdQueryParameter;
  }
}