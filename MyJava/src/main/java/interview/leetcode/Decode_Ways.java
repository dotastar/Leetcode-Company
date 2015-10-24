package interview.leetcode;

/**
 * A message containing letters from A-Z is being encoded to numbers using the
 * following mapping:
 * 
 * 'A' -> 1 'B' -> 2 ... 'Z' -> 26
 * 
 * Given an encoded message containing digits, determine the total number of
 * ways to decode it.
 * 
 * For example, Given encoded message "12", it could be decoded as "AB" (1 2) or
 * "L" (12).
 * 
 * The number of ways decoding "12" is 2.
 * 
 * @author yazhoucao
 * 
 */
public class Decode_Ways {

	public static void main(String[] args) {
		Decode_Ways o = new Decode_Ways();
		assert o.numDecodings("") == 0; // 0
		assert o.numDecodings("8") == 1; // 1
		assert o.numDecodings("12") == 2; // 2
		assert o.numDecodings("112233") == 8; // 8

		assert o.numDecodings("110") == 1;// [1,2,1] -> 1
		assert o.numDecodings("101") == 1; // [1,1,1] -> 1
		assert o.numDecodings("230") == 0;// [1,2,0] -> 0
		assert o.numDecodings("1010") == 1; // [1,1,1,1,1] -> 1
		assert o.numDecodings("10000") == 0; // [1,1,1,0,0,0,0,0,0] -> 0

		String big = "2316696554441358783946227776659488274288912326529233762652836862767154854761414596651257174516398495";
		System.out.println(o.numDecodings(big));
	}

	/**
	 * Dynamic Programming
	 * M[i] = number of decode ways of s[0, i-1]
	 * Base case:
	 * M[0] = 1, M[1] = 1
	 * 
	 * Induction rule:
	 * 1.If s[i-1] == 0,
	 **** if '0' < s[i-2] <= '2', M[i] = M[i-2],
	 **** else can't be decoded
	 * 
	 * 2.(s[i-1] != 0) If s[i-2] * 10 + s[i-1] > 26 || s[i-2] == '0',
	 * M[i] = M[i-1]
	 * 
	 * 3.Else, M[i] = M[i-1] + M[i-2]
	 * 
	 * Corner cases:
	 * 1.0 as head
	 * 2.0 as tail
	 * 3.value > 26
	 * 
	 */
	public int numDecodings_DP(String s) {
		if (s.length() == 0 || s.charAt(0) == '0')
			return 0;
		int[] M = new int[s.length() + 1];
		M[0] = 1;
		M[1] = 1;
		for (int i = 2; i <= s.length(); i++) {
			int cur = s.charAt(i - 1) - '0', prev = s.charAt(i - 2) - '0';
			if (cur == 0) {
				if (prev > 0 && prev <= 2)
					M[i] = M[i - 2];
				else
					return 0; // can't be decoded
			} else if (prev * 10 + cur > 26 || prev == 0)
				M[i] = M[i - 1];
			else
				M[i] = M[i - 1] + M[i - 2];
		}
		return M[s.length()];
	}

	/**
	 * DP, improved in space
	 * 
	 * Time: O(n), Space: O(1)
	 * 
	 */
	public int numDecodings_Improved(String s) {
		int len = s.length();
		if (len == 0 || s.charAt(0) == '0')
			return 0;
		int n1 = 1;
		int n2 = 1;
		int prev = s.charAt(0) - '0';
		for (int i = 2; i <= len; i++) {
			int tmp = 0;
			int curr = s.charAt(i - 1) - '0';
			if (curr == 0 && (prev == 0 || prev > 2)) // 00, 30, 40...90
				return 0;
			else if (curr == 0) // 10, 20
				tmp = n1;
			else if (prev == 1 || (prev == 2 && curr < 7)) // 11~19,21~26
				tmp = n1 + n2;
			else
				// 01~09, 27~99
				tmp = n2;
			n1 = n2;
			n2 = tmp;
			prev = curr;
		}
		return n2;
	}

	/**
	 * Recursion, Time: O(2^n)
	 * 
	 * Corner cases
	 * a.26, 27, ..., 30, 40, 50, ..., 90
	 * b.01, 02, 03, ... 09
	 * c.
	 */
	public int numDecodings(String s) {
		if (s.length() == 0)
			return 0;
		return numDecodings(s, 0);
	}

	private int numDecodings(String s, int i) {
		if (i >= s.length())
			return i == s.length() ? 1 : 0;
		if (s.charAt(i) == '0')
			return 0;
		int num = 0;
		num += numDecodings(s, i + 1);
		if (i + 1 < s.length() && Integer.valueOf(s.substring(i, i + 2)) <= 26)
			num += numDecodings(s, i + 2);
		return num;
	}
}
