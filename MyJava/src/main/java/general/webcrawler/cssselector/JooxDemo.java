package general.webcrawler.cssselector;

import java.io.IOException;
import java.util.List;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.joox.Match;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import static org.joox.JOOX.$;
import static org.junit.Assert.assertEquals;


public class JooxDemo {
  public static void main(String[] args)
      throws IOException, SAXException {
    // A jQuery-like java library that support convert a jQuery select query to xpath(),
    // However it handles html format very poorly, since it has a too strong constraint on html format.
    String url = "https://github.com/humpydonkey";

    // Add HtmlCleaner to clean the raw Html
    HtmlCleaner cleaner = new HtmlCleaner();
    TagNode root = cleaner.clean(Jsoup.connect(url).get().body().html());
    String cleanedHtml = "<" + root.getName() + ">" + cleaner.getInnerHtml(root) + "</" + root.getName() + ">";
    System.out.println(cleanedHtml);

    // Getting xPath
    Document document = $(cleanedHtml).document();
    Match query = $(document).find("[itemprop=email]");
    System.out.println(query.xpath());
    List<Element> elements = query.get();
    assertEquals(1, elements.size());
    System.out.println(elements.get(0).getTextContent().trim());
    // XPath output: /html[1]/body[1]/div[4]/div[1]/div[1]/div[1]/div[1]/ul[1]/li[3]
  }

}
