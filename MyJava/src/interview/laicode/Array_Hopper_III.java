package interview.laicode;

import java.util.Arrays;

/**
 * 
 Array Hopper III
 * Fair
 * DP
 * 
 * Given an array of non-negative integers, you are initially positioned at
 * index 0 of the array. A[i] means the maximum jump distance from that position
 * (you can only jump towards the end of the array). Determine the minimum
 * number of jumps you need to jump out of the array.
 * 
 * By jump out, it means you can not stay at the end of the array. Return -1 if
 * you can not do so.
 * 
 * Assumptions
 * 
 * The given array is not null and has length of at least 1.
 * 
 * Examples
 * 
 * {1, 3, 2, 0, 2}, the minimum number of jumps needed is 3 (jump to index 1
 * then to the end of array, then jump out)
 * 
 * {3, 2, 1, 1, 0}, you are not able to jump out of array, return -1 in this
 * case.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Array_Hopper_III {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Same greedy, neater, more general solution
	 */
	public int minJump3(int[] A) {
		int start = 0, end = 0, jump = 0;
		while (end < A.length) {
			jump++;
			int farthest = 0;
			for (int i = start; i <= end; i++) {
				farthest = Math.max(A[i] + i, farthest);
			}
			if (farthest == end)
				return -1;
			start = end + 1;
			end = farthest;
		}
		return jump;
	}

	/**
	 * Greedy algorithm
	 */
	public int minJump2(int[] A) {
		if (A[0] >= A.length) // special case
			return 1;
		int maxReach = A[0], steps = 1, rest = A[0];
		for (int i = 0; i < A.length; i++, rest--) {
			if (maxReach < i)
				break;
			maxReach = Math.max(i + A[i], maxReach);
			if (maxReach >= A.length)
				return steps + 1;
			if (rest == 0) {
				steps++;
				rest = maxReach - i;
			}
		}
		return -1;
	}

	/**
	 * DP, Time: O(n^2)
	 * M[i] : minimum jumps needed to jump out the end form i
	 * M[n - 1] = A[n - 1] > 0 ? 1 : 0;
	 * Induction rule
	 * If i + A[i] >= A.length, M[i] = 1;
	 * else M[i] = min(M[j]) + 1, if M[j] > 0, for all j that i < j <= i + A[i]
	 **/
	public int minJump(int[] A) {
		int[] M = new int[A.length];
		Arrays.fill(M, -1);
		for (int i = A.length - 1; i >= 0; i--) {
			if (i + A[i] >= A.length) {
				M[i] = 1;
				continue;
			}
			for (int j = i + 1; j <= i + A[i]; j++) {
				if (M[j] > 0)
					M[i] = M[i] < 0 ? M[j] + 1 : Math.min(M[j] + 1, M[i]);
			}
		}
		return M[0];
	}
}
