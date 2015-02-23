package interview.leetcode;

/**
 * A peak element is an element that is greater than its neighbors.
 * 
 * Given an input array where num[i] ≠ num[i+1], find a peak element and return
 * its index.
 * 
 * The array may contain multiple peaks, in that case return the index to any
 * one of the peaks is fine.
 * 
 * You may imagine that num[-1] = num[n] = -∞.
 * 
 * For example, in array [1, 2, 3, 1], 3 is a peak element and your function
 * should return the index number 2.
 * 
 * click to show spoilers.
 * Note:
 * 
 * Your solution should be in logarithmic complexity.
 * 
 * @author yazhoucao
 * 
 */
public class Find_Peak_Element {

	public static void main(String[] args) {
		int[] A = { 1 };
		assert findPeakElement(A) == 0;
	}

	/**
	 * Binary search
	 * search the middle element that (A[mid] > A[mid-1] && A[mid] > A[mid+1]) 
	 */
	public static int findPeakElement(int[] A) {
		int l = 0, r = A.length - 1;
		while (l <= r) {
			int mid = l + (r - l) / 2;
			// don't forget A[-1] and A[n]
			if ((mid - 1 == -1 || A[mid] > A[mid - 1]) && (mid + 1 == A.length || A[mid] > A[mid + 1]))
				return mid;
			else if (mid+1==A.length || A[mid] > A[mid+1])
				r = mid - 1;
			else
				l = mid + 1;
		}
		return -1;
	}
}
