package interview.leetcode;

/**
 * Follow up for "Search in Rotated Sorted Array": What if duplicates are
 * allowed?
 * 
 * Would this affect the run-time complexity? How and why?
 * 
 * Write a function to determine if a given target is in the array.
 * 
 * @author yazhoucao
 * 
 */
public class Search_in_Rotated_Sorted_Array_II {

	public static void main(String[] args) {
		System.out.println(search(new int[] { 3, 1 }, 1));
	}

	/**
	 * If duplicates are allowed, the run-time complexity will change from
	 * O(lgn) to O(n)
	 * 
	 * 
	 * Because the old solution is based on the assumption that:
	 * 
	 * if A[mid]>A[l], then A[l~mid] must longer than A[mid~r], which means
	 * A[mid] is at left hand side.
	 * 
	 * if A[mid]<A[l], then A[l~mid] must shorter than A[mid~r], which means
	 * A[mid] is at right hand side.
	 * 
	 * however it won't work if there is duplicates, because duplicates will
	 * make the length of both sides arbitrary, (we can make any side
	 * longer/shorter by add/delete the duplicates values of one of the number
	 * in that side), so the two conditions above con't be used to decide where
	 * A[mid] is?(either in left hand side or in right hand side).
	 * 
	 * therefore, we add the third condition to exclude duplicate values.
	 */
	public static boolean search(int[] A, int target) {
		int l = 0;
		int r = A.length - 1;
		while (l <= r) {
			int mid = (l + r) / 2;
			if (target == A[mid])
				return true;
			if (A[mid] > A[l]) { // mid at left, left side is longer
				if (target >= A[l] && target < A[mid])
					r = mid - 1;
				else
					l = mid + 1;
			} else if (A[mid] < A[l]) { // mid at right
				if (target <= A[r] && target > A[mid])
					l = mid + 1;
				else
					r = mid - 1;
			} else
				l++; // A[mid] == A[l], skip the duplicate
		}
		return false;
	}

	/**
	 * Classic Binary Search
	 */
	public static boolean bsearch(int[] A, int target, int l, int r) {
		if (l > r)
			return false;
		int mid = (l + r) / 2;
		if (target == A[mid])
			return true;
		if (target > A[mid])
			return bsearch(A, target, mid + 1, r);
		else
			return bsearch(A, target, l, mid - 1);
	}

	/**
	 * Classic Binary Search of Iterative fasion
	 */
	public static boolean bsearch_Iter(int[] A, int target) {
		int l = 0;
		int r = A.length - 1;
		while (l <= r) {
			int mid = (l + r) / 2;
			if (A[mid] == target)
				return true;
			else if (target > A[mid])
				l = mid + 1;
			else
				r = mid - 1;
		}
		return false;
	}
}
