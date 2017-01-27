package interview.leetcode;

/**
 * Follow up for "Remove Duplicates": What if duplicates are allowed at most
 * twice?
 * 
 * For example, Given sorted array A = [1,1,1,2,2,3],
 * 
 * Your function should return length = 5, and A is now [1,1,2,2,3].
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Remove_Duplicates_from_Sorted_Array_II {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Second time
	 */
	public int removeDuplicates2(int[] A) {
		if (A.length == 0)
			return 0;
		int dup = 1, length = 0;
		for (int i = 1; i < A.length; i++) {
			if ((A[i] == A[length] && dup == 1) || A[i] != A[length]) {
				dup = A[i] != A[length] ? 1 : dup + 1;
				length++;
				A[length] = A[i];
			}
		}
		return length + 1;
	}

	public static int removeDuplicates(int[] A) {
		if (A.length == 0)
			return 0;
		int diff = 1;
		int dup = 1;
		for (int i = 1; i < A.length; i++) {
			if (A[i] == A[i - 1]) {
				dup++;
				if (dup <= 2)
					A[diff++] = A[i];
			} else {
				A[diff++] = A[i];
				dup = 1;
			}
		}
		return diff;
	}
}
