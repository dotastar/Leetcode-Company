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

	/**
	 * String atoi + HashMap
	 * 1.decide if it is negative or not, if yes, insert '-' first,
	 * and p = abs(numerator), q = abs(denominator)
	 * 2.decide if it has decimal or not, if not, simply return result
	 * 3.if it has decimal, append dot '.', and then:
	 * 4.use a hashtable to store previous calculated number as key
	 * and length of StringBuilder as value (the index will be inserted)
	 * 5.repeated calculate its result until find recurring:
	 * if (map.contains(p / q)) then find recurring
	 * else p = p * 10, p = p % q
	 * 
	 * 2 / 3, 0.(6)
	 */
	public String fractionToDecimal(int numerator, int denominator) {
		StringBuilder sb = new StringBuilder();
		long p = Math.abs((long) numerator), q = Math.abs((long) denominator);
		if (p != 0 && (numerator ^ denominator) >>> 31 == 1)
			sb.append('-');
		sb.append(p / q);
		p %= q;
		if (p != 0)
			sb.append('.');
		Map<Long, Integer> history = new HashMap<>();
		while (p > 0) {
			if (history.containsKey(p)) { // find recurring
				sb.insert(history.get(p).intValue(), '(');
				sb.append(')');
				break;
			} else
				history.put(p, sb.length());

			p *= 10;
			sb.append(p / q);
			p %= q;
		}
		return sb.toString();
	}
}
