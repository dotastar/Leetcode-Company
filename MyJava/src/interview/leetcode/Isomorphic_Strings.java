package interview.leetcode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Given two strings s and t, determine if they are isomorphic.
 * 
 * Two strings are isomorphic if the characters in s can be replaced to get t.
 * 
 * All occurrences of a character must be replaced with another character while
 * preserving the order of characters. No two characters may map to the same
 * character but a character may map to itself.
 * 
 * For example,
 * Given "egg", "add", return true.
 * 
 * Given "foo", "bar", return false.
 * 
 * Given "paper", "title", return true.
 * 
 * Note:
 * You may assume both s and t have the same length.
 * 
 * @author yazhoucao
 *
 */
public class Isomorphic_Strings {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Isomorphic_Strings.class);
	}

	/**
	 * Replace each char as an integer id, compare each other each iteration
	 */
	public boolean isIsomorphic(String s, String t) {
		int[] smap = new int[128], tmap = new int[128];
		int n = s.length();
		int id = 1;
		for (int i = 0; i < n; i++) {
			char si = s.charAt(i), ti = t.charAt(i);
			if (smap[si] != tmap[ti])
				return false;
			if (smap[si] == 0)
				smap[si] = tmap[ti] = id++;
		}
		return true;
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}
}
