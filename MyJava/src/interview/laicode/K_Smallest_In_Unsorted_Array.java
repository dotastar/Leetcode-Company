package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

/**
 * 
 * K Smallest In Unsorted Array
 * Fair
 * Data Structure
 * 
 * Find the K smallest numbers in an unsorted integer array A. The returned
 * numbers should be in ascending order.
 * 
 * Assumptions
 * 
 * A is not null
 * K is >= 0 and smaller than or equal to size of A
 * 
 * Return
 * 
 * an array with size K containing the K smallest numbers in ascending order
 * 
 * Examples
 * 
 * A = {3, 4, 1, 2, 5}, K = 3, the 3 smallest numbers are {1, 2, 3}
 * 
 * 
 * @author yazhoucao
 * 
 */
public class K_Smallest_In_Unsorted_Array {
	private static Class<?> c = K_Smallest_In_Unsorted_Array.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Quick Selelct
	 * For ordered result output: O(n) + O(klogk)
	 * If the result does not have to be ordered, then the time will be O(n).
	 */
	public int[] kSmallest(int[] array, int k) {
		quickSelect(array, 0, array.length - 1, k); // O(n)
		int[] res = new int[k];
		for (int i = 0, j = 0; k > 0; k--) {
			res[i++] = array[j++];
		}
		Arrays.sort(res); // O(klogk)
		return res;
	}

	private Random ran = new Random();

	private void quickSelect(int[] A, int l, int r, int k) {
		if (l >= r)
			return;
		int pivot = l + ran.nextInt(r - l);
		swap(A, l, pivot);
		int i = l + 1, end = r;
		while (i <= end) {
			if (A[i] > A[l])
				swap(A, i, end--);
			else
				i++;
		}
		swap(A, i - 1, l);
		if (k - 1 > i - 1)
			quickSelect(A, i, r, k);
		else if (k - 1 < i - 1)
			quickSelect(A, l, i - 2, k);
	}

	private void swap(int[] A, int j, int i) {
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}

	@Test
	public void test1() {
		int[] A = { 3, 4, 1, 2, 5 }; // Normal case
		int K = 3;
		int[] res = kSmallest(A, K);
		int[] ans = { 1, 2, 3 };
		assertTrue("Wrong: " + Arrays.toString(res), Arrays.equals(res, ans));
	}

	@Test
	public void test2() {
		int[] A = { 3, 4, 1, 2 }; // K = 1
		int K = 1;
		int[] res = kSmallest(A, K);
		int[] ans = { 1 };
		assertTrue("Wrong: " + Arrays.toString(res), Arrays.equals(res, ans));
	}

	@Test
	public void test3() {
		int[] A = { 2, 1 }; // K = A.length
		int K = 2;
		int[] res = kSmallest(A, K);
		int[] ans = { 1, 2 };
		assertTrue("Wrong: " + Arrays.toString(res), Arrays.equals(res, ans));
	}

	@Test
	public void test4() { // duplicates elements
		int[] A = { 5, 3, 4, 2, 1, 1, 2, 1, 8, 4, 4, 9, 13, 5, 8 };
		int K = 5;
		int[] res = kSmallest(A, K);
		int[] ans = { 1, 1, 1, 2, 2 };
		assertTrue("Wrong: " + Arrays.toString(res), Arrays.equals(res, ans));
	}

}
