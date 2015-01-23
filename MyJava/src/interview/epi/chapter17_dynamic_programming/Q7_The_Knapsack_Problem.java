package interview.epi.chapter17_dynamic_programming;

import general.algorithms.KnapsackProblems.Classic01Knapsack;

import java.util.Arrays;
import java.util.Random;

/**
 * Design an algorithm for the knapsack problem that selects a subset of items
 * that has maximum value and weights at most w ounces. All items have integer
 * weights and values.
 * 
 * @author yazhoucao
 * 
 */
public class Q7_The_Knapsack_Problem {

	public static void main(String[] args) {
		Q7_The_Knapsack_Problem o = new Q7_The_Knapsack_Problem();
		Random r = new Random();
		int n = r.nextInt(10) + 1, W = r.nextInt(100) + 1;
		int[] weight = randArray(n);
		int[] value = randArray(n);

		System.out.println("Weight: " + Arrays.toString(weight));
		System.out.println("Value: " + Arrays.toString(value));

		System.out.println("knapsack size = " + W);
		Classic01Knapsack o1 = new Classic01Knapsack();
		int res = o.knapsack_Improve(W, weight, value);
		int ans = o1.naive(weight, value, W);
		assert res == ans;
		System.out.println("all value = " + res);
	}

	/**
	 * Space improved
	 */
	public int knapsack_Improve(int W, int[] weights, int[] prices) {
		int len = weights.length;
		int[] dp = new int[W + 1];
		for (int i = 1; i < len + 1; i++) {
			for (int j = W; j >= 0; j--) {
				int wi = weights[i - 1], pi = prices[i - 1];
				dp[j] = wi > j ? dp[j] : Math.max(dp[j - wi] + pi, dp[j]);
			}
			// System.out.println(Arrays.toString(dp));
		}
		return dp[W];
	}

	/**
	 * Key:
	 * dp[i][j] =
	 * if wi > j: dp[i - 1][j], 
	 * otherwise: max(dp[i - 1][j - wi] + pi, dp[i- 1][j]).
	 */
	public int knapsack(int W, int[] weights, int[] prices) {
		int len = weights.length;
		int[][] dp = new int[len + 1][W + 1];
		for (int i = 1; i < len + 1; i++) {
			for (int j = 1; j < W + 1; j++) {
				int wi = weights[i - 1], pi = prices[i - 1];
				dp[i][j] = wi > j ? dp[i - 1][j] : Math.max(dp[i - 1][j - wi] + pi, dp[i - 1][j]);
			}
			System.out.println(Arrays.toString(dp[i]));
		}
		return dp[len][W];
	}

	private static int[] randArray(int len) {
		int[] ret = new int[len];
		Random r = new Random();
		for (int i = 0; i < len; i++)
			ret[i] = r.nextInt(40) + 1;
		return ret;
	}
}
