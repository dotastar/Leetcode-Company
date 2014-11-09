package interview.company.yelp;

import java.util.Arrays;

public class LongestInsreasingSequence {

	public static void main(String[] args) {
		int[] input = {-3, 1, 2, -2, 4, -1, 6, -7,-6,5,0,1,2};
		System.out.println(dp(input));
	}
	
	
	/**
	 * dp[i] = 
	 * 		dp[k] + 1, if there exists an k such that k<i, nums[k] < nums[i]
	 * 		1, otherwise
	 * @param nums
	 * @return
	 */
	public static int dp(int[] nums){
		int maxLen = 0;
		int len = nums.length;
		int[] dp = new int[len];
		for(int i=0; i<len; i++)
			dp[i] = 1;
		//end initialize
		
		for(int i=1; i<len; i++){
			for(int k=i-1; k>=0; k--){
				if(nums[k]<nums[i] && dp[i]<dp[k]+1){	
					//find the k that make dp[i] max
					dp[i] = dp[k] + 1;
				}
			}
			
			maxLen = Math.max(maxLen, dp[i]);
		}
		System.out.println(Arrays.toString(dp));
		return maxLen;
	}
}
