package interview.epi.chapter12_searching;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import interview.AutoTestUtils;

/**
 * A Cyclically Sorted Array is an rotated sorted array, assume it is in
 * increasing order, find the index of given element k.
 * 
 * @author yazhoucao
 * 
 */
public class Q4_Variant_Search_K_In_Rotated_Sorted_Array {
	static Class<?> c = Q4_Variant_Search_K_In_Rotated_Sorted_Array.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Two cases:
	 * 1.A[l] <= A[mid], it is in a normal sorted array, do normal binary search
	 * 2.A[l] > A[mid], means l to r includes the rotated index (local minimum),
	 * then do a reversed one, the search direction is opposite to normal binary
	 * search.
	 */
	public int bsearch(int[] A, int k) {
		int l = 0, r = A.length - 1;
		while (l <= r) {
			int m = l + (r - l) / 2;
			if (A[m] == k)
				return m;
			else if (A[m] >= A[l]) {
				if (A[m] > k && k >= A[l])
					r = m - 1;
				else
					l = m + 1;
			} else { // A[m] < A[l]
				if (A[m] < k && k <= A[r])
					l = m + 1;
				else
					r = m - 1;
			}
		}
		return -1;
	}

	@Test
	public void test1() {
		int[] A = { 10, 11, 12, 13, 1, 2, 3, 4 };
		int k = 10;
		int res = bsearch(A, k);
		int ans = 0;
		assertTrue(res == ans);
	}

	@Test
	public void test2() {
		int[] A = { 9, 0, 1, 2, 3, 4, 5, 6, 7, 8 };
		int k = 4;
		int res = bsearch(A, k);
		int ans = 5;
		assertTrue(res == ans);
	}

	@Test
	public void test3() {
		int[] A = { -1, 2, 3, 4, 5, 6, 7, -2 };
		int k = -2;
		int res = bsearch(A, k);
		int ans = 7;
		assertTrue(res == ans);
	}

	@Test
	public void test4() {
		int[] A = { 108, 118, 243, 285, 295, 401, -14, -10, 2 };
		int k = -14;
		int res = bsearch(A, k);
		int ans = 6;
		assertTrue(res == ans);
	}

	@Test
	public void test5() {
		int[] A = { 4, 5, 6, 7, 8, 1, 2, 3 };
		int k = 8;
		int res = bsearch(A, k);
		int ans = 4;
		assertTrue(res == ans);
	}
}
