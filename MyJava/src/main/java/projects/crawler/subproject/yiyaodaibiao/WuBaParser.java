package projects.crawler.subproject.yiyaodaibiao;

import com.google.common.base.Preconditions;
import interview.AutoTestUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import projects.crawler.data.City;
import projects.crawler.subproject.yiyaodaibiao.model.Job;
import projects.crawler.subproject.yiyaodaibiao.model.JobPost;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
public class WuBaParser {

    public static void main(String[] args) {
        AutoTestUtils.runTestClassAndPrint(WuBaParser.class);
    }

    protected static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";

    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    public List<City> extractCities(Document page) throws ParseException {
        final String ID_CLIST = "clist";
        List<City> cities = new ArrayList<>();
        Element cityListElem = page.getElementById(ID_CLIST);
        checkNotNull(cityListElem, ID_CLIST);

        Elements dt_provinces = cityListElem.getElementsByTag("dt");
        for (Element dt_provinceName : dt_provinces) {
            String provinceName = dt_provinceName.text();
            Element dd_cities = dt_provinceName.nextElementSibling();
            checkTagName(dd_cities, "dd");

            Elements cityLinks = dd_cities.getElementsByTag("a");
            for (Element cityLink : cityLinks) {
                City city = new City(provinceName, cityLink.text(), cityLink.absUrl("href"));
                if (provinceName.equals("热门")) {
                    if (City.isMunicipality(city.getName())) {
                        city.setProvinceName(city.getName());
                        cities.add(city);
                    }
                } else {
                    cities.add(city);
                }
            }
        }
        return cities;
    }

    public List<JobPost> extractPosts(Document page, City city) throws ParseException {
        final String ID_INFOLIST = "infolist";
        Element infoList = page.getElementById(ID_INFOLIST);
        checkNotNull(infoList, ID_INFOLIST);

        String parentUrl = page.baseUri();
        Elements dlElems = infoList.getElementsByTag("dl");
        List<JobPost> posts = new ArrayList<>();
        for (Element postEle : dlElems) {
            if (postEle.getElementsByTag("dt").isEmpty()) {
                continue;
            }
            JobPost jobPost = parseJobPost(postEle, parentUrl, city);
            if (jobPost == null) {
                // The end
                break;
            }

            posts.add(jobPost);
        }
        return posts;
    }

    private JobPost parseJobPost(Element dlTag, String parentUrl, City city) throws ParseException {
        final String TAG_DT = "dt";
        final String TAG_A = "a";
        Elements dtTags = dlTag.getElementsByTag(TAG_DT);
        checkSizeOnlyOne(TAG_DT, dtTags);

        Elements aTags = dtTags.get(0).getElementsByTag(TAG_A);
        if (aTags.isEmpty()) {
            // No Title Element, assume it is the end
            return null;
        }

        Element titleEle = aTags.get(0);
        String title = extractTextFromLink(titleEle);
        String url = titleEle.attr("href").trim();
        Elements otherInfo = dlTag.getElementsByTag("dd");
        String companyName = extractTextFromLink(otherInfo.get(1).child(0));
        String district = otherInfo.get(2).text().trim();
        String date = otherInfo.get(3).text().trim();
        // Leave field parentUrl for null, set it later
        return new JobPost(new ObjectId(), title, companyName, district, date, url, parentUrl, city.getName(), city.getProvinceName());
    }

    private String extractTextFromLink(Element e) throws ParseException {
        checkNotNull(e);
        Elements elems = e.getElementsByTag("a");
        checkSizeOnlyOne(e.tagName(), elems);

        return elems.get(0).ownText().trim();
    }

