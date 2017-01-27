package interview.epi.chapter12_searching;

import static org.junit.Assert.*;

import org.junit.Test;

import interview.AutoTestUtils;

/**
 * Write a method that takes a sorted array A and a key k and returns the index
 * of the first k in A. Return -1 if k does not exist.
 * 
 * @author yazhoucao
 * 
 */
public class Q1_Search_A_Sorted_Array_For_First_Occurrence_Of_K {
	static Class<?> c = Q1_Search_A_Sorted_Array_For_First_Occurrence_Of_K.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * a light twist of a binary search
	 */
	public int bsearch(int[] A, int k) {
		int l = 0, r = A.length - 1;
		int res = -1;
		while (l <= r) {
			int mid = l + (r - l) / 2;
			if (A[mid] == k) {
				res = mid; // a little tweak at here, record the result
				r = mid - 1; // keep searching left
			} else if (A[mid] > k)
				r = mid - 1;
			else
				l = mid + 1;
		}
		return res;
	}

	@Test
	public void test1() {
		int[] A = { 1, 1, 1, 1, 1, 1, 1, 1 };
		int k = 1;
		int res = bsearch(A, k);
		int ans = 0;
		assertTrue(res == ans);
	}

	@Test
	public void test2() {
		int[] A = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int k = 5;
		int res = bsearch(A, k);
		int ans = 5;
		assertTrue(res == ans);
	}

	@Test
	public void test3() {
		int[] A = { -1, -1, 2, 2, 4, 4, 7, 7 };
		int k = 7;
		int res = bsearch(A, k);
		int ans = 6;
		assertTrue(res == ans);
	}

	@Test
	public void test4() {
		int[] A = { -14, -10, 2, 108, 108, 243, 285, 285, 285, 401 };
		int k = 285;
		int res = bsearch(A, k);
		int ans = 6;
		assertTrue(res == ans);
	}
}
