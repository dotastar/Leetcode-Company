package projects.crawler.parseq.engine;

import com.google.common.base.Verify;
import com.linkedin.parseq.Engine;
import com.linkedin.parseq.EngineBuilder;
import com.linkedin.parseq.Task;
import com.linkedin.parseq.retry.RetryPolicy;
import com.linkedin.parseq.retry.RetryPolicyBuilder;
import com.linkedin.parseq.retry.backoff.BackoffPolicy;
import com.linkedin.parseq.retry.termination.TerminationPolicy;
import com.linkedin.parseq.trace.TraceUtil;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import projects.crawler.network.SimpleHttpClient;


@Slf4j
@Data
public class CrawlEngine {
  private EngineConfig engineConfig;
  private RetryPolicy retryPolicy;
  private final ExecutorService taskScheduler;
  private final ScheduledExecutorService timerScheduler;
  private final Engine engine;

  @Inject
  public CrawlEngine(EngineConfig engineConfig) {
    this.taskScheduler = Executors.newFixedThreadPool(engineConfig.getEngineThreads());
    this.timerScheduler = Executors.newSingleThreadScheduledExecutor();
    this.engineConfig = engineConfig;
    this.engine = new EngineBuilder()
        .setTaskExecutor(taskScheduler)
        .setTimerScheduler(timerScheduler)
        .setEngineProperty("_MonitorExecution_", true)
        .build();
    this.retryPolicy = new RetryPolicyBuilder()
        .setTerminationPolicy(TerminationPolicy.limitAttempts(engineConfig.getRetryAttempt()))
        .setBackoffPolicy(BackoffPolicy.fibonacci(engineConfig.getBackoffTimeMs()))
        .build();
    log.info("{} starts successfully.", CrawlEngine.class.getSimpleName());
  }

  public void shutdown() {
    this.engine.shutdown();
    try {
      this.engine.awaitTermination(engineConfig.getEngineShutdownTimeoutSecond(), TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      log.error("Failed to shutdown engine.", e);
    }
    this.taskScheduler.shutdown();
    this.timerScheduler.shutdown();
    log.info("{} shutdown successfully.", CrawlEngine.class.getSimpleName());
  }

  public Task<Optional<Document>> getWithRetry(String url) {
    return Task.withRetryPolicy("getWithRetry:" + url, retryPolicy, () -> SimpleHttpClient.asyncGet(url));
  }

  public static void main(String[] args) throws InterruptedException {
    String url = "https://github.com/humpydonkey";
    CrawlEngine engine = new CrawlEngine(new EngineConfig());
    Task<Optional<Document>> task = engine.getWithRetry(url);
    engine.getEngine().blockingRun(task);
    task.await(5, TimeUnit.SECONDS);
    Optional<Document> optionalPage = task.get();
    Verify.verify(optionalPage.isPresent());
    Document page = optionalPage.get();
    Elements elements = page.getElementsContainingOwnText("sql-processing-engine");
    Verify.verify(!elements.isEmpty(), "html:\n" + elements.html());
    SimpleHttpClient.close();
    engine.shutdown();
    System.out.println("Get " + url + " successfully!");
  }

  public static <T> void printTaskTrace(Task<T> task) {
    try {
      log.info("Task Trace:\n{}\n", TraceUtil.getJsonTrace(task));
    } catch (IOException e1) {
      log.error("Failed to getJsonTrace()", e1);
    }
  }
}
