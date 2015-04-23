package interview.laicode;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.Arrays;

import org.junit.Test;

/**
 * Largest SubMatrix Sum
 * Hard
 * DP
 * 
 * Given a matrix that contains integers, find the submatrix with the largest
 * sum.
 * 
 * Return the sum of the submatrix.
 * 
 * Assumptions
 * 
 * The given matrix is not null and has size of M * N, where M >= 1 and N >= 1
 * 
 * Examples
 * 
 * {
 * {1, -2, -1, 4},
 * 
 * {1, -1, 1, 1},
 * 
 * {0, -1, -1, 1},
 * 
 * {0, 0, 1, 1} }
 * 
 * the largest submatrix sum is (-1) + 4 + 1 + 1 + (-1) + 1 + 1 + 1 = 7.
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Largest_SubMatrix_Sum {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Largest_SubMatrix_Sum.class);
	}

	/**
	 * Time: O(m*m*n), Space: O(n)
	 * Try every top and bottom rows combinations, then two borders are fixed,
	 * converted it to a max subarray sum problem.
	 * cur[j]: current max sum that starts at row start, ends at row i, ends at
	 * column j
	 */
	public int largest2(int[][] A) {
		int res = Integer.MIN_VALUE;
		int M = A.length, N = A[0].length;
		int[] cur = new int[N]; // current max sum
		for (int start = 0; start < M; start++) { // start row, fix top border
			for (int i = start; i < M; i++) { // current row
				add(cur, A[i]); // add from start row to the end row
				res = Math.max(res, max(cur));
			}
			Arrays.fill(cur, 0);
		}
		return res;
	}

	private void add(int[] cur, int[] add) {
		for (int i = 0; i < cur.length; i++)
			cur[i] += add[i];
	}

	/**
	 * Maximum sum of subarray
	 */
	private int max(int[] cur) {
		if (cur.length == 0)
			return Integer.MIN_VALUE;
		int res = cur[0], temp = cur[0];
		for (int i = 1; i < cur.length; i++) {
			temp = Math.max(temp + cur[i], cur[i]);
			res = Math.max(temp, res);
		}
		return res;
	}

	/**
	 * DP, Time: (mn)^4 + (mn)^2 = O((mn)^4)
	 * 
	 * M[i][j]:
	 * the total sum of sub-matrix from top-left(0, 0) to bottom-right(i, j)
	 * Base case: M[0][j] = A[0][j]
	 * Induction rule:
	 * M[i][j] = total sum of sub-array at row i end at j + M[i - 1][j]
	 * 
	 * There are total (mn)^2 submatrix
	 * 
	 * The sum of each sub-matrix start at (i, j), end at (row, col):
	 * sum = M[row][col] - M[row][j-1] - M[i-1][col] + M[i-1][j-1]
	 */
	public int largest(int[][] A) {
		int m = A.length, n = A.length == 0 ? 0 : A[0].length;
		int maxSum = Integer.MIN_VALUE;
		int[][] M = new int[m][n];
		// initialize M
		for (int i = 0; i < m; i++) {
			int rowSum = 0;
			for (int j = 0; j < n; j++) {
				rowSum += A[i][j];
				M[i][j] = i == 0 ? rowSum : M[i - 1][j] + rowSum;

				maxSum = Math.max(maxSum, Math.max(M[i][j], A[i][j]));
			}
		}
		printMatrix(M);

		// calculate each sub-matrix's sum
		for (int row = 0; row < m; row++) {
			for (int col = 0; col < n; col++) {
				for (int i = 0; i <= row; i++) {
					for (int j = 0; j <= col; j++) {
						int currSum = M[row][col];
						if (i > 0)
							currSum -= M[i - 1][col];
						if (j > 0)
							currSum -= M[row][j - 1];
						if (i > 0 && j > 0)
							currSum += M[i - 1][j - 1];
						// System.out.println(currSum);
						maxSum = Math.max(currSum, maxSum);
					}
				}
			}
		}
		return maxSum;
	}

	public void printMatrix(int[][] mat) {
		for (int i = 0; i < mat.length; i++)
			System.out.println(Arrays.toString(mat[i]));
		System.out.println();
	}

	@Test
	public void test1() {
		int[][] mat = { { 0 } };

		int res = largest2(mat);
		int ans = 0;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test2() {
		int[][] mat = {

		{ 0, -5, 0, 0, 0 },

		{ 1, -2, 1, 1, 4 },

		{ 1, 0, 2, 1, -1 } };

		int res = largest2(mat);
		int ans = 8;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test3() {
		int[][] mat = {

		{ 1, -2, -1, 4 },

		{ 1, -1, 1, 1 },

		{ 0, -1, -1, 1 },

		{ 0, 0, 1, 1 } };

		int res = largest2(mat);
		int ans = 7;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test4() {
		int[][] mat = {

		{ 2, -1, 2, 1, -3 },

		{ 0, -2, -1, 2, 1 },

		{ 3, 2, 1, -3, -2 } };

		int res = largest2(mat);
		int ans = 6;
		assertTrue("Wrong: " + res, res == ans);
	}
}
