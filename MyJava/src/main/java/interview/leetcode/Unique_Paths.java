package interview.leetcode;

/**
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in
 * the diagram below).
 * 
 * The robot can only move either down or right at any point in time. The robot
 * is trying to reach the bottom-right corner of the grid (marked 'Finish' in
 * the diagram below).
 * 
 * How many possible unique paths are there?
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Unique_Paths {

	public static void main(String[] args) {
		System.out.println("Recursion:\t" + uniquePaths(3, 7));
		System.out.println("DP:\t\t" + uniquePaths_DP(3, 7));
	}

	/**
	 * Dynamic Programming, Time: O(mn), Space: O(mn).
	 * 
	 * Transition state formula:
	 * dp[i][j] = dp[i-1][j] + dp[i][j-1])
	 * 
	 * @return
	 */
	public static int uniquePaths_DP(int m, int n) {
		int[][] dp = new int[m][n];
		for (int i = 0; i < n; i++)
			dp[0][i] = 1;

		for (int i = 1; i < m; i++) {
			dp[i][0] = 1;
			for (int j = 1; j < n; j++) {
				dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
			}
		}
		return dp[m - 1][n - 1];
	}

	/**
	 * DP, Space Improved, Time: O(mn), Space: O(min(m,n)).
	 * the same as dp[i][j] = dp[i-1][j] + dp[i][j-1]), just reuse the array
	 * 
	 * another improve is eliminate a separate initialization of the first row,
	 * integrate the initialization into the dp process (so i start from 0)
	 */
	public static int uniquePaths_DP_Improved(int m, int n) {
		int[] dp = new int[n];
		for (int i = 0; i < m; i++) {
			dp[0] = 1;
			for (int j = 1; j < n; j++) {
				dp[j] = dp[j] + dp[j - 1];
			}
		}
		return dp[n - 1];
	}

	/**
	 * Recursion
	 */
	public static int uniquePaths(int m, int n) {
		if (m < 0 || n < 0)
			return 0;
		if (m == 1 && n == 1)
			return 1;
		return uniquePaths(m - 1, n) + uniquePaths(m, n - 1);
	}
}
