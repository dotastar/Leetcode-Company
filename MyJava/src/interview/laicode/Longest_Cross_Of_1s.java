package interview.laicode;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.Arrays;

import org.junit.Test;

/**
 * 
 * Longest Cross Of 1s
 * Hard
 * DP
 * 
 * Given a matrix that contains only 1s and 0s, find the largest cross which
 * contains only 1s, with the same arm lengths and the four arms joining at the
 * central point.
 * 
 * Return the arm length of the largest cross.
 * 
 * Assumptions
 * 
 * The given matrix is not null
 * 
 * Examples
 * 
 * { {0, 0, 0, 0},
 * 
 * {1, 1, 1, 1},
 * 
 * {0, 1, 1, 1},
 * 
 * {1, 0, 1, 1} }
 * 
 * the largest cross of 1s has arm length 2.
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Longest_Cross_Of_1s {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Longest_Cross_Of_1s.class);
	}

	/**
	 * Time: O(MN)
	 * M[i][j] : the length of longest cross of 1s at i, j
	 **/
	public int largest(int[][] A) {
		int m = A.length, n = A.length == 0 ? 0 : A[0].length;
		int[][][] M = new int[m][n][4];
		int max = 0;
		// traverse from left to right, top-down
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (j == 0)
					M[i][j][0] = A[i][j];
				else
					M[i][j][0] = A[i][j] == 0 ? 0 : M[i][j - 1][0] + 1;
			}
		}
		// traverse from right to left, top-down
		for (int i = 0; i < m; i++) {
			for (int j = n - 1; j >= 0; j--) {
				if (j == n - 1)
					M[i][j][1] = A[i][j];
				else
					M[i][j][1] = A[i][j] == 0 ? 0 : M[i][j + 1][1] + 1;
			}
		}
		// traverse from top to bottom, left-right
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < m; i++) {
				if (i == 0)
					M[i][j][2] = A[i][j];
				else
					M[i][j][2] = A[i][j] == 0 ? 0 : M[i - 1][j][2] + 1;
			}
		}
		// traverse from bottom to top, left-right, and update the max
		for (int j = 0; j < n; j++) {
			for (int i = m - 1; i >= 0; i--) {
				if (i == m - 1)
					M[i][j][3] = A[i][j];
				else
					M[i][j][3] = A[i][j] == 0 ? 0 : M[i + 1][j][3] + 1;

				int length = min(M[i][j]);
				max = Math.max(max, length);
			}
		}
		return max;
	}

	private int min(int[] A) {
		if (A.length == 0)
			return 0;
		int min = A[0];
		for (int i = 1; i < A.length; i++) {
			if (min > A[i])
				min = A[i];
		}
		return min;
	}

	@Test
	public void test1() {
		int[][] mat = { { 0, 0, 0, 0 },

		{ 1, 1, 1, 1 },

		{ 0, 1, 1, 1 },

		{ 1, 0, 1, 1 } };
		int res = largest(mat);
		int ans = 2;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test2() {
		int[][] mat = {

		{ 1, 1, 1, 0, 1 },

		{ 1, 0, 1, 1, 1 },

		{ 1, 1, 1, 1, 1 },

		{ 1, 0, 1, 1, 0 },

		{ 0, 0, 1, 1, 0 } };
		int res = largest(mat);
		int ans = 3;
		assertTrue("Wrong: " + res, res == ans);
	}

	public void printMatrix(int[][] mat) {
		for (int i = 0; i < mat.length; i++)
			System.out.println(Arrays.toString(mat[i]));
		System.out.println();
	}
}
