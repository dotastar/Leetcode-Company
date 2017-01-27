package general.concurrency.taskframework.task;

import general.concurrency.taskframework.model.SavedOffset;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author asia created on 10/3/15.
 */
@Slf4j
public abstract class BaseTask<T> implements Runnable {

    protected static String END_MSG = "Task completed {} ({})";
    protected static final NumberFormat FMT = NumberFormat.getNumberInstance();
    protected static final PeriodFormatter PERIOD_FORMAT = new PeriodFormatterBuilder()
            .appendYears().appendSuffix(" years, ")
            .appendMonths().appendSuffix(" months, ")
            .appendWeeks().appendSuffix(" weeks, ")
            .appendDays().appendSuffix(" days, ")
            .appendHours().appendSuffix(" hours, ")
            .appendMinutes().appendSuffix(" minutes, ")
            .appendSeconds().appendSuffix(" seconds")
            .printZeroNever()
            .toFormatter();

    protected final AtomicInteger modifiedCnt = new AtomicInteger();
    protected final AtomicInteger totalCnt = new AtomicInteger();
    protected final String taskName;
    protected final DateTime start;
    protected final int logFrequency;
    protected final Iterator<? extends T> iterator;
    protected final ThreadPoolExecutor threadPool;
    protected final SavedOffset.Dao<T> saveOffsetDao;

    protected SavedOffset<T> savedOffset;

    public BaseTask(Iterator<? extends T> iterator, int numThreads, int logFrequency) {
        this(iterator, numThreads, logFrequency, null);
        String taskName = this.getClass().getSimpleName();
        SavedOffset<T> existed = saveOffsetDao.findById(taskName);
        this.savedOffset = existed != null ? existed : new SavedOffset<T>(taskName);
    }

    public BaseTask(Iterator<? extends T> iterator, int numThreads, int logFrequency, SavedOffset<T> savedOffset) {
        this.taskName = this.getClass().getSimpleName();
        this.start = new DateTime();
        this.iterator = iterator;
        this.logFrequency = logFrequency;
        this.threadPool = new ThreadPoolExecutor(numThreads, numThreads, Long.MAX_VALUE, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<>());
        this.saveOffsetDao = new SavedOffset.Dao<>();
        this.savedOffset = savedOffset;
    }

    @Override
    public void run() {
        log.info("Task {} started", taskName);

        before();

        Parallel.For(threadPool, iterator,
                /**
                 * {@link, task.Parallel.Operation#perform(T element)}
                 */
                element -> {
                    if (operate(element)) {
                        modifiedCnt.incrementAndGet();
                    } else {
                        saveOffsetDao.saveFailed(taskName, element);
                    }
                    // Log status
                    int total = totalCnt.incrementAndGet();
                    if (logFrequency > 0 && (total % logFrequency == 0 || total == 1)) {
                        String status = statusMessage(modifiedCnt, totalCnt);
                        log.info(status);
                    }
                    // Save offset
                    if (savedOffset != null && total % 1000 == 0) {
                        saveOffsetDao.updateOffset(savedOffset, savedOffset.getOffset() + total);
                    }
                },
                Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        after();

        String endMsg = String.format(END_MSG, taskName, PERIOD_FORMAT.print(new Period(start, new DateTime())));
        endMsg += "\n" + statusMessage(modifiedCnt, totalCnt);
        log.info(endMsg);
    }

    protected String statusMessage(AtomicInteger affected, AtomicInteger total) {
        return "Affected " + FMT.format(affected) + " / " + FMT.format(total) + " processed.";
    }

    public void before() {
    }

    public void after() {
    }

    public abstract boolean operate(T t);

}
