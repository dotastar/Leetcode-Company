package interview.leetcode;

public class Longest_Palindromic_Substring {

	public static void main(String[] args) {
		Longest_Palindromic_Substring o = new Longest_Palindromic_Substring();

		String s1 = o.longestPalindrome_CenterExpand("aba");
		assert s1.equals("aba") : s1;
		String s2 = o.longestPalindrome_CenterExpand("bb");
		assert s2.equals("bb") : s2;
	}

	/************************************ Solution 1 ************************************/
	/**
	 * Brute force, try every substring (end at r start from l).
	 * 
	 * Time: O(n^3)
	 */
	public String longestPalindrome_BruteForce(String s) {
		int len = s.length();
		int longest = 1;
		int begin = 0;
		int end = 0;
		for (int r = 1; r < len; r++) {
			for (int l = 0; l < r; l++) {
				if (isPalindrome(s, l, r)) {
					int length = r - l + 1;
					if (length > longest) {
						longest = length;
						begin = l;
						end = r;
					}
				}
			}
		}

		return s.substring(begin, end);
	}

	/**
	 * Time: O(n), n = r-l
	 */
	private boolean isPalindrome(String s, int l, int r) {
		while (l < r) {
			if (s.charAt(l) != s.charAt(r))
				return false;
			l++;
			r--;
		}
		return true;
	}

	/************************************ Solution 2 ************************************/
	/**
	 * Dynamic Programming, Time: O(n^2), Space: O(n^2)
	 * M[j][i] = if s[j, i] is palindrome or not
	 * 
	 * Base case:
	 * M[j][i] = 1, when i == j
	 * 
	 * Induction rule:
	 * M[j][i] = s[i] == s[j] && (i - j <= 2 || M[j+1][i-1])
	 * 
	 */
	public String longestPalindrome_DP(String s) {
		boolean[][] M = new boolean[s.length()][s.length()];
		int max = 1, left = 0;
		M[0][0] = true;
		for (int i = 1; i < s.length(); i++) {
			for (int j = 0; j < i; j++) {
				if (s.charAt(i) == s.charAt(j) && (i - j <= 2 || M[j + 1][i - 1])) {
					M[j][i] = true;
					int length = i - j + 1;
					if (length > max) {
						max = length;
						left = j;
					}
				}
			}
		}
		return s.substring(left, left + max);
	}

	/************************************ Solution 3 ************************************/
	/**
	 * Center expansion, Time: O(n^2), Space: O(1)
	 * Every character is a center, every position between two interval is also
	 * a center, try every possible center and calculate its length of the
	 * longest palindrome.
	 */
	public String longestPalindrome_CenterExpand(String s) {
		int len = s.length() * 2 - 1; // num of centers(include interval between
										// two points)
		int longest = 1;
		int[] coordinate = new int[2];
		int start = 0;
		int end = 0;
		for (int i = 1; i < len; i++) {
			int length = longestPalindromeLength(s, i, coordinate);
			if (length > longest) {
				start = coordinate[0];
				end = coordinate[1];
				longest = length;
			}
		}

		return s.substring(start, end + 1);
	}

	/**
	 * For a given center, return the length of the longest palindrome that
	 * based on this center.
	 */
	public int longestPalindromeLength(String s, int center, int[] coordinate) {
		if (coordinate.length < 2)
			return 0;
		int len = s.length();
		int l = center / 2; // center%2==1 means that this is not an interval
		int r = center % 2 == 1 ? center / 2 + 1 : center / 2; // Important

		while (l >= 0 && r < len && s.charAt(l) == s.charAt(r)) {
			l--;
			r++;
		}
		coordinate[0] = ++l; // back to the correct palindrome
		coordinate[1] = --r;
		return r - l + 1;
	}

	/************************************ Solution 4 ************************************/
	/**
	 * Manacher’s Algorithm, Time: O(n), Space: O(n)
	 */
	public String longestPalindrome(String str) {
		char[] s = preProcess(str);
		int maxLen = 0;
		int bestId = 0;
		int len = s.length;
		int[] centerAt = new int[len]; // p
		int id = 0; // the best i of maxLength
		int right = 0; // right boundary of maxLength[id], mx

		for (int i = 1; i < len - 1; i++) {
			// the symmetric point of i center in id
			int j = id * 2 - i;

			if (right > i) // make use of symmetric property
				centerAt[i] = Math.min(centerAt[j], right - i);
			else
				centerAt[i] = 1;

			int l = i - centerAt[i];
			int r = i + centerAt[i];
			if (l >= 0 && r <= len - 1) {
				while (s[l] == s[r]) {
					centerAt[i]++;
					l = i - centerAt[i];
					r = i + centerAt[i];
					if (l < 0 || r > len - 1)
						break;
				}
			}

			int newRight = i + centerAt[i];
			if (newRight > right) {
				id = i;
				right = newRight;
			}

			if (centerAt[i] > maxLen) {
				maxLen = centerAt[i];
				bestId = i;
			}
		}

		// output the max palindrome in original string
		StringBuffer sb = new StringBuffer();
		int begin = bestId - maxLen + 1;
		for (int i = begin; i < bestId + maxLen; i++)
			if (s[i] != '#')
				sb.append(s[i]);
		return sb.toString();
	}

	private static char[] preProcess(String s) {
		// n*2+1 = n:string + n+1:#
		char[] chs = new char[s.length() * 2 + 1];
		chs[0] = '#';
		for (int i = 0; i < s.length(); i++) {
			int idx = (i + 1) * 2;
			chs[idx - 1] = s.charAt(i);
			chs[idx] = '#';
		}

		// System.out.println(Arrays.toString(s));
		return chs;
	}
}
