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
		// TODO Auto-generated method stub
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
	
	

	/**
	 * Binary search the target, once find it, binary search its left and right
	 * edge, an edge number is defined as the number is equal to the target and
	 * its neighbor number is greater/less than itself
	 * 
	 * Time: O(lgn)
	 */
	public static int[] searchRange2(int[] A, int target) {
		int l = 0;
		int r = A.length - 1;
		int targetIdx = -1;
		while (l <= r) {
			int mid = (l + r) / 2;
			if (A[mid] == target) {
				targetIdx = mid;
				break;
			} else if (A[mid] < target)
				l = mid + 1;
			else
				r = mid - 1;
		}
		
		if(targetIdx<0)
			return new int[]{-1,-1};
		
		// search for the insert position of left edge
		l = 0;
		r = targetIdx;	//notice here
		while (l <= r) {
			int mid = (l + r) / 2;
			if (A[mid] < target && A[mid + 1] == target) {
				l = mid+1; // find the edge
				break;
			} else if (A[mid] < target)
				l = mid + 1;
			else
				r = mid - 1; // A[lmid]==target
		}

		int leftEdge = l;	//notice here should be l

		// search for the insert position of right edge
		l = targetIdx;	//notice here
		r = A.length - 1;
		while (l <= r) {
			int mid = (l + r) / 2;
			if (A[mid] > target && A[mid - 1] == target) {
				r = mid-1;
				break;
			} else if (A[mid] > target)
				r = mid - 1;
			else
				l = mid + 1;
		}

		int rightEdge = r;	//notice here should be r

		return new int[] { leftEdge, rightEdge };
	}
}
