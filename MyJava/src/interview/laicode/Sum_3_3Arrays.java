package interview.laicode;

import java.util.Arrays;

/**
 * 
 * 3 Sum 3 Arrays
 * Fair
 * Data Structure
 * 
 * Given three arrays, determine if a set can be made by picking one element
 * from each array that sums to the given target number.
 * 
 * Assumptions
 * 
 * The three given arrays are not null and have length of at least 1
 * 
 * Examples
 * 
 * A = {1, 3, 5}, B = {8, 2}, C = {3}, target = 14, return true(pick 3 from A,
 * pick 8 from B and pick 3 from C)
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Sum_3_3Arrays {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Time: O(LenA * (LenB + LenC)) = O(LenA * max(LenB, LenC)).
	 */
	public boolean exist(int[] a, int[] b, int[] c, int target) {
		Arrays.sort(a);
		Arrays.sort(b);
		Arrays.sort(c);
		for (int i = 0; i < a.length && a[i] <= target; i++) {
			int j = 0, k = c.length - 1;
			while (j < b.length && k >= 0) {
				int sum = a[i] + b[j] + c[k];
				if (sum == target)
					return true;
				else if (sum < target)
					j++;
				else
					k--;
			}
		}
		return false;
	}
}
