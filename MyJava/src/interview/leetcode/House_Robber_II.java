package interview.leetcode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * House Robber II
 * 
 * Note: This is an extension of House Robber.
 * 
 * After robbing those houses on that street, the thief has found himself a new
 * place for his thievery so that he will not get too much attention. This time,
 * all houses at this place are arranged in a circle. That means the first house
 * is the neighbor of the last one. Meanwhile, the security system for these
 * houses remain the same as for those in the previous street.
 * 
 * Given a list of non-negative integers representing the amount of money of
 * each house, determine the maximum amount of money you can rob tonight without
 * alerting the police.
 * 
 * @author yazhoucao
 *
 */
public class House_Robber_II {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(House_Robber_II.class);
	}

	/**
	 * DP
	 * 
	 * M[i] = the max amount of money can rob at A[i]
	 * 
	 * M[0] = A[0], M[1] = A[1]
	 * M[i] = max(M[i-1], M[i-2] + A[i])
	 * 
	 * There are only two cases:
	 * 1.Consider stealing the first house --> Start from [0, n - 2]
	 * 2.Not consider stealing the first house --> start from [1, n-1]
	 * 
	 */
	public int rob(int[] A) {
		if (A.length < 2)
			return A.length == 1 ? A[0] : 0;
		int max = A[0] > A[1] ? A[0] : A[1];
		int[] M = new int[A.length];
		// first case
		M[0] = A[0];
		M[1] = Math.max(A[0], A[1]);
		for (int i = 2; i < A.length - 1; i++) {
			M[i] = Math.max(M[i - 1], M[i - 2] + A[i]);
			max = Math.max(M[i], max);
		}
		// second case
		if (A.length >= 3) {
			M[1] = A[1];
			M[2] = Math.max(A[1], A[2]);
		}
		for (int i = 3; i < A.length; i++) {
			M[i] = Math.max(M[i - 1], M[i - 2] + A[i]);
			max = Math.max(M[i], max);
		}

		return max;
	}

	/**
	 * Optimization:
	 * Space improved, logic improved
	 */
	public int rob2(int[] A) {
		if (A.length <= 1)
			return A.length == 1 ? A[0] : 0;
		else
			return Math.max(rob(A, 0, A.length - 2), rob(A, 1, A.length - 1));
	}

	private int rob(int[] A, int l, int r) {
		if (l >= r)
			return l == r ? A[l] : 0;
		int pre_1 = Math.max(A[l], A[l + 1]), pre_2 = A[l];
		int max = Math.max(pre_1, pre_2);
		for (int i = l + 2; i <= r; i++) {
			int curr = Math.max(pre_1, pre_2 + A[i]);
			max = Math.max(curr, max);
			pre_2 = pre_1;
			pre_1 = curr;
		}
		return max;
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}
}
