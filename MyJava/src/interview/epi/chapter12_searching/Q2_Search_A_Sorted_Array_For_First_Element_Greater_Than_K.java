package interview.epi.chapter12_searching;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import interview.AutoTestUtils;

/**
 * We want the first element greater than a given element.
 * 
 * @author yazhoucao
 * 
 */
public class Q2_Search_A_Sorted_Array_For_First_Element_Greater_Than_K {

	static Class<?> c = Q2_Search_A_Sorted_Array_For_First_Element_Greater_Than_K.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	public int bsearch(int[] A, int k) {
		int l = 0, r = A.length - 1;
		while (l <= r) {
			int m = l + (r - l) / 2;
			if (A[m] == k) {
				if (m + 1 < A.length && A[m + 1] > A[m])
					return m + 1;
				else
					l = m + 1; // keep searching right
			} else if (A[m] > k)
				r = m - 1;
			else
				l = m + 1;
		}
		return -1;
	}

	/**
	 * A more concise version
	 */
	public int bsearch_concise(int[] A, int k) {
		int l = 0, r = A.length - 1;
		int res = -1;
		while (l <= r) {
			int m = l + (r - l) / 2;
			if (A[m] > k) {
				res = m;
				r = m - 1;
			} else
				l = m + 1; // A[m] <= k
		}
		return res;
	}

	@Test
	public void test1() {
		int[] A = { 1, 1, 1, 1, 1, 1, 1, 1 };
		int k = 1;
		int res = bsearch(A, k);
		int ans = -1;
		assertTrue(res == ans);
	}

	@Test
	public void test2() {
		int[] A = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int k = 5;
		int res = bsearch(A, k);
		int ans = 6;
		assertTrue(res == ans);
	}

	@Test
	public void test3() {
		int[] A = { -1, -1, 2, 2, 4, 4, 7, 7 };
		int k = 4;
		int res = bsearch(A, k);
		int ans = 6;
		assertTrue(res == ans);
	}

	@Test
	public void test4() {
		int[] A = { -14, -10, 2, 108, 108, 243, 285, 285, 285, 401 };
		int k = -999; // doesn't exist
		int res = bsearch(A, k);
		int ans = -1;
		assertTrue(res == ans);
	}

	@Test
	public void test5() {
		int[] A = { -14, -10, 2, 108, 108, 243, 285, 285, 285, 401 };
		int k = 285;
		int res = bsearch(A, k);
		int ans = 9;
		assertTrue(res == ans);
	}
}
