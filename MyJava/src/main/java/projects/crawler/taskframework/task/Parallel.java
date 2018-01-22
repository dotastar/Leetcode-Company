package projects.crawler.taskframework.task;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author asia created on 10/4/15.
 */
@Slf4j
public class Parallel {

  public static <S extends T, T> void For(
      ThreadPoolExecutor threadPoolExecutor,
      final Iterator<S> iterator,
      final Operation<T> operation,
      Long wait,
      TimeUnit waitUnit
  ) {
    final ThreadSafeIterator<S> threadSafeIter = new ThreadSafeIterator<>(iterator);
    for (int i = 0; i < threadPoolExecutor.getMaximumPoolSize(); i++) {
      threadPoolExecutor.submit(() -> { // a Callable class
            T ele;
            while ((ele = threadSafeIter.next()) != null) {
              try {
                operation.perform(ele);
              } catch (RuntimeException e) {
                if (e.getCause() != null && e.getCause() instanceof InterruptedException) {
                  log.error("Task interrupted: " + e.getCause());
                  return null;
                }
                log.error("Runtime Exception during execution of parallel task: ", e);
              } catch (Exception e) {
                log.error("Exception during execution of parallel task: ", e);
              }
            }
            return null;
          }
      );
    }

    threadPoolExecutor.shutdown();

    if (wait != null) {
      try {
        threadPoolExecutor.awaitTermination(wait, waitUnit);
      } catch (InterruptedException e) {
        Throwables.propagate(e);
      }
    } else {
      log.info("Not waiting on task completion");
    }
  }

  public interface Operation<T> {
    void perform(T element);
  }

  private static class ThreadSafeIterator<T> {

    private final Iterator<T> itr;

    public ThreadSafeIterator(Iterator<T> itr) {
      this.itr = itr;
    }

    public synchronized T next() {
      return itr.hasNext() ? itr.next() : null;
    }
  }
}
