package interview.laicode;

/**
 * 
 * Longest Common Subsequence
 * Fair
 * String
 * 
 * Find the length of longest common subsequence of two given strings.
 * 
 * Assumptions
 * 
 * The two given strings are not null
 * 
 * Examples
 * 
 * S = “abcde”, T = “cbabdfe”, the longest common subsequence of s and t is
 * {‘a’, ‘b’, ‘d’, ‘e’}, the length is 4.
 * 
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Longest_Common_Subsequence {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * M[i][j] : the length of longest common subsequence of s end at i, and t
	 * end at j
	 * 
	 * Base case: M[0][0] = 0
	 * Induction rule:
	 * M[i][j]:
	 * 1.M[i][j] = max(M[i-1][j], M[i][j-1])
	 * 2.if s[i] == t[j], M[i][j] = max(M[i][j], M[i-1][j-1] + 1)
	 * 
	 * # a b c d e
	 * c 0 0 1 1 1
	 * b 0 1 1 1 1
	 * a 1 1 1 1 1
	 * b 1 2 2 2 2
	 * d 1 2 2 3 3
	 * f 1 2 2 3 3
	 * e 1 2 2 3 4
	 * 
	 */
	public int longest(String s, String t) {
		int[][] M = new int[s.length() + 1][t.length() + 1];
		for (int i = 1; i <= s.length(); i++) {
			for (int j = 1; j <= t.length(); j++) {
				M[i][j] = Math.max(M[i - 1][j], M[i][j - 1]);
				if (s.charAt(i - 1) == t.charAt(j - 1))
					M[i][j] = Math.max(M[i][j], M[i - 1][j - 1] + 1);
			}
		}
		return M[s.length()][t.length()];
	}

	/**
	 * Space improved
	 */
	public int longest_Improved(String s, String t) {
		int[] M = new int[t.length() + 1];
		for (int i = 1; i <= s.length(); i++) {
			int prev_j_1 = 0, prev_j = 0;
			for (int j = 1; j <= t.length(); j++) {
				prev_j_1 = prev_j; // M[i-1][j-1]
				prev_j = M[j]; // M[i-1][j]
				M[j] = Math.max(M[j], M[j - 1]);
				if (s.charAt(i - 1) == t.charAt(j - 1))
					M[j] = Math.max(M[j], prev_j_1 + 1);
			}
		}
		return M[t.length()];
	}
}
