package interview.leetcode;

/**
 * Say you have an array for which the ith element is the price of a given stock
 * on day i.
 * 
 * If you were only permitted to complete at most one transaction (ie, buy one
 * and sell one share of the stock), design an algorithm to find the maximum
 * profit.
 * 
 * @author yazhoucao
 * 
 */
public class Best_Time_to_Buy_and_Sell_Stock {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] prices0 = new int[] { 1, 53, 2, 33, 44, 22, 0, 37, 55, 12 };
		System.out.println(maxProfit(prices0));
	}

	/**
	 * Second time
	 */
	public int maxProfit2(int[] prices) {
		if (prices.length < 2)
			return 0;
		int min = prices[0], profit = 0;
		;
		for (int i = 0; i < prices.length; i++) {
			min = Math.min(prices[i], min);
			profit = Math.max(profit, prices[i] - min);
		}
		return profit;
	}

	public static int maxProfit(int[] prices) {
		if (prices.length == 0)
			return 0;
		int buy = prices[0], diff = 0;
		for (int i = 1; i < prices.length; i++) {
			if (prices[i] < buy)
				buy = prices[i];
			else
				diff = prices[i] - buy > diff ? prices[i] - buy : diff;
		}
		return diff;
	}
}
