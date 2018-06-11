package general.algorithms.evaluation;

import interview.AutoTestUtils;
import java.util.Arrays;
import org.junit.Test;

import static com.google.common.truth.Truth.*;


/**
 * CA1
 * Given a list of 'N' coin types each of them has unique value.
 * The coin values are stored in in an array values[], return the minimum number of coins required to sum to 'S'
 *
 */
public class MinimumNumberOfCoins {

  public static void main(String[] args) {
    AutoTestUtils.runTestClassAndPrint(MinimumNumberOfCoins.class);
  }

  @Test
  public void test1() {
    int[] values = new int[]{2, 5, 7};
    int sum = 9;
    int actual = minNumCoins(values, sum);
    int expected = 2;
    assertThat(actual).isSameAs(expected);
  }

  @Test
  public void test2() {
    int[] values = new int[]{2, 5};
    int sum = 9;
    int actual = minNumCoins(values, sum);
    int expected = 3;
    assertThat(actual).isSameAs(expected);
  }

  /**
   * DP solution - Time: O(N * I)
   * dp[n]: min # coins needed to sum to n
   * v[i]: unique coin value at index i
   * dp[n] = min(dp[n - v[i]]) + 1, for all i that (n - v[i] >= 0) and i ranges from 0 to v.length - 1
   *
   * E.g.
   * coins: $2, $5, $7
   * dp 0  1  2  3  4 5 6 7 8 9
   *    0 Max 1 Max 2 1 3 1 4 2
   *
   */
  private static int minNumCoins(int[] values, int sum) {
    Arrays.sort(values);
    int[] dp = new int[sum + 1];
    for (int n = 1; n <= sum; n++) {
      dp[n] = Integer.MAX_VALUE - 1; // Note: make sure it doesn't overflow when plus 1
      for (int i = 0; i < values.length && values[i] <= n; i++) { // Note: can early quit loop - if (values[i] <= n)
        int currMinNum = dp[n - values[i]] + 1;
        if (currMinNum < dp[n]) {
          dp[n] = currMinNum;
        }
      }
    }
    System.out.println("Computed dp: " + Arrays.toString(dp));
    return dp[sum];
  }
}
