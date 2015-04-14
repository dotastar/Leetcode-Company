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
	 * State: total + 2 Map<Char, Integer>
	 * ADOBECODEBANC
	 *          ^
	 *             ^
	 * total: 0
	 * Process:
	 * 1.keep moving r until total = 0
	 * 2.when total = 0, keep moving l until it is minimum
	 * (when total > 0, the balance is broken, the previous l is one possible
	 * minimum left)
	 * 3.compare current minimum length with global minimum, and update it if
	 * current is less.
	 * 4.repeat step 1 until r reach the end
	 */
	public String minWindow2(String S, String T) {
		int[] dict = new int[128], visited = new int[128];
		for (int i = 0; i < T.length(); i++)
			dict[T.charAt(i)]++;

		int min = 0, left = 0;
		int total = T.length();
		for (int l = 0, r = 0; r < S.length(); r++) {
			char sr = S.charAt(r);
			if (dict[sr] > 0) {
				visited[sr]++; // update the window
				if (visited[sr] <= dict[sr])
					total--;

				if (total == 0) { // shorten the window
					while (total == 0) {
						char sl = S.charAt(l++);
						if (dict[sl] > 0) {
							visited[sl]--;
							if (visited[sl] < dict[sl])
								total++;
						}
					} // break the ties out of the loop
					int length = r - (l - 1) + 1; // (l-1, r) is the min window
					if (min == 0 || length < min) {
						min = length;
						left = l - 1;
					}
				}
			}
		}
		return S.substring(left, left + min);
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
		int[] dict = new int[128], visited = new int[128];
		for (int i = 0; i < T.length(); i++)
			dict[T.charAt(i)]++;

		int end = 0, start = 0, visitCnt = 0;
		for (int r = 0, l = 0; r < S.length(); r++) {
			char si = S.charAt(r);
			if (dict[si] > 0) {
				visited[si]++;
				if (visited[si] <= dict[si])
					visitCnt++;
			}
			if (visitCnt < T.length())
				continue;
			// now l to r has covered all T, slide the window to right
			while (visitCnt == T.length()) {
				char cleft = S.charAt(l);
				if (dict[cleft] > 0) {
					if (visited[cleft] <= dict[cleft])
						visitCnt--;
					visited[cleft]--;
				}
				l++;
			} // shorten the window until the balanced is broke
			if (end == 0 || (r - (l - 1) + 1 < (end - start))) {
				end = r + 1;
				start = l - 1;
			}
		}
		return S.substring(start, end);
	}

	/**
	 * For assertion
	 */
	public boolean isRightWindow(String S, int l, int r, String T) {
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
			if (count >= 0)
				total += count;
		}
		return total == matched;
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
