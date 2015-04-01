package interview.leetcode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Compare two version numbers version1 and version2.
 * If version1 > version2 return 1, if version1 < version2 return -1, otherwise
 * return 0.
 * 
 * You may assume that the version strings are non-empty and contain only digits
 * and the . character.
 * The . character does not represent a decimal point and is used to separate
 * number sequences.
 * For instance, 2.5 is not "two and a half" or "half way to version three", it
 * is the fifth second-level revision of the second first-level revision.
 * 
 * Here is an example of version numbers ordering:
 * 
 * 0.1 < 1.1 < 1.2 < 13.37
 * 
 * @author yazhoucao
 * 
 */
public class Compare_Version_Numbers {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Compare_Version_Numbers.class);
	}

	/**
	 * Split String by '.', compare lengths first, if they have equal length,
	 * then come each part character by character:
	 * 
	 * 0.ignore all heading zeroes
	 * 1.if s1[i] == s2[j], look next;
	 * a.if s1 or s2 reaches the end, the other is the greater one
	 * b.else both reach the end, they are equal
	 * 2.if s1[i] > s2[j], return 1
	 * 3.else if s1[i] < s2[j], return -1
	 * 
	 */
	public int compareVersion(String version1, String version2) {
		int i = 0, j = 0;
		while (i < version1.length() || j < version2.length()) {
			i = trim(version1, i); // ignores heading zeroes
			j = trim(version2, j);
			int len1 = lengthFromDot(version1, i); // length away from i to '.'
			int len2 = lengthFromDot(version2, j);
			if (len1 != len2)
				return len1 > len2 ? 1 : -1;

			for (int k = 0; k < len1; k++, i++, j++) { // len1 == len2
				char ch1 = version1.charAt(i), ch2 = version2.charAt(j);
				if (ch1 != ch2)
					return ch1 > ch2 ? 1 : -1;
			}
			i++;
			j++;
		}
		return 0;
	}

	private int trim(String str, int start) {
		while (start < str.length() && str.charAt(start) == '0')
			start++;
		return start;
	}

	private int lengthFromDot(String str, int start) {
		int end = start;
		while (end < str.length() && str.charAt(end) != '.')
			end++;
		return end - start;
	}

	@Test
	public void test1() {
		String v1 = "01";
		String v2 = "1";
		int ans = 0; // v1 == v2
		int res = compareVersion(v1, v2);
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test2() {
		String v1 = "1.0";
		String v2 = "1";
		int ans = 0; // v1 == v2
		int res = compareVersion(v1, v2);
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test3() {
		String v1 = "1.11";
		String v2 = "1.2";
		int ans = 1; // v1 > v2
		int res = compareVersion(v1, v2);
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test4() {
		String v1 = "1.01";
		String v2 = "1.1";
		int ans = 0; // v1 == v2
		int res = compareVersion(v1, v2);
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test5() {
		String v1 = "12.012";
		String v2 = "12.9";
		int ans = 1; // v1 > v2
		int res = compareVersion(v1, v2);
		assertTrue("Wrong: " + res, res == ans);
	}
}
