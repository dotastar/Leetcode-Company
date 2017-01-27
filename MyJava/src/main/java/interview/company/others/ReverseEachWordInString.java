package interview.company.others;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Write a method that reverses a string.
 * 
 * For example, "San Francisco" should become "naS ocsicnarF"
 */
public class ReverseEachWordInString {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(ReverseEachWordInString.class);
	}

	public String reverse(String S) {
		if (S == null)
			throw new IllegalArgumentException();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < S.length();) {
			if (S.charAt(i) == ' ') {
				sb.append(' ');
				i++;
			} else { // find the word, and reverse
				int end = i + 1;
				while (end < S.length() && S.charAt(end) != ' ')
					end++;
				// from i to end - 1 is the word
				for (int j = end - 1; j >= i; j--)
					// reversing
					sb.append(S.charAt(j));
				i = end;
			}
		}
		return sb.toString();
	}

	@Test
	public void test1() {
		String S = " drow  ";
		String res = reverse(S);
		String ans = " word  ";
		assertEquals(ans, res);
	}

	@Test
	public void test2() {
		String S = "#@^  *(^*(";
		String res = reverse(S);
		String ans = "^@#  (*^(*";
		assertEquals(ans, res);
	}

	@Test
	public void test3() {
		String S = " word1  word2   word3";
		String res = reverse(S);
		String ans = " 1drow  2drow   3drow";
		assertEquals(ans, res);
	}

	@Test
	public void test4() {
		String S = " a b c d e f ";
		String res = reverse(S);
		String ans = " a b c d e f ";
		assertEquals(ans, res);
	}

	@Test
	public void test5() {
		String S = " WORD RIGHT ";
		String res = reverse(S);
		String ans = " DROW THGIR ";
		assertEquals(ans, res);
	}
}
