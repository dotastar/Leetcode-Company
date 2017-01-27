package interview.leetcode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Given a 2D binary matrix filled with 0's and 1's, find the largest square
 * containing all 1's and return its area.
 * 
 * For example, given the following matrix:
 * 
 * 1 0 1 0 0
 * 1 0 1 1 1
 * 1 1 1 1 1
 * 1 0 0 1 0
 * 
 * Return 4.
 * 
 * @author yazhoucao
 *
 */
public class Maximal_Square {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Maximal_Square.class);
	}

	/**
	 * DP
	 * 
	 * M[i][j]: length of the largest square whose bottom-left ended at [i, j]
	 * 
	 * Induction rule:
	 * M[i][j] : 0 if matrix[i][j] == 0
	 * else, min(M[i - 1][j], M[i][j - 1], M[i - 1][j - 1]) + 1
	 */
	public int maximalSquare(char[][] matrix) {
		int m = matrix.length, n = matrix.length == 0 ? 0 : matrix[0].length;
		int[][] M = new int[m][n];
		int max = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (i == 0 || j == 0)
					M[i][j] = matrix[i][j] == '1' ? 1 : 0; // base case
				else
					M[i][j] = matrix[i][j] == '0' ? 0 : min(M[i - 1][j], M[i][j - 1],
							M[i - 1][j - 1]) + 1; // induction rule
				max = M[i][j] > max ? M[i][j] : max;
			}
		}
		return max * max;
	}

	private int min(int a, int b, int c) {
		if (a < b)
			return a < c ? a : c;
		else
			return b < c ? b : c;
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}
}
