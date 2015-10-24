package interview.epi.chapter5_primitive_type;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import org.junit.Test;

/**
 * This problem is concerned with computing the reverse of the decimal
 * representation of a binary-encoded integer. For example, the reverse of 42 is
 * 24, and the reverse of -314 is -413.
 * 
 * Problem 5.10
 * Write a function which takes an integer K and returns the integer
 * corresponding to the digits of K written in reverse order.
 * 
 * @author yazhoucao
 * 
 */
public class Q10_Reverse_Digits {

	static String methodName = "ssDecodeColID";
	static Class<?> c = Q10_Reverse_Digits.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Time: O(n), n = length(x), e.g. length(145) = 3, length(45) = 2
	 */
	public static long reverse(int x) {
		long res = 0;
		while (x != 0) {
			res = res * 10 + x % 10;
			x /= 10;
		}
		return res;
	}

	/****************** Unit Test ******************/

	public long invokeMethod(Method m, int x) {
		try {
			Object res = m.invoke(null, x);
			return (long) res;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return -1;
		}
	}

	private static long checkAns(int x) {
		String s = String.valueOf(x);
		char chars[] = s.toCharArray();
		StringBuilder result = new StringBuilder();
		if (chars[0] == '-') {
			result.append('-');
		}
		for (int i = chars.length - 1; i >= 0; i--) {
			if (chars[i] != '-') {
				result.append(chars[i]);
			}
		}
		return Long.parseLong(result.toString());
	}

	@Test
	public void test0() { // random generated test
		Random r = new Random();
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			for (int times = 0; times < 1000; ++times) {
				int n = r.nextInt();
				System.out.println("n = " + n + ", reversed n = " + reverse(n));
				assert (checkAns(n) == invokeMethod(m, n));
			}
		}
	}

	@Test
	public void test1() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int x = 0;
			long res = invokeMethod(m, x);
			assertTrue(m.getName() + " - Wrong:" + res, res == 0);
		}
	}

	@Test
	public void test2() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int x = 24;
			long res = invokeMethod(m, x);
			assertTrue(m.getName() + " - Wrong:" + res, res == 42);
		}
	}

	@Test
	public void test3() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int x = -315;
			long res = invokeMethod(m, x);
			assertTrue(m.getName() + " - Wrong:" + res, res == -513);
		}
	}

	@Test
	public void test4() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int x = -1001;
			long res = invokeMethod(m, x);
			assertTrue(m.getName() + " - Wrong:" + res, res == -1001);
		}
	}
}
