package interview.leetcode;

/**
 * Say you have an array for which the ith element is the price of a given stock
 * on day i.
 * 
 * Design an algorithm to find the maximum profit. You may complete at most two
 * transactions.
 * 
 * Note: You may not engage in multiple transactions at the same time (ie, you
 * must sell the stock before you buy again).
 * 
 * @author yazhoucao
 * 
 */
public class Best_Time_to_Buy_and_Sell_Stock_III {

	public static void main(String[] args) {
		Best_Time_to_Buy_and_Sell_Stock_III obj = new Best_Time_to_Buy_and_Sell_Stock_III();
		int[] A1 = { 6, 1, 3, 2, 4, 7 };
		System.out.println(obj.maxProfit(A1, 2));
	}

	public int maxProfit(int[] prices) {
		int len = prices.length;
		if (len == 0)
			return 0;
		int[] left = new int[len];
		int[] right = new int[len];
		int min = prices[0]; // init left
		for (int i = 1; i < len; i++) {
			if (prices[i] < min)
				min = prices[i];
			left[i] = Math.max(left[i - 1], prices[i] - min); // DP transtion
																// formula
		}

		int max = prices[len - 1]; // init right
		for (int i = len - 2; i >= 0; i--) {
			if (prices[i] > max)
				max = prices[i];
			right[i] = Math.max(right[i + 1], max - prices[i]); // DP transtion
																// formula
		}

		int total = 0; // max total profit
		for (int i = 0; i < len; i++) {
			if (left[i] + right[i] > total)
				total = left[i] + right[i];
		}
		return total;
	}

	/**
	 * A framework to solve problem that can only uses k times transactions.
	 * DP, similar to knapsack problem, Time: O(nk), Space: O(nk).
	 * 
	 * global[i][j] : the maximum profit can get till i days and j transactions
	 * local[i][j] : the maximum profit can get till i days and j transactions
	 * and the jth transaction happened at the ith day.
	 * 
	 * prft_i: the ith day's profit which is prices[i] - prices[i-1]
	 * local[i][j] = max(global[i-1][j-1]+max(prft_i,0), local[i-1][j]+prft_i)
	 * global[i][j] = max(global[i-1][j], local[i][j]);
	 * 
	 * Notice in the formula of local[i][j], the term "local[i-1][j]+prft_i" is
	 * using j not j-1 which means merge the transaction in the jth transaction
	 * of (i-1)th day into the jth transaction of the ith day, or you can think
	 * it as don't sell it on the (i-1)th day, but sell it on the ith day.
	 */
	public int maxProfit(int[] prices, int k) {
		int len = prices.length;
		if (len == 0)
			return 0;
		int[][] local = new int[len][k + 1];
		int[][] global = new int[len][k + 1];

		for (int i = 1; i < len; i++) {
			int prft = prices[i] - prices[i - 1];
			for (int j = 1; j <= k; j++) {
				// Notice the term "local[i-1][j]+prft", is j, not j-1!
				local[i][j] = max(global[i - 1][j - 1] + max(prft, 0), local[i - 1][j] + prft);
				global[i][j] = max(global[i - 1][j], local[i][j]);
			}
		}

		return global[len - 1][k];
	}

	/**
	 * The same idea as above, DP, but space improved, just like the space
	 * improved knapsack problem.
	 * 
	 * Time: O(nk), Space: O(k).
	 */
	public int maxProfit_SpaceImproved(int[] prices, int k) {
		int len = prices.length;
		if (len == 0)
			return 0;
		int[] local = new int[k + 1];
		int[] global = new int[k + 1];

		for (int i = 1; i < len; i++) {
			int prft = prices[i] - prices[i - 1];
			for (int j = k; j >= 1; j--) {
				// Notice the term "local[i-1][j]+prft", is j, not j-1!
				local[j] = max(global[j - 1] + max(prft, 0), local[j] + prft);
				global[j] = max(global[j], local[j]);
			}
		}

		return global[k];
	}

	private int max(int a, int b) {
		return a > b ? a : b;
	}

	public void printState(int[][] dp) {
		int m = dp.length;
		int n = m == 0 ? 0 : dp[0].length;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				System.out.print(dp[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}
}
