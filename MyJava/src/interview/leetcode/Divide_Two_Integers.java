package interview.leetcode;

/**
 * Divide two integers without using multiplication, division and mod operator
 * 
 * @author yazhoucao
 * 
 */
public class Divide_Two_Integers {

	public static void main(String[] args) {

		System.out.print(divide(100, 18) + "\t");
		System.out.println(divide2(100, 18));

		System.out.print(divide(1, 10) + "\t");
		System.out.println(divide2(1, 10));

		System.out.print(divide(5, 5) + "\t");
		System.out.println(divide2(5, 5));

		System.out.print(divide(5, 2) + "\t");
		System.out.println(divide2(5, 2));

		System.out.print(divide(100, -18) + "\t");
		System.out.println(divide2(100, -18));

		System.out.print(divide(-5, -5) + "\t");
		System.out.println(divide2(-5, -5));

		System.out.print(divide(-100, 99) + "\t");
		System.out.println(divide2(-100, 99));

		System.out.print(divide(-1010369383, -2147483648) + "\t");
		System.out.println(divide2(-1010369383, -2147483648));

		System.out.print(divide(-2147483648, 1000) + "\t");
		System.out.println(divide2(-2147483648, 1000));

		/**
		 * Negative number is represented in two's complementary representation.
		 */
		int min = Integer.MIN_VALUE;
		System.out.println(min);
		System.out.println(Integer.toBinaryString(min));
		System.out.println(min | 1);
		System.out.println(Integer.toBinaryString(min | 1));
		System.out.println(-1);
		System.out.println(Integer.toBinaryString(-1));
		System.out.println(min / min);

		/**
		 * For a Integer number, if it overflows, it goes back to the minimum
		 * value and continues from there. If it underflows, it goes back to the
		 * maximum value and continues from there.
		 * 
		 * In this problem, positive Max value won't have any problem, Min value
		 * (negative) has to convert to positive which neither Math.abs() nor
		 * -value works
		 * 
		 * Here is the test, and it shows that they won't work
		 */
		System.out.println("\nMax/Min Integer abs() test:");
		System.out.println("Original: " + Integer.MIN_VALUE); // original
		System.out.println(-Integer.MIN_VALUE); // won't work
		System.out.println(Math.abs(Integer.MIN_VALUE)); // won't work

		System.out.println("Original: " + (Integer.MIN_VALUE - 1)); // underflow
		System.out.println(Math.abs(Integer.MIN_VALUE - 1)); // works

		System.out.println("Original: " + (Integer.MIN_VALUE + 1)); // avoid
																	// underflow
		System.out.println(Math.abs(Integer.MIN_VALUE + 1)); // works
	}

	/**
	 * And number (the result of p/q) can be represented by:
	 * p / q = 2^0 * (1/0) + 2^1 * (1/0) + 2^2 * (1/0) + ... + 2^i * (1/0)
	 * p = (2^0)*q + (2^1)*q + (2^2)*q + ... (2^n)*q
	 */

	/**
	 * A more concise version, combined below two solution
	 * 
	 * Several problems need to be careful:
	 * 1.Because MAX_VALUE+1 = abs(MIN_VALUE), dividend and divisor could be
	 * MIN_VALUE which should MAX_VALUE+1,
	 * Solution for dividend: minus q once in advance in order to plus one so
	 * that the value is balanced and it won't overflow
	 * For divisor, the result is fixed, the result is 1 or 0.
	 * 
	 * 2.when shifting q<<(i+1) <= p, it could be overflow, should instead
	 * shifting p: (q <= (p>>(i+1)))
	 * 
	 * 3.while adding the result: res += (1<<i), it could be overflow as well
	 * (e.g. MIN_INT/-1), it should be checked before adding.
	 */
	public int divide3(int dividend, int divisor) {
		int p = abs(dividend), q = abs(divisor);
		boolean neg = (dividend ^ divisor) >>> 31 == 1;
		int res = 0;
		// case analysis
		if (divisor == Integer.MIN_VALUE) {
			return dividend == Integer.MIN_VALUE ? 1 : 0;
		} else if (dividend == Integer.MIN_VALUE) {
			res++;
			p = p - q + 1;
		}
		while (p >= q) {
			int i = 0;
			// potential overflow solved by shifting p instead of q
			while (q <= (p >> (i + 1)))
				i++;
			if (res > Integer.MAX_VALUE - (1 << i))
				return neg ? Integer.MIN_VALUE : Integer.MAX_VALUE;
			res += (1 << i); // potential overflow
			p -= (q << i);
		}
		return neg ? -res : res;
	}

	private int abs(int a) {
		return a == Integer.MIN_VALUE ? Integer.MAX_VALUE : Math.abs(a);
	}

	/**
	 * Bit shifting solution, use long to avoid overflow
	 * 
	 * One thing needs to be noticed is that we need to convert the integer to
	 * long type. Otherwise the Math.abs() value of Integer.MIN_VALUE will be
	 * quite strange.
	 * 
	 * O(lgn)
	 * 
	 */
	public static int divide(int dividend, int divisor) {
		long p = Math.abs((long) dividend); // this is importants!
		long q = Math.abs((long) divisor);
		boolean neg = ((dividend ^ divisor) >>> 31) == 1;
		int res = 0;
		while (p >= q) {
			int i = 0;
			while (p >= (q << (i + 1)))
				i++;
			if (res >= Integer.MAX_VALUE - (1 << i))
				return neg ? Integer.MIN_VALUE : Integer.MAX_VALUE;
			res += 1 << i;
			p -= q << i;
		}
		return neg ? -res : res;
	}

	/**
	 * Solution without converting int to long
	 * 
	 * Based on the form: num = a_0*2^0 + a_1*2^1 + a_2*2^2 +...+ a_n*2^n
	 */
	public static int divide2(int dividend, int divisor) {
		// unsigned shifting (>>>)
		boolean neg = ((dividend ^ divisor) >>> 31) == 1;
		int res = 0;
		dividend = Math.abs(dividend);
		divisor = Math.abs(divisor);
		// Math.abs() won't work on Integer.MIN
		if (divisor == Integer.MIN_VALUE)
			return dividend == Integer.MIN_VALUE ? 1 : 0;
		else if (dividend == Integer.MIN_VALUE) {
			dividend += divisor;
			dividend = Math.abs(dividend);
			res++;
		}
		int k = 0;
		while (dividend >> 1 >= divisor) {
			divisor = divisor << 1;
			k++;
		}
		while (dividend > 0 && k >= 0) {
			if (dividend >= divisor) {
				dividend -= divisor;
				res += 1 << k; // 2^k
			}
			k--;
			divisor = divisor >> 1;
		}
		return neg ? -res : res;
	}

}
