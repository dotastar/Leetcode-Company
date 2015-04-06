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
	 * 1.find how many times s occurs in input, then we calculate the size
	 * needed for new String
	 * 2.compare s and t, if s.length >= t.length, start from left to right,
	 * else start from right to left
	 * 3.at every index, first check if the current substring(index, index +
	 * s.length) is s, if yes, replace with t, else just copy them
	 */
	public String replace(String input, String s, String t) {
		if (input.length() == 0 || s.length() == 0)
			return input;
		List<Integer> occurs = new ArrayList<>();
		for (int i = 0; i < input.length();) {
			int idx = input.indexOf(s, i);
			if (idx < 0)
				break;
			if (s.length() >= t.length())
				occurs.add(idx);
			else
				occurs.add(idx + s.length() - 1);

			i = idx + s.length();
		}
		if (occurs.size() == 0)
			return input;

		char[] res;
		if (s.length() < t.length()){
			int size = input.length() + occurs.size() * (t.length() - s.length());
			res = new char[size];
			System.arraycopy(input.toCharArray(), 0, res, 0, input.length());
		} else
			res = input.toCharArray();
		
		if (s.length() >= t.length()) {
			int i = 0;
			int matchIdx = 0;
			for (int j = 0; j < input.length();) {
				if (matchIdx < occurs.size() && j == occurs.get(matchIdx)) {
					for (int k = 0; k < t.length();)
						res[i++] = t.charAt(k++);
					j += s.length();
					matchIdx++;
				} else
					res[i++] = res[j++];
			}
			return new String(res, 0, i);
		} else {
			int matchIdx = occurs.size() - 1;
			for (int i = res.length - 1, j = input.length() - 1; i >= 0;) {
				if (matchIdx >= 0 && j == occurs.get(matchIdx)) {
					for (int k = t.length() - 1; k >= 0;)
						res[i--] = t.charAt(k--);
					j -= s.length();
					matchIdx--;
				} else
					res[i--] = res[j--];
			}
			return new String(res);
		}
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
	public String replace2(String input, String s, String t) {
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
