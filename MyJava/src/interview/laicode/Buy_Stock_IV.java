package interview.laicode;

import static org.junit.Assert.*;

import java.util.Arrays;

import interview.AutoTestUtils;

import org.junit.Test;

/**
 * 
 * Buy Stock IV
 * Hard
 * DP
 * 
 * Given an array of integers representing a stock’s price on each day. On each
 * day you can only make one operation: either buy or sell one unit of stock,
 * and at any time you can only hold at most one unit of stock, and you can make
 * at most K transactions in total. Determine the maximum profit you can make.
 * 
 * Assumptions
 * 
 * The give array is not null and is length of at least 2
 * 
 * Examples
 * 
 * {2, 3, 2, 1, 4, 5, 2, 11}, K = 3, the maximum profit you can make is (3 - 2)
 * + (5 - 1) + (11 - 2)= 14
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Buy_Stock_IV {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Buy_Stock_IV.class);
	}

	/**
	 * DP
	 * global[i][k] : the i-th day and made k transactions
	 * local[i][k] : the i-th day and made k transactions, and the last
	 * transaction is made on the i-th day
	 * 
	 * Base case: local[i][0] = 0, global[i][0] = 0, for all i
	 * Induction rule:
	 * local[i][k] = max(global[i-1][k-1] + max(0 currProfit), local[i][k-1] +
	 * currProfit)
	 * global[i][k] = max(global[i-1][k], local[i][k])
	 */
	public int maxProfit(int[] A, int k) {
		int[][] global = new int[A.length][k + 1];
		int[][] local = new int[A.length][k + 1];
		for (int i = 1; i < A.length; i++) {
			for (int j = 1; j <= k; j++) {
				int currProfit = A[i] - A[i - 1];
				local[i][j] = max(global[i - 1][j - 1] + max(currProfit, 0),
						local[i - 1][j] + currProfit);
				global[i][j] = max(local[i][j], global[i - 1][j]);
			}
		}
		
		for (int i = 0; i < A.length; i++)
			System.out.println(Arrays.toString(local[i]) + "\t" + Arrays.toString(global[i]));
		return global[A.length - 1][k];
	}

	private int max(int a, int b) {
		return a > b ? a : b;
	}

	@Test
	public void test1() {
		int[] A = { 5, 1, 2, 3, 7, 2, 5, 1, 3 };
		int K = 1;
		int ans = 6;
		int res = maxProfit(A, K);
		assertTrue("Wrong answer: " + res, ans == res);
	}
	
	@Test
	public void test2() {
		int[] A = { 2, 3, 2, 1, 4, 5, 2, 11 };
		int K = 3;
		int ans = 14;
		int res = maxProfit(A, K);
		assertTrue("Wrong answer: " + res, ans == res);
	}
}
