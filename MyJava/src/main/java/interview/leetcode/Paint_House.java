package interview.leetcode;

/**
 * There are a row of n houses, each house can be painted with one of the three colors: red, blue or green. The cost of painting each house with a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.
 * <p>
 * The cost of painting each house with a certain color is represented by a n x 3 cost matrix. For example, costs[0][0] is the cost of painting house 0 with color red; costs[1][2] is the cost of painting house 1 with color green, and so on... Find the minimum cost to paint all houses.
 * <p>
 * Note:
 * All costs are positive integers.
 * <p>
 * Hide Company Tags LinkedIn
 * Hide Tags Dynamic Programming
 * Show Similar Problems
 *
 * @author asia created on 1/13/16.
 */
public class Paint_House {

  /**
   * DP
   * M[i][j] : min cost from [0, i] houses that the current house[i] is painted by color[j]
   * <p>
   * M[i][0] = min(M[i-1][1], M[i-1][2]) + costs[i][0]
   * M[i][1] = ...
   * M[i][2] = ...
   */
  public int minCost(int[][] costs) {
    int n = costs.length;
    int[][] M = new int[n + 1][3];
    for (int i = 1; i <= n; i++) {
      M[i][0] = Math.min(M[i - 1][1], M[i - 1][2]) + costs[i - 1][0];
      M[i][1] = Math.min(M[i - 1][0], M[i - 1][2]) + costs[i - 1][1];
      M[i][2] = Math.min(M[i - 1][0], M[i - 1][1]) + costs[i - 1][2];
    }
    return Math.min(M[n][0], Math.min(M[n][1], M[n][2]));
  }
}
