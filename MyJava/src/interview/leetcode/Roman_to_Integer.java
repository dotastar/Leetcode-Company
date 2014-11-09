package interview.leetcode;

/**
 * Given a roman numeral, convert it to an integer.
 * 
 * Input is guaranteed to be within the range from 1 to 3999.
 * 
 */
public class Roman_to_Integer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
	 * A more compact way to write it
	 * 
	 */
	public static int romanToInt2(String s) {
		// X IX X IV III
		char[] romans = { 'I', 'V', 'X', 'L', 'C', 'D', 'M' };
		int[] numerals = { 1, 5, 10, 50, 100, 500, 1000 };
		int len = s.length();
		int j = romans.length - 1;
		int res = 0;
		for (int i = 0; i < len; i++) {
			while (j >= 0 && s.charAt(i) != romans[j])
				j--;
			if (i == len - 1)	//the last char
				res += numerals[j];
			else {
				if (j < romans.length - 1 && s.charAt(i + 1) == romans[j + 1]) {
					res += numerals[j + 1] - numerals[j]; // could be IV, XL, CD
					i++;
				} else if (j < romans.length - 2
						&& s.charAt(i + 1) == romans[j + 2]) {
					res += numerals[j + 2] - numerals[j]; // could be IX, XC, CM
					i++;
				} else
					res += numerals[j];
			}
		}
		return res;
	}

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
