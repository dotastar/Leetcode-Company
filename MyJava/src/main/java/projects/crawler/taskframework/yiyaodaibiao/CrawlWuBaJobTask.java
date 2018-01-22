package projects.crawler.taskframework.yiyaodaibiao;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import projects.crawler.taskframework.yiyaodaibiao.crawl.WuBaCrawler;
import projects.crawler.taskframework.task.BaseTask;
import projects.crawler.taskframework.yiyaodaibiao.model.JobPost;

import javax.inject.Inject;
import java.util.Iterator;

/**
 *
 * Created by yazhoucao on 10/28/15.
 */
@Slf4j
public class CrawlWuBaJobTask extends BaseTask<ObjectId, JobPost> {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        // 13692 + 270
        // Affected 12,127 / 12,526 processed.
        // 25819 + 669
//        JobPost.Dao jobPostDao = new YiyaodaibiaoModule().getInstance(JobPost.Dao.class);
//        FailedRecord.Dao failedRecordDao = new YiyaodaibiaoModule().getInstance(FailedRecord.Dao.class);
//        Set<ObjectId> failedIds = failedRecordDao.getFailedRecordIds(JobPost.class.getSimpleName());
//        CrawlWuBaJobTask crawlTask = new CrawlWuBaJobTask(jobPostDao.findByIds(failedIds).iterator());
//        new TaskControl(crawlTask);
    }

    private final static int numThreads = 10;
    private final static int logFrequency = 100;

    @Inject private WuBaCrawler crawler;
//    @Inject private FailedRecord.Dao failRecordDao;

    public CrawlWuBaJobTask(Iterator<? extends JobPost> iterator) {
        super(iterator, numThreads, logFrequency);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean operate(JobPost jobPost) {
        boolean res = crawler.crawlJobAndNotSavePost(jobPost);
//        if (!res) {
//            failRecordDao.insert(new FailedRecord(jobPost.getId(), jobPost.getClass().getSimpleName()));
//        }
        return res;
    }
}
