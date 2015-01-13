package interview.epi.chapter12_searching;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.Random;

import org.junit.Test;

/**
 * Design an algorithm for computing the k-th largest element in an array A that
 * runs in O(n) time expected.
 * 
 * @author yazhoucao
 * 
 */
public class Q11_Find_Kth_Largest_Element {

	static Class<?> c = Q11_Find_Kth_Largest_Element.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Subroutine of quicksort
	 * Worst case: O(n^2), average expected: O(n).
	 */
	public int quickSelect(int[] A, int k) {
		int l = 0, r = A.length - 1;
		k = A.length + 1 - k; // converted to k-th smallest
		Random ran = new Random();
		while (l <= r) {
			int pivot = l + ran.nextInt(r - l + 1);
			int partIdx = partition(A, pivot, l, r);
			if (partIdx == k - 1)
				return A[partIdx];
			else if (k - 1 < partIdx)
				r = partIdx - 1;
			else
				l = partIdx + 1;
		}
		throw new RuntimeException("no k-th node in array A");
	}

	/**
	 * Increasing order, smaller on the left, greater on the right
	 */
	private int partition(int[] A, int p, int l, int r) {
		int lEnd = l + 1, pivot = A[p];
		swap(A, l, p);
		for (int i = l + 1; i <= r; i++) {
			if (A[i] < pivot) {
				swap(A, i, lEnd++);
			}
		}
		swap(A, lEnd - 1, l);
		return lEnd - 1;
	}

	private void swap(int[] A, int i, int j) {
		if (i != j) {
			int temp = A[i];
			A[i] = A[j];
			A[j] = temp;
		}
	}

	@Test
	public void test1() {
		int[] A = { 5, 15, 12, 52, 2, 7, 4, 1, 11 };
		int k = 1;
		int res = quickSelect(A, k);
		int ans = 52;
		assertTrue(res == ans);
	}

	@Test
	public void test2() {
		int[] A = { 5, 15, 12, 52, 2, 7, 4, 1, 11 };
		int k = 9;
		int res = quickSelect(A, k);
		int ans = 1;
		assertTrue(res == ans);
	}

	@Test
	public void test3() {
		int[] A = { 5, 15, 12, 52, 2, 7, 4, 1, 11 };
		int k = 5;
		int res = quickSelect(A, k);
		int ans = 7;
		assertTrue(res == ans);
	}

	@Test
	public void test4() {
		int[] A = { 10, -1, -12, 33, 75, 2000, 199, 14, 3 };
		int k = 8;
		int res = quickSelect(A, k);
		int ans = -1;
		assertTrue(res == ans);
	}

}
