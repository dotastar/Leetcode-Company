package general.webcrawler.helloword;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleResultsSraper {

	/**
	 * Google's URL is special, it needs to be constructed.
	 */
	public static void main(String[] args) throws IOException {
		// Original link
		// String url =
		// "https://www.google.com/search?as_q=&as_epq=%22Yorkshire+Capital%22+&as_oq=fraud+OR+allegations+OR+scam&as_eq=&as_nlo=&as_nhi=&lr=lang_en&cr=countryCA&as_qdr=all&as_sitesearch=&as_occt=any&safe=images&tbs=&as_filetype=&as_rights=";
		// Replace =%22Yorkshire+Capital%22+ with software engineer
		// String url =
		// "https://www.google.com/search?as_q=&as_epq=software engineer&as_eq=&as_nlo=&as_nhi=&lr=lang_en&cr=countryCA&as_qdr=all&as_sitesearch=&as_occt=any&safe=images&tbs=&as_filetype=&as_rights=";
		// Delete: &as_eq=&as_nlo=&as_nhi
		// String url =
		// "https://www.google.com/search?as_q=&as_epq=software engineer&lr=lang_en&cr=countryCA&as_qdr=all&as_sitesearch=&as_occt=any&safe=images&tbs=&as_filetype=&as_rights=";
		// Delete: &cr=countryCA
		// String url =
		// "https://www.google.com/search?as_q=&as_epq=software engineer&lr=lang_en&as_qdr=all&as_sitesearch=&as_occt=any&safe=images&tbs=&as_filetype=&as_rights=";
		// Delete:
		// &lr=lang_en&as_qdr=all&as_sitesearch=&as_occt=any&safe=images&tbs=&as_filetype=&as_rights=
		// String url =
		// "https://www.google.com/search?as_q=&as_epq=software engineer";
		// Replace as_q=&as_epq with q
		// String url = "https://www.google.com/search?q=software engineer";

		// String url ="https://www.google.com/search?q=software+engineer#newwindow=1&q=software+engineer";

		String url = "https://www.google.com/about/careers/search#q=software%2520engineer";
		// https://www.google.com/#newwindow=1&q=software+engineer
		// https://www.facebook.com/careers/university
		print("Fetching %s...", url);

		Document doc = Jsoup
				.connect(url)
				.userAgent("Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201")
				.get();
		// Elements links = doc.select("li[class=g]");
		Elements links = doc.select("a[href]");
		print("\nLinks: (%d)", links.size());
		for (Element link : links) {
			print(" * a: <%s>  (%s)", link.attr("abs:href"),
					trim(link.text(), 35));
		}
		String html = doc.toString();
		print("Software: %d, Engineer: %d", html.indexOf("software"),
				html.indexOf("engineer"));
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}
}
