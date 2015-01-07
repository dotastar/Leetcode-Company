package interview.company.epic;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Dynamic programming problem: Coin change problem: Find the minimum number of
 * coins required to make change for a given sum (given unlimited number of N
 * different denominations coin)
 * 
 * @author yazhoucao
 * 
 */
public class MinCoinsSum {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(MinCoinsSum.class);
	}

	/**
	 * Dynamic programming
	 * dp[i] = the minimum number of coins to sum to i.
	 * dp[sum] = min(dp[sum-coin[0]], ..., dp[sum-coin[n-1]]) + 1.
	 */
	public int minCoins(int[] coin, int sum) {
		int[] dp = new int[sum + 1];
		for (int i = 1; i < dp.length; i++)
			dp[i] = Integer.MAX_VALUE;
		for (int i = 1; i <= sum; i++) {
			for (int cVal : coin) {
				if (cVal <= i && dp[i - cVal] < dp[i])
					dp[i] = dp[i - cVal] + 1;
			}
		}
		return dp[sum] == Integer.MAX_VALUE ? -1 : dp[sum];
	}

	@Test
	public void test1() {
		int[] coin = { 3, 4 };
		int sum = 10; // 3*2 + 4
		assertTrue(minCoins(coin, sum) == 3);
	}

	@Test
	public void test2() {
		int[] coin = { 1, 2, 5, 10 };
		int sum = 10; // the last
		assertTrue(minCoins(coin, sum) == 1);
	}

	@Test
	public void test3() {
		int[] coin = { 1, 2, 5, 10 };
		int sum = 100; // the last*10
		assertTrue(minCoins(coin, sum) == 10);
	}

	@Test
	public void test4() {
		int[] coin = { 1, 2, 5, 10 };
		int sum = 1; // the first
		assertTrue(minCoins(coin, sum) == 1);
	}

	@Test
	public void test5() {
		int[] coin = { 1, 2, 5, 10 };
		int sum = 37; // prime
		assertTrue(minCoins(coin, sum) == 5);
	}

	@Test
	public void test6() {
		int[] coin = { 2, 4 };
		int sum = 7; // negative, don't have the solution
		assertTrue(minCoins(coin, sum) == -1);
	}

	@Test
	public void test7() {
		int[] coin = { 2, 3 };
		int sum = 6;
		assertTrue(minCoins(coin, sum) == 2);
	}
}
