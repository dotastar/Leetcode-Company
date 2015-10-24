package interview.epi.chapter6_array;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import interview.AutoTestUtils;

/**
 * See leetcode Multiply Strings
 * 
 * @author yazhoucao
 * 
 */
public class Q3_Multiply_Two_BigIntegers {
	static String methodName = "multiply";
	static Class<?> c = Q3_Multiply_Two_BigIntegers.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * O(len1*len2)
	 */
	public static String multiply(String num1, String num2) {
		num1 = new StringBuilder(num1).reverse().toString();
		num2 = new StringBuilder(num2).reverse().toString();
		int[] res = new int[num1.length() + num2.length()];
		for (int i = 0; i < num1.length(); i++) {
			for (int j = 0; j < num2.length(); j++) {
				res[i + j] += (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
				res[i + j + 1] += res[i + j] / 10; // carry
				res[i + j] %= 10; // adjust remaining
			}
		}

		StringBuilder sb = new StringBuilder();
		boolean is0valid = false; // for eliminating the first several 0
		for (int i = res.length - 1; i >= 0; i--) {
			if (res[i] != 0) {
				sb.append(res[i]);
				is0valid = true;
			} else if (is0valid | i == 0)
				sb.append(0);
		}
		return sb.toString();
	}

	/****************** Unit Test ******************/

	public String invokeMethod(Method m, String num1, String num2) {
		try {
			Object[] args = new Object[] { num1, num2 };
			Object res = m.invoke(null, args);
			return (String) res;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return "";
		}
	}

	@Test
	public void test1() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String num1 = "11";
			String num2 = "1001";
			String res = invokeMethod(m, num1, num2);
			assertTrue(m.getName() + ": " + res, res.equals("11011"));
		}
	}

	@Test
	public void test2() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String num1 = "0";
			String num2 = "1001";
			String res = invokeMethod(m, num1, num2);
			assertTrue(m.getName() + ": " + res, res.equals("0"));
		}
	}

	@Test
	public void test3() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String num1 = "7";
			String num2 = "7";
			String res = invokeMethod(m, num1, num2);
			assertTrue(m.getName() + ": " + res, res.equals("49"));
		}
	}

	@Test
	public void test4() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String num1 = "99";
			String num2 = "1234";
			String res = invokeMethod(m, num1, num2);
			assertTrue(m.getName() + ": " + res, res.equals("122166"));
		}
	}
}
