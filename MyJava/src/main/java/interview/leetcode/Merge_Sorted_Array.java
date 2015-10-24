package interview.leetcode;

import java.util.Arrays;

/**
 * Given two sorted integer arrays A and B, merge B into A as one sorted array.
 * 
 * Note: You may assume that A has enough space (size that is greater or equal
 * to m + n) to hold additional elements from B. The number of elements
 * initialized in A and B are m and n respectively.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Merge_Sorted_Array {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] A = new int[] { 1, 3, 5, 7, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0 };
		int[] B = new int[] { 0, 2, 4, 6, 8, 10, 12, 14, 16, 18 };
		merge(A, 5, B, B.length);
		System.out.println(Arrays.toString(A));

		int[] A1 = new int[] { 2, 0 };
		int[] B1 = new int[] { 1 };
		merge(A1, 1, B1, B1.length);
		System.out.println(Arrays.toString(A1));

		int[] A2 = new int[] { 1, 2, 4, 5, 6, 0 };
		int[] B2 = new int[] { 3 };
		merge(A2, 5, B2, B2.length);
		System.out.println(Arrays.toString(A2));
	}

	/**
	 * Second time
	 */
	public void merge3(int A[], int m, int B[], int n) {
		m--;
		n--;
		while (n >= 0) {
			if (m < 0) {
				A[n] = B[n--];
			} else {
				if (B[n] > A[m])
					A[m + n + 1] = B[n--];
				else
					A[m + n + 1] = A[m--];
			}
		}
	}

	public void merge4(int A[], int m, int B[], int n) {
		m--;
		n--;
		for (int i = m + n + 1; i >= 0; i--) {
			if (n < 0)
				break;
			if (m < 0 || B[n] > A[m]) {
				A[i] = B[n--];
			} else {
				A[i] = A[m--];
			}
		}
	}

	/**
	 * The key to solve this problem is moving element of A and B backwards.
	 */
	public static void merge(int A[], int m, int B[], int n) {
		while (m > 0 && n > 0) {
			A[m + n - 1] = A[m - 1] > B[n - 1] ? A[--m] : B[--n];
		}

		while (--n >= 0) {
			A[n] = B[n];
		}
	}

	/**
	 * Change while to for loop version
	 */
	public static void merge2(int A[], int m, int B[], int n) {
		for (int i = m + n - 1; i >= 0; i--) {
			if (n - 1 < 0 || (m - 1 >= 0 && A[m - 1] > B[n - 1]))
				A[m + n - 1] = A[--m];
			else
				A[m + n - 1] = B[--n];
		}
	}
}
