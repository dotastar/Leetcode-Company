package interview.laicode;

import interview.AutoTestUtils;

import java.util.Arrays;

import org.junit.Test;

/**
 * 
 * Merge Sort
 * Fair
 * Recursion
 * 
 * Given an array of integers, sort the elements in the array in ascending
 * order. The merge sort algorithm should be used to solve this problem.
 * 
 * Examples
 * 
 * {1} is sorted to {1}
 * {1, 2, 3} is sorted to {1, 2, 3}
 * {3, 2, 1} is sorted to {1, 2, 3}
 * {4, 2, -3, 6, 1} is sorted to {-3, 1, 2, 4, 6}
 * 
 * Corner Cases
 * 
 * What if the given array is null? In this case, we do not need to do anything.
 * What if the given array is of length zero? In this case, we do not need to do
 * anything.
 * 
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Merge_Sort {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Merge_Sort.class);
	}

	public int[] mergeSort(int[] A) {
		sort(A, new int[A.length], 0, A.length - 1);
		return A;
	}

	private void sort(int[] A, int[] aux, int l, int r) {
		if (l >= r)
			return;

		int mid = l + (r - l) / 2;
		sort(A, aux, l, mid);
		sort(A, aux, mid + 1, r);
		merge(A, aux, l, mid, r);
	}

	private void merge(int[] A, int[] aux, int l, int mid, int r) {
		int idx1 = l, idx2 = mid + 1;
		for (int i = 0; i <= r - l; i++) {
			if (idx2 > r || (idx1 <= mid && A[idx1] < A[idx2]))
				aux[i] = A[idx1++];
			else
				aux[i] = A[idx2++];
		}
		
		System.arraycopy(aux, 0, A, l, r - l + 1);
	}

	@Test
	public void test1() {
		int[] A = { 2, 5, 2, 46, 63, 12, 3, 1, 35, 11 };
		mergeSort(A);
		System.out.println(Arrays.toString(A));
	}
}
