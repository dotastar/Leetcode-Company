package interview.epi.chapter14_sorting;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Problem 14.1
 * Given sorted arrays A and B of lengths n and m respectively, return an array
 * C containing elements common to A and B. The array should be free of
 * duplicates. How would you perform this intersection if (1) n~m (2) n<<m.
 * 
 * (Like Join Operation in database.)
 * 
 * @author yazhoucao
 * 
 */
public class Q1_Compute_The_Intersection_Of_Two_Sorted_Arrays {

	static Class<?> c = Q1_Compute_The_Intersection_Of_Two_Sorted_Arrays.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Binary search the begin idx + Merge two arrays.
	 * Time: O(m+n).
	 * Notice: Arrays.binarySearch() return (-(insertion point) - 1) if the
	 * element is not in the array.
	 */
	public List<Integer> intersect(int[] A, int[] B) {
		List<Integer> res = new ArrayList<>();
		if (B.length == 0)
			return res;
		int idxA = Arrays.binarySearch(A, B[0]);
		idxA = idxA < 0 ? 1 - idxA : idxA;
		int idxB = 0;
		while (idxA < A.length && idxB < B.length) {
			if (B[idxB] == A[idxA]) {
				res.add(B[idxB]);
				idxA++;
				idxB++;
			} else if (B[idxB] > A[idxA])
				idxA++;
			else
				idxB++;
		}
		return res;
	}

	/**
	 * For the case that n << m, m = A.length, n = B.length.
	 * Time: O(nlogm). Since n << m, nlog(m) << mlog(n).
	 */
	public List<Integer> intersect2(int[] A, int[] B) {
		List<Integer> res = new ArrayList<>();
		for (int i = 0; i < B.length; i++) { // loop n
			if (Arrays.binarySearch(A, B[i]) >= 0) // binary search m
				res.add(B[i]);
		}
		return res;
	}

	@Test
	public void test1() { // no intersection
		int[] A = { 1, 3, 5, 7, 9 };
		int[] B = { 2, 4, 6, 8, 10 };
		List<Integer> res = intersect(A, B);
		Integer[] ans = {};
		assertTrue(Arrays.equals(res.toArray(), ans));
	}

	@Test
	public void test2() {
		int[] A = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
		int[] B = { 9, 11, 13, 15, 17, 19, 21 };
		List<Integer> res = intersect(A, B);
		Integer[] ans = { 9, 11, 13, 15 };
		assertTrue(Arrays.equals(res.toArray(), ans));
	}

	@Test
	public void test3() {
		int[] A = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
		int[] B = { 15 };
		List<Integer> res = intersect(A, B);
		Integer[] ans = { 15 };
		assertTrue(Arrays.equals(res.toArray(), ans));
	}

	@Test
	public void test4() {
		int[] A = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
		int[] B = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
		List<Integer> res = intersect(A, B);
		Integer[] ans = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
		assertTrue(Arrays.equals(res.toArray(), ans));
	}
}
