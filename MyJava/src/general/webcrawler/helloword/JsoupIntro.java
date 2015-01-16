package general.webcrawler.helloword;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class JsoupIntro {

	public static void main(String[] args) {
		parseHTML();
		System.out.println("======================================");
		loadDocFromURL();
		System.out.println("======================================");
		extractLinks();
		System.out.println("======================================");
		selector();
		System.out.println("======================================");
		cleanUntrustedHTML();
	}

	public static void parseHTML() {
		String html = "<html><head><title>First parse</title></head>"
				+ "<body><p>Parsed HTML into a doc.</p></body></html>";
		Document doc = Jsoup.parse(html);
		System.out.println("Doc text: " + doc.text() + "\n");

		System.out.println("Iterate all elements:");
		for (Element ele : doc.getAllElements()) {
			System.out.println(ele.nodeName() + "\t" + ele.tagName() + "\t"
					+ ele.ownText());
		}
	}

	public static void loadDocFromURL() {
		try {
			Connection conn = Jsoup.connect("http://www.google.com/");
			Document doc = conn.userAgent("Mozilla").timeout(3000).get();
			Response res = conn.response();
			System.out.println(res.headers().toString());
			System.out.println(res.contentType());
			String title = doc.title();
			System.out.println(title);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void extractLinks() {
		Document doc;
		try {
			doc = Jsoup.connect("http://www.google.com/").get();
			Elements links = doc.getElementsByTag("a");
			for (Element link : links) {
				// Use the abs: attribute prefix to get an absolute URL from an attribute:
				String linkHref = link.attr("abs:href");
				String linkText = link.text();
				System.out.println(linkText + ": " + linkHref);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * selector-syntax
	 */
	public static void selector() {
		Document doc;
		try {
			doc = Jsoup.connect("http://www.google.com/").get();
			// finds links (a tags with href attributes)
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String linkHref = link.attr("href");
				String linkText = link.text();
				System.out.println(linkText + ": " + linkHref);
			}
			System.out.println();

			// img with src ending .png
			Elements pngs = doc.select("img[src$=.png]");
			System.out.println("png images: " + pngs.size());

			// div with class=masthead
			Element masthead = doc.select("div.masthead").first();
			System.out.println("first masthead div: " + masthead);

			// direct a after h3
			Elements resultLinks = doc.select("h3.r > a");
			System.out.println("resultLinks: " + resultLinks.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Sanitize untrusted HTML (to prevent XSS)
	 */
	public static void cleanUntrustedHTML(){
		String unsafe = "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>";
		String safe = Jsoup.clean(unsafe, Whitelist.basic());
		System.out.println(safe);
	}
}
