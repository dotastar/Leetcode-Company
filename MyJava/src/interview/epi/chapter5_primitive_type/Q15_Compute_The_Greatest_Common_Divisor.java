package interview.epi.chapter5_primitive_type;

/**
 * Design an efficient algorithms for computing the GCD of two numbers without
 * using multiplication, division or the modulus operators.
 * 
 * @author yazhoucao
 * 
 */
public class Q15_Compute_The_Greatest_Common_Divisor {

	static String methodName = "";
	static Class<?> c = Q15_Compute_The_Greatest_Common_Divisor.class;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Time: O(log(x)+log(y))
	 */
	public static long elementaryGCD(long x, long y) {
		if (x == 0) {
			return y;
		} else if (y == 0) {
			return x;
		} else if ((x & 1) == 0 && (y & 1) == 0) { // x and y are even.
			return elementaryGCD(x >> 1, y >> 1) << 1;
		} else if ((x & 1) == 0 && (y & 1) != 0) { // x is even, y is odd.
			return elementaryGCD(x >> 1, y);
		} else if ((x & 1) != 0 && (y & 1) == 0) { // x is odd, y is even.
			return elementaryGCD(x, y >> 1);
		} else if (x > y) { // Both x and y are odd, and x > y.
			return elementaryGCD(x - y, y);
		}
		return elementaryGCD(x, y - x); // Both x and y are odd, and x <= y.
	}

	/**
	 * If can use mod operation ...
	 */
	public static long anotherGCD(long a, long b) {
		if (b != 0) {
			while ((a = a % b) != 0 && (b = b % a) != 0) {
			}
		}
		return a + b;
	}
}
