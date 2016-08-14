package projects.crawler.subproject.yiyaodaibiao.crawl;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import projects.crawler.data.model.City;
import projects.crawler.subproject.yiyaodaibiao.YiyaodaibiaoModule;
import projects.crawler.subproject.yiyaodaibiao.model.Job;
import projects.crawler.subproject.yiyaodaibiao.model.JobPost;

import javax.inject.Inject;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@Slf4j
public class WuBaCrawler {
    protected static final long WAIT_TIME = 500; // ms
    protected static final int TIMEOUT = 10_000; // ms

    protected static final int MAX_RETRY = 5;
    protected static final int MAX_PAGE = 100;
    protected static final String CITY_LIST = "http://www.58.com/yiyaodaibiao/changecity/";
    //    protected static final String ROOT_URL = "http://city.58.com/yiyaodaibiao/pn#/";
    protected static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";

    @Inject private Job.Dao jobDao;
    @Inject private JobPost.Dao jobPostDao;
    @Inject private WuBaParser parser;
//    @Inject private FailedRecord.Dao failedRecordDao;

    public static void main(String[] args) {
//		AutoTestUtils.runTestClassAndPrint(WuBaCrawler.class);
        WuBaCrawler crawler = new YiyaodaibiaoModule().getInstance(WuBaCrawler.class);
        crawler.crawlAllJobPosts();
    }

    public void crawlAllJobPosts() {
        List<City> cities = crawlAllCities();
        cities.forEach(city -> crawlByCity(city, jobPost -> {
            jobPostDao.insert(jobPost);
            return true;
        }));
    }

    public void crawlAllJobsAndSaveFailedPosts() {
        List<City> cities = crawlAllCities();
        cities.forEach(city -> crawlByCity(city, this::crawlJobAndSavePostIfFail));
    }

    public List<City> crawlAllCities() {
        try {
            Document cityListPage = Jsoup.connect(CITY_LIST).timeout(TIMEOUT).userAgent(USER_AGENT).get();
            return parser.extractCities(cityListPage);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void crawlByCity(City city, Function<JobPost, Boolean> postHandler) {
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
                log.error("Error({}) happened during crawling jobListPage, page url {}\n{}", e.getMessage(), url, e);
//                failedRecordDao.insert(new FailedRecord());
                continue;
            }

            log.info("Extracted " + jobPosts.size() + " job posts");
            jobCount += jobPosts.stream().map(postHandler).filter(Boolean::booleanValue).count();
        }
        log.info("Finish crwaling, crwaled " + (pageIdx - 1) + " pages, " + jobCount + " jobs.");
    }

    /**
     * JobPost handler - Not save post if failed
     */
    public boolean crawlJobAndNotSavePost(JobPost jobPost) {
        Job job = crawlJob(jobPost);
        if (job == null) {
            return false;
        }
        jobDao.insert(job);
        return true;
    }

    /**
     * JobPost handler - Save post if failed
     */
    public boolean crawlJobAndSavePostIfFail(JobPost jobPost) {
        Job job = crawlJob(jobPost);
        if (job == null) {
            jobPostDao.insert(jobPost);
            return false;
        }
        jobDao.insert(job);
        return true;
    }

    /**
     * Give a JobPost, crawl the job page and parse it into a Job
     */
    private Job crawlJob(JobPost post) {
        for (int i = 1; i <= MAX_RETRY; i++) {
            try {
                Document jobPage = Jsoup.connect(post.getUrl()).timeout(TIMEOUT).userAgent(USER_AGENT).get();
                Thread.sleep(WAIT_TIME);
                return parser.parseJob(jobPage, post);
            } catch (InterruptedException e) {
                log.warn("InterruptedException {}", e.getMessage());
                break;
            } catch (Exception e) {
                log.error("Exception {}, {}th time retry", e.getMessage(), i);
                if (i == MAX_RETRY) {
                    log.error("Failed crawling {}, cause: \n{}", post, e);
                }
            }
        }
        return null;
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
