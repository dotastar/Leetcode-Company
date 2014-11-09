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

		if ((dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0))
			return res;
		else
			return -res;
	}

	/**
	 * Solution without converting int to long
	 * 
	 * Based on the form: num = a_0*2^0 + a_1*2^1 + a_2*2^2 +...+ a_n*2^n
	 */
	public static int divide2(int dividend, int divisor) {
		if (divisor == 0)
			return Integer.MAX_VALUE;
		if (dividend == 0)
			return 0;

		int res = 0;

		// Math.abs() won't work on Integer.MIN
		if (dividend == Integer.MIN_VALUE) {
			// equal to - ( abs(dividend) - abs(advisor) )
			dividend += Math.abs(divisor); // so that Math.abs could work
			res += 1; // minus one divisor, so result should add one
		}
		if (divisor == Integer.MIN_VALUE)
			return res;
		// notice: must be unsigned shifting (>>>)
		boolean neg = (dividend ^ divisor) >>> 31 == 1 ? true : false;
		dividend = Math.abs(dividend);
		divisor = Math.abs(divisor);

		int bits = 0; // bits moved
		while ((dividend >> 1) >= divisor) {
			divisor <<= 1;
			bits++;
		}
		// add dividend!=0 could make the loop break earlier
		while (bits >= 0 && dividend!=0) {	
			if (dividend >= divisor) {
				dividend -= divisor;
				res += (1 << bits);
			}
			divisor >>= 1;
			bits--;
		}

		return neg ? -res : res;
	}
}
