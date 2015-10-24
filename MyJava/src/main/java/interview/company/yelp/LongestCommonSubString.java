package interview.company.yelp;

public class LongestCommonSubString {
	
	public static void main(String[] args){
		String s1 = "abcddfeqsd";
		String s2 = "weqbcddfe";
		System.out.println("Longest length: "+dp(s1, s2));
	}
	
	
	/**
	 * Dynamic programming
	 * 
	 * dp[i][j] = 
	 * 		if e1==e2, dp[i-1][j-1] + 1
	 * 		else	0 
	 * @param s1
	 * @param s2
	 */
	public static int dp(String s1, String s2){
		int len1 = s1.length()+1;
		int len2 = s2.length()+1;
		int[][] dp = new int[len1][len2];
		for(int i=0; i<len1; i++)
			dp[i][0] = 0;
		for(int i=0; i<len2; i++)
			dp[0][i] = 0;
		
		int maxLen = 0;
		//end initialize
		
		for(int i=1; i<len1; i++){
			for(int j=1; j<len2; j++){
				if(s1.charAt(i-1)==s2.charAt(j-1)){
					dp[i][j] = dp[i-1][j-1]+1;
				}else{
					dp[i][j] = dp[i-1][j-1];
				}
				if(dp[i][j]>maxLen)
					maxLen = dp[i][j];
			}
		}
		
		print(dp);
		return maxLen;
	}
	
	private static void print(int[][] dp){
		if(dp.length==0) return;
		int len1 = dp.length;
		int len2 = dp[0].length;
		for(int i=0; i<len1; i++){
			for(int j=0; j<len2; j++)
				System.out.print(dp[i][j]+" ");
			System.out.println();
		}
	}
}
