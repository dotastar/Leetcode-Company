package interview.epi.chaper5_primitive_type;

import static org.junit.Assert.assertTrue;
import interview.epi.AutoTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Define the weight of a nonnegative integer x to be the number of bits that
 * are set to 1 in its binary representation. For example, since 92 in base-2
 * equals (1011100), the weight of 92 is 4.
 * 
 * Problem 5.4: Write a function which takes as input an unsigned 64-bit integer
 * x and returns y!=x such that y has the same weight as x, and the difference
 * of x and y is as small as possible. You can assume x is not 0, or all 1s,
 * i.e., 2^64-1.
 * 
 * @author yazhoucao
 * 
 */
public class Q4_Find_A_Closest_Integer_With_The_Same_Weight {

	private final static String methodName = "closestIntegerSameBits";
	private final static Class<?> c = Q4_Find_A_Closest_Integer_With_The_Same_Weight.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Time: O(n), n is the width of long.
	 * Idea: swap the first two consecutive bits that differ.
	 * This approach works because we want to change bits that are as far to the
	 * right as possible.
	 */
	public static long closestIntegerSameBits(long x) {
		for (int i = 0; i < 63; i++) {
			if (((x >> i) & 1) != ((x >> i + 1) & 1)) {
				// ith bit != (i+1)th bit, swap
				return x ^ ((1L << i) | (1L << (i + 1)));
			}
		}
		throw new RuntimeException("All bits are 1 or 0!");
	}

	/****************** Unit Test ******************/

	public long invokeMethod(Method m, long x) {
		try {
			Object res = m.invoke(null, x);
			return (long) res;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	@Test
	public void test1() {
		long x = 8; // 000...1000
		for (Method m : AutoTestUtils.findMethod(methodName, c))
			assertTrue(invokeMethod(m, x) == 4); // 0100
	}

	@Test
	public void test2() {
		long x = 10; // 000...1010
		for (Method m : AutoTestUtils.findMethod(methodName, c))
			assertTrue(invokeMethod(m, x) == 9); // 1001
	}

	@Test
	public void test3() {
		long x = 14; // 000...1110
		for (Method m : AutoTestUtils.findMethod(methodName, c))
			assertTrue(invokeMethod(m, x) == 13); // 1101
	}

	@Test
	public void test4() {
		long x = 7; // 000...0111
		for (Method m : AutoTestUtils.findMethod(methodName, c))
			assertTrue(invokeMethod(m, x) == 11); // 1011
	}

	@Test
	public void test5() {
		long x = Long.MAX_VALUE - 1; // 111...1110
		for (Method m : AutoTestUtils.findMethod(methodName, c))
			assertTrue(invokeMethod(m, x) == Long.MAX_VALUE - 2);
	}

}
