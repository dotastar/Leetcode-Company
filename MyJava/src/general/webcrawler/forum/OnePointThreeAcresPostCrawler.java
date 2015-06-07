package general.webcrawler.forum;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OnePointThreeAcresPostCrawler {

	public static void main(String[] args) {
		OnePointThreeAcresPostCrawler crawler = new OnePointThreeAcresPostCrawler();
		crawler.crawlPostsToFile(ROOT_URL);
	}

	private static final String[] KEY_WORDS = { "booking", "connectifier", "vertica" };
	// { "zenefits" };
	private static final int MAX_PAGE = 68;
	private static final long WAIT_TIME = 200; // ms
	private static final int TIMEOUT = 20_000; // ms

	private static final String ROOT_URL = "http://www.1point3acres.com/bbs/forum-145-#.html";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
	private static final String HTML_BEGIN = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><title>一亩三分地面经</title><base href=\"http://www.1point3acres.com/bbs/\" /><link rel=\"stylesheet\" type=\"text/css\" href=\"data/cache/style_5_common.css?D6I\" /><link rel=\"stylesheet\" type=\"text/css\" href=\"data/cache/style_5_forum_forumdisplay.css?D6I\" /><script src=\"data/cache/common.js?D6I\" type=\"text/javascript\"></script><link rel=\"stylesheet\" id=\"css_widthauto\" type=\"text/css\" href=\"data/cache/style_5_widthauto.css?D6I\" /><script type=\"text/javascript\">HTMLNODE.className += ' widthauto'</script><script src=\"data/cache/forum.js?D6I\" type=\"text/javascript\"></script></head><body id=\"nv_forum\" class=\"pg_forumdisplay\" onkeydown=\"if(event.keyCode==27) return false;\"><table>";
	private static final String HTML_END = "</table> </body> </html>";
	private static final String HTML_CHECKBOX = "<td> <input type=\"checkbox\" name=\"read\" value=\"false\"> </td>";

	public void crawlPostsToFile(String root) {
		DateFormat dateFormat = new SimpleDateFormat("HH-mm-MM-dd-yyyy");
		String fileName = "/Users/yazhoucao/Downloads/ForumPosts/1m3f-"
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
			System.out.println("Ended at page " + pageIdx);
		}
		System.out.println("End crawling!");
	}

	/**
	 * Append posts that contains the keyword to the HTML file
	 */
	private int appendPosts(Document page, BufferedWriter wr, int postId,
			String[] keywords) {
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

	private void printCurrentProgress(int page, int postId) {
		System.out.println(String.format(
				"Has crawled page %d, and collected %d posts totaly.", page, postId - 1));
	}

	/**
	 * Append </table> </body> </html> to the file
	 * 
	 * @param file
	 */
	private void appendCloseTag(File file) {
		try (BufferedWriter wr = new BufferedWriter(new FileWriter(file, true))) {
			wr.write(HTML_END);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
