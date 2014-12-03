package interview.leetcode;

/**
 * Divide two integers without using multiplication, division and mod operator
 * 
 * @author yazhoucao
 * 
 */
public class Divide_Two_Integers {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
	 * Bit shifting solution
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

		int res = 0;
		while (p >= q) {
			int count = 0;
			while (p >= (q << count))
				count++;
			res += 1 << (count - 1);
			p -= q << (count - 1);
		}
		return ((dividend ^ divisor) >>> 31) == 0 ? res : -res;
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

	/**
	 * A more concise version, combined above two solution
	 */
	public static int divide3(int dividend, int divisor) {
		int res = 0;
		int p = Math.abs(dividend);
		int q = Math.abs(divisor);
		if (q == Integer.MIN_VALUE) // Math.abs() won't work on Integer.MIN
			return p == Integer.MIN_VALUE ? 1 : 0;
		else if (p == Integer.MIN_VALUE) {
			p -= q;
			res++;
		}

		while (p >= q) {
			int k = 1; // bits moved
			// equals to p/(2^k) >= q or p >= q*(2^k)
			while ((p >> k) >= q)
				k++;
			res += 1 << (k - 1);
			p -= q << (k - 1);
		}

		return (dividend ^ divisor) >>> 31 == 0 ? res : -res;

	}
}
