package interview.epi.chapter17_dynamic_programming;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

/**
 * Longest non-decreasing subsequence, longest increasing subsequence
 * 
 * @author yazhoucao
 * 
 */
public class Q21_Find_The_Longest_Nondecreasing_Subsequence {

	static Class<?> c = Q21_Find_The_Longest_Nondecreasing_Subsequence.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * DP, Time: O(n^2), Space: O(n).
	 * dp[i]: the length of the longest non-decreasing subsequence end at i.
	 * dp[i] = max(d[0], dp[1],..., d[j]) + 1,
	 * for all j = [0...i-1] that A[j] < A[i]
	 */
	public int longestSubsequence(int[] A) {
		if (A.length == 0)
			return 0;
		int[] dp = new int[A.length];
		dp[0] = 1;
		int longest = 1;
		for (int i = 1; i < A.length; i++) {
			int max = 0; // max value of dp[0] to dp[i-1]
			for (int j = 0; j < i; j++) {
				max = A[j] <= A[i] && dp[j] > max ? dp[j] : max;
			}
			dp[i] = max + 1;
			longest = dp[i] > longest ? dp[i] : longest;
		}
		return longest;
	}

	/**
	 * [Not finished, still have problem, To be continued...]
	 * 
	 * Greedy + Binary Search, Time: O(nlogn), Space: O(n)
	 * The improvement happens at the inner loop by using binary search.
	 * Besides the array dp[], let's have another array c[], c is pretty
	 * special, c[i] means: the minimum value of the last element of the longest
	 * increasing sequence whose length is i.
	 */
	public int longestSubsequence_Improve(int[] A) {
		if (A.length == 0)
			return 0;
		int[] dp = new int[A.length];
		int[] c = new int[A.length + 1];
		Arrays.fill(c, Integer.MIN_VALUE);
		dp[0] = 1;
		c[1] = A[0];
		int sz = 1, longest = 1;
		for (int i = 1; i < A.length; i++) {
			if (A[i] < c[1]) { // you have to update the minimum value right now
				c[1] = A[i];
				dp[i] = 1;
			} else if (A[i] >= c[sz]) {
				c[++sz] = A[i];
				dp[i] = sz;
			} else { // you want to find k so that c[k-1]<array[i]<c[k]
				// After searching, k is 1 greater than the length of the
				// longest prefix of X[i]
				int k = binarySearch(c, sz, A[i]);
				c[k] = A[i];
				dp[i] = k;
			}
			longest = dp[i] > longest ? dp[i] : longest;
		}
		return longest;
	}

	private static int binarySearch(int[] A, int size, int tar) {
		int l = 0, r = size;
		while (l <= r) {
			int mid = l + (r - l) / 2;
			if (A[mid] < tar)
				l = mid + 1;
			else
				r = mid - 1;
		}
		return l;
	}

	@Test
	public void test1() {
		int[] A = { 0, 8, 4, 12, 2, 10, 6, 14, 1, 9 };
		int res = longestSubsequence_Improve(A);
		int ans = 4;
		assertTrue(res == ans);
	}

	@Test
	public void test2() {
		int[] A = { 0, 10, 11, 1, 2, 3, 4, 14, 9, 5 };
		int res = longestSubsequence_Improve(A);
		int ans = 6;
		assertTrue(res == ans);
	}

	@Test
	public void test3() {
		int[] A = { 0, 2, 1, 4, 3, 6, 5, 8, 7, 9 };
		int res = longestSubsequence_Improve(A);
		int ans = 6;
		assertTrue(res == ans);
	}

	@Test
	public void test4() {
		int[] A = { -3, -3, -3, -3, -3, -4 };
		int res = longestSubsequence_Improve(A);
		int ans = 5;
		assertTrue(res == ans);
	}

	@Test
	public void test5() {
		int[] A = { 182, 1, 283, 298, 50, 415, 17, 111, 127, 103, 300, 22, 295,
				403, 22, 21, 160, 197, 244, 9, 470, 372, 287, 37, 435, 222,
				370, 93 };
		int res = longestSubsequence_Improve(A);
		int ans = longestSubsequence(A);
		assertTrue(res == ans);
	}

	@Test
	public void randomTest() {
		Random ran = new Random();
		for (int times = 0; times < 100; times++) {
			int n = ran.nextInt(50) + 10;
			int[] A = new int[n];
			for (int i = 0; i < n; i++)
				A[i] = ran.nextInt(500);

			int res1 = longestSubsequence(A);
			int res2 = longestSubsequence_Improve(A);
			assertTrue(
					Arrays.toString(A) + "\nres1:" + res1 + ", res2:" + res2,
					res1 == res2);
		}

	}
}
