package interview.leetcode;

/**
 * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
 * 
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * 
 * Find the minimum element.
 * 
 * You may assume no duplicate exists in the array.
 * 
 * @author yazhoucao
 * 
 */
public class Find_Minimum_in_Rotated_Sorted_Array {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Find_Minimum_in_Rotated_Sorted_Array obj = new Find_Minimum_in_Rotated_Sorted_Array();
		int[] A0 = { 1 };
		int[] A1 = { 2, 1 };
		assert obj.findMin(A0) == 1;
		assert obj.findMin(A1) == 1 : obj.findMin(A1);
	}

	/**
	 * Idea:
	 * 我们的目的是要找出存在断口的地方。所以我们可以每次求一下mid的值，把mid
	 * 跟左边比一下，如果是正常序，就丢掉左边，反之丢掉右边，不断反复直到找到断口。
	 * 分析一下：
	 * 比如4 5 6 7 0 1 2 从中间断开后，它是由一个有序跟一个无序的序列组成的。
	 * 如果left = 0, right = 6,mid = 3, 那么4，5，6，7 是正序，7，0，1，2是逆序，
	 * 所以我们要丢掉左边。这样反复操作，直到数列中只有2个数字，就是断开处，这题我们会得到7，0，
	 * 返回后一个就可以了。
	 * 
	 * Notice: should not use l = m + 1 and r = m - 1. This may discard the
	 * minimal value.
	 */
	public int findMin3(int[] A) {
		int l = 0, r = A.length - 1;
		while (l < r - 1) { // important!
			if (A[l] < A[r])
				return A[l];

			int m = l + (r - l) / 2;
			if (A[m] < A[r])
				r = m; // important!
			else
				l = m;
		}

		return Math.min(A[l], A[r]);
	}

	/**
	 * Second time
	 * Minimum : A[mid] < A[mid-1] && A[mid] < A[mid+1]
	 * Case analysis by the comparing A[l] ? A[r] and A[mid] ? A[l]:
	 * if (A[l] > A[r] && A[mid] < A[l]) || A[l] < A[r], move toward left
	 * Else, move toward right.
	 */
	public int findMin2(int[] A) {
		int l = 0, r = A.length - 1;
		while (l <= r) {
			int mid = l + (r - l) / 2;
			if ((mid + 1 == A.length || A[mid] < A[mid + 1])
					&& (mid == 0 || A[mid] < A[mid - 1]))
				return A[mid];
			else if ((A[l] > A[r] && A[mid] < A[l]) || A[l] < A[r])
				r = mid - 1;
			else
				l = mid + 1;
		}
		return -1;
	}

	/**
	 * Improved from findMin2(), split the second condition to two conditions
	 */
	public int findMin2_Improve(int[] A) {
		int l = 0, r = A.length - 1;
		while (l <= r) {
			int mid = l + (r - l) / 2;
			if ((mid + 1 == A.length || A[mid] < A[mid + 1])
					&& (mid == 0 || A[mid] < A[mid - 1]))
				return A[mid];
			else if (A[l] < A[r])
				return A[l];
			else if (A[mid] < A[l]) // A[l] > A[r] && A[mid] < A[l]
				r = mid - 1;
			else
				l = mid + 1;
		}
		return -1;
	}

	/**
	 * First time solution
	 */
	public int findMin(int[] A) {
		int l = 0, r = A.length - 1;
		while (l <= r) {
			int m = (l + r) / 2;
			if (A[l] <= A[r]) { // include A.length==1
				return A[l];
			} else if (A[m] > A[m + 1]) {
				return A[m + 1];
			} else { // A[l] > A[r] && A[m] < A[m+1]
				if (A[m] > A[l])
					l = m;
				else
					r = m;
			}
		}
		return -1;
	}

}
