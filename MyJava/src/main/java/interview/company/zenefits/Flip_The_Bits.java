package interview.company.zenefits;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Zenefits
 * OA
 * 
 * You are given an array a of size N. The elements of the array are a[0], a[1],
 * ... a[N-1], where each a[i] is either 0 or 1. You can perform one
 * transformation on the array: choose any two integers L and R, and flip all
 * the elements between (and including) the L-th and R-th bits. What is the
 * maximum number of 1-bits which you can obtain in the final bit-string?
 * 
 * @author yazhoucao
 *
 */
public class Flip_The_Bits {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Flip_The_Bits.class);
	}

	/**
	 * If we think 0 as -1, then the problem becomes to find the smallest sum of
	 * sub-array. --> DP
	 * The final number of 1 is: the # 1s + Abs(smallest Subarray Sum)
	 */
	public int maxBits(int[] A) {
		int sum = 0, globalMin = 0, countOne = 0;
		for (int i = 0; i < A.length; i++) {
			if (A[i] == 1) {
				countOne++;
				sum++;
			} else
				// A[i] == 0
				sum--;

			if (sum > 0)
				sum = 0;
			globalMin = sum < globalMin ? sum : globalMin;
		}

		return countOne - globalMin;
	}

	@Test
	public void test1() {
		int[] A = { 1, 0, 0, 1, 0, 0, 1, 1, 0 };
		assertTrue(maxBits(A) == 7);
	}

	@Test
	public void test2() {
		int[] A = { 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0 };
		assertTrue(maxBits(A) == 8);
	}

	@Test
	public void test3() {
		int[] A = { 1, 0, 1, 0 };
		assertTrue(maxBits(A) == 3);
	}

	@Test
	public void test4() {
		int[] A = { 1, 0, 1, 0, 1 };
		assertTrue(maxBits(A) == 4);
	}
}
