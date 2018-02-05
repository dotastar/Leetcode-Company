package projects.crawler.utils;

import lombok.NonNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import projects.crawler.data.model.RawPage;


public class RawPageUtils {

  public static RawPage from(Document parsedPage) {
    RawPage rawPage = new RawPage(parsedPage.location(), parsedPage.html());
    rawPage.setParsedPage(parsedPage);
    return rawPage;
  }

//  public static RawPage from(FetchResult result) {
//    Preconditions.checkArgument(result.getResult() == FetchResult.Result.SUCCESS);
//
//
//    return rawPage;
//  }

  public static Document parseDoc(@NonNull String html, @NonNull String url) {
    return Jsoup.parse(html, url);
  }
}
