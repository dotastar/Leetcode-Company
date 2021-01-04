package interview.leetcode;

import interview.AutoTestUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;


/**
 * https://leetcode.com/problems/logger-rate-limiter/
 *
 * Design a logger system that receives a stream of messages along with their timestamps. Each unique message should only
 * be printed at most every 10 seconds (i.e. a message printed at timestamp t will prevent other identical messages from
 * being printed until timestamp t + 10).
 *
 * All messages will come in chronological order. Several messages may arrive at the same timestamp.
 *
 * Implement the Logger class:
 *
 * Logger() Initializes the logger object.
 * bool shouldPrintMessage(int timestamp, string message) Returns true if the message should be printed in the given
 * timestamp, otherwise returns false.
 *
 * Your Logger object will be instantiated and called as such:
 * Logger obj = new Logger();
 * boolean param_1 = obj.shouldPrintMessage(timestamp,message);
 */
public class Logger_Rate_Limiter {

  public static void main(String[] args) {
    AutoTestUtils.runTestClassAndPrint(Logger_Rate_Limiter.class);
  }

  private final Map<String, Integer> tsMap = new HashMap<>();

  /** Initialize your data structure here. */
  public Logger_Rate_Limiter() {}

  /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
   If this method returns false, the message will not be printed.
   The timestamp is in seconds granularity. */
  public boolean shouldPrintMessage(int timestamp, String message) {
    cleanUpExpiredKeys(timestamp);
    if (!tsMap.containsKey(message)) {
      tsMap.put(message, timestamp);
      return true;
    }
    Integer oldTs = tsMap.get(message);
    boolean shouldPrint = timestamp - oldTs >= 10;
    if (shouldPrint) {
      tsMap.put(message, timestamp);
    }
    return shouldPrint;
  }

  // This is an optimization to solve the problem that accumulating too many expired/cold keys
  private void cleanUpExpiredKeys(int timestamp) {
    if (tsMap.size() > 100) {
      Set<String> expiredKeys = tsMap.entrySet()
          .stream()
          .filter(ent -> timestamp - ent.getValue() >= 10)
          .map(Map.Entry::getKey)
          .collect(Collectors.toSet());
      expiredKeys.forEach(tsMap::remove);
    }
  }

  @Test
  public void test_sanity() {
    Logger_Rate_Limiter solution = new Logger_Rate_Limiter();
    assertThat(solution.shouldPrintMessage(0, "foo")).isTrue();
    assertThat(solution.shouldPrintMessage(0, "foo")).isFalse();
    assertThat(solution.shouldPrintMessage(1, "bar")).isTrue();
    assertThat(solution.shouldPrintMessage(5, "baz")).isTrue();
    assertThat(solution.shouldPrintMessage(9, "foo")).isFalse();
    assertThat(solution.shouldPrintMessage(10, "foo")).isTrue();
    assertThat(solution.shouldPrintMessage(19, "foo")).isFalse();
    assertThat(solution.shouldPrintMessage(20, "foo")).isTrue();
    assertThat(solution.shouldPrintMessage(20, "bar")).isTrue();
    assertThat(solution.shouldPrintMessage(21, "bar")).isFalse();

  }
}