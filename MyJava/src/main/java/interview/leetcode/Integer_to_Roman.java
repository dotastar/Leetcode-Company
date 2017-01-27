package interview.leetcode;

/**
 * Given an integer, convert it to a roman numeral.
 * 
 * Input is guaranteed to be within the range from 1 to 3999.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Integer_to_Roman {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int num1 = 8; // "VIII"
		int num2 = 99; // "XCIX";
		int num3 = 14; // "XIV";
		int num4 = 1437; // "MCDXXXVII";
		int num5 = 1880; // "MDCCCLXXX";
		int num6 = 3333; // "MMMCCCXXXIII";

		System.out.println(intToRoman(num1));
		System.out.println(intToRoman(num2));
		System.out.println(intToRoman(num3));
		System.out.println(intToRoman(num4));
		System.out.println(intToRoman(num5));
		System.out.println(intToRoman(num6));
	}

	/**
	 * Second time
	 */
	private final static int[] values = { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000 };
	private final static String[] romans = { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M" };

	public String intToRoman2(int num) {
		assert values.length == romans.length;
		StringBuilder sb = new StringBuilder();
		for (int i = values.length - 1; i >= 0; i--) {
			int times = num / values[i];
			for (int j = 0; j < times; j++)
				sb.append(romans[i]);
			num %= values[i];
		}
		return sb.toString();
	}

	public static String intToRoman(int num) {
		StringBuffer sb = new StringBuffer();
		String[] symbol = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
		int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };

		for (int i = 0; i < symbol.length; i++) {
			int val = values[i];
			int cnt = num / val;
			num = num % val;
			for (int j = 0; j < cnt; j++)
				sb.append(symbol[i]);
		}

		return sb.toString();
	}
}
