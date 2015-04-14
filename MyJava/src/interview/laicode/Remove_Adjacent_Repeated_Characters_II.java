package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Remove Adjacent Repeated Characters II
 * Fair
 * String
 * 
 * Remove adjacent, repeated characters in a given string, leaving only two
 * characters for each group of such characters. The characters in the string
 * are sorted in ascending order.
 * 
 * Assumptions
 * 
 * Try to do it in place.
 * 
 * Examples
 * 
 * “aaaabbbc” is transferred to “aabbc”
 * 
 * Corner Cases
 * 
 * If the given string is null, we do not need to do anything.
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Remove_Adjacent_Repeated_Characters_II {

	public static void main(String[] args) {
		AutoTestUtils
				.runTestClassAndPrint(Remove_Adjacent_Repeated_Characters_II.class);
	}

	/**
	 * Two pointers
	 * aabbcbbc
	 * ^ ^
	 * cnt = 2
	 * 1.s[i] != s[i-1]:
	 * s[end++] = s[i]
	 * cnt = 1
	 * 2.s[i] == s[i-1]
	 * if (cnt == 1)
	 * s[end++] = s[i]
	 * else
	 * cnt++
	 */
	public String deDup(String input) {
		if (input.length() == 0)
			return input;
		char[] str = input.toCharArray();
		int end = 1, cnt = 1;
		for (int i = 1; i < str.length; i++) {
			if (str[i] != str[i - 1] || cnt == 1) {
				cnt = str[i] != str[i - 1] ? 1 : cnt + 1;
				str[end++] = str[i];
			}
		}
		return new String(str, 0, end);
	}

	@Test
	public void test1() {
		String input = "aaaabbbc";
		String ans = "aabbc";
		String res = deDup(input);
		assertTrue("Wrong: " + res, res.equals(ans));
	}

	@Test
	public void test2() {
		String input = "abbbccd";
		String ans = "abbccd";
		String res = deDup(input);
		assertTrue("Wrong: " + res, res.equals(ans));
	}
}
