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

	public static void main(String[] args) {
		AutoTestUtils
				.runTestClassAndPrint(Best_Time_to_Buy_and_Sell_Stock_IV.class);
	}

	public int maxProfit(int k, int[] prices) {
		int len = prices.length;
		int[][] t = new int[k + 1][len];
		for (int i = 1; i <= k; i++) {
			int tmpMax = -prices[0];
			// System.out.println("tmpMax: " + tmpMax);
			System.out.print(t[i][0] + "(" + tmpMax +")" + "\t");
			for (int j = 1; j < len; j++) {
				t[i][j] = Math.max(t[i][j - 1], prices[j] + tmpMax);
				tmpMax = Math.max(tmpMax, t[i - 1][j - 1] - prices[j]);
				System.out.print(t[i][j] + "(" + tmpMax +")" + "\t");
			}
			System.out.println();
			// printMat(t);
		}
		return t[k][len - 1];
	}

	private void printMat(int[][] mat) {
		for (int i = 0; i < mat.length; i++) {
			System.out.println(Arrays.toString(mat[i]));
		}
		System.out.println();
	}

	@Test
	public void test1() {
		int[] prices = { 1, 3, 6, 2, 4, 0, 8 };
		int k = 2;
		int ans = 13;
		int res = maxProfit(k, prices);
		assertTrue("Wrong: " + res, ans == res);
	}

	@Test
	public void test2() {
		int[] prices = {};
		int k = 2;
		int ans = 14;
		// int res = maxProfit(k, prices);
		// assertTrue("Wrong: " + res, ans == res);
	}

	@Test
	public void test3() {
		int[] prices = {};
		int k = 2;
		int ans = 14;
		// int res = maxProfit(k, prices);
		// assertTrue("Wrong: " + res, ans == res);
	}
}
