package projects.crawler.utils;

import java.util.Optional;
import org.slf4j.Logger;


/**
 * Convenience methods when dealing with recovering tasks
 */
public class RecoveryHelper {

  private RecoveryHelper() {
    // utility class
  }

  /**
   * Log the error and return an empty {@link Optional}
   */
  public static <T> Optional<T> errorLoggingOptional(Logger logger, String message, Throwable e) {
    logger.error(message, e);
    return Optional.empty();
  }

  /**
   * Log the error and return {@code defaultValue}
   */
  public static <T> T errorLoggingDefault(Logger logger, String message, Throwable e, T defaultValue) {
    logger.error(message, e);
    return defaultValue;
  }
}