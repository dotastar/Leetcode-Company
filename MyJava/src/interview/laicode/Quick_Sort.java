package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.Random;

import org.junit.Test;

/**
 * 
 * Quick Sort
 * Fair
 * Recursion
 * 
 * Given an array of integers, sort the elements in the array in ascending
 * order. The quick sort algorithm should be used to solve this problem.
 * 
 * Examples
 * 
 * {1} is sorted to {1}
 * {1, 2, 3} is sorted to {1, 2, 3}
 * {3, 2, 1} is sorted to {3, 2, 1}
 * {4, 2, -3, 6, 1} is sorted to {-3, 1, 2, 4, 6}
 * 
 * Corner Cases
 * 
 * What if the given array is null? In this case, we do not need to do anything.
 * What if the given array is of length zero? In this case, we do not need to do
 * anything.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Quick_Sort {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Quick_Sort.class);
	}

	private Random ran = new Random();

	public int[] quickSort(int[] A) {
		if (A != null)
			sort(A, 0, A.length - 1);
		return A;
	}

	private void sort(int[] A, int l, int r) {
		if (l >= r)
			return;

		int pivot = ran.nextInt(r - l) + l;
		swap(A, l, pivot);
		int pivotVal = A[l];
		int j = l + 1;
		for (int i = l + 1; i <= r; i++) {
			if (A[i] < pivotVal)
				swap(A, i, j++);
		}
		swap(A, j - 1, l); // j - 1 should be the pivot idx

		sort(A, l, j - 2);
		sort(A, j, r);
	}

	private void swap(int[] A, int i, int j) {
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}

	@Test
	public void test1() {
		int[] A = { 2, 1 };
		quickSort(A);
		assertTrue(isSorted(A));
	}

	@Test
	public void test2() {
		int[] A = { 2, 1, 2, 1 };
		quickSort(A);
		assertTrue(isSorted(A));
	}

	@Test
	public void test3() {
		int[] A = { 5, -1, 0, 1, 4, 3, 2 };
		quickSort(A);
		assertTrue(isSorted(A));
	}

	@Test
	public void test4() {
		int[] A = { 5, 4, 3, 2, 1 };
		quickSort(A);
		assertTrue(isSorted(A));
	}

	@Test
	public void test5() {
		int[] A = { 1, 2, 3, 4, 5 };
		quickSort(A);
		assertTrue(isSorted(A));
	}

	private boolean isSorted(int[] A) {
		for (int i = 0; i < A.length - 1; i++) {
			if (A[i + 1] < A[i])
				return false;
		}
		return true;
	}
}
