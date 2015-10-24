package interview.epi.chapter12_searching;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import interview.AutoTestUtils;

/**
 * Takes a sorted array A of distinct integers, return an index i such that A[i]
 * = i, if no such index exists, return -1.
 * 
 * @author yazhoucao
 * 
 */
public class Q3_Search_A_Sorted_Array_For_Ai_Equals_i {

	static Class<?> c = Q3_Search_A_Sorted_Array_For_Ai_Equals_i.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Assume there is no repeated element
	 */
	public int bsearch(int[] A) {
		int l = 0, r = A.length - 1;
		while (l <= r) {
			int m = l + (r - l) / 2;
			if (A[m] == m)
				return m;
			else if (A[m] > m)
				r = m - 1;
			else
				l = m + 1;
		}
		return -1;
	}

	@Test
	public void test1() {
		int[] A = { 0, 2, 3, 9 };
		int res = bsearch(A);
		assertTrue(res == 0);
	}

	@Test
	public void test2() {
		int[] A = { -1, 0, 1, 2, 3, 4, 5, 6, 7, 9 };
		int res = bsearch(A);
		int ans = 9;
		assertTrue(res == ans);
	}

	@Test
	public void test3() {
		int[] A = { -3, -2, -1, 3, 4, 7, 17 };
		int res = bsearch(A);
		assertTrue(res == 3);
	}

	@Test
	public void test4() {
		int[] A = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		int res = bsearch(A);
		assertTrue(res == -1);
	}
}
