package interview.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Given a string s and a dictionary of words dict, add spaces in s to construct
 * a sentence where each word is a valid dictionary word.
 * 
 * Return all such possible sentences.
 * 
 * For example,
 * 
 * given s = "catsanddog", dict = ["cat", "cats", "and", "sand", "dog"].
 * 
 * A solution is ["cats and dog", "cat sand dog"].
 * 
 * @author yazhoucao
 * 
 */
public class Word_Break_II {

	public static void main(String[] args) {
		Word_Break_II obj = new Word_Break_II();

		String s1 = "abcd";
		Set<String> dict1 = new HashSet<String>();
		dict1.add("a");
		dict1.add("abc");
		dict1.add("ab");
		dict1.add("cd");
		System.out.println(obj.wordBreak2(s1, dict1));

		String s2 = "catsanddog";
		Set<String> dict2 = new HashSet<String>();
		String[] data2 = { "cat", "cats", "and", "sand", "dog" };
		for (String d : data2)
			dict2.add(d);
		System.out.println(obj.wordBreak2(s2, dict2));

		String s3 = "a";
		Set<String> dict3 = new HashSet<String>();
		String[] data1 = { "a" };
		for (String d : data1)
			dict1.add(d);
		System.out.println(obj.wordBreak2(s3, dict3));

		String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
		Set<String> dict = new HashSet<String>();
		String[] data = { "a", "aa", "aaa", "aaaa", "aaaaa", "aaaaaa", "aaaaaaa",
				"aaaaaaaa", "aaaaaaaaa", "aaaaaaaaaa" };
		for (String d : data)
			dict.add(d);
		System.out.println(obj.wordBreak(s, dict));
	}

	/**
	 * Second practice
	 * 
	 * Thought: same as word break I, first use DP to get the break point(where
	 * to break it), then recursively get the path.
	 * 
	 */
	public List<String> wordBreak2(String s, Set<String> dict) {
		int len = s.length();
		@SuppressWarnings("unchecked")
		List<Integer>[] dp = new List[len + 1];
		dp[0] = new ArrayList<Integer>();

		for (int i = 0; i < len; i++) {
			if (dp[i] == null)
				continue;
			for (String word : dict) {
				int j = i + word.length();
				if (j <= len && word.equals(s.substring(i, j))) {
					if (dp[j] == null)
						dp[j] = new ArrayList<Integer>();
					dp[j].add(i);
				}
			}
		}

		List<String> res = new ArrayList<String>();
		buildPath(s, dp, new Stack<String>(), len, res);
		return res;
	}

	private void buildPath(String s, List<Integer>[] dp, Stack<String> path, int idx,
			List<String> res) {
		if (idx == 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = path.size() - 1; i >= 0; i--)
				sb.append(path.get(i) + " ");
			sb.deleteCharAt(sb.length() - 1);
			res.add(sb.toString());
			return;
		}

		List<Integer> branches = dp[idx];
		for (int i : branches) {
			path.push(s.substring(i, idx));
			buildPath(s, dp, path, i, res);
			path.pop();
		}
	}

	/**
	 * Recursion + DP
	 * 
	 */
	public List<String> wordBreak(String s, Set<String> dict) {
		List<String> res = new ArrayList<>();
		boolean[] breakable = new boolean[s.length() + 1]; // for dp
		Arrays.fill(breakable, true);
		breakWord(res, s, dict, new StringBuilder(), 0, breakable);
		return res;
	}

	private void breakWord(List<String> res, String s, Set<String> dict,
			StringBuilder sb, int start, boolean[] breakable) {
		if (start == s.length()) {
			res.add(sb.substring(1));
			return;
		}

		for (int i = start; i < s.length(); i++) {
			if (!breakable[i + 1]) // eliminate unnecessary search
				continue;
			String word = s.substring(start, i + 1);
			if (dict.contains(word)) {
				int length = sb.length();
				sb.append(' ');
				sb.append(word);
				int resultSize = res.size();
				breakWord(res, s, dict, sb, i + 1, breakable);
				// if no solution, set the possible to false
				breakable[i + 1] = res.size() != resultSize;
				sb.setLength(length);
			}
		}
	}

	/**
	 * Simply recursion
	 */
	public List<String> wordBreak_Recursion(String s, Set<String> wordDict) {
		List<String> res = new ArrayList<>();
		breakWord_Recur(s, wordDict, 0, new StringBuilder(), res);
		return res;
	}

	private void breakWord_Recur(String s, Set<String> dict, int idx, StringBuilder solu,
			List<String> res) {
		if (idx == s.length()) {
			res.add(solu.substring(1));
			return;
		}
		StringBuilder word = new StringBuilder();
		for (int i = idx; i < s.length(); i++) {
			word.append(s.charAt(i));
			if (dict.contains(word.toString())) {
				int oldLen = solu.length();
				solu.append(' ');
				solu.append(word.toString());
				breakWord_Recur(s, dict, i + 1, solu, res);
				solu.setLength(oldLen);
			}
		}
	}
}
