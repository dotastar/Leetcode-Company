package interview.leetcode;

/**
 * Implement atoi to convert a string to an integer.
 * 
 * Hint: Carefully consider all possible input cases. If you want a challenge,
 * please do not see below and ask yourself what are the possible input cases.
 * 
 * Notes: It is intended for this problem to be specified vaguely (ie, no given
 * input specs). You are responsible to gather all the input requirements up
 * front.
 * 
 * spoilers alert... click to show requirements for atoi.
 * 
 * Requirements for atoi: The function first discards as many whitespace
 * characters as necessary until the first non-whitespace character is found.
 * Then, starting from this character, takes an optional initial plus or minus
 * sign followed by as many numerical digits as possible, and interprets them as
 * a numerical value.
 * 
 * The string can contain additional characters after those that form the
 * integral number, which are ignored and have no effect on the behavior of this
 * function.
 * 
 * If the first sequence of non-whitespace characters in str is not a valid
 * integral number, or if no such sequence exists because either str is empty or
 * it contains only whitespace characters, no conversion is performed.
 * 
 * If no valid conversion could be performed, a zero value is returned. If the
 * correct value is out of the range of representable values, INT_MAX
 * (2147483647) or INT_MIN (-2147483648) is returned.
 * 
 * @author yazhoucao
 * 
 */
public class String_to_Integer_atoi {

	public static void main(String[] args) {
		System.out.println(Integer.toBinaryString(-5)); // "two's complement"
														// representation
		System.out.println(-(INT_MIN + 1) / 10); // negate is working because +1
		System.out.println(-(INT_MIN + 0) / 10); // negate is not working,
													// equals to below
		System.out.println(-INT_MIN); // negate the INT_MIN will get the same,
										// INT_MIN
		System.out.println(-((INT_MIN + 0) / 10)); // solution: add parenthesis,
													// to do division first
		System.out.println(Integer.MAX_VALUE);
		System.out.println(1 + Integer.MAX_VALUE);
		System.out.println(147483647 + 2000000000);
		System.out.println(90000000 + (1052254545 * 10));
		System.out.println("=============================");
		System.out.println(Integer.MIN_VALUE);
		System.out.println((-100) + Integer.MIN_VALUE);
		System.out.println("=============================");
		System.out.println(atoi("1"));
		assert atoi("+1") == 1;
		System.out.println(atoi("+-3"));
		assert atoi("+-3") == 0;
		System.out.println("=============================");
		System.out.println(atoi("-123"));
		assert atoi("-123") == -123;
		System.out.println(atoi("  -0012a42"));
		assert atoi("  -0012a42") == -12;
		System.out.println("=============================");
		System.out.println(atoi("2147483648"));
		assert atoi("2147483648") == Integer.MAX_VALUE;
		System.out.println(atoi("92581232147483648"));
		assert atoi("2147483648") == Integer.MAX_VALUE;
		System.out.println("=============================");
		System.out.println(atoi("-2147483648"));
		assert atoi("-2147483648") == Integer.MIN_VALUE;
		System.out.println(atoi("92581232147483648"));
		assert atoi("-952132147483648") == Integer.MIN_VALUE;
		// 10522545459
		// 1052254545
		// 214748363
		System.out.println(atoi("-2147483649"));
		System.out.println(atoi("    10522545459"));

	}

	/**
	 * Be careful corner cases:
	 * 1.positive, negative sign should be taken into consideration
	 * 2.spaces at the beginning and the end should be ignored
	 * 3.other characters could appear at any position should be ignored and
	 * stop converting immediately
	 * 4.deal with overflow cases
	 */

	/**
	 * Second time
	 */
	public int atoi2(String str) {
		str = str.trim();
		if (str.length() == 0)
			return 0;
		int i = 0;
		boolean neg = false;
		if (str.charAt(0) == '-' || str.charAt(0) == '+') {
			neg = str.charAt(i) == '-' ? true : false;
			i++;
		}
		int res = 0;
		for (; i < str.length(); i++) {
			int digit = str.charAt(i) - '0';
			if (digit < 0 || digit > 9)
				break;
			// use > instead of >=, this can include both positive and negative
			if (res > (Integer.MAX_VALUE - digit) / 10)
				return neg ? Integer.MIN_VALUE : Integer.MAX_VALUE;
			res = res * 10 + digit;
		}

		return neg ? -res : res;
	}

	/**
	 * Key: how to handle overflow cases.
	 */
	private static final int INT_MAX = 2147483647;
	private static final int INT_MIN = -2147483648;

	public static int atoi(String str) {
		str = str.trim();
		int len = str.length();
		if (len == 0)
			return 0;

		int i = 0;
		boolean isNeg = false;
		char c = str.charAt(0);
		if (c == '-' || c == '+') {
			i++;
			if (c == '-')
				isNeg = true;
		}

		int res = 0;
		while (i < len) {
			c = str.charAt(i);
			if (!Character.isDigit(c))// not a digit
				break;
			int val = c - '0';
			if ((!isNeg && res > (INT_MAX - val) / 10)) // overflow
				return INT_MAX;
			else if (isNeg && res > -((INT_MIN + val) / 10))
				return INT_MIN;

			res = res * 10 + val;
			i++;
		}
		return isNeg ? -res : res;
	}
}
