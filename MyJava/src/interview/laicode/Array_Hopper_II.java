package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * 
 * Array Hopper II
 * Fair
 * DP
 * 
 * Given an array A of non-negative integers, you are initially positioned at
 * index 0 of the array. A[i] means the maximum jump distance from index i (you
 * can only jump towards the end of the array). Determine the minimum number of
 * jumps you need to reach the end of array. If you can not reach the end of the
 * array, return -1.
 * 
 * Assumptions
 * 
 * The given array is not null and has length of at least 1.
 * 
 * Examples
 * 
 * {3, 3, 1, 0, 4}, the minimum jumps needed is 2 (jump to index 1 then to the
 * end of array)
 * 
 * {2, 1, 1, 0, 2}, you are not able to reach the end of array, return -1 in
 * this case.
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Array_Hopper_II {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Array_Hopper_II.class);
	}

	/**
	 * Same greedy, neater, more general solution
	 */
	public int minJump3(int[] A) {
		int start = 0, end = 0, jump = 0;
		while (end < A.length - 1) {
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

	public int minJumpII(int[] A) {
		if (A.length == 1) {
			return 0;
		}
		// # of jumps currently
		int jump = 0;
		// max index by current # of jumps
		int currMax = 0;
		// max index by current # of jumps + 1
		int nextMax = 0;
		for (int i = 0; i < A.length; i++) {
			// has to jump one more step, from currMax to nextMax
			if (i > currMax) {
				jump++;
				if (currMax == nextMax)
					return -1;
				currMax = nextMax;
			}
			nextMax = Math.max(nextMax, A[i] + i);
		}
		return jump;
	}

	/**
	 * 1 0
	 * ^
	 * 1 1 0
	 * ^
	 * 0 1 2 3 4
	 * rest: 2
	 * maxJump: 4
	 */
	public int minJump(int[] A) {
		int maxJump = 0, jumps = 0, rest = 1;
		for (int i = 0; i < A.length - 1; i++) {
			if (i + A[i] > maxJump)
				maxJump = i + A[i];
			if (maxJump == i)
				return -1;
			if (maxJump >= A.length - 1)
				return jumps + 1;

			rest--;
			if (rest == 0) {
				rest = maxJump - i;
				jumps++;
			}
		}
		return jumps;
	}

	@Test
	public void test1() {
		int[] A = { 1, 0 };
		int ans = 1;
		int res = minJumpII(A);
		assertTrue("Wrong: " + res, ans == res);
	}

	@Test
	public void test2() {
		int[] A = { 1, 1, 0 };
		int ans = 2;
		int res = minJumpII(A);
		assertTrue("Wrong: " + res, ans == res);
	}

	@Test
	public void test3() {
		int[] A = { 3, 3, 1, 0, 4 };
		int ans = 2;
		int res = minJumpII(A);
		assertTrue("Wrong: " + res, ans == res);
	}

	@Test
	public void test4() {
		int[] A = { 2, 1, 1, 0, 2 };
		int ans = -1;
		int res = minJumpII(A);
		assertTrue("Wrong: " + res, ans == res);
	}
}
