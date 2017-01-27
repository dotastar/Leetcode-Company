package interview.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Given a string s and a dictionary of words dict, determine if s can be
 * segmented into a space-separated sequence of one or more dictionary words.
 * 
 * For example, given s = "leetcode", dict = ["leet", "code"].
 * 
 * Return true because "leetcode" can be segmented as "leet code".
 * 
 * @author yazhoucao
 * 
 */
public class Word_Break {

	public static void main(String[] args) {
		Word_Break obj = new Word_Break();

		String s1 = "abcd";
		Set<String> dict1 = new HashSet<String>();
		dict1.add("a");
		dict1.add("abc");
		dict1.add("ab");
		dict1.add("cd");
		System.out.println(obj.wordBreak_ImprovedDP(s1, dict1));

		String s2 = "catsanddog";
		Set<String> dict2 = new HashSet<String>();
		String[] data2 = { "cat", "cats", "and", "sand", "dog" };
		for (String d : data2)
			dict2.add(d);
		System.out.println(obj.wordBreak_ImprovedDP(s2, dict2));

		String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
		Set<String> dict = new HashSet<String>();
		String[] data = { "a", "aa", "aaa", "aaaa", "aaaaa", "aaaaaa",
				"aaaaaaa", "aaaaaaaa", "aaaaaaaaa", "aaaaaaaaaa" };
		for (String d : data)
			dict.add(d);
		System.out.println(obj.wordBreak_ImprovedDP(s, dict));
	}

	/**
	 * DFS improved
	 * Time: O(n^k), n = wordDict.size(),
	 * k = s.length() / average word length of wordDict that matches in s
	 * 
	 * try break at every position i, i = [1, i-1]
	 * if (wordDict.contains(s[0, i-1]) && wordBreak(s[i, n], wordDict))
	 * return true
	 */
	public boolean wordBreak(String s, Set<String> wordDict) {
		if (s.length() == 0)
			return true;
		for (String word : wordDict) {
			if (word.length() <= s.length()) {
				String substr = s.substring(0, word.length());
				if (wordDict.contains(substr)) {
					if (wordBreak(s.substring(word.length()), wordDict))
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * Naive DFS, Brute force generate all the substring of s
	 * try break at every position i, i = [1, i-1]
	 * if (wordDict.contains(s[0, i-1]) && wordBreak(s[i, n], wordDict))
	 * return true
	 */
	public boolean wordBreak2(String s, Set<String> wordDict) {
		if (s.length() == 0)
			return true;
		for (int i = 1; i < s.length(); i++) {
			String substr = s.substring(0, i);
			if (wordDict.contains(substr)
					&& wordBreak2(s.substring(i), wordDict))
				return true;
		}
		return false;
	}

	/**
	 * DP, Time: O(n^2), Space: O(n)
	 * M[i] = s[0, i - 1] can be segmented or not
	 * M[i] = M[j] && s[j, i] is in wordDict, j = [0, i - 1]
	 */
	public boolean wordBreak_DP(String s, Set<String> wordDict) {
		boolean[] M = new boolean[s.length() + 1];
		M[0] = true;
		for (int i = 1; i <= s.length(); i++) {
			for (int j = 0; j < i; j++) {
				if (M[j] && wordDict.contains(s.substring(j, i))) {
					M[i] = true;
					break;
				}
			}
		}
		return M[s.length()];
	}

	/**
	 * Dynamic Programming Improved
	 * 
	 * Time: O(strlen * dict size), it is faster if dict size < strlen
	 * 
	 * Define an array segmentable[] such that segmentable[i] == true => 0-(i-1)
	 * can be segmented using dictionary
	 * 
	 * segmentable[end] = segmentable[i] && s.substring(i, end) is in dict
	 * 
	 * Initial state segmentable[0] == true
	 * 
	 */
	public boolean wordBreak_ImprovedDP(String s, Set<String> dict) {
		if (dict.contains(s))
			return true;
		boolean[] segmentable = new boolean[s.length() + 1];
		segmentable[0] = true;
		for (int i = 0; i < s.length(); i++) {
			if (!segmentable[i]) // if it's false, it's forever false.
				continue;
			for (String word : dict) { // try all words with substring_0-(i-1)
				int end = i + word.length();
				if (end > s.length() || segmentable[end])
					continue;
				segmentable[end] = s.substring(i, end).equals(word);
			}
		}
		return segmentable[s.length()];
	}

}
