package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * 
 * Largest Rectangle Of 1s
 * Hard
 * DP
 * 
 * Determine the largest rectangle of 1s in a binary matrix (a binary matrix
 * only contains 0 and 1), return the area.
 * 
 * Assumptions
 * 
 * The given matrix is not null and has size of M * N, M >= 0 and N >= 0
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
 * the largest rectangle of 1s has area of 2 * 3 = 6
 * 
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Largest_Rectangle_Of_1s {
	private static Class<?> c = Largest_Rectangle_Of_1s.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * DP, Time: O(m*m*n)
	 * M[i][j] = the length of the longest subarray ends at (i, j) at row i
	 * 
	 * Base case: M[i][j] = A[0][j] (1/0), when size = 1;
	 * Induction rule:
	 * M[i][j] = A[i][j] == 0 ? 0 : M[i][j-1] + 1
	 * 
	 * The max area at (i, j) will be:
	 * Scan from M[i][j] to M[0][j], 0 <= k <= i, calculate every possible area,
	 * min(M[k][j]) is width, (i - k + 1) is height, and update global max area.
	 * 
	 **/
	public int largest(int[][] A) {
		int m = A.length, n = A.length == 0 ? 0 : A[0].length;
		int maxArea = 0;
		int[][] M = new int[m][n];
		// initialize M
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (j == 0)
					M[i][j] = A[i][j];
				else
					M[i][j] = A[i][j] == 0 ? 0 : M[i][j - 1] + 1;

				maxArea = Math.max(maxArea, M[i][j]);
			}
		}
		// calculate area and update max
		for (int i = 1; i < m; i++) {
			for (int j = 0; j < n; j++) {
				int width = M[i][j];
				for (int k = i; k >= 0; k--) {
					width = Math.min(M[k][j], width);
					int area = (i - k + 1) * width;
					maxArea = Math.max(maxArea, area);
				}
			}
		}
		return maxArea;
	}

	@Test
	public void test1() {
		int[][] A = { { 0 }, { 1 }, { 1 } };
		int res = largest(A);
		int ans = 2;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test2() {
		int[][] A = { { 0, 0, 0 } };
		int res = largest(A);
		int ans = 0;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test3() {
		int[][] A = { { 0, 1, 1, 1, 1 }, { 1, 1, 1, 0, 1 }, { 1, 1, 1, 0, 1 },
				{ 1, 1, 1, 0, 1 } };
		int res = largest(A);
		int ans = 9;
		assertTrue("Wrong: " + res, res == ans);
	}
}
