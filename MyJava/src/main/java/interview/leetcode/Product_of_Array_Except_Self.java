package interview.leetcode;

/**
 * Given an array of n integers where n > 1, nums, return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].
 * <p>
 * Solve it without division and in O(n).
 * <p>
 * For example, given [1,2,3,4], return [24,12,8,6].
 * <p>
 * Follow up:
 * Could you solve it with constant space complexity? (Note: The output array does not count as extra space for the purpose of space complexity analysis.)
 * <p>
 * Hide Company Tags LinkedIn Facebook
 * Hide Tags Array
 * Show Similar Problems
 *
 * @author asia created on 1/13/16.
 */
public class Product_of_Array_Except_Self {
  // O(n)
  public int[] productExceptSelf(int[] nums) {
    int[] res = new int[nums.length];
    int product = 1;
    res[0] = 1;
    for (int i = 1; i < nums.length; i++) {
      product *= nums[i - 1];
      res[i] = product;
    }
    product = 1;
    for (int i = nums.length - 2; i >= 0; i--) {
      product *= nums[i + 1];
      res[i] *= product;
    }
    return res;
  }
}
