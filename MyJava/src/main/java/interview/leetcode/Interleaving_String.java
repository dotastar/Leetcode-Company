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
	 * Dynamic programming
	 * M[i][j]: s1[0, i) and s2[0, j) is interleaving String of s3[0, i+j-1)
	 * 
	 * Base case: M[0][0] = true
	 * 
	 * Induction rule:
	 * M[i][j] = M[i-1][j] && s1[i-1] == s3[i+j-1] or M[i][j-1] && s2[j-1] == s3[i+j-1]
	 * 
	 * E.g. 
	 * a a d b b b a c c c
	 * 
	 * # 0 a a b c c
	 * 0 1 1 1 0 0 0
	 * d 0 0 1 1 0 0
	 * b 0 0 1 1 0 0
	 * b 0 0 1 1 0 0
	 * c 0 0 0 0 0 0
	 * a 0 0 0 0 0 0
	 * 
	 */
	public static boolean isInterleave_DP(String s1, String s2, String s3) {
		if (s1.length() + s2.length() != s3.length())
			return false;
		boolean[][] M = new boolean[s1.length() + 1][s2.length() + 1];
		M[0][0] = true;
		for (int i = 1; i <= s1.length() && s1.charAt(i - 1) == s3.charAt(i - 1); i++)
			M[i][0] = true;
		for (int i = 1; i <= s2.length() && s2.charAt(i - 1) == s3.charAt(i - 1); i++)
			M[0][i] = true;

		for (int i = 1; i <= s1.length(); i++) {
			for (int j = 1; j <= s2.length(); j++) {
				M[i][j] = (M[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1))
						|| (M[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
			}
		}
		return M[s1.length()][s2.length()];
	}

	/**
	 * Dynamic Programming, space improved 
	 * Time: O(mn), 
	 * Space: O(min(m, n))
	 */
	public static boolean isInterleave_DP_Improved(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length())
            return false;
        boolean[] M = new boolean[s2.length() + 1];
        M[0] = true;
        for (int i = 1; i <= s2.length() && s2.charAt(i-1) == s3.charAt(i-1); i++)
            M[i] = true;
        for (int i = 1; i <= s1.length(); i++) {
            M[0] = (M[0] && s1.charAt(i - 1) == s3.charAt(i - 1));
            for (int j = 1; j <= s2.length(); j++) {
                M[j] = (M[j] && s1.charAt(i - 1) == s3.charAt(i+j-1))  || (M[j - 1] && s2.charAt(j - 1) == s3.charAt(i+j-1));
            }
        }
        return M[s2.length()];
	}

	/**
	 * Recursion, Time O(2^n)
	 * 
	 * Time Limit Exceeded
	 * 
	 */
	public static boolean isInterleave(String s1, String s2, String s3) {
		int len1 = s1.length(), len2 = s2.length(), len3 = s3.length();
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
