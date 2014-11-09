package interview.leetcode;

/**
 * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
 * 
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * 
 * You are given a target value to search. If found in the array return its
 * index, otherwise return -1.
 * 
 * You may assume no duplicate exists in the array.
 * 
 * @author yazhoucao
 * 
 */
public class Search_in_Rotated_Sorted_Array {

	public static void main(String[] args) {
		int[] A0 = new int[] { 4, 5, 6, 7, 0, 1, 2 };
		System.out.println(search(A0, 4));
		System.out.println(search2(A0, 4));
		int[] A1 = new int[] { 3, 1 };
		System.out.println(search(A1, 1));
		System.out.println(search2(A1, 1));

		// a counter example which have duplicates which can't be handled
		// Because the solutions are all based on the assumption that if
		// A[mid]>A[0], then A[0~mid] must longer than A[mid~length],
		// if A[mid]<A[0], then A[0~mid] must shorter than A[mid~length]
		// however it won't work if there is duplicates, because duplicates will
		// make the length of both sides arbitrary.
		int[] A2 = new int[] { 1, 3, 1, 1, 1 };
		System.out.println(search(A2, 3));
		System.out.println(search2(A2, 3));
	}

	public static int search(int[] A, int target) {
		int l = 0;
		int r = A.length - 1;

		while (l <= r) {
			int mid = (l + r) / 2;
			if (target == A[mid])
				return mid;
			if (A[mid] >= A[l]) { // it must be >=
				if (target > A[mid] || target < A[l])
					l = mid + 1; // move to right
				else
					r = mid - 1; // move to left
			} else {
				if (target < A[mid] || target > A[r])
					r = mid - 1; // to left
				else
					l = mid + 1; // to right
			}
		}

		return -1;
	}

	/**
	 * Same solution, a different view of the four if/else conditions
	 * 
	 */
	public static int search2(int[] A, int target) {
		int l = 0;
		int r = A.length - 1;

		while (l <= r) {
			int mid = (l + r) / 2;
			if (A[mid] == target)
				return mid;
			else if (A[mid] < A[l]) {
				if (target > A[mid] && target <= A[r])
					l = mid + 1;
				else
					r = mid - 1;
			} else { // A[mid]>=A[l]
				if (target < A[mid] && target >= A[l])
					r = mid - 1;
				else
					l = mid + 1;
			}
		}
		return -1;
	}
}
