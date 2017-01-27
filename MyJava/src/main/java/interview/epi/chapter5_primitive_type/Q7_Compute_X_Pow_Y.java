package interview.epi.chapter5_primitive_type;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import org.junit.Test;

/**
 * Problem 5.7
 * Write a function that takes a double x and an integer y and returns x^y. The
 * time complexity should be O(n) where n is the number of bits in y. Assume
 * addition, multiplication, and division take constant time. You cannot use any
 * functions, except for those you write yourself.
 * You can ignore overflow and underflow.
 * 
 * @author yazhoucao
 * 
 */
public class Q7_Compute_X_Pow_Y {

	static String methodName = "powerXY";
	static Class<?> c = Q7_Compute_X_Pow_Y.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Divide and conquer, binary Search
	 * Time: log(y).
	 */
	public static double powerXY_BSearch(double x, int y) {
		if (x == 0)
			return 0;

		if (y >= 0)
			return pow(x, y);
		else
			return 1 / pow(x, -y);
	}

	private static double pow(double x, int y) {
		if (y == 0)
			return 1;
		else if (y == 1)
			return x;

		double half = pow(x, y / 2);

		if (y % 2 == 1)
			return half * half * x;
		else
			return half * half;
	}

	/**
	 * Decompose y ==> binary representation, y = 2^p + 2^q + ... + 2^k.
	 * E.g y = 101101, x^(2^p + 2^q) = x^(2^p) + x^(2^q),
	 * x^y = x^(101101) = x^(2^0) * x^(2^2) * x^(2^3) * x^(2^5).
	 */
	public static double powerXY_Iterative(double x, int y) {
		if (x == 0)
			return 0;

		double res = 1.0;
		if (y < 0) {
			x = 1 / x;
			y = -y;
		}

		while (y != 0) {
			if ((y & 1) == 1)
				res *= x;
			x *= x; // equals x <<= 1, but << doesn't support double
			y >>= 1;
		}
		return res;
	}

	/****************** Unit Test ******************/

	public double invokeMethod(Method m, double x, int y) {
		try {
			Object[] args = new Object[] { x, y };
			Object res = m.invoke(null, args);
			return (double) res;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Uses normalization for precision problem.
	 * 0 means equal, -1 means smaller, and 1 means larger.
	 */
	private static int compare(double a, double b) {
		double diff = (a - b) / b;
		return diff < -0.0001 ? -1 : diff > 0.0001 ? 1 : 0;
	}

	@Test
	public void test0() { // random test
		Random r = new Random();
		int boundary = 100;
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			for (int i = 0; i < 100; ++i) {
				int x = r.nextInt(boundary) - boundary / 2;
				int y = r.nextInt(boundary) - boundary / 2;
				double res = invokeMethod(m, x, y);
				double correct = Math.pow(x, y);
				assertTrue(m.getName() + " - No." + i + ": " + x + "^" + y
						+ "\tWrong:" + res + "\tCorrect:" + correct,
						compare(res, correct) == 0);
				// System.out.println(x + ", " + y);
			}
		}
	}

	@Test
	public void test1() { // 10 ^ 0
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			double x = 10;
			int y = 0;
			double res = invokeMethod(m, x, y);
			assertTrue(m.getName() + " - Wrong:" + res, res == 1);
		}
	}

	@Test
	public void test2() { // -10 ^ 0
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			double x = 10;
			int y = 0;
			double res = invokeMethod(m, x, y);
			assertTrue(m.getName() + " - Wrong:" + res, res == 1);
		}
	}

	@Test
	public void test3() { // -10 ^ 2
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			double x = -10;
			int y = 2;
			double res = invokeMethod(m, x, y);
			assertTrue(m.getName() + " - Wrong:" + res, res == 100);
		}
	}

	@Test
	public void test4() { // -10 ^ -3
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			double x = -10;
			int y = -3;
			double res = invokeMethod(m, x, y);
			assertTrue(m.getName() + " - Wrong:" + res,
					compare(res, -0.001) == 0);
		}
	}

	@Test
	public void test5() { // 0 ^ -3
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			double x = 0;
			int y = -3;
			double res = invokeMethod(m, x, y);
			assertTrue(m.getName() + " - Wrong:" + res, compare(res, 0) == 0);
		}
	}
}
