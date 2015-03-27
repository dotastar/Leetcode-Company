package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * 
 * Compress String
 * Hard
 * String
 * 
 * Given a string, replace adjacent, repeated characters with the character
 * followed by the number of repeated occurrences. If the character does not has
 * any adjacent, repeated occurrences, it is not changed.
 * 
 * Assumptions
 * 
 * The string is not null
 * 
 * The characters used in the original string are guaranteed to be ‘a’ - ‘z’
 * 
 * There are no adjacent repeated characters with length > 9
 * 
 * Examples
 * 
 * “abbcccdeee” → “ab2c3de3”
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Compress_String {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Compress_String.class);
	}

	/**
	 * Compress it by counting the same char, if meets a different char, copy
	 * the previous value and its count
	 * E.g.
	 * abbcccdeee
	 * ^
	 */
	public String compress(String S) {
		char[] res = S.toCharArray();
		int cnt = 1, end = 0;
		for (int i = 0; i < S.length(); i++) {
			if (i == S.length() - 1 || S.charAt(i) != S.charAt(i + 1)) {
				res[end++] = S.charAt(i);
				if (cnt > 1)
					res[end++] = (char) ('0' + cnt);
				cnt = 1;
			} else {
				cnt++;
			}
		}
		return String.valueOf(res, 0, end);
	}

	@Test
	public void test1() {
		String input = "abbcccdeee";
		String ans = "ab2c3de3";
		String res = compress(input);
		assertTrue("Wrong: " + res, ans.equals(res));
	}

	@Test
	public void test2() {
		String input = "aaabbc";
		String ans = "a3b2c";
		String res = compress(input);
		assertTrue("Wrong: " + res, ans.equals(res));
	}
}
