package interview.company.linkedin;

import java.util.Arrays;

/**
 * Find a triangle in a list of random numbers (a triangle is three numbers such that no one number is larger than the other two numbers added up)
 *
 * @author asia created on 1/13/16.
 */
public class FindATriangle {
  public static void main(String[] args) {

  }

  // O(nlogn)
  public boolean findTriangle(int[] nums) {
    if (nums == null) {
      return false;
    }
    Arrays.sort(nums);
    for (int i = 2; i < nums.length; i++) {
      if (nums[i] < nums[i - 1] + nums[i - 2]) {
        return true;
      }
    }
    return false;
  }
}
