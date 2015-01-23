package interview.leetcode;

/**
 * Follow up for "Unique Paths":
 * 
 * Now consider if some obstacles are added to the grids. How many unique paths
 * would there be?
 * 
 * An obstacle and empty space is marked as 1 and 0 respectively in the grid.
 * 
 * For example, There is one obstacle in the middle of a 3x3 grid as illustrated
 * below.
 * 
 * [
 * [0,0,0],
 * [0,1,0],
 * [0,0,0]
 * ]
 * 
 * The total number of unique paths is 2.
 * 
 * Note: m and n will be at most 100.
 * 
 * @author yazhoucao
 * 
 */
public class Unique_Paths_II {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		uniquePathsWithObstacles_Improved(new int[][] { { 1 } });
	}

	/**
	 * DP, Space Improved, Time: O(mn), Space( min(m, n) )
	 */
	public static int uniquePathsWithObstacles_Improved(int[][] grid) {
		int m = grid.length;
		int n = m == 0 ? 0 : grid[0].length;
		int[] dp = new int[n];
		dp[0] = 1; // assume grid[0][0] can't be 1
		for (int i = 0; i < m; i++) {
			dp[0] = grid[i][0] == 1 ? 0 : dp[0];
			for (int j = 1; j < n; j++) {
				dp[j] = grid[i][j] == 1 ? 0 : dp[j] + dp[j - 1];
			}
		}
		return dp[n - 1];
	}

	/**
	 * DP, Time O(mn), Space O(mn)
	 */
	public int uniquePathsWithObstacles(int[][] obstacleGrid) {
		int m = obstacleGrid.length;
		if (m == 0)
			return 0;
		int n = obstacleGrid[0].length;
		int[][] dp = new int[m][n];

		// init
		boolean meetObstacle = false;
		for (int i = 0; i < n; i++) {
			if (obstacleGrid[0][i] == 1)
				meetObstacle = true;
			if (meetObstacle)
				dp[0][i] = 0;
			else
				dp[0][i] = 1;
		}

		meetObstacle = false;
		for (int i = 0; i < m; i++) {
			if (obstacleGrid[i][0] == 1)
				meetObstacle = true;
			if (meetObstacle)
				dp[i][0] = 0;
			else
				dp[i][0] = 1;
		}

		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				if (obstacleGrid[i][j] == 1) {
					dp[i][j] = 0;
				} else {
					dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
				}
			}
		}
		return dp[m - 1][n - 1];
	}
}
