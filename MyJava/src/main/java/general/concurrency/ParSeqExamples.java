package general.concurrency;

import com.linkedin.parseq.Engine;
import com.linkedin.parseq.EngineBuilder;
import com.linkedin.parseq.Task;
import com.linkedin.parseq.function.Try;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ParSeqExamples {

  private final static Engine ENGINE;

  static {
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    final EngineBuilder builder = new EngineBuilder().setTaskExecutor(scheduler).setTimerScheduler(scheduler);
    ENGINE = builder.build();
  }

  private final static String TEXT_TO_TEST = "hello world";

  public static void main(String[] args) throws InterruptedException {
    what_is_toTry();
    how_recover_works();

    ENGINE.shutdown();
  }

  private static void what_is_toTry() throws InterruptedException {
    Task<String> successTask = Task.value(TEXT_TO_TEST);
    // toTry() will catch and wrap error/failures happened in the task and return a successful completion state:
    Task<Try<String>> successTryTask = Task.value(TEXT_TO_TEST).toTry();
    Task<Try<String>> failedTryTask = Task.callable(() -> TEXT_TO_TEST.substring(TEXT_TO_TEST.length() + 100)).toTry();

    Task<String> parTask = Task.par(successTask, successTryTask, failedTryTask).map((successResult, successTryResult, failedTryResult) -> {
      log.info(successResult);
      log.info(successTryResult.get());
      log.info(failedTryResult.get());
      return "success";
    });

    executeTaskAndPrint("Task.par() result is: ", parTask);
  }

  private static void how_recover_works() throws InterruptedException {
    /**
     * build a task that will fail due to StringIndexOutOfBoundsException
     */
    Task<String> task = Task.callable(() -> TEXT_TO_TEST.substring(TEXT_TO_TEST.length() + 100));
    executeTaskAndPrint("original task: ", task);

    /**
     * chain a recover() to handle original failure task
     */
    Task<Optional<String>> recoveredTask = task
        .map("toOptional", subStr -> subStr == null ? Optional.<String>empty() : Optional.of(subStr))
        .recover(e -> Optional.of("recovered value from Exception[" + e.getMessage() + "]"));

    executeTaskAndPrint("recoveredTask result is: ", recoveredTask);

    /**
     * after recoveredTask, chain a failure task due to IndexOutOfBoundsException without recover()
     */
    Task<Integer> notRecoveredTask = recoveredTask
        .map("change optional to string", opt -> new ArrayList<Integer>())
        .map("substring() again", list -> list.get(999));
    executeTaskAndPrint("notRecoveredTask result is: ", notRecoveredTask);

    /**
     * after notRecoveredTask, chain a recover()
     */
    Task<Integer> recoverNotRecoveredTask = notRecoveredTask.recover(e -> {
      log.debug("Print error inside recover(): " + e);
      return 0;
    });
    executeTaskAndPrint("recoverNotRecoveredTask result is: ", recoverNotRecoveredTask);
  }

  private static <T> void executeTaskAndPrint(String message, Task<T> task) {
    ENGINE.blockingRun(task);

    try {
      task.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      log.info(message + task.get());
    } catch (Exception e) {
      log.error(message + e);
    }
  }
}
