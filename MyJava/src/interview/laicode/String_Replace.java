package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * String Replace
 * Hard
 * String
 * 
 * Given an original string input, and two strings S and T, replace all
 * occurrences of S in input with T.
 * 
 * Assumptions
 * 
 * input, S and T are not null, S is not empty string
 * 
 * Examples
 * 
 * input = "appledogapple", S = "apple", T = "cat", input becomes "catdogcat"
 * input = "dodododo", S = "dod", T = "a", input becomes "aoao"
 * 
 * 
 * @author yazhoucao
 * 
 */
public class String_Replace {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(String_Replace.class);
	}

	/**
	 * 
	 * 1.search for matches of s in input, store those indice
	 * 2.replacing s with t by those stored indice:
	 * 2a.check if need to allocate new spaces? if yes, allocate new spaces.
	 * 2b.copy input to char[] res, if meets the idx of matches, copy t instead
	 * of input
	 * 
	 * appledogapple, apple -> cat
	 * ^
	 * catdogcat
	 * 
	 * dodododo, dod -> a
	 * ^
	 * aoao
	 */
	public String replace(String input, String s, String t) {
		if (input.indexOf(s) < 0)
			return input;
		List<Integer> matches = new ArrayList<>();
		for (int i = 0; i < input.length();) {
			if (isMatch(input, s, i)) {
				matches.add(i);
				i += s.length();
			} else
				i++;
		}

		if (matches.size() > 0) { // if matched any s in input
			char[] res;
			if (s.length() < t.length()) { // need to allocate new space
				int extraLength = matches.size() * (t.length() - s.length());
				res = new char[input.length() + extraLength];
			} else
				res = input.toCharArray();

			// replacing
			int iRes = 0; // index of res
			for (int iInput = 0, iMatch = 0; iInput < input.length();) {
				// replace s with t, start at curr
				if (iMatch < matches.size() && iInput == matches.get(iMatch)) {
					// copy t to res
					for (int i = 0; i < t.length(); i++, iRes++)
						res[iRes] = t.charAt(i);
					iMatch++;
					iInput += s.length();
				} else
					res[iRes++] = input.charAt(iInput++);
			}
			
			input = String.valueOf(res, 0, iRes);
		}
		
		return input;
	}

	private boolean isMatch(String input, String target, int start) {
		if (start + target.length() <= input.length()) {
			for (int i = 0; i < target.length(); i++) {
				if (input.charAt(start + i) != target.charAt(i))
					return false;
			}
			return true;
		}
		return false;
	}

	@Test
	public void test0() {
		String input = "";
		String s = "";
		String t = "";
		String res = replace(input, s, t);
		String ans = "";
		assertTrue("Wrong: " + res, res.equals(ans));
	}

	@Test
	public void test1() {
		String input = "appledogapple";
		String s = "apple";
		String t = "cat";
		String res = replace(input, s, t);
		String ans = "catdogcat";
		assertTrue("Wrong: " + res, res.equals(ans));
	}

	@Test
	public void test2() {
		String input = "dodododo";
		String s = "dod";
		String t = "a";
		String res = replace(input, s, t);
		String ans = "aoao";
		assertTrue("Wrong: " + res, res.equals(ans));
	}

	@Test
	public void test3() {
		String input = "appledogapple";
		String s = "apple";
		String t = "watermelon";
		String res = replace(input, s, t);
		String ans = "watermelondogwatermelon";
		assertTrue("Wrong: " + res, res.equals(ans));
	}

	@Test
	public void test4() {
		String input = "dodododo";
		String s = "do";
		String t = "did";
		String res = replace(input, s, t);
		String ans = "diddiddiddid";
		assertTrue("Wrong: " + res, res.equals(ans));
	}

	@Test
	public void test5() {
		String input = "aaaaaa";
		String s = "aa";
		String t = "abc";
		String res = replace(input, s, t);
		String ans = "abcabcabc";
		assertTrue("Wrong: " + res, res.equals(ans));
	}
}
