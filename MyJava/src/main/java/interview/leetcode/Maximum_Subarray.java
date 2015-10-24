package interview.leetcode;

/**
 * Find the contiguous subarray within an array (containing at least one number)
 * which has the largest sum.
 * 
 * For example, given the array [−2,1,−3,4,−1,2,1,−5,4], the contiguous subarray
 * [4,−1,2,1] has the largest sum = 6.
 * 
 * click to show more practice.
 * 
 * More practice: If you have figured out the O(n) solution, try coding another
 * solution using the divide and conquer approach, which is more subtle.
 * 
 * @author yazhoucao
 * 
 */
public class Maximum_Subarray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * DP
	 * dp[i] means the maximum contiguous subarray that ends at A[i]
	 */
	public int maxSubArray(int[] A) {
		int[] dp = new int[A.length];
		dp[0] = A[0];
		int max = dp[0];
		for (int i = 1; i < dp.length; i++) {
			dp[i] = dp[i - 1] > 0 ? dp[i - 1] + A[i] : A[i];
			if (dp[i] > max)
				max = dp[i];
		}
		return max;
	}

	/**
	 * Same DP thought, Space Improved
	 * constant space
	 * 
	 * Now, old sum is dp[i-1], current sum is dp[i].
	 */
	public int maxSubArray_Improved(int[] A) {
		int sum = A[0];
		int max = A[0];
		for (int i = 1; i < A.length; i++) {
			sum = sum > 0 ? sum + A[i] : A[i];
			if (sum > max)
				max = sum;
		}
		return max;
	}

	public int maxSubArray_Improved2(int[] A) {
		int sum = A.length > 0 ? A[0] : 0;
		int max = sum;
		for (int i = 1; i < A.length; i++) {
			sum = sum > 0 ? sum + A[i] : A[i];
			max = sum > max ? sum : max;
		}
		return max;
	}
}
