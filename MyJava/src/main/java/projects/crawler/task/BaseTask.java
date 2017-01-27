package projects.crawler.task;

import projects.crawler.data.model.Model;
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
public abstract class BaseTask<K, T extends Model<K>> implements Runnable {

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

    public BaseTask(Iterator<? extends T> iterator, int numThreads, int logFrequency) {
        this.taskName = this.getClass().getSimpleName();
        this.start = new DateTime();
        this.iterator = iterator;
        this.logFrequency = logFrequency;
        this.threadPool = new ThreadPoolExecutor(numThreads, numThreads, Long.MAX_VALUE, TimeUnit.HOURS, new LinkedBlockingQueue<>());
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
                    }
                    // Log status
                    int total = totalCnt.incrementAndGet();
                    if (logFrequency > 0 && (total % logFrequency == 0 || total == 1)) {
                        String status = statusMessage(modifiedCnt, totalCnt);
                        log.info(status);
                    }
                },
                Long.MAX_VALUE, TimeUnit.HOURS
        );

        after();

        log.info("Task {} completed, took {}.\nSummary:\n{}",
                taskName,
                PERIOD_FORMAT.print(new Period(start, new DateTime())),
                statusMessage(modifiedCnt, totalCnt));
    }

    public void shutdown() {
        threadPool.shutdown();
    }

    public boolean isTerminating() {
        return threadPool.isTerminating();
    }

    public boolean isTerminated() {
        return threadPool.isTerminated();
    }

    public void printStatus() {
        System.out.println("Affected " + FMT.format(modifiedCnt) + " / " + FMT.format(totalCnt) + " processed.");
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
