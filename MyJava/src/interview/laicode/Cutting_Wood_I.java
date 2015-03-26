package interview.laicode;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * 
 * Cutting Wood I
 * Hard
 * DP
 * 
 * There is a wooden stick with length L >= 1, we need to cut it into pieces,
 * where the cutting positions are defined in an int array A. The positions are
 * guaranteed to be in ascending order in the range of [1, L - 1]. The cost of
 * each cut is the length of the stick segment being cut. Determine the minimum
 * total cost to cut the stick into the defined pieces.
 * 
 * Examples
 * 
 * L = 10, A = {2, 4, 7}, the minimum total cost is 10 + 4 + 6 = 20 (cut at 4
 * first then cut at 2 and cut at 7)
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Cutting_Wood_I {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Cutting_Wood_I.class);
	}

	/**
	 * DP
	 * M[i][j]: minimum cost of cutting a wood start at i point, has j length
	 * Base case: M[i][0] = 0, M[i][1] = cuts[i + 1]/length - cuts[i - 1]/0
	 * Induction rule:
	 * M[i][len] = min(M[j][len - 1]) + currLength, 0 <= j <= i
	 */
	public int minCost_DP(int[] cuts, int length) {
		int n = cuts.length;
		if (n == 0)
			return 0;
		int[][] M = new int[n][n + 1];
		for (int len = 1; len <= n; len++) {
			for (int i = 0; i < n; i++) {
				M[i][len] = Integer.MAX_VALUE;
				int end = i + len < n ? i + len : n;
				int left = i == 0 ? 0 : cuts[i - 1];
				int right = end == n ? length : cuts[end];
				int currLength = right - left;
				for (int j = i; j < end; j++) {
					int cost = currLength;
					if (j > i)
						cost += M[i][j - i];
					if (j < end - 1)
						cost += M[j + 1][end - j - 1];
					M[i][len] = Math.min(M[i][len], cost);
				}
			}
		}
		return M[0][n];
	}

	/**
	 * DFS, brute force
	 * -- -- --- ---
	 * 2 4 7
	 */
	public int minCost(int[] cuts, int length) {
		return minCost(cuts, 0, cuts.length - 1, 0, length, length);
	}

	private int minCost(int[] cuts, int l, int r, int start, int end, int length) {
		if (l > r)
			return 0;
		int cost = Integer.MAX_VALUE;
		for (int i = l; i <= r; i++) {
			int pos = cuts[i];
			int currCost = length
					+ minCost(cuts, l, i - 1, start, pos, pos - start)
					+ minCost(cuts, i + 1, r, pos, end, end - pos);
			cost = Math.min(cost, currCost);
		}
		return cost;
	}

	@Test
	public void test1() {
		int[] cuts = { 1, 3 };
		int length = 10;
		int res = minCost_DP(cuts, length);
		int ans = 13;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test2() {
		int[] cuts = { 2, 5, 7 };
		int length = 10;
		int res = minCost_DP(cuts, length);
		int ans = 20;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test3() {
		int[] cuts = { 2, 4, 7 };
		int length = 10;
		int res = minCost_DP(cuts, length);
		int ans = 20;
		assertTrue("Wrong: " + res, res == ans);
	}

}
