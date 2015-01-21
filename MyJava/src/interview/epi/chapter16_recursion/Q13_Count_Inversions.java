package interview.epi.chapter16_recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * 16.13
 * Let A be an array of n numbers. The pair of indices (i, j) is said to be
 * inverted if i<j and A[i]>A[j]. Design an efficient algorithm that takes an
 * array A of n numbers and returns the number of inverted pairs of indices.
 * 
 * @author yazhoucao
 * 
 */
public class Q13_Count_Inversions {

	/**
	 * Brute force, enumerate all the pairs (i,j) that i<j
	 * Time: O(n^2)
	 */
	public int countInversions(int[] A) {
		int count = 0;
		int n = A.length;
		for (int i = 0; i < n - 1; i++) {
			for (int j = i + 1; j < n; j++) {
				if (A[i] > A[j])
					count++;
			}
		}
		return count;
	}

	/**
	 * Divide and conquer, Time: O(nlogn), Space: O(logn)
	 * Like merge sort
	 */
	public int countInversions_Daq(List<Integer> A) {
		return helper(A, 0, A.size());
	}

	private int helper(List<Integer> a, int start, int end) {
		if (end - start <= 1)
			return 0;
		int mid = start + ((end - start) / 2);
		return helper(a, start, mid) + helper(a, mid, end) + merge(a, start, mid, end);
	}

	private int merge(List<Integer> A, int start, int mid, int end) {
		List<Integer> sortedA = new ArrayList<>();
		int leftStart = start, rightStart = mid, inverCount = 0;
		while (leftStart < mid && rightStart < end) {
			if (A.get(leftStart).compareTo(A.get(rightStart)) <= 0) {
				sortedA.add(A.get(leftStart++));
			} else {
				// A[leftStart:mid - 1] will be the inversions.
				inverCount += mid - leftStart;
				sortedA.add(A.get(rightStart++));
			}
		}
		sortedA.addAll(A.subList(leftStart, mid));
		sortedA.addAll(A.subList(rightStart, end));
		// Updates A with sortedA.
		for (Integer t : sortedA) {
			A.set(start++, t);
		}
		return inverCount;
	}
}
