package interview.leetcode;

/**
 * Rotate an array of n elements to the right by k steps.
 * 
 * For example, with n = 7 and k = 3, the array [1,2,3,4,5,6,7] is rotated to
 * [5,6,7,1,2,3,4].
 * 
 * Note:
 * Try to come up as many solutions as you can, there are at least 3 different
 * ways to solve this problem.
 * 
 * [show hint]
 * Hint:
 * Could you do it in-place with O(1) extra space?
 * 
 * Related problem: Reverse Words in a String II
 * 
 * @author yazhoucao
 * 
 */
public class Rotate_Array {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void rotate(int[] nums, int k) {
		k = k % nums.length;
		k = nums.length - k;
		if (k == 0)
			return;
		reverse(nums, 0, k - 1);
		reverse(nums, k, nums.length - 1);
		reverse(nums, 0, nums.length - 1);
	}

	private void reverse(int[] A, int i, int j) {
		while (i < j) {
			int temp = A[i];
			A[i] = A[j];
			A[j] = temp;
			i++;
			j--;
		}
	}
}
