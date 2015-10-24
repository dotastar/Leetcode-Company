package general.algorithms;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Given a set of non-negative integers, and a value sum, determine if there is
 * a subset of the given set with sum equal to given sum.
 * Examples: set[] = {1, 4, 20, 5, 7}, sum = 8
 *
 * Partition problem
 * http://en.wikipedia.org/wiki/Partition_problem#Recurrence_relation
 * 
 * @author yazhoucao
 *
 */
public class SubsetSum {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(SubsetSum.class);
	}

	/**
	 * DFS
	 * Time: O(2^n), Space: O(n)
	 */
	public boolean subsetSum(int[] set, int sum) {
		return subsetHelper(set, 0, sum);
	}

	private boolean subsetHelper(int[] set, int i, int sum) {
		if (sum <= 0 || i == set.length)
			return sum == 0;
		return subsetHelper(set, i + 1, sum - set[i]) || subsetHelper(set, i + 1, sum);
	}

	/**
	 * DP ---> M[sum][n]
	 * M[i][j] : represents whether there is a subset from the 1st element to
	 * the j-the element (A[0 ... j-1]), whose sum is equal to i.
	 * (Note that j == 0 represents empty set here).
	 * 
	 * M[i][j] = if i - A[j - 1] < 0: A[i][j - 1]
	 * if i - A[j - 1] >= 0:
	 ******* M[i - A[j - 1]][j - 1] // use the j-th elment
	 ******** || M[i][j - 1] // do not use the j-th element
	 */
	public boolean subsetSum_DP(int[] A, int sum) {
		int n = A.length;
		boolean[][] M = new boolean[sum + 1][n + 1];
		for (int i = 0; i <= n; i++)
			M[0][i] = true;
		for (int i = 1; i <= sum; i++) {
			for (int j = 1; j <= n; j++) {
				M[i][j] = i >= A[j - 1] ? (M[i - A[j - 1]][j - 1] || M[i][j - 1])
						: M[i][j - 1];
			}
		}
		return M[sum][n];
	}

	@Test
	public void test1() {
		int[] subset = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int sum = 33;
		assertTrue(subsetSum(subset, sum) == subsetSum_DP(subset, sum));
	}

	@Test
	public void test2() {
		int[] subset = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int sum = 56;
		assertTrue(subsetSum(subset, sum) == subsetSum_DP(subset, sum));
	}

	@Test
	public void test3() {
		int[] subset = { 2, 2, 5, 2, 1, 9, 7, 13 };
		int sum = 20;
		assertTrue(subsetSum(subset, sum) == subsetSum_DP(subset, sum));
	}

}
