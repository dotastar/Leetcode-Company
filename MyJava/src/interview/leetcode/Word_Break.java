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
		System.out.println(obj.wordBreak_Improved(s1, dict1));
		
		
		String s2 = "catsanddog";
		Set<String> dict2 = new HashSet<String>();
		String[] data2 = {"cat", "cats", "and", "sand", "dog"};
		for(String d : data2)
			dict2.add(d);
		System.out.println(obj.wordBreak_Improved(s2, dict2));
		

		String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
		Set<String> dict = new HashSet<String>();
		String[] data = { "a", "aa", "aaa", "aaaa", "aaaaa", "aaaaaa",
				"aaaaaaa", "aaaaaaaa", "aaaaaaaaa", "aaaaaaaaaa" };
		for (String d : data)
			dict.add(d);
		System.out.println(obj.wordBreak_Improved(s, dict));
	}

	/**
	 * Brute force generate all the substring of s, and check it in the dick
	 * 
	 */
	public boolean wordBreak(String s, Set<String> dict) {
		if (dict.contains(s))
			return true;
		return wordBreakRecur3(s, dict);
	}

	/**
	 * Recursion 1, way too slow
	 * 
	 * Time O(2^n), n is the length of s
	 */
	public boolean wordBreakRecur1(String s, Set<String> dict, int begin) {
		if (begin == s.length())
			return true;
		for (int i = begin + 1; i < s.length(); i++) {
			if (dict.contains(s.substring(begin, i))) {
				if (wordBreakRecur1(s, dict, i))
					return true;
			}
		}
		return false;
	}

	/**
	 * Recursion 2, better, still very slow
	 * 
	 * Time O(n!), n is the length of dict
	 */
	public boolean wordBreakRecur2(String s, Set<String> dict, int begin) {
		if (begin == s.length())
			return true;
		for (String word : dict) {
			int len = word.length();
			int end = begin + len;
			if (end > s.length())
				continue;
			if (s.substring(begin, end).equals(word))
				if (wordBreakRecur2(s, dict, end))
					return true;
		}
		return false;
	}

	/**
	 * Same as above, second practice
	 * 
	 */
	public boolean wordBreakRecur3(String s, Set<String> dict) {
		if (s.length() == 0)
			return true;
		for (String word : dict) {
			if (word.length() > s.length())
				continue;
			String sub = s.substring(0, word.length());
			if (sub.equals(word)) {
				if (wordBreakRecur3(s.substring(word.length()), dict))
					return true;
			}
		}
		return false;
	}

	/**
	 * DP, dp[i] = dp[j] && dict.contains(s.substring(j, i)), j is 0...i.
	 * 
	 * Time: O(n^2), n is the s.length()
	 */
	public boolean wordBreak_DP_LessImproved(String s, Set<String> dict){
		if(dict.contains(s))
			return true;
		int len = s.length();
		boolean[] dp = new boolean[len+1];
		dp[0] = true;
		for(int i=1; i<=len; i++){
			for(int j=0; j<i; j++){
				if(dp[j] && dict.contains(s.substring(j, i))){
					dp[i] = true;
					break;
				}
			}
		}
		return dp[len];
	}
	
	
	/**
	 * Dynamic Programming solution
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
	public boolean wordBreak_Improved(String s, Set<String> dict) {
		if (dict.contains(s))
			return true;
		boolean[] segmentable = new boolean[s.length() + 1];
		segmentable[0] = true;
		for (int i = 0; i < s.length(); i++) {
			if (!segmentable[i])	//if it's false, it's forever false. 
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
