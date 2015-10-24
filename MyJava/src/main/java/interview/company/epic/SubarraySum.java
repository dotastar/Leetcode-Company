package interview.company.epic;

import static org.junit.Assert.*;

import org.junit.Test;

import interview.AutoTestUtils;
import interview.epi.utils.Pair;

/**
 * Substring Addition
 * Write a program to add the substring
 * E.g :say you have a list {1 7 6 3 5 8 9 } and user is entering a sum 16.
 * Output should display (2-4) that is {7 6 3} cause 7+6+3=16.
 * 
 * @author yazhoucao
 * 
 */
public class SubarraySum {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(SubarraySum.class);
	}

	/**
	 * Sliding window (l, r)
	 */
	public Pair<Integer, Integer> substringSum(int[] arr, int sum) {
		int l = 0;
		int r = 0;
		int sumLR = arr[0];
		while (r < arr.length - 1) {
			if (sumLR == sum)
				break;
			else if (sumLR < sum || l == r) {
				r++;
				sumLR += arr[r];
			} else {
				sumLR -= arr[l];
				l++;
			}
		}

		while (l < arr.length) {
			if (sumLR == sum)
				break;
			else {
				sumLR -= arr[l];
				l++;
			}
		}

		return new Pair<Integer, Integer>(l + 1, r + 1);
	}

	@Test
	public void test0() { // the first one
		int[] A = { 1, 7, 6, 3, 5, 8, 9 };
		int sum = 1;
		Pair<Integer, Integer> res = substringSum(A, sum);
		assertTrue(res.getFirst() == 1 && res.getSecond() == 1);
	}

	@Test
	public void test1() { // the first 2-4
		int[] A = { 1, 7, 6, 3, 5, 8, 9 };
		int sum = 16;
		Pair<Integer, Integer> res = substringSum(A, sum);
		assertTrue(res.getFirst() == 2 && res.getSecond() == 4);
	}

	@Test
	public void test2() { // the last one
		int[] A = { 1, 7, 6, 3, 5, 8, 19 };
		int sum = 19;
		Pair<Integer, Integer> res = substringSum(A, sum);
		assertTrue(res.getFirst() == 7 && res.getSecond() == 7);
	}

	@Test
	public void test3() { // contains negative, the last two
		int[] A = { 1, 2, 3, -4, -5, 6, 7 };
		int sum = 13;
		Pair<Integer, Integer> res = substringSum(A, sum);
		assertTrue(res.getFirst() == 6 && res.getSecond() == 7);
	}

	@Test
	public void test4() { // all of them
		int[] A = { 1, 2, 3, 4, 5, 6, 7 };
		int sum = 28;
		Pair<Integer, Integer> res = substringSum(A, sum);
		assertTrue(res.getFirst() == 1 && res.getSecond() == 7);
	}

}
