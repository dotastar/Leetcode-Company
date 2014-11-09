package interview.leetcode;

/**
 * Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.
 * 
 * For example, Given: s1 = "aabcc", s2 = "dbbca",
 * 
 * When s3 = "aadbbcbcac", return true.
 * 
 * When s3 = "aadbbbaccc", return false.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Interleaving_String {

	public static void main(String[] args) {
		System.out.println(isInterleave("aabcc", "dbbca", "aadbbcbcac"));
		System.out.println(isInterleave("aabcc", "dbbca", "aadbbbaccc"));

		System.out.println(isInterleave_DP("aabcc", "dbbca", "aadbbcbcac"));
		System.out.println(isInterleave_DP("aabcc", "dbbca", "aadbbbaccc"));
		
		
		System.out.println(isInterleave_DP_Improved("a", "", "c"));
	}

	/**
	 * Dynamic Programming, Time: O(mn), Space: O(mn)
	 * 
	 * dp[i][j] : length i of s1 and length j of s2 can interleave length i+j of
	 * s3
	 * 
	 * dp[i][j] = ((dp[i - 1][j] && c1 == c3) || (dp[i][j - 1] && c2 == c3));
	 * 
	 * @return
	 */
	public static boolean isInterleave_DP(String s1, String s2, String s3) {
		int len1 = s1.length();
		int len2 = s2.length();
		if (len1 + len2 != s3.length())
			return false;
		boolean[][] dp = new boolean[len1 + 1][len2 + 1];
		dp[0][0] = true;
		for (int i = 1; i <= len1; i++)
			dp[i][0] = dp[i-1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);
		for (int i = 1; i <= len2; i++)
			dp[0][i] = dp[0][i-1] && s2.charAt(i - 1) == s3.charAt(i - 1);

		for (int i = 1; i <= len1; i++) {
			for (int j = 1; j <= len2; j++) {
				char c = s3.charAt(i+j-1);
                dp[i][j] = (c==s1.charAt(i-1) && dp[i-1][j]) || (c==s2.charAt(j-1) && dp[i][j-1]);
			}
		}
		return dp[len1][len2];
	}

	/**
	 * Dynamic Programming, space improved, Time: O(mn), Space: O(min(m, n))
	 */
	public static boolean isInterleave_DP_Improved(String s1, String s2, String s3) {
		int len1 = s1.length();
		int len2 = s2.length();
		int len3 = s3.length();
		if (len1 + len2 != len3)
			return false;
		
		boolean[] dp = new boolean[len2 + 1];
		dp[0] = true;
		
		for (int i = 1; i <= len2; i++)
			dp[i] = dp[i-1] && s2.charAt(i - 1) == s3.charAt(i - 1);
		
		for (int i = 1; i <= len1; i++) {
			//dp[i][0] = dp[i-1][0] && ....
			dp[0] = dp[0] && s1.charAt(i-1)==s3.charAt(i-1);
			for (int j = 1; j <= len2; j++) {
				char c = s3.charAt(i + j - 1);
				dp[j] = (c == s1.charAt(i-1) && dp[j]) || (c == s2.charAt(j - 1) && dp[j - 1]);
			}
		}
		return dp[len2];
	}

	/**
	 * Recursion, Time O(2^n)
	 * 
	 * Time Limit Exceeded
	 * 
	 */
	public static boolean isInterleave(String s1, String s2, String s3) {
		int len1 = s1.length();
		int len2 = s2.length();
		int len3 = s3.length();
		if (len1 + len2 != len3)
			return false;
		else if (0 == len3)
			return true;
		else {
			if (len1 > 0 && s1.charAt(0) == s3.charAt(0))
				if (isInterleave(s1.substring(1), s2, s3.substring(1)))
					return true;
			if (len2 > 0 && s2.charAt(0) == s3.charAt(0))
				if (isInterleave(s1, s2.substring(1), s3.substring(1)))
					return true;
			return false;
		}
	}
}
