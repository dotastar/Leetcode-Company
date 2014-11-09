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
		grid1[0] = new int[]{1,3,1};
		grid1[1] = new int[]{1,5,1};
		grid1[2] = new int[]{4,2,1};
		System.out.println(minPathSum(grid1));
		
		
		int[][] grid2 = new int[2][3];
		grid2[0] = new int[]{1,2,5};
		grid2[1] = new int[]{3,2,1};
		System.out.println(minPathSum(grid2));
	}

	/**
	 * DP	Time: O(mn), Space: O(mn)
	 * State transition formula:
	 * dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + grid[i][j]
	 */
	public static int minPathSum(int[][] grid) {
        int m = grid.length;
        if(m==0)
            return 0;
        int n = grid[0].length;
        
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for(int i=1; i<n; i++)
            dp[0][i] = dp[0][i-1]+grid[0][i];
        for(int i=1; i<m; i++)
            dp[i][0] = dp[i-1][0]+grid[i][0];
            
        for(int i=1; i<m; i++){
            for(int j=1; j<n; j++){
                dp[i][j] = dp[i-1][j]<dp[i][j-1] ? dp[i-1][j] : dp[i][j-1];
                dp[i][j] += grid[i][j];
            }
        }
        return dp[m-1][n-1];
	}
	
	/**
	 * DP, Space Improved, Time: O(mn), Space: O( min(m,n) )
	 */
    public static int minPathSum_Impr(int[][] grid) {
        int m = grid.length;
        if(m==0)
            return 0;
        int n = grid[0].length;
        
        int[] dp = new int[n];
        dp[0] = grid[0][0];
        for(int i=1; i<n; i++)
            dp[i] = dp[i-1]+grid[0][i];
            
        for(int i=1; i<m; i++){
        	//dp[i][0] = dp[i-1][0]+grid[i][0];
            dp[0] = dp[0] + grid[i][0];
            for(int j=1; j<n; j++){
            	//dp[i][j] = dp[i-1][j]<dp[i][j-1] ? dp[i-1][j] : dp[i][j-1];
                dp[j] = dp[j]<dp[j-1] ? dp[j] : dp[j-1];
                dp[j] += grid[i][j];
            }
        }
        return dp[n-1];
    }
}
