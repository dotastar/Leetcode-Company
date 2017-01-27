package interview.company.palantir;

import java.util.Arrays;

/**
 * Given an array first in ascending order, then in descending order, find if a target is in the array.
 * int[] array = {1, 3, 5, 8, 9, 7, 4, 2}
 * target = 5, return 2.
 * target = 6, return -1.
 *
 * Created by yazhoucao on 10/29/15.
 */
public class FindNumberInPeakAray {

    /**
     * Assumption: there is no duplicate elements in this array
     */

    /**
     * Thought:
     * 1.Find the index of the peak element, divide the array to [0, peakIdx], (peakIdx, n-1]
     * 2.Binary search in the first part
     * 3.Binary search in the second part
     */
    public int find(int[] A, int target) {
        int peakIdx = findPeakIdx(A);
        int res = Arrays.binarySearch(A, 0, peakIdx, target);
        if (res < 0) {
            res = Arrays.binarySearch(A, peakIdx + 1, A.length - 1, target);
        }
        return res;
    }

    private int findPeakIdx(int[] A) {
        int peakIdx = -1;
        int l = 0, r = A.length - 1;
        while (l < r - 1) {
            int mid = l + (r - l) / 2;
            if (A[mid] > A[mid - 1] && A[mid + 1] > A[r]) {
                peakIdx = mid;
                break;
            } else if (A[mid] < A[mid - 1] && A[mid] > A[mid + 1]) {
                l = mid;
            } else {
                r = mid;
            }
        }
        if (peakIdx < 0) {
            peakIdx = A[l] > A[r] ? A[l] : A[r];
        }
        return peakIdx;
    }
}
