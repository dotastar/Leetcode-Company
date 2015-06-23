package interview.leetcode;

import static org.junit.Assert.assertEquals;
import interview.AutoTestUtils;

import java.util.Random;

import org.junit.Test;

/**
 * Find the kth largest element in an unsorted array. Note that it is the kth
 * largest element in the sorted order, not the kth distinct element.
 * 
 * For example,
 * Given [3,2,1,5,6,4] and k = 2, return 5.
 * 
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ array's length.
 * 
 * @author yazhoucao
 *
 */
public class Kth_Largest_Element_in_an_Array {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Kth_Largest_Element_in_an_Array.class);
	}

	Random ran = new Random();

	public int findKthLargest(int[] nums, int k) {
		// Don't forget to convert k-th to index, convert smallest to largest
		return quickSelect(nums, 0, nums.length - 1, nums.length - k);
	}

	/**
	 * Time: O(n) average, O(n^2) worst
	 */
	private int quickSelect(int[] nums, int l, int r, int k) {
		if (l > r)
			return -1; // // can't find
		int pivot = l + ran.nextInt(r - l + 1);
		swap(nums, l, pivot);
		int j = l + 1;
		for (int i = l + 1; i <= r; i++) {
			if (nums[i] < nums[l])
				swap(nums, i, j++);
		}
		swap(nums, j - 1, l); // (j-1)th is fixed
		if (j - 1 == k)
			return nums[j - 1];
		else if (j - 1 < k)
			return quickSelect(nums, j, r, k);
		else
			return quickSelect(nums, l, j - 2, k);
	}

	/**
	 * Iterative fashion
	 */
	public int quickSelect(int[] nums, int k) {
		int l = 0, r = nums.length - 1;
		while (l <= r) {
			int pivot = l + ran.nextInt(r - l + 1);
			swap(nums, l, pivot);
			int j = l + 1;
			for (int i = l + 1; i <= r; i++) {
				if (nums[i] < nums[l])
					swap(nums, i, j++);
			}
			swap(nums, j - 1, l); // (j-1)th is fixed
			if (j - 1 == k)
				return nums[j - 1];
			else if (j - 1 < k)
				l = j;
			else
				r = j - 2;
		}
		return -1; // can't find
	}

	private void swap(int[] A, int i, int j) {
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}

	@Test
	public void test1() {
		int[] A = { 2, 1 };
		int k = 1;
		int ans = 2;
		int res = findKthLargest(A, k);
		assertEquals(ans, res);
	}
}
