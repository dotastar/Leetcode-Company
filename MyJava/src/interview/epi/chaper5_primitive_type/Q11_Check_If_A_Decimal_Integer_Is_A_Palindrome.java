package interview.epi.chaper5_primitive_type;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import org.junit.Test;

public class Q11_Check_If_A_Decimal_Integer_Is_A_Palindrome {

	static String methodName = "isPalindrome";
	static Class<?> c = Q11_Check_If_A_Decimal_Integer_Is_A_Palindrome.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Time: O(n) = n, n is the length of x in decimal,
	 * e.g. length(145) = log10(145) ~= 3.
	 * Reverse the integer, and see if equals.
	 */
	public static boolean isPalindrome(int x) {
		if (x < 0)
			return false;
		int reversed = 0;
		int original = x;
		while (x != 0) {
			reversed = reversed * 10 + x % 10;
			x /= 10;
		}
		return original == reversed;
	}

	/**
	 * Time: O(n) = n/2, n is the length of x in decimal.
	 * If we know the length of digits of x in decimal, then we can start from
	 * left and right towards middle.
	 * The length(x) = log10(x) + 1
	 */
	public static boolean isPalindrome_Improved(int x) {
		if (x < 0)
			return false;
		final int LenOfDigits = (int) Math.log10(x) + 1;
		int fromLeft = x; // copy of x, retrieve bit start from left
		int fromRight = x; // copy of x, retrieve bit start from right
		int rightBase = (int) Math.pow(10, LenOfDigits - 1);
		for (int i = 0; i < LenOfDigits / 2; i++) {
			if (fromRight / rightBase != fromLeft % 10)
				return false;
			fromRight %= rightBase; // delete the highest bit (rightmost)
			fromLeft /= 10; // delete the lowest bit (leftmost)
			rightBase /= 10;
		}
		return true;
	}

	/****************** Unit Test ******************/

	public boolean invokeMethod(Method m, int x) {
		try {
			Object res = m.invoke(null, x);
			return (boolean) res;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static boolean checkAns(int x) {
		String s = String.valueOf(x);
		for (int i = 0, j = s.length() - 1; i < j; ++i, --j) {
			if (s.charAt(i) != s.charAt(j)) {
				return false;
			}
		}
		return true;
	}

	@Test
	public void test0() {
		Random r = new Random();
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			for (int times = 0; times < 1000; ++times) {
				int x = r.nextInt(99999 * 2 + 1) - 99999;
				assertTrue(checkAns(x) == invokeMethod(m, x));
			}
		}
	}

	@Test
	public void test1() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int x = 0;
			boolean res = invokeMethod(m, x);
			assertTrue(m.getName() + " - Wrong:" + res, res);
		}
	}

	@Test
	public void test2() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int x = 11;
			boolean res = invokeMethod(m, x);
			assertTrue(m.getName() + " - Wrong:" + res, res);
		}
	}

	@Test
	public void test3() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int x = 333;
			boolean res = invokeMethod(m, x);
			assertTrue(m.getName() + " - Wrong:" + res, res);
		}
	}

	@Test
	public void test4() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int x = 4114;
			boolean res = invokeMethod(m, x);
			assertTrue(m.getName() + " - Wrong:" + res, res);
		}
	}

	@Test
	public void test5() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int x = 543212345;
			boolean res = invokeMethod(m, x);
			assertTrue(m.getName() + " - Wrong:" + res, res);
		}
	}

	@Test
	public void test6() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int x = -1;
			boolean res = invokeMethod(m, x);
			assertTrue(m.getName() + " - Wrong:" + res, !res);
		}
	}

	@Test
	public void test7() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int x = -11;
			boolean res = invokeMethod(m, x);
			assertTrue(m.getName() + " - Wrong:" + res, !res);
		}
	}

	@Test
	public void test8() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int x = 122;
			boolean res = invokeMethod(m, x);
			assertTrue(m.getName() + " - Wrong:" + res, !res);
		}
	}

	@Test
	public void test9() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int x = 2147383647;
			boolean res = invokeMethod(m, x);
			assertTrue(m.getName() + " - Wrong:" + res, !res);
		}
	}
}
