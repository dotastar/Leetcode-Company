package general.concurrency;

import com.linkedin.parseq.Engine;
import com.linkedin.parseq.EngineBuilder;
import com.linkedin.parseq.Task;
import com.linkedin.parseq.function.Try;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class ParSeqExamples {

  private final static Engine ENGINE;

  static {
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    final EngineBuilder builder = new EngineBuilder().setTaskExecutor(scheduler).setTimerScheduler(scheduler);
    ENGINE = builder.build();
  }

  private final static String TEXT_TO_TEST = "hello world";

  public static void main(String[] args) throws InterruptedException {
    Task<String> successTask = Task.value(TEXT_TO_TEST);
    // toTry() will catch and wrap error/failures happened in the task and return a successful completion state:
    Task<Try<String>> successTryTask = Task.value(TEXT_TO_TEST).toTry();
    Task<Try<String>> failedTryTask = Task.callable(() -> TEXT_TO_TEST.substring(TEXT_TO_TEST.length() + 100)).toTry();

    Task<String> parTask = Task.par(successTask, successTryTask, failedTryTask).map((successResult, successTryResult, failedTryResult) -> {
      System.out.println(successResult);
      System.out.println(successTryResult);
      System.out.println(failedTryResult);
      return "success";
    });
    ENGINE.blockingRun(parTask);
    parTask.await();
    System.out.println("Task.par() result is: " + parTask.get());
  }
}
