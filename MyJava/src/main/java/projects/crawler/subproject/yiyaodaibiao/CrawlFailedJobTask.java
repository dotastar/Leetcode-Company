package projects.crawler.subproject.yiyaodaibiao;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import projects.crawler.subproject.yiyaodaibiao.crawl.WuBaCrawler;
import projects.crawler.subproject.yiyaodaibiao.model.JobPost;
import projects.crawler.task.BaseTask;

import javax.inject.Inject;
import java.util.Iterator;

/**
 * CrawlFailedJobTask
 * <p>
 * Created by yazhoucao on 10/31/15.
 */
@Slf4j
public class CrawlFailedJobTask extends BaseTask<ObjectId, JobPost> {

  private final static int numThreads = 10;
  private final static int logFrequency = 100;

//    @Inject private FailedRecord.Dao failedRecordDao;

  @Inject private JobPost.Dao jobPostDao;

  public static void main(String[] args) {
//        FailedRecord.Dao failedRecordDao = new YiyaodaibiaoModule().getInstance(FailedRecord.Dao.class);
//        JobPost.Dao jobPostDao = new YiyaodaibiaoModule().getInstance(JobPost.Dao.class);
//        @SuppressWarnings("unchecked")
//        Set<ObjectId> failedIds = failedRecordDao.getFailedRecordIds(JobPost.class.getSimpleName());
//        Iterator<JobPost> failedJobPosts = jobPostDao.findByIds(failedIds).iterator();
//        CrawlFailedJobTask crawlTask = new CrawlFailedJobTask(failedJobPosts);
//        new TaskControl(crawlTask);
  }

  private WuBaCrawler crawler;

  public CrawlFailedJobTask(Iterator<? extends JobPost> iterator) {
    super(iterator, numThreads, logFrequency);
    this.crawler = new WuBaCrawler();
  }

  @Override
  public boolean operate(JobPost jobPost) {
    boolean isOperateSuccess = crawler.crawlJobAndNotSavePost(jobPost);
//        if (isOperateSuccess && !failedRecordDao.deleteRecord(jobPost.getId(), jobPost.getClass().getSimpleName())) {
//            log.error("Delete failedRecord failed,  {} {}", jobPost.getId(), jobPost.getClass().getSimpleName());
//        }
    return isOperateSuccess;
  }
}
