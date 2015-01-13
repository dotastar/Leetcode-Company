package interview.epi.chapter12_searching;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import interview.AutoTestUtils;

/**
 * Let A be an unsorted array of n integers, with A[0]>=A[1] and A[n-1]>=A[n-1].
 * Call an index i a local minimum if A[i] is less than or equal to its
 * neighbors. How would you efficiently find a local minimum, if one exists?
 * 
 * @author yazhoucao
 * 
 */
public class Q1_Variant_Find_Local_Minimum {

	static Class<?> c = Q1_Variant_Find_Local_Minimum.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Binary Search
	 * If A[m] is not the local minimum, then it must be in descending order or
	 * ascending order.
	 * Then we can:
	 * 1.searching left if it is ascending
	 * 2.searching right if it is descending.
	 */
	public int bsearchLocalMinimum(int[] A) {
		assert A[0] >= A[1] && A[A.length - 1] >= A[A.length - 2];
		int l = 0, r = A.length - 1;
		while (l <= r) {
			int m = l + (r - l) / 2;
			if (A[m] <= A[m - 1]) {
				if (A[m] <= A[m + 1])
					return m;
				else
					l = m + 1;
			} else
				r = m - 1;
		}
		return -1;
	}

	@Test
	public void test1() {
		int[] A = { 10, 4, 5, 2, 1, 6, 3, 9 };
		int res = bsearchLocalMinimum(A);
		assertTrue((res == 1) || (res == 4) || (res == 6));
	}

	@Test
	public void test2() {
		int[] A = { 10, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int res = bsearchLocalMinimum(A);
		int ans = 1;
		assertTrue(res == ans);
	}

	@Test
	public void test3() {
		int[] A = { 13, 12, 4, 4, -1, -1, 7, 17 };
		int res = bsearchLocalMinimum(A);
		assertTrue((res == 2) || (res == 4) || (res == 5));
	}

	@Test
	public void test4() {
		int[] A = { 14, 10, 2, 108, 108, 243, 285, 1, 285, 401 };
		int res = bsearchLocalMinimum(A);
		assertTrue(res == 2 || res == 4 || res == 7);
	}
}
