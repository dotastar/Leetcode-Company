package general.webcrawler.yiyaodaibiao;

import com.google.common.base.Preconditions;
import general.webcrawler.yiyaodaibiao.model.City;
import general.webcrawler.yiyaodaibiao.model.FailedRecord;
import general.webcrawler.yiyaodaibiao.model.Job;
import general.webcrawler.yiyaodaibiao.model.JobPost;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@Slf4j
public class WuBaCrawler {
    protected static final long WAIT_TIME = 500; // ms
    protected static final int TIMEOUT = 10_000; // ms

    protected static final int MAX_RETRY = 3;
    protected static final int MAX_PAGE = 100;
    protected static final String CITY_LIST = "http://www.58.com/yiyaodaibiao/changecity/";
    //    protected static final String ROOT_URL = "http://city.58.com/yiyaodaibiao/pn#/";
    protected static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";

    private Job.Dao jobDao = new Job.Dao();
    private JobPost.Dao jobPostDao = new JobPost.Dao();
    private WuBaParser parser = new WuBaParser();
    private FailedRecord.Dao failedRecordDao = new FailedRecord.Dao();

    public static void main(String[] args) {
//		AutoTestUtils.runTestClassAndPrint(WuBaCrawler.class);
        WuBaCrawler crawler = new WuBaCrawler();
        crawler.crawlAllCities(false);
    }

    public void crawlAllCities(boolean shouldParseJob) {
        try {
            Document cityListPage = Jsoup.connect(CITY_LIST).timeout(TIMEOUT).userAgent(USER_AGENT).get();
            List<City> cities = parser.extractCities(cityListPage);
            cities.forEach(city -> crawlByCity(city, shouldParseJob ? this::saveJobPost : this::crawlAndSaveJobs));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void crawlByCity(City city, Function<JobPost, Boolean> action) {
        Preconditions.checkNotNull(city);
        log.info("Begin crawling city: {}", city);

        int jobCount = 0;
        int pageIdx = 1;
        String baseUrl = city.getUrl() + "pn#/";
        for (; pageIdx <= MAX_PAGE; pageIdx++) {
            String url = baseUrl.replace("#", Integer.toString(pageIdx));
            List<JobPost> jobPosts;
            try {
                log.info("=============== Page " + pageIdx + " =================");
                Document jobListPage = Jsoup.connect(url).timeout(TIMEOUT).userAgent(USER_AGENT).get();
                if (WuBaParser.isEndPage(jobListPage)) {
                    break;
                }
                jobPosts = parser.extractPosts(jobListPage, city);
            } catch (IOException | ParseException e) {
                log.error("Error({}) happened during crawling jobListPage, page url {}\n{}", e.getMessage(), url, toString(e.getStackTrace()));
                failedRecordDao.insert(new FailedRecord(url));
                continue;
            }

            log.info("Extracted " + jobPosts.size() + " job posts");
            jobCount += jobPosts.stream().map(action).filter(Boolean::booleanValue).count();
        }
        log.info("Finish crwaling, crwaled " + (pageIdx - 1) + " pages, " + jobCount + " jobs.");
    }

    private Boolean saveJobPost(JobPost jobPost) {
        jobPostDao.insert(jobPost);
        return true;
    }

    private Boolean crawlAndSaveJobs(JobPost jobPost) {
        Job job = crawlJob(jobPost);
        if (job == null) {
            return saveJobPost(jobPost);
        }
        jobDao.insert(job);
        return true;
    }

    /**
     * Give a JobPost, crawl the job page and parse it into a Job
     */
    private Job crawlJob(JobPost post) {
        Document jobPage = null;
        for (int i = 0; jobPage == null && i < MAX_RETRY; i++) {
            try {
                jobPage = Jsoup.connect(post.getUrl()).timeout(TIMEOUT).userAgent(USER_AGENT).get();
                Thread.sleep(WAIT_TIME);
            } catch (IOException e) {
                log.error("IOException {}, {} time retry", e.getMessage(), i);
            } catch (InterruptedException e) {
                log.warn("InterruptedException {}", e.getMessage());

                return null;
            }
        }
        if (jobPage == null) {
            log.error("Crawl {} failed, saved it in database.", post);
            jobPostDao.insert(post);
            return null;
        }

        try {
            return parser.parseJob(jobPage, post);
        } catch (Exception e) {
            log.error("{}\tParse {} failed\n{}", e.getMessage(), post, toString(e.getStackTrace()));
            jobPostDao.insert(post);
            return null;
        }
    }

    private String toString(StackTraceElement[] stackTraceElements) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement stackTrace : stackTraceElements) {
            sb.append(stackTrace).append(System.lineSeparator());
        }
        return sb.toString();
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
