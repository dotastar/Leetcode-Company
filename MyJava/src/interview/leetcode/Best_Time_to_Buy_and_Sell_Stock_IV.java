package interview.leetcode;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.Arrays;

import org.junit.Test;

/**
 * 
 * Say you have an array for which the ith element is the price of a given stock
 * on day i.
 * 
 * Design an algorithm to find the maximum profit. You may complete at most k
 * transactions.
 * 
 * Note:
 * You may not engage in multiple transactions at the same time (ie, you must
 * sell the stock before you buy again).
 * 
 * @author yazhoucao
 * 
 */
public class Best_Time_to_Buy_and_Sell_Stock_IV {
	static Class<?> c = Best_Time_to_Buy_and_Sell_Stock_IV.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * DP
	 * M[i][j]: is the max profit for up to i transactions by time j (0<=i<=K,
	 * 0<=j<=T).
	 * 
	 * Base case:
	 * prftAfterBuy = -prices[0];
	 * 
	 * Induction rule:
	 * M[i][j] = Math.max(M[i][j - 1], prices[j] + prftAfterBuy);
	 * (prices[j] + prftAfterBuy means the maximum profit when sell it at j)
	 * prftAfterBuy = Math.max(prftAfterBuy, M[i - 1][j - 1] - prices[j]);
	 * 
	 * When k >= len/2, that means we can do as many transactions as we want.
	 * So, in case k >= len/2, this problem is same to Best Time to Buy and Sell
	 * Stock III
	 */
	public int maxProfit(int k, int[] prices) {
		int len = prices.length;
		if (k >= len / 2)
			return quickSolve(prices);

		int[][] M = new int[k + 1][len];
		for (int i = 1; i <= k; i++) {
			int prftAfterBuy = -prices[0];
			for (int j = 1; j < len; j++) {
				// gives us the maximum price when we can sell at this price
				M[i][j] = Math.max(M[i][j - 1], prices[j] + prftAfterBuy);
				// gives us the value when we buy at this price and leave this
				// value for prices[j+1].
				prftAfterBuy = Math.max(prftAfterBuy, M[i - 1][j - 1] - prices[j]);
			}
		}
		return M[k][len - 1];
	}

	private int quickSolve(int[] prices) {
		int len = prices.length, profit = 0;
		for (int i = 1; i < len; i++)
			// as long as there is a price gap, we gain a profit.
			if (prices[i] > prices[i - 1])
				profit += prices[i] - prices[i - 1];
		return profit;
	}

	public void printMat(int[][] mat) {
		for (int i = 0; i < mat.length; i++) {
			System.out.println(Arrays.toString(mat[i]));
		}
		System.out.println();
	}

	@Test
	public void test1() {
		int[] prices = { 1, 3, 6, 2, 4, 0, 5, 4, 8 };
		int k = 2;
		int ans = 13;
		int res = maxProfit(k, prices);
		assertTrue("Wrong: " + res, ans == res);
	}

	@Test
	public void test2() {
		int[] prices = { 1, 3, 6, 2, 4, 0, 5, 4, 8 };
		int k = 3;
		int ans = 15;
		int res = maxProfit(k, prices);
		assertTrue("Wrong: " + res, ans == res);
	}

	@Test
	public void test3() {
		int[] prices = { 5, 4, 3, 2, 1 };
		int k = 3;
		int ans = 0;
		int res = maxProfit(k, prices);
		assertTrue("Wrong: " + res, ans == res);
	}

}
