package interview.leetcode;

import static org.junit.Assert.assertEquals;
import interview.AutoTestUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Given an array of integers, find if the array contains any duplicates. Your
 * function should return true if any value appears at least twice in the array,
 * and it should return false if every element is distinct.
 * 
 * @author yazhoucao
 *
 */
public class Contains_Duplicate {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Contains_Duplicate.class);
	}

	/**
	 * Method - 1 Sort + Scan
	 */
	public boolean containsDuplicate(int[] nums) {
		Arrays.sort(nums);
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] == nums[i - 1])
				return true;
		}
		return false;
	}

	/**
	 * Method - 2 HashSet
	 */
	public boolean containsDuplicate2(int[] nums) {
		Set<Integer> distinct = new HashSet<Integer>();
		for (int num : nums) {
			if (!distinct.add(num))
				return true;
		}
		return false;
	}

	/**
	 * Method - 3 Functional, distinct()
	 */
	public boolean containsDuplicate3(int[] nums) {
		return nums.length != Arrays.stream(nums).distinct().count();
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}
}
