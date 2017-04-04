package interview.company.twitter;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import interview.AutoTestUtils;

/**
 * Majority element
 * In an array, find the element which appears more than 50% of the time. Find
 * element which appears more than 33% of the time in the array.
 * 
 * @author yazhoucao
 * 
 */
public class MajorityElement {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(MajorityElement.class);
	}

	/**
	 * Naive solution: HashMap, count occurrences, scan for specific count
	 * E.g. 50%/33%/...
	 */

	/**
	 * Moore’s Voting Algorithm, O(n), notice: this only work if majority>50%
	 * This is a two step process.
	 * 1. Get an element occurring most of the time in the array. (it may not be
	 * exceeded 50%.)
	 * 2. Check if the element obtained from above step is above 50%.
	 * 
	 * The algorithm for first phase that works in O(n) is known as Moore’s
	 * Voting Algorithm. Basic idea of the algorithm is if we cancel out each
	 * occurrence of an element e with all the other elements that are different
	 * from e then e will exist till end if it is a majority element.
	 * 
	 */
	public int findHalf(int[] A) {
		int cnt = 1, majority = A[0]; // find majority
		for (int i = 1; i < A.length; i++) {
			if (A[i] == majority)
				cnt++;
			else
				cnt--;

			if (cnt == 0) {
				majority = A[i];
				cnt = 1;
			}
		}

		cnt = 0; // check if it is above 50%
		for (int a : A) {
			if (a == majority)
				cnt++;
		}

		return cnt * 2 >= A.length ? majority : -1;
	}

	/**
	 * Find 33%
	 * 
	 * Start with two empty candidate slots and two counters set to 0.
	 * for each item:
	 * if it is equal to either candidate, increment the corresponding count
	 * else if there is an empty slot (i.e. a slot with count 0), put it in that
	 * slot and set the count to 1
	 * else reduce both counters by 1
	 * 
	 * At the end, make a second pass over the array to check whether the
	 * candidates really do have the required count.
	 */
	public int[] findThird(int[] A) {
		int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
		int cnt1 = 0, cnt2 = 0;
		// find the most two majority
		for (int i = 0; i < A.length; i++) {
			if (A[i] == first)
				cnt1++;
			else if (A[i] == second)
				cnt2++;
			else {
				cnt1--;
				cnt2--;
			}

			if (cnt1 < 0 && A[i] != second) {
				first = A[i];
				cnt1 = 1;
			}
			if (cnt2 < 0 && A[i] != first) {
				second = A[i];
				cnt2 = 1;
			}
		}

		cnt1 = 0;
		cnt2 = 0;
		for (int a : A) {
			if (a == first)
				cnt1++;
			else if (a == second)
				cnt2++;
		}

		// return result
		if (cnt1 * 3 >= A.length && cnt2 * 3 >= A.length) {
			int[] res = new int[] { first, second };
			Arrays.sort(res);
			return res;
		} else if (cnt1 * 3 >= A.length)
			return new int[] { first };
		else if (cnt2 * 3 >= A.length)
			return new int[] { second };
		else
			return new int[] { -1 };
	}

	@Test
	public void test1() {
		int[] A = { 3, 3, 3, 4, 5, 6, 6 };
		int[] res = findThird(A);
		int[] ans = { 3 };
		assertTrue(Arrays.equals(res, ans));
	}

	@Test
	public void test2() {
		int[] A = { 1, 2, 3, 3, 3, 1, 4, 1, 5 };
		int[] res = findThird(A);
		int[] ans = { 1, 3 };
		assertTrue(Arrays.equals(res, ans));
	}

	@Test
	public void test3() {
		int[] A = { 1, 2, 3, 4, 2, 3, 3, 3 };
		int[] res = findThird(A);
		int[] ans = { 3 };
		assertTrue(Arrays.equals(res, ans));
	}

	@Test
	public void test4() {
		int[] A = { 1, 2, 3, 4, 1, 4, 1, 4, 1 };
		int[] res = findThird(A);
		int[] ans = { 1, 4 };
		assertTrue(Arrays.equals(res, ans));
	}

	@Test
	public void test5() {
		int[] A = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 2 };
		int[] res = findThird(A);
		int[] ans = { -1 };
		assertTrue(Arrays.equals(res, ans));
	}

}
