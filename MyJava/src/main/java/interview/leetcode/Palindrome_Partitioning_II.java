package interview.leetcode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

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
		AutoTestUtils.runTestClassAndPrint(Palindrome_Partitioning_II.class);
	}

	/**
	 * DP, Time O(n^2), Space O(n^2)
	 * M[i] = number of min cuts of s[0, i]
	 * isPalin[j][i] = if s[j, i] is palindrome or not
	 * 
	 * Base case
	 * M[0] = 0, M[i] = M[i - 1] + 1
	 * isPalin[j][i] = true, if i == j
	 * 
	 * Induction rule
	 * M[i] = min(M[j] + 1) if s[j+1, i] is palindrome
	 * if s[0, i] is palindrome, M[i] = 0.
	 * 
	 * isPalin[j][i] = s[j] == s[i] && (i - j <= 2 || isPalin[j+1][i-1])
	 */
	public int minCut_DP(String S) {
		if (S.length() == 0)
			return 0;
		int N = S.length();
		boolean[][] isPalin = new boolean[N][N];
		int[] M = new int[N];
		for (int i = 1; i < N; i++) {
			M[i] = M[i - 1] + 1;
			for (int j = i - 1; j >= 0; j--) {
				if (S.charAt(j) == S.charAt(i) && (i - j <= 2 || isPalin[j + 1][i - 1])) {
					isPalin[j][i] = true;
					M[i] = j == 0 ? 0 : Math.min(M[i], M[j - 1] + 1);
				}
			}
		}
		return M[N - 1];
	}

	/**
	 * For every substring s, try to cut it at every position i where s[0, i] is
	 * a palindrome
	 * a b a b b
	 * ^
	 */
	public int minCut(String s) {
		if (isPalindrome(s, 0, s.length() - 1))
			return 0;
		int min = s.length() - 1;
		// cut at every position
		for (int i = 1; i < s.length(); i++) {
			if (isPalindrome(s, 0, i - 1)) {
				min = Math.min(min, minCut(s.substring(i)) + 1);
			}
		}
		return min;
	}

	private boolean isPalindrome(String s, int l, int r) {
		while (l < r) {
			if (s.charAt(l++) != s.charAt(r--))
				return false;
		}
		return true;
	}

	@Test
	public void test1() {
		String s = "ababb";
		int ans = 1;
		int res = minCut(s);
		assertTrue("Wrong: " + res, ans == res);
	}

	@Test
	public void test2() {
		String s = "aba";
		int ans = 0;
		int res = minCut(s);
		assertTrue("Wrong: " + res, ans == res);
	}

	@Test
	public void test3() {
		String s = "abcdefghdcba";
		int ans = 11;
		int res = minCut(s);
		assertTrue("Wrong: " + res, ans == res);
	}

	@Test
	public void test4() {
		String s = "ab";
		int ans = 1;
		int res = minCut(s);
		assertTrue("Wrong: " + res, ans == res);
	}

	@Test
	public void test5() {
		String s = "ababababababababababababcbabababababababababababa";
		int ans = minCut_DP(s);
		int res = minCut(s);
		assertTrue("Wrong: " + res, ans == res);
	}
}
