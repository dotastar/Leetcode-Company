package interview.leetcode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * You are a professional robber planning to rob houses along a street. Each
 * house has a certain amount of money stashed, the only constraint stopping you
 * from robbing each of them is that adjacent houses have security system
 * connected and it will automatically contact the police if two adjacent houses
 * were broken into on the same night.
 * 
 * Given a list of non-negative integers representing the amount of money of
 * each house, determine the maximum amount of money you can rob tonight without
 * alerting the police.
 * 
 * @author yazhoucao
 *
 */
public class House_Robber {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(House_Robber.class);
	}

	/**
	 * DP
	 * M[i] = the max amount of money can rob that ends at i (must rob A[i])
	 * 
	 * M[i] = Math.max(M[i -2], M[i - 3]) + A[i]
	 */
	public int rob(int[] A) {
		if (A.length <= 2) {
			int res = 0;
			for (int a : A)
				res = Math.max(a, res);
			return res;
		}
		int res = Math.max(A[0], A[1]);
		int[] M = new int[A.length + 1];
		M[1] = A[0];
		M[2] = A[1];
		for (int i = 2; i < A.length; i++) {
			M[i + 1] = Math.max(M[i - 1], M[i - 2]) + A[i];
			res = Math.max(M[i + 1], res);
		}
		return res;
	}

	/**
	 * A more concise solution
	 * Use two variables, even and odd, to track the maximum value so far as
	 * iterating the array
	 */
	public int rob2(int[] num) {
		if (num == null || num.length == 0)
			return 0;

		int even = 0;
		int odd = 0;

		for (int i = 0; i < num.length; i++) {
			if (i % 2 == 0) {
				even += num[i];
				even = even > odd ? even : odd;
			} else {
				odd += num[i];
				odd = even > odd ? even : odd;
			}
		}

		return even > odd ? even : odd;
	}

	@Test
	public void test1() {
		int[] A = { 1, 3, 7, 2, 3, 6 };
		int res = rob(A);
		int ans = 14;
		assertTrue(res == ans);
	}

	@Test
	public void test2() {
		int[] A = { 1, 3, 1 };
		int res = rob(A);
		int ans = 3;
		assertTrue(res == ans);
	}
}
