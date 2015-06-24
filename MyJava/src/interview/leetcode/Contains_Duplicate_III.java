package interview.leetcode;

import static org.junit.Assert.assertEquals;
import interview.AutoTestUtils;

import java.util.TreeSet;

import org.junit.Test;

/**
 * Given an array of integers, find out whether there are two distinct indices i
 * and j in the array such that the difference between nums[i] and nums[j] is at
 * most t and the difference between i and j is at most k.
 * 
 * @author yazhoucao
 *
 */
public class Contains_Duplicate_III {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Contains_Duplicate_III.class);
	}

	/**
	 * Maintain a k-sized BST window
	 */
	public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
		TreeSet<Integer> bst = new TreeSet<>();
		for (int i = 0; i < nums.length; i++) {
			if (i > k)
				bst.remove(nums[i - k - 1]);
			Integer ceilVal = bst.ceiling(nums[i]);
			// convert to long to prevent overflow
			if (ceilVal != null && ceilVal - (long) nums[i] <= t)
				return true;
			Integer floorVal = bst.floor(nums[i]);
			if (floorVal != null && (long) nums[i] - floorVal <= t)
				return true;
			bst.add(nums[i]);
		}
		return false;
	}

	@Test
	public void test1() {
		int[] nums = { 0 };
		int k = 0;
		int t = 0;

		assertEquals(false, containsNearbyAlmostDuplicate(nums, k, t));
	}

	@Test
	public void test2() {
		// [-1,2147483647], 1, 2147483647
		int[] nums = { -1, 2147483647 };
		int k = 1;
		int t = 2147483647;

		assertEquals(false, containsNearbyAlmostDuplicate(nums, k, t));
	}
}
