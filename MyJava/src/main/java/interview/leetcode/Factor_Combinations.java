package interview.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Numbers can be regarded as product of its factors. For example,
 * <p>
 * 8 = 2 x 2 x 2;
 * = 2 x 4.
 * Write a function that takes an integer n and return all possible combinations of its factors.
 * <p>
 * Note:
 * Each combination's factors must be sorted ascending, for example: The factors of 2 and 6 is [2, 6], not [6, 2].
 * You may assume that n is always positive.
 * Factors should be greater than 1 and less than n.
 * Examples:
 * input: 1
 * output:
 * []
 * input: 37
 * output:
 * []
 * input: 12
 * output:
 * [
 * [2, 6],
 * [2, 2, 3],
 * [3, 4]
 * ]
 * input: 32
 * output:
 * [
 * [2, 16],
 * [2, 2, 8],
 * [2, 2, 2, 4],
 * [2, 2, 2, 2, 2],
 * [2, 4, 4],
 * [4, 8]
 * ]
 * Hide Company Tags LinkedIn
 * Hide Tags Backtracking
 * Show Similar Problems
 *
 * @author asia created on 1/13/16.
 */
public class Factor_Combinations {
  public List<List<Integer>> getFactors(int n) {
    List<List<Integer>> all = new ArrayList<>();
    getFactors(n, 2, new ArrayList<>(), all);
    return all;
  }

  private void getFactors(int n, int start, List<Integer> factors, List<List<Integer>> all) {
    if (n == 1)
      return;
    if (factors.size() > 0) {
      // Insight: Only add n if it is >= previously added number
      if (n >= factors.get(factors.size() - 1)) {
        List<Integer> copy = new ArrayList<>(factors);
        copy.add(n);
        all.add(copy);
      } else {
        return;
      }
    }
    for (int i = start; i <= n / 2; i++) {
      if (n % i == 0) {
        factors.add(i);
        getFactors(n / i, i, factors, all);
        factors.remove(factors.size() - 1);
      }
    }
  }
}
