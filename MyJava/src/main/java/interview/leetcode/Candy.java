package interview.leetcode;

/**
 * There are N children standing in a line. Each child is assigned a rating
 * value.
 * 
 * You are giving candies to these children subjected to the following
 * requirements:
 * 
 * 1.Each child must have at least one candy.
 * 
 * 2.Children with a higher rating get more candies than their neighbors.
 * 
 * What is the minimum candies you must give?
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Candy {

	public static void main(String[] args) {
		int[] rates1 = new int[] { 2, 2 }; // 2
		System.out.println(candy(rates1));

		int[] rates2 = new int[] { 1, 2, 2 }; // 4
		System.out.println(candy(rates2));

		int[] rates3 = new int[] { 1, 0, 2 }; // 5
		System.out.println(candy(rates3));

		int[] rates4 = new int[] { 4, 2, 3, 4, 1 };// 9
		System.out.println(candy(rates4));
	}

	/**
	 * Two dependencies:
	 * 1.For left to right: if Rating[l] > Rating[r], then Candy[l] > Candy[r]
	 * to minimize the total number candies, then Candy[l] = Candy[r] + 1
	 * 2.For right to left: if Rating[r] > Rating[l], then Candy[r] > Candy[l]
	 * to minimize the total number candies, then Candy[r] = Candy[l] + 1
	 */

	/**
	 * Greedy, Time O(n)
	 */
	public static int candy(int[] ratings) {
		int[] candies = new int[ratings.length];
		candies[0] = 1;
		// scan left to right and initialization
		for (int i = 1; i < candies.length; i++) { // left to right
			if (ratings[i] > ratings[i - 1])
				candies[i] = candies[i - 1] + 1;
			else
				candies[i] = 1;
		}

		// scan right to left and sum up all candies
		int sum = candies[candies.length - 1];
		for (int i = candies.length - 2; i >= 0; i--) { // right to left
			// only when right+1>left, current candies[i] can't be decreased
			if (ratings[i] > ratings[i + 1] && candies[i + 1] + 1 > candies[i])
				candies[i] = candies[i + 1] + 1;
			sum += candies[i];
		}
		return sum;
	}
}
