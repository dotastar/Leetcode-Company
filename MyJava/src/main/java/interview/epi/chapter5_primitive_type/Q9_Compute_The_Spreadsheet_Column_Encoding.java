package interview.epi.chapter5_primitive_type;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Widely deployed spreadsheets use an alphabetical encoding of the successive
 * columns. Specifically, consecutive columns are identified by "A", "B", "C",
 * ..., "X", "Y", "Z", "AA", "AB", ..., "ZZ", "AAA"...
 * 
 * Problem 5.9
 * Implement a function that converts Excel column ids to the corresponding
 * integer, with "A" corresponding to 1. How would you test your code?
 * 
 * @author yazhoucao
 * 
 */
public class Q9_Compute_The_Spreadsheet_Column_Encoding {

	static String methodName = "ssDecodeColID";
	static Class<?> c = Q9_Compute_The_Spreadsheet_Column_Encoding.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Time: O(n), n = length(col)
	 * Conversion start from lowest bit to highest bit.
	 */
	public static int ssDecodeColID_LowToHigh(final String col) {
		int res = 0;
		int len = col.length();
		for (int i = 0; i < len; i++) {
			res += (col.charAt(len - 1 - i) - 'A' + 1) * Math.pow(26, i);
		}
		return res;
	}

	/**
	 * Conversion start from highest bit to lowest bit.
	 * A little better than above solution cause it avoid power() calculation.
	 */
	public static int ssDecodeColID_HighToLow(final String col) {
		int res = 0;
		for (char c : col.toCharArray()) {
			res = res * 26 + c - 'A' + 1;
		}
		return res;
	}

	/****************** Unit Test ******************/

	public int invokeMethod(Method m, String col) {
		try {
			Object res = m.invoke(null, col);
			return (int) res;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Test
	public void test1() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String col = "A";
			int res = invokeMethod(m, col);
			assertTrue(m.getName() + " - Wrong:" + res, res == 1);
		}
	}

	@Test
	public void test2() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String col = "AA";
			int res = invokeMethod(m, col);
			assertTrue(m.getName() + " - Wrong:" + res, res == 27);
		}
	}

	@Test
	public void test3() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String col = "AAA";
			int res = invokeMethod(m, col);
			assertTrue(m.getName() + " - Wrong:" + res, res == 703);
		}
	}

	@Test
	public void test4() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String col = "AZ";
			int res = invokeMethod(m, col);
			assertTrue(m.getName() + " - Wrong:" + res, res == 52);
		}
	}

	@Test
	public void test5() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String col = "BB";
			int res = invokeMethod(m, col);
			assertTrue(m.getName() + " - Wrong:" + res, res == 54);
		}
	}

}
