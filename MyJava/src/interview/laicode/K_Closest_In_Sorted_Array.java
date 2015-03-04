package interview.laicode;

/**
 * 
 K Closest In Sorted Array
 * Fair
 * Data Structure
 * 
 * Given a target integer T, a non-negative integer K and an integer array A
 * sorted in ascending order, find the K closest numbers to T in A.
 * 
 * Assumptions
 * 
 * K is guranteed to be >= 0 and K is guranteed to be <= A.length
 * 
 * Return
 * 
 * A size K integer array containing the K closest numbers(not indices) in A,
 * sorted in ascending order by the difference between the number and T.
 * 
 * Examples
 * 
 * A = {1, 2, 3}, T = 2, K = 3, return {2, 1, 3} or {2, 3, 1}
 * A = {1, 4, 6, 8}, T = 3, K = 3, return {4, 1, 6}
 * 
 * Corner Cases
 * 
 * What if A is null? We should return null in this case.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class K_Closest_In_Sorted_Array {

	public static void main(String[] args) {

	}

	/**
	 * Binary search the index, expand to left and right from that point  
	 * Time: O(logn) + O(k)
	 */
	public int[] kClosest(int[] array, int target, int k) {
		if (array == null || array.length == 0)
			return null;
		// search the closest index
		int l = 0, r = array.length - 1;
		while (l < r - 1) {
			int mid = l + (r - l) / 2;
			if (array[mid] == target) {
				l = mid;
				r = mid + 1;
				break;
			} else if (array[mid] > target)
				r = mid;
			else
				l = mid;
		}
		// the closest index will be either l or r, expanding
		int[] res = new int[k];
		for (int i = 0; i < k; i++) {
			if (r >= array.length || (l >= 0 && Math.abs(target - array[l]) < Math.abs(target - array[r])))
				res[i] = array[l--]; // expand left
			else
				res[i] = array[r++]; // expand right
		}
		return res;
	}
}
