package general.webcrawler.forum;

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

public class MeetQunPostCrawler extends PostCrawler {

	public static void main(String[] args) {
		MeetQunPostCrawler pc = new MeetQunPostCrawler();
		pc.crawlPostsToFile(ROOT_URL);
	}

	protected static final int MAX_PAGE = 141;
	protected static final String ROOT_URL = "http://www.meetqun.com/forum-36-#.html";
	protected static final String HTML_BEGIN = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\"> <html> <head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> <meta http-equiv=\"Content-Style-Type\" content=\"text/css\"> <base href=\"http://www.meetqun.com/\"> <link rel=\"stylesheet\" type=\"text/css\" href=\"data/cache/style_7_common.css?K6D\"> <link rel=\"stylesheet\" type=\"text/css\" href=\"data/cache/style_7_forum_forumdisplay.css?K6D\">   <script src=\"data/cache/common.js?K6D\" type=\"text/javascript\"></script>  <link href=\"template/week_steady/images//js/portal.css\" rel=\"stylesheet\" type=\"text/css\"> <link rel=\"stylesheet\" id=\"css_widthauto\" type=\"text/css\" href=\"data/cache/style_7_widthauto?K6D\">  <script src=\"data/cache/common.js?K6D\" type=\"text/javascript\"></script>  <script type=\"text/javascript\">HTMLNODE.className += ' widthauto'</script>  <script src=\"data/cache/forum.js?K6D\" type=\"text/javascript\"></script>  <script src=\"data/cache/portal.js?K6D\" type=\"text/javascript\"></script>  <title>过滤面经</title> </head> <body id=\"nv_forum\" class=\"pg_forumdisplay\" onkeydown=\"if(event.keyCode==27) return false;\">  <table>";

	public MeetQunPostCrawler() {

	}

	public void crawlPostsToFile(String root) {
		DateFormat dateFormat = new SimpleDateFormat("HH-mm-MM-dd-yyyy");
		String fileName = "/Users/yazhoucao/Downloads/ForumPosts/mq-"
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
			System.out.println("MeetQunPostCrawler ended at page " + pageIdx);
		}
		System.out.println("MeetQunPostCrawler end crawling!");
	}

	@Override
	public void run() {
		MeetQunPostCrawler pc = new MeetQunPostCrawler();
		pc.crawlPostsToFile(ROOT_URL);
	}

}
