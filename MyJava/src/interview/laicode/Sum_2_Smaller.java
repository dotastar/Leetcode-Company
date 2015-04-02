package interview.laicode;

import java.util.Arrays;

/**
 * 2 Sum Smaller
 * Fair
 * Data Structure
 * 
 * Determine the number of pairs of elements in a given array that sum to a
 * value smaller than the given target number.
 * 
 * Assumptions
 * 
 * The given array is not null and has length of at least 2
 * 
 * Examples
 * 
 * A = {1, 2, 2, 4, 7}, target = 7, number of pairs is 6({1,2}, {1, 2}, {1, 4},
 * {2, 2}, {2, 4}, {2, 4})
 * 
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Sum_2_Smaller {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Sort the array, and enumerate all the pairs, if the sum is >= target,
	 * there is no need to enumerate the rest of the current loop, because it's
	 * sorted, the rest pairs must >= target too.
	 */
	public int smallerPairs(int[] A, int target) {
		int cnt = 0;
		Arrays.sort(A);
		for (int i = 0; i < A.length - 1; i++) {
			for (int j = i + 1; j < A.length; j++) {
				if (A[i] + A[j] >= target)
					break;
				cnt++;
			}
		}
		return cnt;
	}
}
