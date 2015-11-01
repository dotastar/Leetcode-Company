package general.webcrawler.yiyaodaibiao.task;

import general.webcrawler.yiyaodaibiao.WuBaCrawler;
import general.webcrawler.yiyaodaibiao.model.FailedRecord;
import general.webcrawler.yiyaodaibiao.model.JobPost;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by yazhoucao on 10/31/15.
 */
@Slf4j
public class CrawlFailedJobTask extends BaseTask<ObjectId, JobPost> {

    private final static int numThreads = 10;
    private final static int logFrequency = 100;

    private static FailedRecord.Dao failedRecordDao = new FailedRecord.Dao();
    private static JobPost.Dao jobPostDao = new JobPost.Dao();

    public static void main(String[] args) {
        Set<ObjectId> failedIds = failedRecordDao.getFailedRecordIds(JobPost.class.getSimpleName());
        Iterator<JobPost> failedJobPosts = jobPostDao.findByIds(failedIds).iterator();
        CrawlFailedJobTask crawlTask = new CrawlFailedJobTask(failedJobPosts);
        new TaskControl(crawlTask);
    }

    private WuBaCrawler crawler;

    public CrawlFailedJobTask(Iterator<? extends JobPost> iterator) {
        super(iterator, numThreads, logFrequency);
        this.crawler = new WuBaCrawler();
    }

    @Override
    public boolean operate(JobPost jobPost) {
        boolean isOperateSuccess = crawler.crawlJobAndNotSavePost(jobPost);
        if (isOperateSuccess && !failedRecordDao.deleteRecord(jobPost.getId(), jobPost.getClass().getSimpleName())) {
            log.error("Delete failedRecord failed,  {} {}", jobPost.getId(), jobPost.getClass().getSimpleName());
        }
        return isOperateSuccess;
    }
}
