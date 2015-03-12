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
	 * Greedy
	 * Because it is guaranteed that the end can be reached, so we jump only
	 * when there is running out of steps, and jump to the point which can have
	 * the maxReach point, though we don't know where exactly it is, but we
	 * don't have to know. After jumping, we have 0 + maxReach-i more steps.
	 */
	public static int jump_improved(int[] A) {
		if (A.length < 2)
			return 0;
		int maxJump = A[0], restSteps = A[0], jumps = 0;
		for (int i = 1; i < A.length; i++) {
			if (i > maxJump)
				return -1;
			if (i + A[i] > maxJump) // update max position it can reach
				maxJump = i + A[i];
			restSteps--;
			if (restSteps == 0 || i == A.length - 1) { // have to jump
				restSteps = maxJump - i;
				jumps++;
			}
		}
		return jumps;
	}

	/**
	 * Dynamic Programming dp[i]: minimal steps of jump from i to the end
	 * Time: O(n^2)
	 * 
	 * M[i] means the minimum jumps needed to reach end form i,
	 * -1 means it can't reach the end
	 * Base case M[n-1] = 0
	 * Induction rule: M[i] = min(M[j]) + 1, if M[j] >= 0, i < j <= i + A[i]
	 **/
	public static int jump(int[] A) {
		int[] M = new int[A.length];
		Arrays.fill(M, -1);
		M[A.length - 1] = 0;
		for (int i = A.length - 2; i >= 0; i--) {
			// deal with out of bound
			int j = i + A[i] > A.length - 1 ? A.length - 1 : i + A[i];
			for (; j > i; j--) {
				if (M[j] >= 0)
					M[i] = M[i] < 0 ? M[j] + 1 : Math.min(M[i], M[j] + 1);
			}
		}
		return M[0];
	}

}
