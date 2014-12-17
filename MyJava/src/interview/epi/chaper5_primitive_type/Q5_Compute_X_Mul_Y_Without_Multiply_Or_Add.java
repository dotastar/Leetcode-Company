package interview.epi.chaper5_primitive_type;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import org.junit.Test;

public class Q5_Compute_X_Mul_Y_Without_Multiply_Or_Add {

	static String methodName = "multiplyNoOperator";
	static Class<?> c = Q5_Compute_X_Mul_Y_Without_Multiply_Or_Add.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Time: O(n^2), n is the width of y (number of bits).
	 * Idea: use (2^p + 2^q + ... + 2^k) to represent y, <<k equals to 2^k
	 * x*y = x*(2^p + 2^q + ... + 2^k) = x*2^p + x*2^q + ... + x*2^k.
	 */
	public static long multiplyNoOperator(long x, long y) {
		if (x == 0 || y == 0)
			return 0;
		boolean neg = ((x ^ y) >>> 63) == 1;
		if (x < 0)
			x = x == Long.MIN_VALUE ? Long.MAX_VALUE : -x;
		if (y < 0)
			y = y == Long.MIN_VALUE ? Long.MAX_VALUE : -y;

		assert x >= 0 && y >= 0;
		long res = 0;
		while (y > 0) {
			int count = 0;
			// the same: (1<<count) <= y, shift y cause 1<<count could overflow!
			while (1 <= (y >> count))
				count++; //
			count--; // there is one more count/add when it is greater than y

			if (res >= (Long.MAX_VALUE - (x << count))) // deal with overflow
				return neg ? Long.MIN_VALUE : Long.MAX_VALUE;

			y -= (1 << count);
			res += x << count;
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
				int x = r.nextInt(boundary) - boundary / 2, y = r
						.nextInt(boundary) - boundary / 2;
				long prod = invokeMethod(m, x, y);
				assertTrue(prod == (long) x * y);
			}
		}
	}

	@Test
	public void test1() {
		long x = 8;
		long y = 2;
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			long res = invokeMethod(m, x, y);
			assertTrue("res:" + res, res == 16);
		}
	}

	@Test
	public void test2() {
		long x = 88;
		long y = 0;
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			long res = invokeMethod(m, x, y);
			assertTrue("res:" + res, res == 0);
		}
	}

	@Test
	public void test3() {
		long x = 3;
		long y = -10;
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			long res = invokeMethod(m, x, y);
			assertTrue("res:" + res, res == -30);
		}
	}

	@Test
	public void test4() {
		long x = -10000;
		long y = -10;
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			long res = invokeMethod(m, x, y);
			assertTrue("res:" + res, res == 100000);
		}
	}

	@Test
	public void test5() {
		long x = 4611686018427387909L; // > Long.MAX_VALUE/2
		long y = -22222;
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			long res = invokeMethod(m, x, y);
			assertTrue("res:" + res, res == Long.MIN_VALUE);
		}
	}

	@Test
	public void test6() {
		long x = 4611686018427387909L; // > Long.MAX_VALUE/2
		long y = 22222;
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			long res = invokeMethod(m, x, y);
			assertTrue("res:" + res, res == Long.MAX_VALUE);
		}
	}
}
