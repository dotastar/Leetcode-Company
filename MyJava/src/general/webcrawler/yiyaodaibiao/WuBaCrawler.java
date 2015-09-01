package general.webcrawler.yiyaodaibiao;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class WuBaCrawler {

	protected static final String CRAWLER_NAME = WuBaCrawler.class.getSimpleName();
	protected static final String[] KEY_WORDS = { };
	protected static final long WAIT_TIME = 100; // ms
	protected static final int TIMEOUT = 10_000; // ms

	protected static final int MAX_PAGE = 141;
	protected static final String ROOT_URL = "http://gz.58.com/yiyaodaibiao/pn#/";
	protected static final String HTML_BEGIN = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\"> <html> <head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> <meta http-equiv=\"Content-Style-Type\" content=\"text/css\"> <link rel=\"stylesheet\" type=\"text/css\" href=\"data/cache/style_7_common.css?K6D\"> <link rel=\"stylesheet\" type=\"text/css\" href=\"data/cache/style_7_forum_forumdisplay.css?K6D\">   <script src=\"data/cache/common.js?K6D\" type=\"text/javascript\"></script>  <link href=\"template/week_steady/images//js/portal.css\" rel=\"stylesheet\" type=\"text/css\"> <link rel=\"stylesheet\" id=\"css_widthauto\" type=\"text/css\" href=\"data/cache/style_7_widthauto?K6D\">  <script src=\"data/cache/common.js?K6D\" type=\"text/javascript\"></script>  <script type=\"text/javascript\">HTMLNODE.className += ' widthauto'</script>  <script src=\"data/cache/forum.js?K6D\" type=\"text/javascript\"></script>  <script src=\"data/cache/portal.js?K6D\" type=\"text/javascript\"></script>  <title> Crawling Information </title> </head> <body id=\"nv_forum\" class=\"pg_forumdisplay\" onkeydown=\"if(event.keyCode==27) return false;\">  <table>";

	protected static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
	protected static final String HTML_END = "</table> </body> </html>";
	protected static final String HTML_CHECKBOX = "<td> <input type=\"checkbox\" name=\"read\" value=\"false\"> </td>";

	public static void main(String[] args) {
		WuBaCrawler c = new WuBaCrawler();
		c.run();
	}

	public void run() {
		crawlPostsToFile(ROOT_URL);
	}

	public void crawlPostsToFile(String root) {
		DateFormat dateFormat = new SimpleDateFormat("HH-mm-MM-dd-yyyy");
		String fileName = "/Users/yazhoucao/Downloads/58同城－CrawlingResults-"
				+ dateFormat.format(Calendar.getInstance().getTime()) + ".html";
		File file = new File(fileName);
		int pageIdx = 1;
		try (BufferedWriter wr = new BufferedWriter(new FileWriter(file))) {
			wr.write(HTML_BEGIN);
			int postId = 1;
			pageIdx = 1;
			Response res = null;
			do {
				String currUrl = root.replace("#", String.valueOf(pageIdx));
				res = Jsoup.connect(currUrl).timeout(TIMEOUT).userAgent(USER_AGENT)
						.execute();
				if (res.statusCode() == 200) {
					Document page = res.parse();
					postId += appendPosts(page, wr, postId, KEY_WORDS);
					printCurrentProgress(pageIdx, postId);

					if (WAIT_TIME > 0)
						Thread.sleep(WAIT_TIME);
					pageIdx++;
				} else
					System.err.println(res.statusCode() + "-" + res.statusMessage());
			} while (res != null && res.statusCode() == 200 && pageIdx <= MAX_PAGE);

		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		} finally {
			appendCloseTag(file);
			System.out.println(CRAWLER_NAME + " ended at page " + pageIdx);
		}
		System.out.println(CRAWLER_NAME + " end crawling!");
	}

	/**
	 * Append posts that contains the keyword to the HTML file
	 */
	protected int appendPosts(Document page, BufferedWriter wr, int postId, String[] keywords) {
		int postCnt = 0;
		for (String keyword : keywords) {
			Elements eles = page.select("tbody:contains(" + keyword + ")");
			for (Element ele : eles) {
				try {
					Element tr = ele.child(0); // <tr> tag
					// append <td> Id </td> and <td> check-box </td>
					tr.append("<td >" + postId + "</td>");
					tr.append(HTML_CHECKBOX);
					wr.write(ele.toString());
					postId++;
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
			postCnt += eles.size();
		}

		return postCnt;
	}

	protected void printCurrentProgress(int page, int postId) {
		System.out.println(String.format(
				"Has crawled page %d, and collected %d posts totaly.", page, postId - 1));
	}

	/**
	 * Append </table> </body> </html> to the file
	 * 
	 * @param file
	 */
	protected void appendCloseTag(File file) {
		try (BufferedWriter wr = new BufferedWriter(new FileWriter(file, true))) {
			wr.write(HTML_END);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}
}
