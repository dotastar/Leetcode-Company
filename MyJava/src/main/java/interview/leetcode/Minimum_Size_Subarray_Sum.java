package interview.leetcode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Given an array of n positive integers and a positive integer s, find the
 * minimal length of a subarray of which the sum ≥ s. If there isn't one, return
 * 0 instead.
 * 
 * For example, given the array [2,3,1,2,4,3] and s = 7,
 * the subarray [4,3] has the minimal length under the problem constraint.
 * 
 * click to show more practice.
 * More practice:
 * 
 * If you have figured out the O(n) solution, try coding another solution of
 * which the time complexity is O(n log n).
 * 
 * @author yazhoucao
 *
 */
public class Minimum_Size_Subarray_Sum {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Minimum_Size_Subarray_Sum.class);
	}

	/**
	 * Sliding window
	 * Time: O(n)
	 */
	public int minSubArrayLen(int s, int[] nums) {
		int minLen = 0, sum = 0;
		for (int l = 0, r = 0; r < nums.length; r++) {
			sum += nums[r];
			if (sum >= s) {
				while (sum >= s)
					sum -= nums[l++];
				int currLen = r - l + 2;
				if (minLen == 0 || currLen < minLen)
					minLen = currLen;
			}
		}
		return minLen;
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}
}
