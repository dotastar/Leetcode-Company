package interview.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a string S and a string T, find the minimum window in S which will
 * contain all the characters in T in complexity O(n).
 * 
 * For example, S = "ADOBECODEBANC", T = "ABC", Minimum window is "BANC".
 * 
 * Note: If there is no such window in S that covers all characters in T, return
 * the emtpy string "".
 * 
 * If there are multiple such windows, you are guaranteed that there will always
 * be only one unique minimum window in S.
 * 
 * @author yazhoucao
 * 
 */
public class Minimum_Window_Substring {

	public static void main(String[] args) {
		Minimum_Window_Substring o = new Minimum_Window_Substring();
		String S = "of_characters_and_as";
		String T = "aas";
		System.out.println(o.minWindow2(S, T));
		System.out.println(o.minWindow_Improved(S, T));

		String S1 = "bba";
		String T1 = "ab";
		System.out.println(o.minWindow2(S1, T1));
		System.out.println(o.minWindow_Improved(S1, T1));
	}

	/**
	 * Second time practice, maintaining a window, expand and shorten the window
	 */
	public String minWindow2(String S, String T) {
		if (T.length() == 0 || T.length() > S.length())
			return "";
		Map<Character, Integer> dict = new HashMap<Character, Integer>();
		for (int i = 0; i < T.length(); i++) {
			char c = T.charAt(i);
			if (dict.containsKey(c))
				dict.put(c, dict.get(c) + 1);
			else
				dict.put(c, 1);
		}

		int matched = T.length();
		int minLen = Integer.MAX_VALUE;
		int start = -1;
		for (int l = 0, r = 0; r < S.length(); r++) {
			char cr = S.charAt(r);
			if (dict.containsKey(cr)) { // matched a char at right
				dict.put(cr, dict.get(cr) - 1);
				if (dict.get(cr) >= 0)
					matched--;
				assert isCountMatched(dict, matched);
				if (matched == 0) { // matched all T, try moving window left
					while (matched == 0) {
						char cl = S.charAt(l);
						if (dict.containsKey(cl)) {
							dict.put(cl, dict.get(cl) + 1);
							if(dict.get(cl)>0)
								matched++;
						}
						l++;
					} // just broke the balance, S.charAt(l-1) contains in T
					int len = r - (l - 1) + 1;
					start = len < minLen ? l - 1 : start;
					minLen = len < minLen ? len : minLen;
					assert isRightWindow(S, l - 1, r, T);
				}
			}
		}

		assert start + minLen <= S.length();
		return start == -1 ? "" : S.substring(start, start + minLen);
	}
	
	/**
	 * For assertion
	 */
	private boolean isRightWindow(String S, int l, int r, String T) {
		if (l < 0 || r >= S.length() || l > r)
			return false;
		Map<Character, Integer> dict = new HashMap<Character, Integer>();
		for (int i = 0; i < T.length(); i++) {
			char c = T.charAt(i);
			if (dict.containsKey(c))
				dict.put(c, dict.get(c) + 1);
			else
				dict.put(c, 1);
		}
		for (int i = l; i <= r; i++) {
			char c = S.charAt(i);
			if (dict.containsKey(c))
				dict.put(c, dict.get(c) - 1);
		}
		return isCountMatched(dict, 0);
	}

	/**
	 * For assertion
	 */
	private boolean isCountMatched(Map<Character, Integer> dict, int matched) {
		int total = 0;
		for (Integer count : dict.values()) {
			if(count>=0)
				total += count;
		}
		return total == matched;
	}

	/**
	 * Use two pointers l,r to maintain a window
	 * 
	 * keep advancing r to expand the window until the window satisfies the
	 * constraint, then keep advancing l to narrow the window until the
	 * almost not satisfies the constraint
	 * 
	 * Time O(n)
	 */
	public String minWindow_Improved(String S, String T) {
		int slen = S.length();
		int tlen = T.length();
		if (tlen > slen)
			return "";

		int[] s_exists = new int[128];
		int[] t_needs = new int[128];
		for (int i = 0; i < tlen; i++)
			t_needs[T.charAt(i)]++;

		int count = 0;
		int min = slen; // min length
		int minStart = -1;
		int minEnd = slen; // for substring
		for (int l = 0, r = 0; r < slen; r++) {
			char cr = S.charAt(r);
			if (t_needs[cr] == 0) // skip char that is not in T
				continue;

			s_exists[cr]++;
			if (s_exists[cr] <= t_needs[cr])
				count++;

			if (count == tlen) { // if window constraint is satisfied
				// advance l index as far right as possible to shorten it,
				// stop when advancing breaks window constraint.
				char cl = S.charAt(l);
				while (t_needs[cl] == 0 || s_exists[cl] > t_needs[cl]) {
					if (s_exists[cl] > t_needs[cl])
						s_exists[cl]--; // delete old
					l++; // advance
					cl = S.charAt(l);
				}

				if (r - l < min) {
					min = r - l;
					minStart = l;
					minEnd = r + 1;
				}
			}
		}
		return minStart == -1 ? "" : S.substring(minStart, minEnd);
	}

	/**
	 * Naive solution Recursive shorten the window from the full size to (l+1,r)
	 * and (l,r-1) until find the minimum width
	 * 
	 * this also won't work in LeetCode because it can't tell repeated chars in
	 * T
	 * 
	 * Time O(2^n), Time Limit Exceeded
	 * 
	 */
	int min;

	public String minWindow(String S, String T) {
		int[] exists = new int[128];
		for (int i = 0; i < S.length(); i++) {
			exists[S.charAt(i)]++;
		}
		min = Integer.MAX_VALUE;
		int res[] = new int[2];
		findMin(S, T, exists, 0, S.length() - 1, res);
		return S.substring(res[0], res[1]);
	}

	public void findMin(String S, String T, int[] exists, int l, int r,
			int[] res) {
		if (l >= r)
			return;
		for (int i = 0; i < T.length(); i++) {
			char c = T.charAt(i);
			if (exists[c] < 1)
				return;
		}
		if (r - l < min) {
			min = r - l;
			res[0] = l;
			res[1] = r + 1; // for substring, end+1
		}
		int idxl = S.charAt(l);
		int idxr = S.charAt(r);
		exists[idxl]--;
		findMin(S, T, exists, l + 1, r, res);
		exists[idxl]++;

		exists[idxr]--;
		findMin(S, T, exists, l, r - 1, res);
		exists[idxr]++;
	}

}
