package general.algorithms;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Sortings {

	public static void main(String[] args) {
		Result res = JUnitCore.runClasses(Sortings.class);
		for (Failure f : res.getFailures()) {
			System.err.println(f.toString());
			System.err.println(f.getTrace().toString());
		}
		System.out.println("Total Tests: " + res.getRunCount());
		System.out.println("Failures : " + res.getFailureCount());

	}

	/************************** Merge Sort **************************/
	public static class MergeSort {
		private MergeSort() {
		}

		/**
		 * Time: O(logn) (Worst case), Space: O(n).
		 * Stable sort
		 */
		public static int[] mergeSort(int[] A) {
			sort(A, new int[A.length], 0, A.length - 1);
			return A;
		}

		private static void sort(int[] A, int[] aux, int l, int r) {
			if (l >= r)
				return;
			int mid = l + (r - l) / 2;
			sort(A, aux, l, mid);
			sort(A, aux, mid + 1, r);
			merge(A, aux, l, mid, r);
		}

		private static void merge(int[] A, int[] aux, int l, int m, int r) {
			assert isSorted(A, l, m);
			assert isSorted(A, m + 1, r);
			System.arraycopy(A, l, aux, l, r - l + 1);
			for (int i = l, j = m + 1, k = l; k <= r; k++) {
				if (i > m || (j <= r && A[j] < A[i]))
					A[k] = A[j++];
				else
					A[k] = A[i++];
			}
			assert isSorted(A, l, r);
		}
	}

	/************************** Unit Test **************************/
	@Test
	public void testMergeSort1() {
		int[] A = { 5, 91, 2, 13, 25, 2, 33, 8, 16, 9, 68, 11, 3 };
		MergeSort.mergeSort(A);
		assertTrue(isSorted(A));
	}

	/***********************************************************************
	 * Check if array is sorted - useful for debugging
	 ***********************************************************************/
	private static boolean isSorted(int[] a) {
		return isSorted(a, 0, a.length - 1);
	}

	private static boolean isSorted(int[] a, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++)
			if (a[i] < a[i - 1])
				return false;
		return true;
	}

}
