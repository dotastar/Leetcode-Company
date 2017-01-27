package interview.leetcode;

/**
 * Given a string S and a string T, count the number of distinct subsequences of
 * T in S.
 * 
 * A subsequence of a string is a new string which is formed from the original
 * string by deleting some (can be none) of the characters without disturbing
 * the relative positions of the remaining characters. (ie, "ACE" is a
 * subsequence of "ABCDE" while "AEC" is not).
 * 
 * Here is an example: S = "rabbbit", T = "rabbit"
 * 
 * Return 3.
 * 
 * @author yazhoucao
 * 
 */
public class Distinct_Subsequences {

	public static void main(String[] args) {
		Distinct_Subsequences obj = new Distinct_Subsequences();
		System.out.print(obj.numDistinct_DP("rabbbit", "rabebit"));
		System.out.print(obj.numDistinct_DP("ABCDE", "ACE"));
		System.out.print(obj.numDistinct_DP("rabbbit", "rabbit"));
		System.out.print(obj.numDistinct_DP("abcdefghhiijjkopqrst", "hj"));

		System.out.println();

		System.out.print(obj.numDistinct_DP_Improved("rabbbit", "rabebit"));
		System.out.print(obj.numDistinct_DP_Improved("ABCDE", "ACE"));
		System.out.print(obj.numDistinct_DP_Improved("rabbbit", "rabbit"));
		System.out.print(obj.numDistinct_DP_Improved("abcdefghhiijjkopqrst",
				"hj"));

	}

	/**
	 * Brute force, try to delete every character in S
	 * 
	 * Exponential Time, O(k^k), k = S.length - T.length
	 * 
	 */
	public int numDistinct(String S, String T) {
		int rest = S.length() - T.length();
		if (rest < 0)
			return 0;
		return deleteChars(rest, new StringBuilder(S), T);
	}

	public int deleteChars(int count, StringBuilder S, String T) {
		if (count == 0) {
			return S.toString().equals(T) ? 1 : 0;
		}
		int res = 0;
		for (int i = 0; i < S.length(); i++) {
			char deletedChar = S.charAt(i);
			S.deleteCharAt(i);
			res += deleteChars(count - 1, S, T);
			S.insert(i, deletedChar);
		}
		return res;
	}

	/**
	 * DP second practice
	 *** 0 A C E
	 * 0 1
	 * A 1 1
	 * B 1 1 0
	 * C 1 1 1 0
	 * D 1 1 1 1
	 * A 1 2 1 1
	 * 
	 * M[i][j]: distinct subsequences of S[0, i-1] and T[0, j-1]
	 * 
	 * Base case:
	 * M[i][0] = 1
	 * 
	 * Induction rule:
	 * M[i][j] = M[i-1][j] + M[i-1][j-1], if s[i-1] == t[j-1]
	 ********* = M[i-1][j], else
	 */
	public int numDistinct_DP2(String S, String T) {
		if (S.length() < T.length())
			return 0;
		int[][] M = new int[S.length() + 1][T.length() + 1];
		M[0][0] = 1;
		for (int i = 1; i <= S.length(); i++) {
			M[i][0] = 1;
			for (int j = 1; j <= i && j <= T.length(); j++) {
				M[i][j] = S.charAt(i - 1) == T.charAt(j - 1) ? M[i - 1][j - 1] + M[i - 1][j] : M[i - 1][j];
			}
		}
		return M[S.length()][T.length()];
	}

	/**
	 * When you see string problem that is about subsequence or matching,
	 * dynamic programming method should come to your mind naturally. The key is
	 * to find the changing condition.
	 * 
	 * Let W(i, j) stand for the number of subsequences of S(0, i) in T(0, j).
	 * If S.charAt(i) == T.charAt(j), W(i, j) = W(i-1,j) + W(i-1, j-1)
	 * Otherwise, W(i, j) = W(i-1,j).
	 * 
	 * Explanation:
	 * if S[i] != T[j], then whether deleting S[i] will have no difference,
	 * so W(i,j) = W(i-1,j)
	 * 
	 * if S[i] = T[j], then delete S[i] will have a difference, which we can
	 * choose to delete S[i] and keep matching S[0 ~ i-1] and T[0~j] or to match
	 * S[i] and T[j] and keep matching the rest S[0 ~ i-1] and T[0 ~ j-1].
	 * 
	 * So W(i, j) should be the sum of these two choices
	 * 
	 * therefore W(i, j) = W(i-1,j) + W(i-1, j-1) ;
	 * 
	 * Time : O(mn), Space: O(mn)
	 */
	public int numDistinct_DP(String S, String T) {
		int lenS = S.length(), lenT = T.length();
		int[][] dp = new int[lenS + 1][lenT + 1];
		for (int i = 0; i <= lenS; i++)
			dp[i][0] = 1;
		for (int i = 1; i <= lenS; i++) {
			for (int j = 1; j <= lenT; j++) {
				dp[i][j] = S.charAt(i - 1) == T.charAt(j - 1) ? dp[i - 1][j] + dp[i - 1][j - 1] : dp[i - 1][j];
			}
		}
		return dp[lenS][lenT];
	}

	/**
	 * Still DP, Space Improved, just use O(n) is enough
	 * 
	 * Because of the transition formula:
	 * 
	 * W(i, j) = S[i]==T[j] ? W(i-1,j) + W(i-1, j-1) : W(i-1, j),
	 * 
	 * what we really need is just W[j] = S[i]==T[j] ? W(j) + W(j-1) : W(j),
	 * 
	 * Because we iterative in rows, so after calculated W of row i-1, we can
	 * use it to calculate W of row i
	 */
	public int numDistinct_DP_Improved(String S, String T) {
		int lenS = S.length();
		int lenT = T.length();
		if (lenS < lenT)
			return 0;
		int[] M = new int[lenT + 1];

		for (int i = 1; i <= lenS; i++) {
			M[0] = 1;
			for (int j = lenT; j >= 1; j--) {
				M[j] = S.charAt(i - 1) == T.charAt(j - 1) ? M[j] + M[j - 1] : M[j];
			}
		}

		return M[lenT];
	}
}
