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
	 * 
	 * This problem is similar with 3 Sum. This kind of problem can be solve by
	 * using similar approach, i.e., two pointers from both left and right.
	 * 
	 * O(n^2)
	 * 
	 * @return
	 */
	public static int threeSumClosest(int[] num, int target) {
		Arrays.sort(num);
		int closest = 0;
		int minDiff = Integer.MAX_VALUE;
		for(int i=0; i<num.length; i++){
			int j = i+1;
			int k = num.length-1;
			while(j<k){
				int sum = num[i] + num[j] + num[k];
				int diff = Math.abs(target-sum);
				if(diff<minDiff){
					minDiff = diff;
					closest = sum;
				}
				if(sum==target)	return sum;
				if(sum>target)	k--;
				else	j++;
			}
		}
		return closest;
	}

}
