package interview.leetcode;

import static org.junit.Assert.assertEquals;
import interview.AutoTestUtils;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Given an array of integers and an integer k, find out whether there there are
 * two distinct indices i and j in the array such that nums[i] = nums[j] and the
 * difference between i and j is at most k.
 * 
 * @author yazhoucao
 *
 */
public class Contains_Duplicate_II {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Contains_Duplicate_II.class);
	}

	/**
	 * Keep a k-sized window, sliding window
	 */
	public boolean containsNearbyDuplicate(int[] nums, int k) {
		Set<Integer> window = new HashSet<>();
		for (int l = 0, r = 0; r < nums.length; r++) {
			if (r > k)
				window.remove(nums[l++]);

			if (!window.add(nums[r]))
				return true;
		}
		return false;
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}
}
