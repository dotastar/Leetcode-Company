package interview.leetcode;

import java.util.Arrays;

/**
 * Given a sorted array of integers, find the starting and ending position of a
 * given target value.
 * 
 * Your algorithm's runtime complexity must be in the order of O(log n).
 * 
 * If the target is not found in the array, return [-1, -1].
 * 
 * For example, Given [5, 7, 7, 8, 8, 10] and target value 8, return [3, 4].
 * 
 * @author yazhoucao
 * 
 */
public class Search_for_a_Range {

	public static void main(String[] args) {
		String s = Arrays.toString(searchRange2(new int[] { 2, 2 }, 2)); // 0,1
		System.out.println(s);

		s = Arrays.toString(searchRange2(new int[] { 1, 2, 3, 4, 5 }, 1)); // 0,0
		System.out.println(s);
		s = Arrays.toString(searchRange2(new int[] { 1, 2, 3, 4, 5 }, 3)); // 2,2
		System.out.println(s);
		s = Arrays.toString(searchRange2(new int[] { 1, 2, 3, 4, 5 }, 5)); // 4,4
		System.out.println(s);

		s = Arrays.toString(searchRange2(new int[] { 1, 2, 2, 2, 2, 2, 3 }, 1));// 0,0
		System.out.println(s);
		s = Arrays.toString(searchRange2(new int[] { 1, 2, 2, 2, 2, 2, 2 }, 2));// 1.6
		System.out.println(s);
		s = Arrays.toString(searchRange2(new int[] { 1, 2, 2, 2, 2, 2, 3 }, 3));// 6,6
		System.out.println(s);
	}

	/**
	 * Use binary search template of search leftmost/rightmost target
	 * Keys:
	 * 1.stop condition is l < r-1, it stops when l and r are neighboring
	 * 2.while binary searching, set l or r = mid, not mid+1/mid-1
	 * 3.need to do post-processing, it can be A[l] or A[r]
	 */
	public int[] searchRange3(int[] A, int target) {
		int[] res = { -1, -1 };
		int l = 0, r = A.length - 1;
		while (l < r - 1) { // search left border
			int mid = l + (r - l) / 2;
			if (A[mid] >= target)
				r = mid;
			else
				l = mid;
		}
		if (A[l] == target || A[r] == target)
			res[0] = A[l] == target ? l : r;
		else
			return res; // no answer find

		r = A.length - 1;
		while (l < r - 1) { // search right border
			int mid = l + (r - l) / 2;
			if (A[mid] > target)
				r = mid;
			else
				l = mid;
		}
		res[1] = A[r] == target ? r : l;
		return res;
	}

	/**
	 * Binary search the target, once find it, binary search its left and right
	 * edge, an edge number is defined as the number is equal to the target and
	 * its neighbor number is greater/less than itself
	 * 
	 * Time: O(lgn)
	 */
	public static int[] searchRange2(int[] A, int target) {
		int[] res = { -1, -1 };
		int l = 0, r = A.length - 1;
		while (l <= r) { // Search for the index of the leftmost target
			int mid = l + (r - l) / 2;
			if (target <= A[mid])
				r = mid - 1;
			else
				l = mid + 1;
		}
		// l could be exceeded A when target > A[n-1], l = A.length
		res[0] = l < A.length && A[l] == target ? l : -1;

		r = A.length - 1; // don't need to reset l
		while (l <= r) { // Search for the index of the rightmost target
			int mid = l + (r - l) / 2;
			if (target >= A[mid])
				l = mid + 1;
			else
				r = mid - 1;
		}
		// r could be exceeded A when target < A[0], r = -1
		res[1] = r >= 0 && A[r] == target ? r : -1;

		return res;
	}

	/**
	 * Binary search the target, once find it, traversing till reach its left
	 * and right edge
	 * 
	 * Time : O(n), worst case (if all the numbers are the same in array)
	 */
	public int[] searchRange(int[] A, int target) {
		int last = A.length - 1;
		int l = 0;
		int r = last;

		while (l <= r) {
			int mid = (l + r) / 2;
			if (target == A[mid]) {
				int start = mid;
				while (start > 0 && A[start - 1] == target)
					start--;
				int end = mid;
				while (end < last && A[end + 1] == target)
					end++;
				return new int[] { start, end };
			} else if (target > A[mid])
				l = mid + 1;
			else
				r = mid - 1;
		}

		return new int[] { -1, -1 };
	}

}
