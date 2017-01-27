package interview.leetcode;

/**
 * Say you have an array for which the ith element is the price of a given stock
 * on day i.
 * 
 * Design an algorithm to find the maximum profit. You may complete as many
 * transactions as you like (ie, buy one and sell one share of the stock
 * multiple times).
 * 
 * However, you may not engage in multiple transactions at the same time (ie,
 * you must sell the stock before you buy again).
 * 
 * @author yazhoucao
 * 
 */
public class Best_Time_to_Buy_and_Sell_Stock_II {

	public static void main(String[] args) {
		int[] prices0 = { 1, 2, 4 }; // 3
		int[] prices1 = { 1, 4, 6, 5, 7, -2, 3, 1, 8 }; // 19

		System.out.println(maxProfit(prices0));
		System.out.println(maxProfit(prices1));
	}

	/**
	 * Greedy: buy and sell it as long as it is in an increasing order
	 */
	public static int maxProfit(int[] prices) {
		int profit = 0;
		for (int i = 1; i < prices.length; i++) {
			if (prices[i] > prices[i - 1])
				profit += prices[i] - prices[i - 1];
		}
		return profit;
	}

}
