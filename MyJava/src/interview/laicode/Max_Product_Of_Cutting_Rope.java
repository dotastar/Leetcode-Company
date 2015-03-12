package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Max Product Of Cutting Rope
 * Fair
 * DP
 * 
 * Given a rope with positive integer-length n, how to cut the rope into m
 * integer-length parts with length p[0], p[1], ...,p[m-1], in order to get the
 * maximal product of p[0]*p[1]* ... *p[m-1]? m is determined by you and must be
 * greater than 0 (at least one cut must be made). Return the max product you
 * can have.
 * 
 * Assumptions
 * n >= 2
 * 
 * Examples
 * n = 12, the max product is 3 * 3 * 3 * 3 = 81(cut the rope into 4 pieces with
 * length of each is 3).
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Max_Product_Of_Cutting_Rope {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Max_Product_Of_Cutting_Rope.class);
	}

	/**
	 * Notice: at least one cut must be made
	 */

	/**
	 * Recursion, Time: n! --> O(n^n)
	 * Try cut the rope at every i.
	 * The left cut is i, the right cut is either (n - i) as a whole or keep
	 * cutting in (n - i)
	 * so maxProduct(n - i) means keep cutting n - i
	 * 
	 * Whenever first enter the function, there must be a cut. (assume n >= 2)
	 * This can make sure there will be always at least one cut.
	 * And if we enter the recursion, more cuts may not be necessary,
	 * because if (n - i) > maxProduct(n - i), we don't cut the rest.
	 */
	public int maxProduct_Recur(int n) {
		if (n == 1)
			return 1;
		int max = 0;
		for (int i = 1; i < n; i++) {
			// cut at i, left = i, right = max(maxProduct(n - i), n - i)
			int product = i * Math.max(maxProduct_Recur(n - i), n - i);
			max = product > max ? product : max;
		}
		return max;
	}

	@Test
	public void testRecursion1() {
		int n = 2;
		int res = maxProduct_Recur(n);
		int ans = 1;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void testRecursion2() {
		int n = 11;
		int res = maxProduct_Recur(n);
		int ans = 54;
		assertTrue("Wrong: " + res, res == ans);
	}

	/**
	 * DP, Time: O(n^2)
	 * M[i] means the max product when the rope has length i.
	 * Base case: M[1] = 1;
	 * Induction rule:
	 * M[i] = max(max(j, M[j]) * max(i-j, M[i-j])) for all j, 1 <= j < i
	 */
	public int maxProduct_DP(int n) {
		int[] M = new int[n + 1];
		M[1] = 1;
		int max = 1;
		for (int i = 2; i <= n; i++) {
			// optimization: because it's symmetric, only need to try i/2
			for (int j = 1; j <= i / 2; j++) {
				// at least one cut in i
				int product = Math.max(j, M[j]) * Math.max(i - j, M[i - j]);
				max = Math.max(max, product);
			}
			M[i] = max;
		}
		return M[n];
	}

	@Test
	public void testDP1() {
		int n = 2;
		int res = maxProduct_DP(n);
		int ans = 1;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void testDP2() {
		int n = 11;
		int res = maxProduct_DP(n);
		int ans = 54;
		assertTrue("Wrong: " + res, res == ans);
	}
}
