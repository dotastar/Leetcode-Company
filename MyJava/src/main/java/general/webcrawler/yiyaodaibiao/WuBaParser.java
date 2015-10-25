package general.webcrawler.yiyaodaibiao;

import general.webcrawler.yiyaodaibiao.model.Job;
import general.webcrawler.yiyaodaibiao.model.JobPost;
import interview.AutoTestUtils;
import org.bson.types.ObjectId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WuBaParser {

    public static void main(String[] args) {
        AutoTestUtils.runTestClassAndPrint(WuBaParser.class);
    }

    protected static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";

    public JobPost[] extractPosts(Document page) {
        Element infolist = page.getElementById("infolist");
        if (infolist == null) {
            System.err.println("No HTML tag id infolist found");
            return new JobPost[0];
        }
        String parentUrl = page.baseUri();
        Elements eles = infolist.getElementsByTag("dl");
        JobPost[] posts = new JobPost[eles.size()];
        int idx = 0;
        for (Element postEle : eles) {
            if (postEle.getElementsByTag("dt").isEmpty()) {
                continue;
            }
            Element titleEle = postEle.getElementsByTag("dt").get(0).child(0);
            String title = extractTextFromLink(titleEle);
            String url = titleEle.attr("href").trim();
            Elements otherInfo = postEle.getElementsByTag("dd");
            String companyName = extractTextFromLink(otherInfo.get(1).child(0));
            String district = otherInfo.get(2).text().trim();
            String date = otherInfo.get(3).text().trim();
            posts[idx++] = new JobPost(new ObjectId(), title, companyName, district, date, url, parentUrl);
        }
        return posts;
    }

    private String extractTextFromLink(Element e) {
        if (e == null) {
            throw new IllegalArgumentException("Null element");
        }
        Elements eles = e.getElementsByTag("a");
        if (eles.size() != 1) {
            throw new AssertionError(eles.size() + " links inside this element " + e.tagName());
        }
        return eles.get(0).ownText().trim();
    }

    // leave city, province for null for now
    public Job parseJob(Document page, JobPost post) {
        // company info
        Elements companyInfo = page.getElementsByClass("compMsg").get(0).getElementsByTag("li");
        String industry = extractTextFromLink(companyInfo.get(0).getElementsByTag("a").get(0)).trim();
        String companyType = companyInfo.get(1).ownText().trim();
        String companySize = companyInfo.get(2).ownText().trim();
        // job info
        Element jobInfoDiv = page.getElementsByClass("xq").get(0);
        String salaryRange = jobInfoDiv.getElementsByClass("salaNum").isEmpty() ? null : jobInfoDiv.getElementsByClass("salaNum").get(0).text().trim();
        Elements jobInfoListItem = jobInfoDiv.getElementsByTag("li");
        String jobTitle = jobInfoListItem.size() < 2 ? null : jobInfoListItem.get(1).child(0).ownText().trim();
        String jobAddress = jobInfoListItem.size() < 3 ? null : jobInfoListItem.get(2).child(1).text().trim();
        String contactName = jobInfoDiv.getElementsByClass("conTip").isEmpty() ? null : jobInfoDiv.getElementsByClass("conTip").get(0).ownText().trim();
        String phoneUrl = jobInfoDiv.getElementById("ck1") == null ? null : jobInfoDiv.getElementById("ck1").child(0).attr("src").trim();
        if (phoneUrl != null) { // part of the phone number is hidden, replace it with the real page number
            String pageNum = page.getElementById("pagenum").attr("value").trim();
            phoneUrl = replacePhoneUrl(phoneUrl, pageNum);
        }
        // other info from job post
        String postTitle = post.getTitle();
        String district = post.getDistrict();
        String url = post.getUrl();
        String postDate = post.getPostDate();
        String companyName = post.getCompanyName();

        return new Job(new ObjectId(), jobTitle, companyName, contactName, phoneUrl, jobAddress, industry, companyType, companySize, salaryRange, district, null, null, postTitle, postDate, url);
    }

    private String replacePhoneUrl(String url, String pageNum) {
        if (url == null || pageNum == null) {
            String errMsg = url == null ? "phone url is null; " : "";
            errMsg += pageNum == null ? "pageNum is null;" : "";
            throw new IllegalArgumentException(errMsg);
        }
        String identifier = "&v=";
        int idx = url.indexOf(identifier);
        return url.substring(0, idx + identifier.length()) + pageNum;
    }


    //	@Test
    public void testParsePostThenParseJob() throws IOException {
        String url = "http://gz.58.com/yiyaodaibiao/pn3/";
        int timeout = 20_000;
        Document doc = Jsoup.connect(url).timeout(timeout).userAgent(USER_AGENT).get();
        JobPost[] posts = extractPosts(doc);
        System.out.println("Extracted " + posts.length + " job posts");
        for (JobPost post : posts) {
            System.out.println(post);
            Document jobPage = Jsoup.connect(post.getUrl()).timeout(timeout).userAgent(USER_AGENT).get();
            Job job = parseJob(jobPage, post);
            System.out.println(job);
        }
    }

    @Test
    public void testParseJob_NoPhone() throws IOException {
        String url = "http://gz.58.com/yewu/21564236746016x.shtml";
        JobPost post = new JobPost(new ObjectId(), "Fake post title", "Fake company name", "Fake district", "Fake date", url, "Fake parent url");
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).get();
        Job job = parseJob(doc, post);
        System.out.println(job);
    }

    @Test
    public void testParseJob_HasPhone() throws IOException {
        String url = "http://gz.58.com/yewu/23111813389471x.shtml";
        JobPost post = new JobPost(new ObjectId(), "Fake post title", "Fake company name", "Fake district", "Fake date", url, "Fake parent url");
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).get();
        Job job = parseJob(doc, post);
        System.out.println(job);
    }

    @Test
    public void testExtractPosts_SeteUrl() throws IOException {
        String url = "http://gz.58.com/yiyaodaibiao/pn3/";
        int timeout = 10_000;
        Document doc = Jsoup.connect(url).timeout(timeout).userAgent(USER_AGENT).get();
        JobPost[] posts = extractPosts(doc);
        System.out.println("Extracted " + posts.length + " job posts");
        for (JobPost post : posts)
            System.out.println(post);
        assertEquals(url, posts[0].getParentUrl());
        assertTrue(posts.length > 0);
    }
}
