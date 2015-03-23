package interview.leetcode;


/**
 * Given an input string, reverse the string word by word.
 * For example,
 * Given s = "the sky is blue",
 * return "blue is sky the".
 * 
 * @author yazhoucao
 * 
 */
public class Reverse_Words_in_a_String {

	public static void main(String[] args) {
		String s1 = "the sky is blue 1";
		String s2 = "  the sky     is  blue   2  ";
		String s3 = "a  b";
		String s4 = " ";
		System.out.println(reverseWords1(s1));
		System.out.println(reverseWords1(s2));
		System.out.println(reverseWords(s3));
		System.out.println(reverseWords1(s4));
	}

	/**
	 * First pass to split the string by spaces into an array of words, then
	 * second pass to extract the words in reversed order.
	 * 
	 * Did not use built-in String's split()
	 */
	public static String reverseWords(String s) {
		s = s.trim();
		StringBuilder sb = new StringBuilder();
		for (int l = 0, r = 0; r < s.length();) {
			while (r < s.length() && s.charAt(r) != ' ')
				r++;
			// append backward
			for (int i = r - 1; i >= l; i--)
				sb.append(s.charAt(i));
			while (r < s.length() && s.charAt(r) == ' ')
				r++;
			l = r;
			if (r < s.length())
				sb.append(' ');
		}
		// reverse the whole String
		return sb.reverse().toString();
	}

	/**
	 * With the help of String.split(), very neat solution
	 * 
	 * Time: O(n), Space: O(n).
	 */
	public static String reverseWords1(String s) {
		if (s == null)
			return s;
		String[] words = s.trim().split(" ");
		StringBuffer sb = new StringBuffer();
		for (int i = words.length - 1; i >= 0; i--) {
			if (words[i].equals(""))
				continue;
			sb.append(words[i].trim() + " ");
		}
		return sb.toString().trim();
	}

}
