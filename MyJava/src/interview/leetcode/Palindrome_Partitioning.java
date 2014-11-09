package interview.leetcode;

import java.util.ArrayList;
import java.util.List;

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
	 * Recursion, DFS-like fasion
	 * Time out
	 * 
	 * @param s
	 * @return
	 */
	public List<List<String>> partition(String s) {
		List<List<String>> res = new ArrayList<List<String>>();
		if (s == null || s.length() == 0)
			return res;
		addPartition(s, 0, new ArrayList<String>(), res);
		return res;
	}

	public void addPartition(String s, int start, List<String> partition,
			List<List<String>> res) {
		if (start == s.length()) {
			res.add(new ArrayList<String>(partition));
			return;
		}

		for (int i = start; i < s.length(); i++) {
			String substr = s.substring(start, i + 1);
			if (isPalindrome(substr)) {
				partition.add(substr);
				addPartition(s, i + 1, partition, res);
				partition.remove(partition.size() - 1);
			}
		}
	}

	public boolean isPalindrome(String s) {
		int l = 0;
		int r = s.length() - 1;
		while (l < r) {
			if (s.charAt(l) != s.charAt(r))
				return false;
			l++;
			r--;
		}
		return true;
	}
}
