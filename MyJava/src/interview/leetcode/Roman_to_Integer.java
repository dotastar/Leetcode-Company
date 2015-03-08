package interview.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a roman numeral, convert it to an integer.
 * 
 * Input is guaranteed to be within the range from 1 to 3999.
 * 
 */
public class Roman_to_Integer {

	public static void main(String[] args) {
		System.out.println(-5 / 2);
		String num7 = "DCXXI";
		System.out.println(romanToInt3(num7));

		String num1 = "VIII"; // 8
		String num2 = "XCIX"; // 99
		String num3 = "XIV"; // 14
		String num4 = "MCDXXXVII"; // 1437
		String num5 = "MDCCCLXXX"; // 1880
		String num6 = "MMMCCCXXXIII"; // 3333

		System.out.println(romanToInt(num1));
		System.out.println(romanToInt(num2));
		System.out.println(romanToInt(num3));
		System.out.println(romanToInt(num4));
		System.out.println(romanToInt(num5));
		System.out.println(romanToInt(num6));

	}

	/**
	 * Best solution
	 * One key observation: E.g."DCIX",
	 * Start from left to right, the value is in decreasing order
	 * unless it's something like IX, IV, XC,
	 * if we meet cases like "IX", we just need to minus its value.
	 */
	public int romanToInt4(String s) {
		Map<Character, Integer> dict = new HashMap<>();
		dict.put('I', 1);
		dict.put('V', 5);
		dict.put('X', 10);
		dict.put('L', 50);
		dict.put('C', 100);
		dict.put('D', 500);
		dict.put('M', 1000);

		int res = 0;
		for (int i = 0; i < s.length(); i++) {
			char si = s.charAt(i);
			if (i < s.length() - 1 && dict.get(si) < dict.get(s.charAt(i + 1)))
				res -= dict.get(si);
			else
				res += dict.get(si);
		}
		return res;
	}

	/**
	 * Solution 2
	 */
	private final static int[] Values = { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400,
			500, 900, 1000 };
	private final static String[] Romans = { "I", "IV", "V", "IX", "X", "XL",
			"L", "XC", "C", "CD", "D", "CM", "M" };

	public static int romanToInt3(String s) {
		int res = 0;
		for (int i = 0, j = Values.length - 1; i < s.length() && j >= 0;) {
			char c = s.charAt(i);
			if (c == Romans[j].charAt(0)) {
				if (Romans[j].length() == 1
						|| (i != s.length() - 1 && Romans[j].charAt(1) == s
								.charAt(i + 1))) {
					res += Values[j];
					i = Romans[j].length() == 1 ? i + 1 : i + 2;
				} else
					j--;
			} else
				j--;
		}
		return res;
	}

	/**
	 * The same as solution 2
	 */
	public int romanToInt2(String s) {
		String[] romans = { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C",
				"CD", "D", "CM", "M" };
		int[] values = { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000 };
		assert romans.length == values.length;
		int res = 0;
		for (int i = 0, j = romans.length - 1; i < s.length();) {
			char si = s.charAt(i);
			String roman = romans[j];
			if (si == roman.charAt(0)) {
				if (roman.length() == 1) {
					res += values[j];
					i++;
				} else if (i + 1 < s.length()
						&& s.charAt(i + 1) == roman.charAt(1)) {
					res += values[j];
					i += 2;
					j--;
				} else
					j--;
			} else
				j--;
		}
		return res;
	}

	/**
	 * First solution
	 */
	public static int romanToInt(String s) {
		char[] chs = s.toCharArray();
		int res = 0;

		for (int i = 0; i < chs.length; i++) {
			char c = chs[i];
			char cnext = ' ';
			if (i + 1 < chs.length)
				cnext = chs[i + 1];

			int add = 0;
			switch (c) {
			case 'I': // 1, minus
				if (cnext == 'V') {
					add = 4;
					i++;
				} else if (cnext == 'X') {
					add = 9;
					i++;
				} else
					add = 1;
				break;
			case 'V': // 5
				add = 5;
				break;
			case 'X': // 10, minus
				if (cnext == 'L') {
					add = 40;
					i++;
				} else if (cnext == 'C') {
					add = 90;
					i++;
				} else
					add = 10;
				break;
			case 'L': // 50
				add = 50;
				break;
			case 'C': // 100, minus
				if (cnext == 'D') {
					add = 400;
					i++;
				} else if (cnext == 'M') {
					add = 900;
					i++;
				} else
					add = 100;
				break;
			case 'D': // 500
				add = 500;
				break;
			case 'M': // 1000
				add = 1000;
				break;
			}
			res += add;
		}
		return res;
	}
}