    public Job parseJob(Document page, JobPost post) throws ParseException {
        // Company info
        final String CLASS_COMPMSG = "compMsg";
        Elements compMsgElems = page.getElementsByClass("compMsg");
        checkNotEmpty(CLASS_COMPMSG, compMsgElems);

        Elements companyInfo = compMsgElems.get(0).getElementsByTag("li");
        String industry = extractTextFromLink(companyInfo.get(0).getElementsByTag("a").get(0)).trim();
        String companyType = companyInfo.get(1).ownText().trim();
        String companySize = companyInfo.get(2).ownText().trim();
        // Job info
        Element jobInfoDiv = page.getElementsByClass("xq").get(0);
        String salaryRange = jobInfoDiv.getElementsByClass("salaNum").isEmpty() ? null : jobInfoDiv.getElementsByClass("salaNum").get(0).text().trim();
        Elements jobInfoListItem = jobInfoDiv.getElementsByTag("li");
        String jobTitle = jobInfoListItem.size() < 2 ? null : jobInfoListItem.get(1).child(0).ownText().trim();
        String jobAddress = jobInfoListItem.size() < 3 ? null : jobInfoListItem.get(2).child(1).text().trim();
        String contactName = jobInfoDiv.getElementsByClass("conTip").isEmpty() ? null : jobInfoDiv.getElementsByClass("conTip").get(0).ownText().trim();
        String phoneUrlBase = jobInfoDiv.getElementById("ck1") == null ? null : jobInfoDiv.getElementById("ck1").child(0).attr("src").trim();
        List<String> phoneUrls = new ArrayList<>();
        if (phoneUrlBase != null) {
            // Part of the phone number is hidden, replace it with the real page number
            String pageNumStr = page.getElementById("pagenum").attr("value").trim();
            String[] pageNumbs = pageNumStr.split("_");
            for (String pageNum : pageNumbs) {
                phoneUrls.add(replacePhoneUrl(phoneUrlBase, pageNum));
            }
        }
        // Other info from job post
        String postTitle = post.getTitle();
        String district = post.getDistrict();
        String sourceUrl = post.getUrl();
        String postDate = convertRelativeDateStr(post.getPostDate());
        String companyName = post.getCompanyName();

        return new Job(
                new ObjectId(),
                jobTitle,
                companyName,
                contactName,
                phoneUrls,
                jobAddress,
                industry,
                companyType,
                companySize,
                salaryRange,
                district,
                post.getCity(),
                post.getProvince(),
                postTitle,
                postDate,
                sourceUrl
        );
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

    private String convertRelativeDateStr(String date) {
        if (date == null) {
            return "";
        }
        if (date.contains("今天") || date.contains("小时")) {
            return DATE_FORMATTER.format(new Date());
        }
        return date;
    }

    public static boolean isEndPage(Document jobListPage) throws ParseException {
        return parseCurrentPageNumber(jobListPage) < 0;
    }

    public static int parseCurrentPageNumber(Document jobListPage) throws ParseException {
        final String CLASS_PAGER = "pager";
        final String TAG_STRONG = "strong";

        Elements pagers = jobListPage.getElementsByClass(CLASS_PAGER);
        checkSizeOnlyOne(CLASS_PAGER, pagers);

        Element pager = pagers.first();
        Elements strongTags = pager.getElementsByTag(TAG_STRONG);
        if (strongTags.isEmpty()) {
            // End of a page
            return -1;
        }
        checkSizeOnlyOne(TAG_STRONG, strongTags);

        Element strongTag = strongTags.first();
        Preconditions.checkNotNull(strongTag.text());

        return Integer.valueOf(strongTag.text());
    }

    private static void checkTagName(Element element, String tagName) throws ParseException {
        if (!element.tagName().equals(tagName)) {
            throw new ParseException("Expected tag " + tagName + ", but it is " + element.tagName(), -1);
        }
    }

    private static void checkNotNull(Object object) throws ParseException {
        checkNotNull(object, null);
    }

    private static void checkNotNull(Object object, String name) throws ParseException {
        if (object == null) {
            throw new ParseException("Expected element " + name + " not null!", 0);
        }
    }

    private static void checkNotEmpty(String name, Collection elements) throws ParseException {
        if (elements.size() == 0) {
            throw new ParseException("Empty collection " + name, 0);
        }
    }

    private static void checkSizeOnlyOne(String name, Collection elements) throws ParseException {
        checkSize(name, elements, 1);
    }

    private static void checkSize(String name, Collection elements, int expected) throws ParseException {
        if (elements.size() == 0) {
            throw new ParseException("No " + name + " found.", 0);
        }
        if (elements.size() != expected) {
            throw new ParseException("Expected size " + expected + ", found " + elements.size() + " for " + name, elements.size());
        }
    }

    @Test
    public void testParseJob_NoPhone() throws IOException, ParseException {
        String url = "http://gz.58.com/yewu/21564236746016x.shtml";
        JobPost post = new JobPost(new ObjectId(), "Fake post title", "Fake company name", "Fake district", "Fake date", url, "Fake parent url", "Beijing", "Beijing");
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).get();
        Job job = parseJob(doc, post);
        System.out.println(job);
    }

    @Test
    public void testParseJob_HasPhone() throws IOException, ParseException {
        String url = "http://gz.58.com/yewu/23111813389471x.shtml";
        JobPost post = new JobPost(new ObjectId(), "Fake post title", "Fake company name", "Fake district", "Fake date", url, "Fake parent url", "GuangZhou", "GuangDong");
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).get();
        Job job = parseJob(doc, post);
        System.out.println(job);
    }

    @Test
    public void testParseJob_HasTwoPhoneNumbers() throws IOException, ParseException {
        String url = "http://gz.58.com/yewu/22669508894473x.shtml";
        JobPost post = new JobPost(new ObjectId(), "Fake post title", "Fake company name", "Fake district", "Fake date", url, "Fake parent url", "YiYang", "HuNan");
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).get();
        Job job = parseJob(doc, post);
        System.out.println(job);
    }

    @Test
    public void testExtractPosts_SeteUrl() throws IOException, ParseException {
        String url = "http://gz.58.com/yiyaodaibiao/pn3/";
        int timeout = 10_000;
        Document doc = Jsoup.connect(url).timeout(timeout).userAgent(USER_AGENT).get();
        List<JobPost> posts = extractPosts(doc, new City("Beijing", "Beijing", "bj.58.com/yiyaodaibiao/"));
        System.out.println("Extracted " + posts.size() + " job posts");
        for (JobPost post : posts)
            System.out.println(post);
        assertEquals(url, posts.get(0).getParentUrl());
        assertTrue(posts.size() > 0);
    }
}
