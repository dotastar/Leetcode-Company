package projects.crawler.subproject.forum;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OnePointThreeAcresPostCrawler extends PostCrawler {

  public static void main(String[] args) {
    OnePointThreeAcresPostCrawler crawler = new OnePointThreeAcresPostCrawler();
    crawler.crawlPostsToFile(ROOT_URL);
  }

  protected static final int MAX_PAGE = 134;
  protected static final String ROOT_URL = "http://www.1point3acres.com/bbs/forum-145-#.html";
  protected static final String HTML_BEGIN = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><title>一亩三分地面经</title><base href=\"http://www.1point3acres.com/bbs/\" /><link rel=\"stylesheet\" type=\"text/css\" href=\"data/cache/style_5_common.css?D6I\" /><link rel=\"stylesheet\" type=\"text/css\" href=\"data/cache/style_5_forum_forumdisplay.css?D6I\" /><script src=\"data/cache/common.js?D6I\" type=\"text/javascript\"></script><link rel=\"stylesheet\" id=\"css_widthauto\" type=\"text/css\" href=\"data/cache/style_5_widthauto.css?D6I\" /><script type=\"text/javascript\">HTMLNODE.className += ' widthauto'</script><script src=\"data/cache/forum.js?D6I\" type=\"text/javascript\"></script></head><body id=\"nv_forum\" class=\"pg_forumdisplay\" onkeydown=\"if(event.keyCode==27) return false;\"><table>";

  public void crawlPostsToFile(String root) {
    DateFormat dateFormat = new SimpleDateFormat("HH-mm-MM-dd-yyyy");
    String fileName = PostCrawler.ROOT_DIRECTORY + "1m3f-" + dateFormat.format(Calendar.getInstance().getTime()) + ".html";
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
      System.out.println("OnePointThreeAcresPostCrawler ended at page " + pageIdx);
    }
    System.out.println("OnePointThreeAcresPostCrawler end crawling!");
  }

  @Override
  public void run() {
    OnePointThreeAcresPostCrawler crawler = new OnePointThreeAcresPostCrawler();
    crawler.crawlPostsToFile(ROOT_URL);
  }

}
