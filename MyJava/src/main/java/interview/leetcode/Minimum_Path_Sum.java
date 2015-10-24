package interview.leetcode;

/**
 * Given a m x n grid filled with non-negative numbers, find a path from top
 * left to bottom right which minimizes the sum of all numbers along its path.
 * 
 * Note: You can only move either down or right at any point in time.
 * 
 * @author yazhoucao
 * 
 */
public class Minimum_Path_Sum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] grid = new int[1][1];
		grid[0][0] = 1;
		System.out.println(minPathSum(grid));

		int[][] grid1 = new int[3][3];
		grid1[0] = new int[] { 1, 3, 1 };
		grid1[1] = new int[] { 1, 5, 1 };
		grid1[2] = new int[] { 4, 2, 1 };
		System.out.println(minPathSum(grid1));

		int[][] grid2 = new int[2][3];
		grid2[0] = new int[] { 1, 2, 5 };
		grid2[1] = new int[] { 3, 2, 1 };
		System.out.println(minPathSum(grid2));
	}

	/**
	 * DP, Time: O(mn), Space: O(mn).
	 * M[i][j] is the minimum path at (i, j)
	 * Base case:
	 * M[i][0] = sum(A[0][0]+ ... + A[i][0])
	 * M[0][j] = sum(A[0][0]+ ... + A[0][j])
	 * 
	 * Induction rule:
	 * M[i][j] = min(M[i-1][j], M[i][j-1]) + A[i][j]
	 */
	public static int minPathSum(int[][] A) {
		int m = A.length, n = A.length == 0 ? 0 : A[0].length;
		if (m == 0)
			return 0;
		int[][] M = new int[m][n];
		M[0][0] = A[0][0];
		for (int i = 1; i < m; i++)
			M[i][0] = M[i - 1][0] + A[i][0];
		for (int i = 1; i < n; i++)
			M[0][i] = M[0][i - 1] + A[0][i];

		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				M[i][j] = Math.min(M[i][j - 1], M[i - 1][j]) + A[i][j];
			}
		}
		return M[m - 1][n - 1];
	}

	/**
	 * DP, Space Improved, Time: O(mn), Space: O( min(m,n) )
	 * 
	 * M[i][j] is the minimum path at (i, j)
	 * Base case:
	 * M[i][0] = sum(A[0][0]+ ... + A[i][0])
	 * M[0][j] = sum(A[0][0]+ ... + A[0][j])
	 * 
	 * Induction rule:
	 * M[i][j] = min(M[i-1][j], M[i][j-1]) + A[i][j]
	 */
	public int minPathSum2(int[][] A) {
		int m = A.length, n = A.length == 0 ? 0 : A[0].length;
		if (m == 0)
			return 0;
		int[] M = new int[n];
		M[0] = A[0][0];
		for (int i = 1; i < n; i++)
			M[i] = M[i - 1] + A[0][i];

		for (int i = 1; i < m; i++) {
			M[0] = M[0] + A[i][0];
			for (int j = 1; j < n; j++) {
				M[j] = Math.min(M[j - 1], M[j]) + A[i][j];
			}
		}
		return M[n - 1];
	}
}
