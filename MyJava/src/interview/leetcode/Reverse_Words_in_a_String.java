package interview.leetcode;

import java.util.Stack;

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
		String s3 = "";
		String s4 = " ";
		System.out.println(solution1_split(s1));
		System.out.println(solution1_split(s2));
		System.out.println(solution1_split(s3));
		System.out.println(solution1_split(s4));
	}

	/**
	 * With the help of String.split(), very neat solution
	 * 
	 * Time: O(n), Space: O(n).
	 */
	public static String solution1_split(String s) {
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

	/**
	 * Naive solution, without any libraries' help, use stack to reverse the
	 * order of each word.
	 * 
	 * Time: O(n), Space: O(k), k is the length of the longest word.
	 */
	public String reverseWords(String s) {
		s = s.trim();
		StringBuilder sb = new StringBuilder();
		Stack<Character> stk = new Stack<Character>();
		for (int i = s.length() - 1; i >= 0; i--) {
			char c = s.charAt(i);
			if (c == ' ') {
				if (s.charAt(i - 1) == ' ')
					continue; // ignore multiple continuous spaces
				while (!stk.isEmpty())
					sb.append(stk.pop()); // reversely append
				sb.append(' ');
			} else
				stk.push(c);
		}
		while (!stk.isEmpty())
			sb.append(stk.pop());
		return sb.toString();
	}
}
