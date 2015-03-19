package interview.laicode;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * 
 * Largest X Of 1s
 * Hard
 * DP
 * 
 * Given a matrix that contains only 1s and 0s, find the largest X shape which
 * contains only 1s, with the same arm lengths and the four arms joining at the
 * central point.
 * 
 * Return the arm length of the largest X shape.
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
 * the largest X of 1s has arm length 2.
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Largest_X_Of_1s {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Largest_X_Of_1s.class);
	}

	/**
	 * 
	 */
	public int largest(int[][] A) {
		int max = 0;
		int m = A.length, n = A.length == 0 ? 0 : A[0].length;
		int[][][] M = new int[m][n][4];
		/*** direction (M-1, 0) to (0, N-1) diagonally (top left half) ***/
		// starting line (0, 0) to (M-1, 0)
		for (int row = 1; row <= m; row++) {
			for (int i = row - 1, j = 0; i >= 0 && j < n; i--, j++) {
				if (A[i][j] == 0 || j == 0 || i == row - 1)
					M[i][j][0] = A[i][j];
				else
					M[i][j][0] = M[i + 1][j - 1][0] + 1;
			}
		}
		// direction (M-1, 0) to (0, N-1) diagonally (bottom right half)
		// starting line (M-1, 1) to (M-1, N-1)
		for (int row = 2; row <= n; row++) {
			for (int i = m - 1, j = row - 1; i >= 0 && j < n; i--, j++) {
				if (A[i][j] == 0 || j == row - 1 || i == m - 1)
					M[i][j][0] = A[i][j];
				else
					M[i][j][0] = M[i + 1][j - 1][0] + 1;
			}
		}
		printMatrix(M, 0);

		/**** direction (0, N-1) to (M-1, 0) diagonally (top left half) ****/
		// starting line (0, 0) to (0, N-1)
		for (int row = 1; row <= n; row++) {
			for (int i = 0, j = row - 1; i < m && j >= 0; i++, j--) {
				if (A[i][j] == 0 || j == row - 1 || i == 0)
					M[i][j][1] = A[i][j];
				else
					M[i][j][1] = M[i - 1][j + 1][1] + 1;
			}
		}
		// direction (0, N-1) to (M-1, 0) diagonally (bottom right half)
		// starting line (1, N-1) to (M-1, N-1)
		for (int row = 2; row <= m; row++) {
			for (int i = row - 1, j = n - 1; i < m && j >= 0; i++, j--) {
				if (A[i][j] == 0 || j == n - 1 || i == row - 1)
					M[i][j][1] = A[i][j];
				else
					M[i][j][1] = M[i - 1][j + 1][1] + 1;
			}
		}
		printMatrix(M, 1);

		/*** direction (0, 0) to (M-1, N-1) diagonally (top right half) ***/
		// starting line (0, N - 1) to (0, 0)
		for (int row = n; row > 0; row--) {
			for (int i = 0, j = row - 1; i < m && j < n; i++, j++) {
				if (A[i][j] == 0 || j == row - 1 || i == 0)
					M[i][j][2] = A[i][j];
				else
					M[i][j][2] = M[i - 1][j - 1][2] + 1;
			}
		}
		// direction (0, 0) to (M-1, N-1) diagonally (bottom left half)
		// starting line (1, 0) to (M-1, 0)
		for (int row = 2; row <= m; row++) {
			for (int i = row - 1, j = 0; i < m && j < n; i++, j++) {
				if (A[i][j] == 0 || i == row - 1 || j == 0)
					M[i][j][2] = A[i][j];
				else
					M[i][j][2] = M[i - 1][j - 1][2] + 1;
			}
		}
		printMatrix(M, 2);

		/*** direction (M-1, N-1) to (0, 0) diagonally (top right half) ***/
		// starting line (0, N-1) to (M-1, N-1)
		for (int row = 1; row <= m; row++) {
			for (int i = row - 1, j = n - 1; i >= 0 && j >= 0; i--, j--) {
				if (A[i][j] == 0 || i == row - 1 || j == n - 1)
					M[i][j][3] = A[i][j];
				else
					M[i][j][3] = M[i + 1][j + 1][3] + 1;
				
				max = Math.max(max, min(M[i][j]));
			}
		}
		// direction (M-1, N-1) to (0, 0) diagonally (bottom left half)
		// starting line (M-1, N-2) to (M-1, 0)
		for (int row = n - 1; row > 0; row--) {
			for (int i = m - 1, j = row - 1; i >= 0 && j >= 0; i--, j--) {
				if (A[i][j] == 0 || i == m - 1 || j == row - 1)
					M[i][j][3] = A[i][j];
				else
					M[i][j][3] = M[i + 1][j + 1][3] + 1;

				max = Math.max(max, min(M[i][j]));
			}
		}
		printMatrix(M, 3);
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

	public void printMatrix(int[][][] mat, int thirdDimension) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				System.out.print(mat[i][j][thirdDimension] + " ");
			}
			System.out.println();
		}

		System.out.println();
	}

	@Test
	public void test1() {
		int[][] mat = { {} };

		int res = largest(mat);
		int ans = 0;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test2() {
		int[][] mat = {

		{ 0, 0, 0, 0 },

		{ 1, 1, 1, 1 },

		{ 0, 1, 1, 1 },

		{ 1, 0, 1, 1 } };

		int res = largest(mat);
		int ans = 2;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test3() {
		int[][] mat = {

		{ 1, 1, 1, 1, 1 },

		{ 1, 0, 0, 1, 1 },

		{ 1, 1, 1, 1, 1 },

		{ 1, 1, 1, 1, 0 },

		{ 0, 0, 0, 1, 1 } };

		int res = largest(mat);
		int ans = 2;
		assertTrue("Wrong: " + res, res == ans);
	}

}
