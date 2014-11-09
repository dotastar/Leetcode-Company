package interview.leetcode;

/**
 * Follow up for "Find Minimum in Rotated Sorted Array":
 * What if duplicates are allowed?
 * 
 * Would this affect the run-time complexity? How and why?
 * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
 * 
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * 
 * Find the minimum element.
 * 
 * The array may contain duplicates.
 * 
 * @author yazhoucao
 * 
 */
public class Find_Minimum_in_Rotated_Sorted_Array_II {

	public static void main(String[] args) {
		Find_Minimum_in_Rotated_Sorted_Array_II o = new Find_Minimum_in_Rotated_Sorted_Array_II();
		assert o.findMin(new int[] { 3, 3, 3, 1 }) == 1;
	}

	public int findMin(int[] A) {
		int l = 0;
		int r = A.length - 1;
		while (l <= r) {
			int m = (l + r) / 2;
			if (A[l] < A[r]) {
				return A[l];
			} else if (A[l] == A[r]) { // to handle cases like: 3 3 1 3 3
				if (l == r)
					return A[l];	//cases like: 1 1 1
				l++;	// normal cases like: 3 3 1 3 3
			} else if (A[m] > A[m + 1]) {
				return A[m + 1];
			} else {
				if (A[m] >= A[l])
					l = m;
				else
					r = m;
			}
		}
		return -1;
	}
}
