package interview.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Given two integers representing the numerator and denominator of a fraction,
 * return the fraction in string format.
 * 
 * If the fractional part is repeating, enclose the repeating part in
 * parentheses.
 * 
 * For example,
 * 
 * Given numerator = 1, denominator = 2, return "0.5".
 * Given numerator = 2, denominator = 1, return "2".
 * Given numerator = 2, denominator = 3, return "0.(6)".
 * 
 * @author yazhoucao
 * 
 */
public class Fraction_to_Recurring_Decimal {

	public static void main(String[] args) {
		Fraction_to_Recurring_Decimal o = new Fraction_to_Recurring_Decimal();
		int a = -1, b = -2147483648;
		String ans = "0.0000000004656612873077392578125";
		String res = o.fractionToDecimal(a, b);
		assert ans.equals(res) : res;
	}

	public String fractionToDecimal(int numerator, int denominator) {
		StringBuilder sb = new StringBuilder();
		long p = Math.abs((long) numerator), q = Math.abs((long) denominator);
		if (((numerator ^ denominator) >>> 31 == 1) && numerator != 0)
			sb.append('-');

		sb.append(p / q);
		p %= q;
		if (p == 0) // is divisible
			return sb.toString();
		sb.append('.');

		assert p < q;
		Map<Long, Integer> map = new HashMap<>();
		for (int i = sb.length(); p > 0; i++) {
			if (map.containsKey(p)) {
				int start = map.get(p);
				sb.insert(start, '(');
				sb.append(')');
				break;
			} else
				map.put(p, i);

			p *= 10; // potential overflow if use int
			sb.append(p / q);
			p %= q;

		}

		return sb.toString();
	}

	public int abs(int a) {
		return a == Integer.MIN_VALUE ? Integer.MAX_VALUE : (int) Math.abs(a);
	}
}
