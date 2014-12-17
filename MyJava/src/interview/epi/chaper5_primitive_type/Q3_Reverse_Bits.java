package interview.epi.chaper5_primitive_type;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Here is a bit fiddling problem that is concerned with restructuring.
 * 
 * Problem 5.3: Write a function that takes a 64-bit word x and returns a 64-bit
 * word consisting of the bits of x in reverse order.
 * 
 * @author yazhoucao
 * 
 */
public class Q3_Reverse_Bits {
	String methodName = "reverseBits";
	Class<?> c = Q3_Reverse_Bits.class;
	private final static int SIZE = 64;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Q3_Reverse_Bits.class);
	}

	/**
	 * Naive solution, Time: O(n) = n, n bits
	 */
	public static long reverseBits(long x, int n) {
		long res = 0;
		for (int i = 0; i < n; i++) {
			res <<= 1;
			res |= x & 1;
			x >>>= 1;
		}
		return res;
	}

	/**
	 * Swap bit of head and tail, Time: O(n) = n/2, n bits
	 */
	public static long reverseBits_Improved(long x, int n) {
		for (int i = 0, j = n - 1; i < j; i++, j--)
			x = Q2_Swap_Bits.swapBits(x, i, j);
		return x;
	}

	/****************** Unit Test ******************/

	public long invokeMethod(Method m, long data, int n) {
		try {
			Object[] args = new Object[] { data, n };
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
		long x = 0; // 0
		for (Method m : AutoTestUtils.findMethod(methodName, c))
			assertTrue(invokeMethod(m, x, SIZE) == 0);
	}

	@Test
	public void test2() {
		long x = 1; // 1
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			long res = invokeMethod(m, x, SIZE);
			assertTrue("result:" + res, res == (1L << (SIZE - 1)));
		}

	}

	@Test
	public void test3() {
		long x = Long.MIN_VALUE; // 100000...
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			long res = invokeMethod(m, x, SIZE);
			assertTrue("result:" + res, res == 1);
		}
	}

	@Test
	public void test4() {
		long x = 8; // 1000
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			long res = invokeMethod(m, x, SIZE);
			assertTrue("result:" + res, res == (1L << (SIZE - 4)));
		}
	}

}
