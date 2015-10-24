package general.webcrawler.yiyaodaibiao;

import static org.junit.Assert.assertEquals;

import general.webcrawler.yiyaodaibiao.model.Job;
import general.webcrawler.yiyaodaibiao.model.JobPost;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

@Slf4j
public class WuBaCrawler {

    protected static final String CRAWLER_NAME = WuBaCrawler.class.getSimpleName();
    protected static final String[] KEY_WORDS = {};
    protected static final long WAIT_TIME = 500; // ms
    protected static final int TIMEOUT = 10_000; // ms

    protected static final int MAX_RETRY = 3;
    protected static final int MAX_PAGE = 18;
    protected static final String ROOT_URL = "http://gz.58.com/yiyaodaibiao/pn#/";
    protected static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";

    public static void main(String[] args) {
//		AutoTestUtils.runTestClassAndPrint(WuBaCrawler.class);
        WuBaCrawler crawler = new WuBaCrawler();
        crawler.run();
    }

    public void run() {
        Job.Dao jobDao = new Job.Dao();
        JobPost.Dao postDao = new JobPost.Dao();
        Set<Integer> visitedPageHash = new HashSet<>();
        WuBaJobParser parser = new WuBaJobParser();
        int jobCount = 0, pageCount = 0;
        for (int i = 4; i <= MAX_PAGE; i++) {
            String url = ROOT_URL.replace("#", Integer.toString(i));
            try {
                Document postsPage = Jsoup.connect(url).timeout(TIMEOUT).userAgent(USER_AGENT).get();
                // TODO detect the end page
                pageCount++;
                System.out.println("=============== Page " + i + " =================");
                JobPost[] posts = parser.extractPosts(postsPage);
                System.out.println("Extracted " + posts.length + " job posts");
                for (JobPost post : posts) {
                    postDao.insert(post);
                    log.info("Saved post: {}", post);
                    Document jobPage = null;
                    for (int j = 0; jobPage == null && j < MAX_RETRY; j++) {
                        try {
                            Thread.sleep(WAIT_TIME);
                            jobPage = Jsoup.connect(post.getUrl()).timeout(TIMEOUT).userAgent(USER_AGENT).get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                    if (jobPage == null) {
                        continue;
                    }
                    Job job = parser.parseJob(jobPage, post);
                    jobDao.insert(job);
                    jobCount++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finish crwaling, crwaled " + pageCount + " pages, " + jobCount + " jobs.");
    }


    protected void printCurrentProgress(int page, int postId) {
        System.out.println(String.format(
                "Has crawled page %d, and collected %d posts totaly.", page, postId - 1));
    }


    @Test
    public void test1() throws IOException {
        String url1 = "http://gz.58.com/yiyaodaibiao/pn1/";
        String url2 = "http://gz.58.com/yiyaodaibiao/pn2/";
        String url3 = "http://gz.58.com/yiyaodaibiao/pn3/";
        System.out.println(Jsoup.connect(url1).timeout(TIMEOUT).userAgent(USER_AGENT).get().hashCode());
        System.out.println(Jsoup.connect(url2).timeout(TIMEOUT).userAgent(USER_AGENT).get().hashCode());
        System.out.println(Jsoup.connect(url3).timeout(TIMEOUT).userAgent(USER_AGENT)
                .get().hashCode());
        assertEquals(true, true);
    }
}
