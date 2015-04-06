package interview.laicode;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Maximum Values Of Sliding Windows
 * Fair
 * Data Structure
 * 
 * Given an integer array A and a sliding window of size K, find the maximum
 * value of each window as it slides from left to right.
 * 
 * Assumptions
 * 
 * The given array is not null and is not empty
 * 
 * K >= 1, K <= A.length
 * 
 * Examples
 * 
 * A = {1, 2, 3, 2, 4, 2, 1}, K = 3, the windows are {{1,2,3}, {2,3,2}, {3,2,4},
 * {2,4,2}, {4,2,1}},
 * 
 * and the maximum values of each K-sized sliding window are [3, 3, 4, 4, 4]
 * 
 * @author yazhoucao
 * 
 */
public class Maximum_Values_Of_Sliding_Windows {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Sliding Window, record the current max value and index of current window,
	 * 1.If current max is valid in current, put it in the result list,
	 * 2.Otherwise(maxIdx == l), the maxValue is expired, search the new max in
	 * this window again.
	 * 
	 * Time: O(nk), worst case is n*k, when the array is in descending order.
	 * 
	 * 1, 2, 3, 2, 1, 2, 1
	 * ^_____^
	 * maxVal: 4
	 * maxIdx: 4
	 * 
	 */
	public List<Integer> maxWindows(int[] A, int k) {
		List<Integer> res = new ArrayList<>();
		int maxVal = 0, maxIdx = 0;
		// initialization
		for (int i = 0; i < k; i++) {
			if (A[i] > maxVal) {
				maxVal = A[i];
				maxIdx = i;
			}
		}
		res.add(maxVal);
		for (int l = 0, r = k; r < A.length; r++, l++) {
			if (A[r] >= maxVal) {
				maxVal = A[r];
				maxIdx = r;
			} else {
				if (maxIdx == l) { // current max is expired, find a new max
					maxVal = A[l + 1];
					maxIdx = l + 1;
					for (int i = l + 1; i <= r; i++) {
						if (A[i] >= maxVal) {
							maxVal = A[i];
							maxIdx = i;
						}
					}
				}
			}
			res.add(maxVal);
		}
		return res;
	}
}
