package interview.epi.chaper5_primitive_type;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import interview.epi.AutoTestUtils;

import org.junit.Test;

/**
 * There are a number of ways in which bit manipulations can be accelerated.
 * For example:
 * the expression x&(x-1) equals x with the lowest set bit cleared, and
 * x & ~(x-1) extracts the lowest set bit of x(all other bits are cleared).
 * 
 * Problem 5.2:
 * A 64-bit integer can be viewed as an array of 64 bits, with the bit at index
 * 0 corresponding to the least significant bit(LSB), and the bit at index 63
 * corresponding to the most significant bit(MSB). Implement code that takes as
 * input a 64-bit integer and swaps the bits in that integer at indices i and j.
 * 
 * @author yazhoucao
 * 
 */
public class Q2_Swap_Bits {
	String methodName = "swapBits";
	Class<?> c = Q2_Swap_Bits.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Q2_Swap_Bits.class);
	}

	/**
	 * Time: O(1), constant
	 * 
	 * Key:
	 * 1.It is not always need to swap, when does it need to swap?
	 * 2.To remain the bits: x^0, to reverse the bit on i: x^(1<<i).
	 */
	public static long swapBits(long x, int i, int j) {
		// Extract the i-th and j-th bits, and see if they differ.
		if (((x >> i) & 1) != ((x >> j) & 1))
			// Swap i-th and j-th bits by flipping them.
			// Select and flip bits by using a bit mask and XOR.
			x ^= (1L << i) | (1L << j);

		return x;
	}

	/****************** Unit Test ******************/

	public long invokeMethod(Method m, long x, int i, int j) {
		try {
			Object[] args = new Object[] { x, i, j };
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
	public void test1() {
		long x = 9; // no differs, all 1
		for (Method m : AutoTestUtils.findMethod(methodName, c))
			assertTrue(invokeMethod(m, x, 0, 3) == 9);
	}

	@Test
	public void test2() {
		long x = 0; // no differs, all 0
		for (Method m : AutoTestUtils.findMethod(methodName, c))
			assertTrue(invokeMethod(m, x, 0, 64) == 0);
	}

	@Test
	public void test3() {
		long x = 1; // swap 1 and 0
		for (Method m : AutoTestUtils.findMethod(methodName, c))
			assertTrue(invokeMethod(m, x, 0, 64) == (1 << 64));
	}

	@Test
	public void test4() {
		long x = 8; // swap 0 and 1
		for (Method m : AutoTestUtils.findMethod(methodName, c))
			assertTrue(invokeMethod(m, x, 0, 3) == 1);
	}

	@Test
	public void test5() {
		// random number 1
		for (Method m : AutoTestUtils.findMethod(methodName, c))
			assertTrue(invokeMethod(m, 47, 1, 4) == 61);

	}

	@Test
	public void test6() {
		// random number 2
		for (Method m : AutoTestUtils.findMethod(methodName, c))
			assertTrue(invokeMethod(m, 28, 0, 2) == 25);
	}
}
