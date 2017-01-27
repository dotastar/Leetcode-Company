package interview.leetcode;

/**
 * Implement regular expression matching with support for '.' and '*'.
 * 
 * '.' Matches any single character. '*' Matches zero or more of the preceding
 * element.
 * 
 * The matching should cover the entire input string (not partial).
 * 
 * The function prototype should be: bool isMatch(const char *s, const char *p)
 * 
 * Some examples: isMatch("aa","a") → false
 * 
 * isMatch("aa","aa") → true
 * 
 * isMatch("aaa","aa") → false
 * 
 * isMatch("aa", "a*") → true
 * 
 * isMatch("aa", ".*") → true
 * 
 * isMatch("ab", ".*") → true
 * 
 * isMatch("aab", "c*a*b") → true
 * 
 * @author yazhoucao
 * 
 */
public class Regular_Expression_Matching {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Regular_Expression_Matching o = new Regular_Expression_Matching();

		"a".substring(1);
		assert o.isMatch("a", "ab*") == true;
		assert o.isMatch3("aa", "a*") == true;
		assert o.isMatch("aab", "aab") == true;
		assert o.isMatch("aab", ".*") == true;
		assert o.isMatch("aab", "c*a*b") == true;
		assert o.isMatch("aab", "a*a*a*b*b") == true;

		assert o.isMatch("a", "aaaa") == false;
		assert o.isMatch("aab", "aa") == false;
		assert o.isMatch("aab", "a*") == false;
		assert o.isMatch("aab", "a*c*bb") == false;

	}

	/**
	 * Third recursion solution, avoid using substring() method, because in Java
	 * 1.7, it becomes a linear time method.
	 * 
	 * This is about 300ms faster than above two in LeetCode.
	 */
	public boolean isMatch3(String s, String p) {
		return match(s, p, 0, 0);
	}

	/**
	 * DFS, try every possible pattern of p
	 * 1.Pattern p will have different forms only if it has '*'
	 * 2.If p does not have '*', it's just a strict match (like a for loop)
	 * 3.If p has '*', we need to consider begin from its preceding element,
	 * because it can cancel one or adding more its preceding element
	 * 
	 * Cases:
	 * 0.j == p.length, return i == s.length()
	 * 
	 * 1.j == p.length - 1, if i < s.length && (si == pj || pj == '.')
	 * return isMatch(i+1, j+1), else return false.
	 * 
	 * 2.j != p.length - 1,
	 * a.if p[j + 1] == '*', if (si == pj || pj == '.') satisfied,
	 * try isMatch(i, j+2), and if false, i++ (borrow one more)
	 * b.else, check if i < s.length && (si == pj || pj == '.')
	 */
	private boolean match(String s, String p, int i, int j) {
		if (j == p.length())
			return s.length() == i;

		char pj = p.charAt(j);
		if (j == p.length() - 1 || p.charAt(j + 1) != '*') {
			if (i < s.length() && (pj == s.charAt(i) || pj == '.'))
				return match(s, p, i + 1, j + 1);
			else
				return false;
		}
		// p[j+1] == '*'
		while (i < s.length() && (pj == s.charAt(i) || pj == '.')) {
			if (match(s, p, i, j + 2))
				return true;
			i++;
		}
		return match(s, p, i, j + 2);
	}

	/**
	 * Brute Force
	 * Overall, there are 2 different cases:
	 * 1) the second char of pattern is "*".
	 * For the 1st case, if the first char of pattern is not ".", the first char
	 * of pattern and string should be the same. Then continue to match the left
	 * part.
	 * 
	 * 2) the second char of pattern is not "*".
	 * For the 2nd case, if the first char of pattern is "." or first char of
	 * pattern == the first i char of string, continue to match the left.
	 * 
	 * Be careful about the offset.
	 * 
	 */
	public boolean isMatch1(String s, String p) {
		if (p.length() == 0)
			return s.length() == 0;

		if (p.length() == 1 || p.charAt(1) != '*') { // second is not a '*'
			if (s.length() >= 1
					&& (p.charAt(0) == '.' || p.charAt(0) == s.charAt(0)))
				return isMatch1(s.substring(1), p.substring(1));
		} else {
			int len = s.length();
			int i = -1;
			while (i < len
					&& (i < 0 || p.charAt(0) == '.' || p.charAt(0) == s
							.charAt(i))) {
				if (isMatch1(s.substring(i + 1), p.substring(2)))
					return true;
				i++;
			}
		}
		return false;
	}

	/**
	 * Second practice, Recursion
	 */
	public boolean isMatch(String s, String p) {
		if (p.length() == 0)
			return s.length() == 0;

		int i = 0;
		int j = 0;
		// keep matching until the reach the end of s
		while (i < s.length()) {
			if (j == p.length())
				return false;
			if (j == p.length() - 1)
				return i == s.length() - 1
						&& (p.charAt(j) == '.' || p.charAt(j) == s.charAt(i));
			// current char has a match
			if (p.charAt(j) == '.' || p.charAt(j) == s.charAt(i)) {
				if (p.charAt(j + 1) != '*') {
					j++;
				} else if (isMatch(s.substring(i), p.substring(j + 2)))
					return true; // next is '*', try skip the current and '*'
			} else
				break; // can't continue, preceding char of * does not match
			i++;
		}
		if (j + 1 < p.length() && p.charAt(j + 1) == '*')
			return isMatch(s.substring(i), p.substring(j + 2));
		else
			return false;
	}

	/**
	 * DP solution -- [Unfinished]
	 * 
	 * dp[i][j] = s[0...i-1] and p[0...j-1] is match?
	 * 
	 * if p[j] != '*', dp[i][j] = dp[i][j+1] = s[i-1]==p[j-1] || p[j-1]=='.'
	 * 
	 * else, p[j] == '*', dp[i][j] =
	 * 
	 */
	public boolean isMatch_DP(String s, String p) {
		s = s.trim();
		int slen = s.length();
		int plen = p.length();
		boolean[][] dp = new boolean[slen + 1][plen + 1];

		return dp[slen][plen];
	}
}
