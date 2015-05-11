package interview.laicode.hard;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.Arrays;

import org.junit.Test;

/**
 * An array contains both positive and negative numbers in random order. Order
 * the array elements so that positive and negative numbers are placed
 * alternatively. If there are more positive numbers they appear at the end of
 * the array. If there are more negative numbers, they too appear in the end of
 * the array.
 * 
 * For example, if the input array is [1 2 3 4 5 -1 -1 -1], then the output
 * should be [1, -1, 2, -1, 3, -1, 4, 5,] (The ordering of positive/negative
 * numbers do not matter)
 * 
 * 
 * @author yazhoucao
 *
 */
public class ReorderPositiveNegativeInArray {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(ReorderPositiveNegativeInArray.class);
	}

	/**
	 * 1.Partition the array to two parts, positive and negative, by using two
	 * pointers (like rainbow sort)
	 * 2.Reset the two pointers to the beginning of each part
	 * 3.Scan toward right, swapping elements in positive and negative.
	 */
	public void reorder(int[] A) {
		int l = 0, r = A.length - 1;
		for (int i = 0; i <= r; i++) {
			if (A[i] < 0)
				swap(A, i--, r--);
			else
				l++;
		}

		r = l;
		l = 1;
		while (l < r && r < A.length) {
			swap(A, l++, r++);
			l++;
		}
		System.out.println(Arrays.toString(A));
	}

	private void swap(int[] A, int l, int r) {
		int temp = A[l];
		A[l] = A[r];
		A[r] = temp;
	}

	@Test
	public void test1() {
		int[] A = { -1, 1 };
		reorder(A);
		assertTrue("Wrong: " + Arrays.toString(A), isReordered(A));
	}

	@Test
	public void test2() {
		int[] A = { 1, -1, -2, -3, -4, -5, 2 };
		reorder(A);
		assertTrue("Wrong: " + Arrays.toString(A), isReordered(A));
	}

	@Test
	public void test3() {
		int[] A = { -1, 1, 2, 3, 4, 5 - 2, -3 };
		reorder(A);
		assertTrue("Wrong: " + Arrays.toString(A), isReordered(A));
	}

	private boolean isReordered(int[] A) {
		int remainVal = 0;
		for (int i = 1; i < A.length; i += 2) {
			if (remainVal != 0) {
				if (((remainVal ^ A[i]) & 1) == 1)
					return false;
			}
			if (A[i - 1] < 0 || A[i] > 0)
				remainVal = A[i - 1] < 0 ? -1 : 1;
		}
		return true;
	}
}
