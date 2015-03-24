package interview.leetcode;

/**
 * Given a string s, partition s such that every substring of the partition is a
 * palindrome.
 * 
 * Return the minimum cuts needed for a palindrome partitioning of s.
 * 
 * For example, given s = "aab", Return 1 since the palindrome partitioning
 * ["aa","b"] could be produced using 1 cut.
 * 
 * @author yazhoucao
 * 
 */
public class Palindrome_Partitioning_II {

	public static void main(String[] args) {
		System.out.println(minCut2("ab")); // 1
		System.out.println(minCut2("aa")); // 0
		System.out.println(minCut2("abb")); // 1
		System.out.println(minCut2("aab")); // 1
	}

	public static int minCut2(String S) {
		int length = S.length();
		int[][] M = new int[length][length + 1];
		for (int i = length - 2; i >= 0; i--) {
			for (int len = 2; i + len <= length; len++) {
				int j = i + len - 1;
				M[i][j] = M[i][j - 1] + 1;
				if (S.charAt(i) == S.charAt(j))
					M[i][j] = Math.min(M[i + 1][j - 1], M[i][j]);
			}
		}
		return M[0][length - 1];
	}

	/**
	 * DP, Time O(n^2), Space O(n^2)
	 * 
	 * dp[i] : min cut of substring from 0 to i (include i)
	 * 
	 * use a boolean[l][r] isPal to store if the substring l to r is a
	 * palindrome
	 * 
	 * isPal[l][r] =
	 * 
	 * inner is a palindrome and outer equals || str length is 1/0 and eqauals
	 * (isPal[l+1][r-1] && str[l]==str[r]) || (r-l<2 && str[l]==str[r])
	 * 
	 * l is from 0 to r, try all possible substr[l...r] and at the same time
	 * update the min dp[r](only when substr[l...r] is a palindrome):
	 * 
	 * dp[r] = min(dp[l-1]+1, dp[r]), if substr[l...r] is a palindrome.
	 * Otherwise, can't update dp[r] at point l.
	 * 
	 */
	public static int minCut(String s) {
		int len = s.length();
		boolean[][] isPal = new boolean[len][len];
		int[] dp = new int[len];
		// dp[i] : the min cuts from 0 to i, length = i+1
		for (int i = 0; i < len; i++)
			dp[i] = i;
		for (int r = 0; r < len; r++) {
			for (int l = 0; l <= r; l++) {
				if (s.charAt(l) == s.charAt(r)
						&& (r - l <= 1 || isPal[l + 1][r - 1])) {
					isPal[l][r] = true; // s[l..r] is palindrome
					if (l == 0)
						dp[r] = 0; // because str[l...r] == s, is a palindrome
					else
						dp[r] = dp[l - 1] + 1 < dp[r] ? dp[l - 1] + 1 : dp[r];
				}
			}
		}
		return dp[len - 1];
	}
}
