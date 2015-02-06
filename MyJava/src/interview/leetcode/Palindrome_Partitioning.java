package interview.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Given a string s, partition s such that every substring of the partition is a
 * palindrome.
 * 
 * Return all possible palindrome partitioning of s.
 * 
 * For example, given s = "aab", Return [ ["aa","b"], ["a","a","b"] ]
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Palindrome_Partitioning {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Palindrome_Partitioning o = new Palindrome_Partitioning();
		System.out.println(o.partition("a").toString());
	}

	/**
	 * Backtracking, recursion
	 */
	public List<List<String>> partition(String s) {
		List<List<String>> res = new ArrayList<>();
		partitionString(res, s, new Stack<String>());
		return res;
	}

	private void partitionString(List<List<String>> res, String s,
			Stack<String> parts) {
		if (s.length() == 0) {
			res.add(new ArrayList<String>(parts));
			return;
		}

		for (int i = 1; i <= s.length(); i++) {
			String part = s.substring(0, i);
			if (isPalindrome(part)) {
				parts.push(part);
				partitionString(res, s.substring(i), parts);
				parts.pop();
			}
		}
	}

	private boolean isPalindrome(String s) {
		int l = 0, r = s.length() - 1;
		while (l < r) {
			if (s.charAt(l++) != s.charAt(r--))
				return false;
		}
		return true;
	}
}
