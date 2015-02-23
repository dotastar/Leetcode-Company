package interview.leetcode;

import java.util.Arrays;

/**
 * Given an array S of n integers, find three integers in S such that the sum is
 * closest to a given number, target. Return the sum of the three integers. You
 * may assume that each input would have exactly one solution.
 * 
 * For example, given array S = {-1 2 1 -4}, and target = 1.
 * 
 * The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
 * 
 * @author yazhoucao
 * 
 */
public class Sum3_Closest {

	public static void main(String[] args) {
		int[] num = new int[] { 0, 2, 1, -3 };
		int target = 1;
		System.out.println(threeSumClosest(num, target));
	}

	/**
	 * Thoughts
	 * This problem is similar with 3 Sum. This kind of problem can be solve by
	 * using similar approach, i.e., two pointers from both left and right.
	 * 
	 * O(n^2)
	 * 
	 */
	public static int threeSumClosest(int[] num, int target) {
		Arrays.sort(num);
		int minDiff = Integer.MAX_VALUE, bestSum = 0;
		for (int i = 0; i < num.length - 2; i++) {
			int l = i + 1, r = num.length - 1, first = num[i];
			while (l < r) {
				int sum = first + num[l] + num[r];
				if (Math.abs(sum - target) < minDiff) {
					bestSum = sum;
					minDiff = Math.abs(sum - target);
				}

				if (sum == target)
					return target;
				else if (sum > target)
					r--;
				else
					l++;
			}
		}
		return bestSum;
	}

}
