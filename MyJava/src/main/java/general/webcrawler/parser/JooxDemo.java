package general.webcrawler.parser;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import org.joox.Match;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import static org.joox.JOOX.$;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class JooxDemo {
  public static void main(String[] args)
      throws IOException, SAXException {
    // A jQuery-like java libarary that support convert a jQuery select query to xpath(),
    // However it handles html format very poorly, give up it.
    // Github page can’t be compiled to document since it has a too strong constraint on html format.
    URL resource = URI.create("https://github.com/humpydonkey").toURL();
    assertNotNull(resource);
    // Parse the document from a file
    Document document = $(resource).document();

    // Wrap the document with the jOOX API
    Match x1 = $(document);
    System.out.println(x1.xpath());

    Match x2 = $(document).find("[itemprop=email]");
    System.out.println(x2.xpath());
    List<Element> elements = x2.get();
    assertEquals(1, elements.size());
    System.out.println(elements.get(0).getTextContent());
  }

}
