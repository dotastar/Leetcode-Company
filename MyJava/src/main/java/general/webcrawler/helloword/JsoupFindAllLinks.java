package general.webcrawler.helloword;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupFindAllLinks {

	public static void main(String[] args) throws IOException {
		List<Integer> a = new ArrayList<>();
		a.toArray(new Integer[0]);
		if (args.length == 1)
			print("usage: supply url to fetch");
		else
			args = new String[] { "https://www.google.com/about/careers/search#t=sq&q=j&d=software%2520engineer%2520new%2520grad&li=10&j=software%2520engineer%2520new%2520grad" };
		// https://www.google.com/#newwindow=1&q=software+engineer
		// https://www.facebook.com/careers/university
		String url = args[0];
		print("Fetching %s...", url);

		Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
		Elements links = doc.select("a[href]");
		Elements media = doc.select("[src]");
		Elements imports = doc.select("link[href]");

		print("\nMedia: (%d)", media.size());
		for (Element src : media) {
			if (src.tagName().equals("img"))
				print(" * %s: <%s> %sx%s (%s)", src.tagName(),
						src.attr("abs:src"), src.attr("width"),
						src.attr("height"), trim(src.attr("alt"), 20));
			else
				print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
		}

		print("\nImports: (%d)", imports.size()); // doc.select("link[href]");
		for (Element link : imports) {
			print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"), link.attr("rel"));
		}

		print("\nLinks: (%d)", links.size()); // doc.select("a[href]");
		for (Element link : links) {
			print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
		}
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
