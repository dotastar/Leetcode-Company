package general.webcrawler.yiyaodaibiao.task;

import general.webcrawler.yiyaodaibiao.WuBaCrawler;
import general.webcrawler.yiyaodaibiao.model.FailedRecord;
import general.webcrawler.yiyaodaibiao.model.JobPost;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by yazhoucao on 10/28/15.
 */
@Slf4j
public class CrawlWuBaJobTask extends BaseTask<ObjectId, JobPost> {

    public static void main(String[] args) {
        // 13692 + 270
        // Affected 12,127 / 12,526 processed.
        // 25819 + 669
        JobPost.Dao jobPostDao = new JobPost.Dao();
        FailedRecord.Dao failedRecordDao = new FailedRecord.Dao();
        Set<ObjectId> failedIds = failedRecordDao.getFailedRecordIds(JobPost.class.getSimpleName());
        CrawlWuBaJobTask crawlTask = new CrawlWuBaJobTask(jobPostDao.findByIds(failedIds).iterator());
        new TaskControl(crawlTask);
    }

    private final static int numThreads = 10;
    private final static int logFrequency = 100;

    private WuBaCrawler crawler;
    private FailedRecord.Dao failRecordDao;

    public CrawlWuBaJobTask(Iterator<? extends JobPost> iterator) {
        super(iterator, numThreads, logFrequency);
        this.crawler = new WuBaCrawler();
        this.failRecordDao = new FailedRecord.Dao();
    }

    @Override
    public boolean operate(JobPost jobPost) {
        boolean res = crawler.crawlJobAndNotSavePost(jobPost);
        if (!res) {
            failRecordDao.insert(new FailedRecord(jobPost.getId(), jobPost.getClass().getSimpleName()));
        }
        return res;
    }
}
