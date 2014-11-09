package interview.leetcode;

import java.util.Arrays;

/**
 * Given an array of non-negative integers, you are initially positioned at the
 * first index of the array.
 * 
 * Each element in the array represents your maximum jump length at that
 * position.
 * 
 * Your goal is to reach the last index in the minimum number of jumps.
 * 
 * For example: Given array A = [2,3,1,1,4]
 * 
 * The minimum number of jumps to reach the last index is 2. (Jump 1 step from
 * index 0 to 1, then 3 steps to the last index.)
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Jump_Game_II {

	public static void main(String[] args) {
		System.out.print(jump(new int[] { 1, 1 }) + "\t");
		System.out.println(jump_improved(new int[] { 1, 1 }));

		System.out.print(jump(new int[] { 1, 2, 1, 1, 1 }) + "\t");
		System.out.println(jump_improved(new int[] { 1, 2, 1, 1, 1 }));

		System.out.print(jump(new int[] { 2, 3, 0, 1, 4 }) + "\t");
		System.out.println(jump_improved(new int[] { 2, 3, 0, 1, 4 }));

		System.out.print(jump(new int[] { 7, 0, 9, 6, 9, 6, 1, 7, 9, 0, 1, 2,
				9, 0, 3 })
				+ "\t");
		System.out.println(jump_improved(new int[] { 7, 0, 9, 6, 9, 6, 1, 7, 9,
				0, 1, 2, 9, 0, 3 }));

	}

	/**
	 * Dynamic Programming dp[i]: minimal steps of jump from i to the end Time:
	 * O(n^2)
	 * 
	 * @param A
	 * @return
	 */
	public static int jump(int[] A) {
		int[] dp = new int[A.length];
		for (int i = A.length - 2; i >= 0; i--) {
			int onestep = A.length - 1 - i;
			if (A[i] >= onestep) {
				dp[i] = 1;
			} else {
				// find the minimal jump point that i can jump to
				int min = dp[i + 1];
				for (int j = i + 1; j <= i + A[i]; j++) {
					if (dp[j] < min)
						min = dp[j];
				}
				dp[i] = min + 1;
			}
		}
		return dp[0];
	}

	/**
	 * Because it is guranteed that the end can be reached, so we jump only when
	 * there is running out of steps, and jump to the point which can have the
	 * maxReach point, though we don't know where exactly it is, but we don't
	 * have to know. After jumping, we have 0 + maxReach-i more steps.
	 * 
	 * @param A
	 * @return
	 */
	public static int jump_improved(int[] A) {
		if (A.length <= 1)
			return 0;
		int jumps = 1;
		int maxReach = A[0];
		int steps = A[0];
		System.out.println("Input:"+Arrays.toString(A));
		for (int i = 1; i < A.length; i++) {
			System.out.println("step:"+steps+"\tjumps:"+jumps+"\tmaxReach:"+maxReach);
			if (i == A.length - 1)
				return jumps;
			if (i + A[i] > maxReach)
				maxReach = i + A[i];
			steps--;
			if (steps == 0) { // need more steps, jump
				steps = maxReach - i;
				jumps++;
			}
		}
		return jumps;
	}
	
}
