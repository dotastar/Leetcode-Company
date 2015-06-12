package general.webcrawler.forum;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class PostCrawler implements Runnable {

	protected static final String[] KEY_WORDS = { "Egencia" };
	protected static final long WAIT_TIME = 100; // ms
	protected static final int TIMEOUT = 10_000; // ms

	protected static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
	protected static final String HTML_END = "</table> </body> </html>";
	protected static final String HTML_CHECKBOX = "<td> <input type=\"checkbox\" name=\"read\" value=\"false\"> </td>";

	public abstract void crawlPostsToFile(String root);

	public static void main(String[] args) {
		new Thread(new MeetQunPostCrawler()).start();
		new Thread(new OnePointThreeAcresPostCrawler()).start();
	}

	/**
	 * Append posts that contains the keyword to the HTML file
	 */
	protected int appendPosts(Document page, BufferedWriter wr, int postId,
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
}
