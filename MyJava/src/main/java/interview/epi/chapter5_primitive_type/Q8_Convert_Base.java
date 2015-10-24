package interview.epi.chapter5_primitive_type;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Problem 5.8
 * Write a function that performs base conversion. Specifically, the input is an
 * integer base b1, a string s, representing an integer x in base b1, and
 * another integer base b2; the output is the string representing the integer x
 * in base b2. Assume 2<=b1, b2<=16. Use "A" to represent 10, "B" for 11, ...,
 * and "F" for 15.
 * 
 * @author yazhoucao
 * 
 */
public class Q8_Convert_Base {

	static String methodName = "convertBase";
	static Class<?> c = Q8_Convert_Base.class;

	public static void main(String[] args) {
		// int a = 0xFFF;
		// System.out.println(Integer.toOctalString(a));
		AutoTestUtils.runTestClassAndPrint(c);
		// System.out.println(convertBase("27", 10, 16));
	}

	private final static String digitsMap = "0123456789ABCDEF";

	private static int convert(char c) {
		if (c >= '0' && c <= '9')
			return c - '0';
		else if (c >= 'A' && c <= 'F')
			return 10 + (c - 'A');
		else
			throw new RuntimeException("Unkown character: " + c);
	}

	/**
	 * Time: O(n), n = length(s)
	 * Convert to decimal first, then convert from decimal to base2.
	 */
	public static String convertBase(String s, int b1, int b2) {
		if (b1 == b2)
			return s;
		int decimal = 0; // convert from b1 to decimal
		for (int i = 0; i < s.length(); i++) {
			int digit = convert(s.charAt(s.length() - 1 - i));
			decimal += digit * Math.pow(b1, i);
		}

		StringBuilder sb = new StringBuilder();
		do { // convert from decimal to b2
			int digit = decimal % b2;
			sb.append(digitsMap.charAt(digit));
			decimal /= b2;
		} while (decimal != 0);

		return sb.reverse().toString();
	}

	/**
	 * A little improved by avoiding unnecessary conversion.
	 */
	public static String convertBase_Improved(String s, int b1, int b2) {
		if (b1 == b2)
			return s;
		int decimal = 0; // convert to decimal
		if (b1 == 10) // no need to convert
			decimal = Integer.valueOf(s);
		else {
			for (int i = 0; i < s.length(); i++) {
				int digit = convert(s.charAt(s.length() - 1 - i));
				decimal += digit * Math.pow(b1, i);
			}
		}

		if (b2 == 10) // no need to convert
			return String.valueOf(decimal);

		StringBuilder sb = new StringBuilder();
		do {
			int digit = decimal % b2;
			sb.append(digitsMap.charAt(digit));
			decimal /= b2;
		} while (decimal != 0);
		return sb.reverse().toString();
	}

	/****************** Unit Test ******************/

	public String invokeMethod(Method m, String s, int b1, int b2) {
		try {
			Object[] args = new Object[] { s, b1, b2 };
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
			String s = "10101"; // 21
			int b1 = 2;
			int b2 = 10;
			String res = invokeMethod(m, s, b1, b2);
			assertTrue(m.getName() + " - Wrong:" + res, res.equals("21"));
		}
	}

	@Test
	public void test2() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String s = "10101"; // 21
			int b1 = 2;
			int b2 = 16;
			String res = invokeMethod(m, s, b1, b2);
			assertTrue(m.getName() + " - Wrong:" + res, res.equals("15"));
		}
	}

	@Test
	public void test3() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String s = "10101"; // 21
			int b1 = 2;
			int b2 = 8;
			String res = invokeMethod(m, s, b1, b2);
			assertTrue(m.getName() + " - Wrong:" + res, res.equals("25"));
		}
	}

	@Test
	public void test4() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String s = "FFF"; // 4095
			int b1 = 16;
			int b2 = 2;
			String res = invokeMethod(m, s, b1, b2);
			assertTrue(m.getName() + " - Wrong:" + res,
					res.equals("111111111111"));
		}
	}

	@Test
	public void test5() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String s = "FFF"; // 4095
			int b1 = 16;
			int b2 = 8;
			String res = invokeMethod(m, s, b1, b2);
			assertTrue(m.getName() + " - Wrong:" + res, res.equals("7777"));
		}
	}

	@Test
	public void test6() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String s = "FFF"; // 4095
			int b1 = 16;
			int b2 = 10;
			String res = invokeMethod(m, s, b1, b2);
			assertTrue(m.getName() + " - Wrong:" + res, res.equals("4095"));
		}
	}

	@Test
	public void test7() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String s = "FFF"; // 4095
			int b1 = 16;
			int b2 = 16;
			String res = invokeMethod(m, s, b1, b2);
			assertTrue(m.getName() + " - Wrong:" + res, res.equals("FFF"));
		}
	}

	@Test
	public void test8() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String s = "3"; // 3
			int b1 = 5;
			int b2 = 9;
			String res = invokeMethod(m, s, b1, b2);
			assertTrue(m.getName() + " - Wrong:" + res, res.equals("3"));
		}
	}

}
