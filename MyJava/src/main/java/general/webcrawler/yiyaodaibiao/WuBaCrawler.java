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
        crawler.crawlAllCities();
    }

    public void crawlAllCities() {
        try {
            Document cityListPage = Jsoup.connect(CITY_LIST).timeout(TIMEOUT).userAgent(USER_AGENT).get();
            List<City> cities = parser.extractCities(cityListPage);
//            cities.forEach(System.out::println);
            cities.forEach(this::crawlByCity);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void crawlByCity(City city) {
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
                jobPosts = parser.extractPosts(jobListPage);
            } catch (IOException | ParseException e) {
                log.error("Error({}) happened during crawling jobListPage, page url {}\n{}", e.getMessage(), url, toString(e.getStackTrace()));
                failedRecordDao.insert(new FailedRecord(url));
                continue;
            }

            jobCount += crawlAndParseJobs(jobPosts, city);
        }
        log.info("Finish crwaling, crwaled " + (pageIdx - 1) + " pages, " + jobCount + " jobs.");
    }

    private int crawlAndParseJobs(List<JobPost> jobPosts, City city) {
        int success = 0;
        log.info("Extracted " + jobPosts.size() + " job posts");
        for (JobPost post : jobPosts) {
            Job job = crawlJob(post);
            if (job == null) {
                continue;
            }

            job.setCity(city.getName());
            job.setProvince(city.getProvinceName());
            jobDao.insert(job);
            success++;
        }
        return success;

    }

    /**
     * Give a JobPost, crawl the job page and parse it into a Job
     */
    private Job crawlJob(JobPost post) {
        Document jobPage = null;
        for (int i = 0; jobPage == null && i < MAX_RETRY; i++) {
            try {
                Thread.sleep(WAIT_TIME);
                jobPage = Jsoup.connect(post.getUrl()).timeout(TIMEOUT).userAgent(USER_AGENT).get();
            } catch (IOException e) {
                log.error("IOException {}, {} time retry", e.getMessage(), i);
            } catch (InterruptedException e) {
                log.warn("InterruptedException {}", e.getMessage());
                break;
            }
        }

        Job job = null;
        if (jobPage != null) {
            try {
                job = parser.parseJob(jobPage, post);
            } catch (ParseException e) {
                log.error("Parse failed {}\n{}", e.getMessage(), toString(e.getStackTrace()));
            }
        }
        if (job == null) {
            log.error("Failed to crawl/parse job post, saved the JobPost {} in database.", post);
            jobPostDao.insert(post);
        }
        return job;
    }

    private String toString(StackTraceElement[] stackTraceElements) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement stackTrace : stackTraceElements) {
            sb.append(stackTrace + "\n");
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
