package interview.leetcode;


/**
 * Implement wildcard pattern matching with support for '?' and '*'.
 * 
 * '?' Matches any single character. '*' Matches any sequence of characters
 * (including the empty sequence).
 * 
 * The matching should cover the entire input string (not partial).
 * 
 * The function prototype should be: bool isMatch(const char *s, const char *p)
 * 
 * Some examples:
 * 
 * isMatch("aa","a") → false
 * 
 * isMatch("aa","aa") → true
 * 
 * isMatch("aaa","aa") → false
 * 
 * isMatch("aa", "*") → true
 * 
 * isMatch("aa", "a*") → true
 * 
 * isMatch("ab", "?*") → true
 * 
 * isMatch("aab", "c*a*b") → false
 * 
 */
public class Wildcard_Matching {

	public static void main(String[] args) {
		
		String s1 = "aaabbbabbababaababbbbbabbaaaaabbbbaaaabaaaabbabbbaaababaaaabababbaababbaabbbbaabaabaababbbabaabbabbaaaaaabbbabaabaaabbbaabbabaabbbbabbabaababbbbbbbaaaaababaabbaaaabbabbaaaabbbbaabbaabbaababaabaabbbaab";
		String p1 = "******a****aa**a**aa**aa**ba**aba*aa*b*a*bb**a*****b*******bb******a*aa*bbba*a*ba***baa*a*abb***ab*ab";
		assert isMatch(s1, p1) == true;

		assert isMatch("aa", "a") == false;
		assert isMatch("aa", "*b") == false;
		assert isMatch("aa", "aaa") == false;
		assert isMatch("aa", "aa") == true;
		assert isMatch("aa", "*") == true;
		assert isMatch("aa", "*?") == true;
		assert isMatch("cab", "*ab") == true;
		assert isMatch("cabgqwvzxiowadgasdg", "*") == true;

		assert isMatch_Improved("aa", "a") == false;
		assert isMatch_Improved("aa", "*b") == false;
		assert isMatch_Improved("aa", "aaa") == false;
		assert isMatch_Improved("aa", "aa") == true;
		assert isMatch_Improved("aa", "*") == true;
		assert isMatch_Improved("aa", "*?") == true;
		assert isMatch_Improved("cab", "*ab") == true;

		// big data set

		String s = "babbbbaabababaabbababaababaabbaabababbaaababbababaaaaaabbabaaaabababbabbababbbaaaababbbabbbbbbbbbbaabbb";
		String p = "b**bb**a**bba*b**a*bbb**aba***babbb*aa****aabb*bbb***a";
		long begin = System.currentTimeMillis();
		assert isMatch(s, p) == false;
		System.out.println("isMatch Running Time: "
				+ (System.currentTimeMillis() - begin));

		begin = System.currentTimeMillis();
		assert isMatch_Improved(s, p) == false;
		System.out.println("isMatch_Improved Running Time: "
				+ (System.currentTimeMillis() - begin));

	}

	/**
	 * Time: O(s.length + p.length), in the first while loop, either i or j or
	 * both will increment one
	 * 
	 * Greedy, keep matching until it can't, then back to the previous '*', use
	 * it once and then start from there again
	 * 
	 * 贪心的策略，能匹配就一直往后遍历，匹配不上了就看看前面有没有'*'来救救场，再从'*'后面接着试。
	 */
	public static boolean isMatch_Improved(String s, String p) {
		int i = 0, j = 0, previ = -1, prevj = -1;
		while (i < s.length()) {
			char pj = j < p.length() ? p.charAt(j) : '\0';
			if (pj == '?' || s.charAt(i) == pj) {
				i++;
				j++;
			} else if (pj == '*') {
				previ = i;
				prevj = ++j;
			} else if (previ >= 0) {
				i = ++previ;
				j = prevj;
			} else
				return false;
		}
		while (j < p.length() && p.charAt(j) == '*')
			j++;
		return j == p.length();
	}


	/**
	 * Backtracking (Recursion + Pruning), Time: Exponential
	 * 
	 * Second practice, avoid using String substring() method
	 */
	public static boolean isMatch(String s, String p) {
		return isMatch(s, p, 0, 0);
	}

	public static boolean isMatch(String s, String p, int i, int j) {
		final int slen = s.length(), plen = p.length();
		while (i < slen) {
			if (j == plen)
				return false;
			char si = s.charAt(i), pj = p.charAt(j);
			if (si == pj || pj == '?')
				j++;
			else if (pj == '*') {
				// pruning, skip all continuous '*', here is a huge improve
				while (j + 1 < plen && p.charAt(j + 1) == '*')
					j++;
				if (isMatch(s, p, i, j + 1))
					return true;
			} else
				return false;

			i++;
		}
		while (j < plen && p.charAt(j) == '*')
			j++;
		return j == plen;
	}

}
