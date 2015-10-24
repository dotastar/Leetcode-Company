package interview.company.epic;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Find the largest sum contiguous sub array. The length of the returned sub
 * array must be at least of length 2.
 * 
 * @author yazhoucao
 * 
 */
public class MaxSumOfContiguousSubArray {
	static Class<?> c = MaxSumOfContiguousSubArray.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Version of sub array must be at least of length 2.
	 * Convert it to the problem that length = 1 by combining two items together
	 * at a time.
	 */
	public int maxSubArray(int[] A) {
		assert A.length >= 2;
		int sum = A[0] + A[1];
		int max = sum;
		for (int i = 1; i < A.length - 1; i++) {
			// notice here is sum + A[i+1] not sum + A[i]
			// because A[i] is already added in the previous round.
			sum = sum > 0 ? sum + A[i + 1] : A[i] + A[i + 1];
			max = sum > max ? sum : max;
		}
		return max;
	}

	@Test
	public void test1() {
		int[] arr = { 1, -2 };
		assertTrue(maxSubArray(arr) == -1);
	}

	@Test
	public void test2() {
		int[] arr = { 1, -1, 1 };
		assertTrue(maxSubArray(arr) == 0);
	}

	@Test
	public void test3() { // first part is answer, ignore the rest
		int[] arr = { 1, 2, 3, -1, -2, 3, -6 };
		assertTrue(maxSubArray(arr) == 6);
	}

	@Test
	public void test4() { // all negative
		int[] arr = { -1, -2, -3, -4, -5, -4, -3 };
		assertTrue(maxSubArray(arr) == -3);
	}

	@Test
	public void test5() { // the whole part, all counts
		int[] arr = { 1, 2, 3, -2, -3, 5 };
		assertTrue(maxSubArray(arr) == 6);
	}

	@Test
	public void test6() { // the last part count
		int[] arr = { -2, 1, 1 };
		assertTrue(maxSubArray(arr) == 2);
	}

	@Test
	public void test7() {
		int[] arr = { -1, -1, -10, 6, -10, 7 };
		assertTrue(maxSubArray(arr) == -2);
	}
}
