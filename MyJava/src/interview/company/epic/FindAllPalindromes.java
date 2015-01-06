package interview.company.epic;

import java.util.ArrayList;
import java.util.List;

/**
 * Print all palindromes of size greater than equal to 3 of a given string
 * 
 * @author yazhoucao
 * 
 */
public class FindAllPalindromes {

	public static void main(String[] args) {
		FindAllPalindromes o = new FindAllPalindromes();
		System.out.println(o.findPalindromes("abcicba").toString());
	}

	/**
	 * Similar to longest palindrome substring
	 * 
	 * DP solution
	 * dp[l][r] : if substring(l,r) is a palindrome or not
	 * dp[l][r] = dp[l+1][r-1] && s.charAt(l)==s.charAt(r)
	 */
	public List<String> findPalindromes(String s) {
		List<String> res = new ArrayList<>();
		int len = s.length();
		boolean[][] dp = new boolean[len][len];
		for (int r = 0; r < len; r++) {	// Notice: traverse r first, then l
			for (int l = 0; l <= r; l++) {
				dp[l][r] = s.charAt(l) == s.charAt(r) && (r - l < 2 || dp[l + 1][r - 1]);
				if (r - l >= 2 && dp[l][r])
					res.add(s.substring(l, r + 1));
			}
		}
		return res;
	}
}
