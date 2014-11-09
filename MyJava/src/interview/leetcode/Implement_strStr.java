package interview.leetcode;

/**
 * Implement strStr().
 * 
 * Returns a pointer to the first occurrence of needle in haystack, or null if
 * needle is not part of haystack.
 * 
 * @author yazhoucao
 * 
 */
public class Implement_strStr {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(strStr("a", "a"));
	}

	/**
	 * Naive solution, brute force
	 * 
	 * O(nk) Time
	 * 
	 * n: length of haystack
	 * 
	 * k: length of needle
	 * 
	 * @param haystack
	 * @param needle
	 * @return
	 */
	public static String strStr(String haystack, String needle) {
		int n = haystack.length();
		int k = needle.length();
		if (n == 0 && k == 0)
			return "";
		for (int i = 0; i < n - k + 1; i++) {
			int match = 0;
			for (int j = 0; j < k; j++) {
				if (needle.charAt(j) != haystack.charAt(i + j))
					break;
				else
					match++;
			}
			if (match == k)
				return haystack.substring(i);
		}

		return null;
	}
	
	/**
	 * KMP, O(n) linear time, O(k) space
	 * 
	 */
	public int kmp(String text, String p) {
		int tlen = text.length();
		int plen = p.length();
		if (plen > tlen)
			return -1;

		int[] next = generateNext(p);
		int i = 0, j = 0;
		while (i < tlen && j < plen) {
			if (j < 0 || text.charAt(i) == p.charAt(j)) {
				i++;
				j++;
			} else {
				j = next[j];
			}
		}

		return j == plen ? i - j : -1;
	}

	/**
	 * Constructing an DFA, Deterministic Finite state Automation
	 * 
	 * next[i] means jump from i to value of next[i], which is jump from
	 * state(i) to state(next[i])
	 * 
	 * Notice: the statement "next[i] = next[k];	//double jump" is an
	 * optimization that optimize the case like "ABABC".
	 * 
	 * When the pattern is "ABABC" and j = 2, we know that k = 0 and
	 * p.charAt(++i) == p.charAt(++k), we add the condition to make it a double
	 * jump, because one single jump which will jump to k = 1, which p[k] is B
	 * is equal to the current p[i] (p[3], B), and it will have to jump again
	 * anyway, so we just ignore this meaningless step, and make a double jump.
	 * 
	 * @param p
	 */
	private int[] generateNext(String p) {
		int plen = p.length();
		int[] next = new int[plen];
		if (plen == 0)
			return next;

		next[0] = -1;
		int i = 0, k = -1;
		while (i < plen - 1) {
			if (k == -1 || p.charAt(i) == p.charAt(k)) {
				if (p.charAt(++i) == p.charAt(++k))
					next[i] = next[k]; // double jump
				else
					next[i] = k; // single jump
			} else {
				// make k jump to next[k]
				k = next[k];
			}
		}
		return next;
	}
	
}
