package interview.epi.chapter12_searching;

import interview.AutoTestUtils;

/**
 * Find Kth Element In Two Sorted Arrays
 * You are given two sorted arrays A and B, and a positive integer k. Design an
 * algorithm that runs in O(logk) time for computing the k-th smallest element
 * in array formed by merging A and B. Array elements may be duplicated within
 * and between A and B.
 * 
 * @author yazhoucao
 * 
 */
public class Q8_Search_In_Two_Sorted_Array {

	static Class<?> c = Q8_Search_In_Two_Sorted_Array.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * See EPI p304
	 */
	public int bsearch(int[] A, int[] B, int k) {
		// Lower bound of elements we will choose in A.
		int l = max(0, k - B.length);
		// Upper bound of elements we will choose in A.
		int r = min(A.length, k);
		while (l < r) {
			int mid = l + ((r - l) >> 1);
			int ax1 = mid <= 0 ? Integer.MIN_VALUE : A[mid - 1];
			int ax = mid >= A.length ? Integer.MAX_VALUE : A[mid];
			int bkx1 = k - mid <= 0 ? Integer.MIN_VALUE : B[k - mid - 1];
			int bkx = k - mid >= B.length ? Integer.MAX_VALUE : B[k - mid];
			if (ax < bkx1)
				l = mid + 1;
			else if (ax1 > bkx)
				r = mid - 1;
			else // B[k - x - 1] <= A[x] && A[x - 1] < B[k- x].
				return max(ax1, bkx1); 

		}
		int ab1 = l <= 0 ? Integer.MIN_VALUE : A[l - 1];
		int bkb1 = k - l - 1 < 0 ? Integer.MIN_VALUE : B[k - l - 1];
		return max(ab1, bkb1);
	}

	private int min(int a, int b) {
		return a < b ? a : b;
	}

	private int max(int a, int b) {
		return a > b ? a : b;
	}
}
