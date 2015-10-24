package interview.epi.chapter14_sorting;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Write a function which takes as input two sorted arrays of integers, A and B,
 * and updates A to the combined entries of A and B in sorted order. Assume A
 * has enough empty entries at its end to hold the result.
 * 
 * @author yazhoucao
 * 
 */
public class Q2_Implement_Mergesort_In_Place {

	static Class<?> c = Q2_Implement_Mergesort_In_Place.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Key: from the end towards the start. Time: O(n), n = A.length
	 */
	public void mergesort(int[] A, int lenA, int[] B) {
		int i = lenA - 1, j = B.length - 1, end = A.length - 1;
		while (i >= 0 && j >= 0) 
			A[end--] = B[j] > A[i] ? B[j--] : A[i--];
		
		while (j >= 0)
			A[end--] = B[j--];
	}

	@Test
	public void test1() {
		int[] tempA = { 1, 3, 5, 7, 9 };
		int[] B = { 2, 4, 6, 8 };
		int[] A = new int[tempA.length + B.length];
		System.arraycopy(tempA, 0, A, 0, tempA.length);
		mergesort(A, tempA.length, B);

		System.out.println(Arrays.toString(A));
		for (int i = 0; i < A.length - 1; i++)
			assertTrue(A[i] <= A[i + 1]);
	}

	@Test
	public void test2() {
		int[] tempA = { 1, 3, 4, 6 };
		int[] B = { 0, 4, 4 };
		int[] A = new int[tempA.length + B.length];
		System.arraycopy(tempA, 0, A, 0, tempA.length);
		mergesort(A, tempA.length, B);

		System.out.println(Arrays.toString(A));
		for (int i = 0; i < A.length - 1; i++)
			assertTrue(A[i] <= A[i + 1]);
	}

	@Test
	public void test3() {
		int[] tempA = { 6, 7, 8 };
		int[] B = { 1, 2, 3, 4, 5 };
		int[] A = new int[tempA.length + B.length];
		System.arraycopy(tempA, 0, A, 0, tempA.length);
		mergesort(A, tempA.length, B);

		System.out.println(Arrays.toString(A));
		for (int i = 0; i < A.length - 1; i++)
			assertTrue(A[i] <= A[i + 1]);
	}

	@Test
	public void test4() {
		int[] tempA = { 1, 2, 3, 7, 8, 9 };
		int[] B = { 4, 5, 6 };
		int[] A = new int[tempA.length + B.length];
		System.arraycopy(tempA, 0, A, 0, tempA.length);
		mergesort(A, tempA.length, B);

		System.out.println(Arrays.toString(A));
		for (int i = 0; i < A.length - 1; i++)
			assertTrue(A[i] <= A[i + 1]);
	}
}
