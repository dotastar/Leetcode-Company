package interview.epi.chapter5_primitive_type;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import org.junit.Test;

import interview.AutoTestUtils;

/**
 * Problem 5.6
 * Given two positive integers, compute their quotient, using only the addition,
 * subtraction, and shifting operatiors.
 * 
 * @author yazhoucao
 * 
 */
public class Q6_Compute_X_Div_Y {
	static String methodName = "divideXByY";
	static Class<?> c = Q6_Compute_X_Div_Y.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Time: O(n^2), n is the number bits of x.
	 * Idea: use y * 2^k to represent x, use shift to represent 2^k.
	 * x/y = y*(2^p + 2^q + ... + 2^k) / y = 2^p + 2^q + ... + 2^k
	 */
	public static long divideXByY(long x, long y) {
		if (y == 0)
			throw new RuntimeException("Divisor shouldn't be 0!");
		boolean neg = (x ^ y) >>> 63 == 1;
		if (x < 0)
			x = x == Long.MIN_VALUE ? x = Long.MAX_VALUE : -x;
		if (y < 0)
			y = y == Long.MIN_VALUE ? y = Long.MAX_VALUE : -y;

		long res = 0;
		while (x >= y) { // don't forget the equal
			int count = 1;
			while (y <= (x >> count))
				count++; // to avoid overflow, shift x instead of y
			count--;
			res += 1L << count; // L is important, otherwise it will be integer
			x -= y << count;
		}
		return neg ? -res : res;
	}

	/****************** Unit Test ******************/

	public long invokeMethod(Method m, long x, long y) {
		try {
			Object[] args = new Object[] { x, y };
			Object res = m.invoke(null, args);
			return (long) res;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	@Test
	public void test0() { // random test
		Random r = new Random();
		int boundary = 100000;
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			for (int i = 0; i < 10000; ++i) {
				long x = r.nextInt(boundary) - boundary / 3, y = r
						.nextInt(boundary) - boundary / 2;
				if (y == 0)
					continue;
				long res = invokeMethod(m, x, y);
				assertTrue(x + "/" + y + "\tWrong:" + res + "\tCorrect:"
						+ (long) x / y, res == (long) x / y);
				// System.out.println(x + ", " + y);
			}
		}
	}

	@Test
	public void test1() { // Dividend is 0
		long x = 0, y = Long.MAX_VALUE;
		for (Method m : AutoTestUtils.findMethod(methodName, c))
			assertTrue(invokeMethod(m, x, y) == 0);
	}

	@Test
	public void test2() { // Dividend is MAX_VALUE
		long x = Long.MAX_VALUE, y = -1;
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			long res = invokeMethod(m, x, y);
			assertTrue("res:" + res, res == Long.MIN_VALUE + 1);
		}
	}

	@Test
	public void test3() { // Dividend is MIN_VALUE
		long x = Long.MIN_VALUE, y = 1;
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			long res = invokeMethod(m, x, y);
			// because the algorithm convert negative to positive at first,
			// and when it is MIN_VALUE, it will be converted to MAX_VALUE.
			assertTrue("res:" + res, res == -Long.MAX_VALUE);
		}
	}

	@Test
	public void test4() { // Dividend is MIN_VALUE
		long x = Long.MIN_VALUE, y = -1;
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			long res = invokeMethod(m, x, y);
			assertTrue("res:" + res, res == Long.MAX_VALUE);
		}
	}

	@Test
	public void test5() {
		long x = 21, y = 4;
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			long res = invokeMethod(m, x, y);
			assertTrue("res:" + res, res == x / y);
		}
	}
}
