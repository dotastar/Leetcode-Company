package interview.company.epic;

/**
 * You are given a grid of numbers. A snake sequence is made up of adjacent
 * numbers such that for each number, the number on the right or the number
 * below it is +1 or -1 its value. For example,
 * 
 * 1 3 2 6 8
 * -9 7 1 -1 2
 * 1 5 0 1 9
 * 
 * In this grid, (3, 2, 1, 0, 1) is a snake sequence.
 * 
 * Given a grid, find the longest snake sequences and their lengths (so there
 * can be multiple snake sequences with the maximum length).
 * 
 * @author yazhoucao
 * 
 */
public class SnakeSequence {

	public static void main(String[] args) {
		SnakeSequence o = new SnakeSequence();
		int[][] grid = { 
				{ 1, 3, 2, 6, 8 }, 
				{ 9, 7, 1, 0, -1 },
				{ 1, 5, 0, 1, 9 } };
		
		System.out.println(o.longestSnakeLength(grid));
		System.out.println(o.longestSnakeLength_Improved(grid));
	}

	/**
	 * Dynamic Programming
	 * dp[i][j] = max(fromTop, fromLeft) + 1.
	 * 1. fromTop = if(abs(dp[i-1][j]-dp[i][j])==1) dp[i-1][j] else 0
	 * 2. fromLeft = if(abs(dp[i][j-1]-dp[i][j])==1) dp[i][j-1] else 0
	 */
	public int longestSnakeLength(int[][] grid) {
		int m = grid.length;
		int n = m == 0 ? 0 : grid[0].length;
		int max = 0;
		int[][] dp = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				int top = i == 0 ? 0 : Math.abs(grid[i - 1][j] - grid[i][j]) == 1 ? dp[i - 1][j] : 0;
				int left = j == 0 ? 0 : Math.abs(grid[i][j - 1] - grid[i][j]) == 1 ? dp[i][j - 1] : 0;
				dp[i][j] = top > left ? top + 1 : left + 1;
				if(dp[i][j]>max)
					max = dp[i][j];
			}
		}
		return max;
	}

	/**
	 * Space improved
	 */
	public int longestSnakeLength_Improved(int[][] grid) {
		int m = grid.length;
		int n = m == 0 ? 0 : grid[0].length;
		int max = 0;
		int[] dp = new int[n];
		// initialize
		dp[0] = 1;
		for (int i = 1; i < n; i++)
			dp[i] = Math.abs(grid[0][i - 1] - grid[0][i]) == 1 ? dp[i - 1] + 1 : 1;

		for (int i = 1; i < m; i++) {
			for (int j = 0; j < n; j++) {
				int top = Math.abs(grid[i - 1][j] - grid[i][j]) == 1 ? dp[j] + 1: 1;
				int left = j==0 ? 1 : Math.abs(grid[i][j - 1] - grid[i][j]) == 1 ? dp[j - 1] + 1 : 1;
				dp[j] = top > left ? top : left;
				if(dp[j]>max)
					max = dp[j];
			}
		}
		return max;
	}
}
