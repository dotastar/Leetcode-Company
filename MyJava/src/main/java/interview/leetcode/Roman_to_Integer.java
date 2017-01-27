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
		System.out.println(romanToInt(num7));

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
	 * Use a HashMap to store Roman to Integer Value
	 * Traverse the String, 2 cases:
	 * 1.If the next char's value is greater than current char,
	 * means current char is sth like IX, IV, we should this value
	 * 2.Else, we add this value
	 * 
	 * One key observation: E.g."DCIX",
	 * Start from left to right, the value is in decreasing order
	 * unless it's something like IX, IV, XC,
	 * if we meet cases like "IX", we just need to minus its value.
	 */
	public int romanToInt1(String s) {
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
	 * Same as above, but scan from right to left, easier
	 */
	public static int romanToInt(String s) {
		int res = 0;
		Map<Character, Integer> dict = new HashMap<>();
		dict.put('I', 1);
		dict.put('V', 5);
		dict.put('X', 10);
		dict.put('L', 50);
		dict.put('C', 100);
		dict.put('D', 500);
		dict.put('M', 1000);
		int prevVal = 0;
		for (int i = s.length() - 1; i >= 0; i--) {
			int curVal = dict.get(s.charAt(i));
			res += curVal < prevVal ? -curVal : curVal;
			prevVal = curVal;
		}
		return res;
	}

}
