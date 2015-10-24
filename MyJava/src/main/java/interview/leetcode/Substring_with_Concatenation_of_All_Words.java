package interview.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * You are given a string, S, and a list of words, L, that are all of the same
 * length. Find all starting indices of substring(s) in S that is a
 * concatenation of each word in L exactly once and without any intervening
 * characters.
 * 
 * For example, given: S: "barfoothefoobarman" L: ["foo", "bar"]
 * 
 * You should return the indices: [0,9]. (order does not matter).
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Substring_with_Concatenation_of_All_Words {

	public static void main(String[] args) {
		String S8 = "sheateateseatea";
		String[] L8 = { "sea", "tea", "ate" };
		System.out.println(findSubstring2(S8, L8).toString());//

		String S7 = "aaa";
		String[] L7 = { "a", "a" };
		System.out.println(findSubstring2(S7, L7).toString());// [0, 2, 4]

		String S6 = "abababab";
		String[] L6 = new String[] { "a", "b", "a" };
		System.out.println(findSubstring2(S6, L6).toString());// [0, 2, 4]

		String S4 = "aaa";
		String[] L4 = new String[] { "a", "a" };
		System.out.println(findSubstring2(S4, L4).toString());// [0, 1]

		String S5 = "aaa";
		String[] L5 = new String[] { "a", "b" };
		System.out.println(findSubstring2(S5, L5).toString());// []

		String S = "barfoothefoobarman";
		String[] L = new String[] { "foo", "bar" };
		System.out.println(findSubstring2(S, L).toString()); // [0,9]

		String S0 = "a";
		String[] L0 = new String[] { "a" };
		System.out.println(findSubstring5(S0, L0).toString());// [0]

		String S1 = "aaa";
		String[] L1 = { "aa", "aa" };
		System.out.println(findSubstring2(S1, L1).toString()); // []

		String S2 = "lingmindraboofooowingdingbarrwingmonkeypoundcake";
		String[] L2 = { "fooo", "barr", "wing", "ding", "wing" };
		System.out.println(findSubstring2(S2, L2).toString()); // [13], fooo

		String S3 = "abaababbaba";
		String[] L3 = { "ab", "ba", "ab", "ba" };
		System.out.println(findSubstring2(S3, L3).toString()); // [1,3]

	}

	/**
	 * Brute force: try start from every position 
	 * Time: O(n*l), n = S.length(), l = L[0].length();
	 * 
	 * More concise and clear than below, but slower
	 */
	public static List<Integer> findSubstring5(String S, String[] L) {
		List<Integer> result = new ArrayList<Integer>();
		Map<String, Integer> dict = new HashMap<>(), visited = new HashMap<>();
		final int DictLen = L.length, WordLen = L[0].length();
		for (String word : L) {
			Integer cnt = dict.get(word);
			if (cnt == null)
				cnt = 0;
			dict.put(word, cnt + 1);
		}
		for (int start = 0, i = 0; start <= S.length() - WordLen * DictLen; start++) {
			for (i = 0; i < DictLen; i++) {
				int k = start + i * WordLen;
				String word = S.substring(k, k + WordLen);
				if (!dict.containsKey(word))
					break;
				int cnt = visited.containsKey(word) ? visited.get(word) + 1 : 1;
				visited.put(word, cnt);
				if (cnt > dict.get(word))
					break;
			}
			if (i == DictLen)
				result.add(start);
			visited.clear();
		}
		return result;
	}

	/**
	 * Sliding window, move left/right one wordLength at a time.
	 * Time: l*2*(n/l) = O(n), n = S.length(), l = L[0].length().
	 * 
	 * Key:
	 * 1.when to slide/stop the window to right (move left side) :
	 * when the new word we meet makes that wordCnt > dict.get(new word),
	 * we have to move left to until the balance is restored, move left to
	 * removed the nearest new word we can find at left in order to keep the
	 * balance.
	 * 
	 * 2.state variable in the program:
	 * l: the window's left side, start point
	 * r: the begin index of last word in the window
	 * (i+WordLen is the windw's right side)
	 * wordVisited : visited number of word
	 * dict : count the number of times of all words appeared in L
	 * visited : count the number of times of words in the current window
	 */
	public static List<Integer> findSubstring2(String S, String[] L) {
		List<Integer> res = new ArrayList<>();
		if (L.length == 0)
			return res;

		Map<String, Integer> dict = new HashMap<>(), visited = new HashMap<>();
		for (String w : L) {
			if (dict.containsKey(w))
				dict.put(w, dict.get(w) + 1);
			else
				dict.put(w, 1);
		}

		final int WordLen = L[0].length();
		for (int offset = 0; offset < WordLen; offset++) {
			int wordVisited = 0;
			for (int r = offset, l = offset; r + WordLen <= S.length(); r += WordLen) {
				String word = S.substring(r, r + WordLen);
				if (dict.containsKey(word)) {
					int cnt = visited.containsKey(word) ? visited.get(word) + 1 : 1;
					visited.put(word, cnt);
					// sliding window when the word exceeded the times in dict
					if (cnt > dict.get(word)) {
						while (l < r) {
							String removed = S.substring(l, l + WordLen);
							visited.put(removed, visited.get(removed) - 1);
							l += WordLen;
							if (removed.equals(word))
								break; // stop when the new word is the the same
										// as old
							else
								wordVisited--;
						}
					} else
						wordVisited++;

					if (wordVisited == L.length)
						res.add(l);
				} else { // start over
					visited.clear();
					wordVisited = 0;
					l = r + WordLen;
				}
			}
			visited.clear();
		}
		return res;
	}

	/**
	 * Sliding window, move left/right one wordLength at a time.
	 * Time: l*2(n/l) = O(n), n = S.length(), l = L[0].length().
	 * 
	 * Key:
	 * 1.when to slide/stop the window to right (move left side) :
	 * when the new word we meet makes that wordCnt > dict.get(new word),
	 * we have to move left to until the balance is restored, move left to
	 * removed the nearest new word we can find at left in order to keep the
	 * balance.
	 * 
	 */
	public List<Integer> findSubstring(String S, String[] L) {
		List<Integer> res = new ArrayList<>();
		if (L.length == 0)
			return res;
		Map<String, Integer> dict = new HashMap<>(), visited = new HashMap<>();
		for (String word : L) {
			Integer cnt = dict.get(word);
			if (cnt == null)
				cnt = 0;
			dict.put(word, cnt + 1);
		}

		int total = L.length;
		final int WordLen = L[0].length();
		// try different window combination (start with different index)
		for (int offset = 0; offset < WordLen; offset++) {
			for (int l = offset, r = offset; r + WordLen <= S.length(); r += WordLen) {
				String rword = S.substring(r, r + WordLen);
				if (dict.containsKey(rword)) {
					int cnt = visited.containsKey(rword) ? visited.get(rword) + 1 : 1;
					visited.put(rword, cnt); // update current word count map
					// if exceeded, then l advance right until it is equal
					if (cnt > dict.get(rword)) {
						while (l < r) {
							String lword = S.substring(l, l + WordLen);
							l += WordLen;
							int lcnt = visited.get(lword) - 1;
							visited.put(lword, lcnt);
							if (lcnt < dict.get(lword))
								total++;
							if (lword.equals(rword))
								break;
						}
					} else
						total--; // not exceeded, r keep right one wordLen

					if (total == 0)
						res.add(l); // satisfied the requirement
				} else { // the word is not in L, advance both l and r
					l = r + WordLen;
					total = L.length;
					visited.clear();
				}
			}
			total = L.length;
			visited.clear();
		}
		return res;
	}

}
