package interview.epi.chaper11_heap;

import interview.AutoTestUtils;

import java.util.Arrays;

/**
 * Problem 11.7 (the same of Candy of LeetCode)
 * Given an array A of non-negative integers, compute an array T whose sum is
 * minimum subject to fore every i, if A[i]>A[i-1] then T[i]>T[i-1], and if
 * A[i]>A[i+1] then T[i]>T[i+1].
 * 
 * @author yazhoucao
 * 
 */
public class Q7_Compute_Fair_Bonuses {
	static Class<?> c = Q7_Compute_Fair_Bonuses.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	public static int[] calculateBonus(int[] productivity) {
		int[] tickets = new int[productivity.length];
		Arrays.fill(tickets, 1);
		// From left to right.
		for (int i = 1; i < productivity.length; ++i) {
			if (productivity[i] > productivity[i - 1]) {
				tickets[i] = tickets[i - 1] + 1;
			}
		}
		// From right to left.
		for (int i = productivity.length - 2; i >= 0; --i) {
			if (productivity[i] > productivity[i + 1]) {
				tickets[i] = Math.max(tickets[i], tickets[i + 1] + 1);
			}
		}
		return tickets;
	}
}
