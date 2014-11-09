package interview.leetcode;

/**
 * Write a function to find the longest common prefix string amongst an array of
 * strings.
 * 
 * @author yazhoucao
 * 
 */
public class Longest_Common_Prefix {

	public static void main(String[] args) {
		String[] strs0 = new String[] { "abcd", "abvv" };
		String res = longestCommonPrefix(strs0);
		System.out.println(res);
	}

	/**
	 * Time: O(l*n), Space: O(1).  l:length of strs, n: length of LCP.
	 */
	public static String longestCommonPrefix(String[] strs) {
		if (strs.length == 0)
			return "";
		int end = 0;
		String first = strs[0];
		for (int i = 0; i < first.length(); i++) {
			char c = first.charAt(i);
			for (String s : strs) {
				if (i >= s.length() || c != s.charAt(i))
					return first.substring(0, end);
			}
			end++;
		}
		return first.substring(0, end);
	}
}
