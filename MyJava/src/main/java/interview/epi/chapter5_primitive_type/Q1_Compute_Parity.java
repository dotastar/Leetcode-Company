package interview.epi.chapter5_primitive_type;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * The parity of a sequence of bits is 1 if the number of 1s in the sequence is
 * odd; otherwise, it is 0. Parity checks are used to detect single bit errors
 * in data storage and communication. It is fairly straightforward to write code
 * that computes the parity of a single 64-bit nonnegative integer.
 * 
 * Problem 5.1: How would you go about computing the parity of a very large
 * number of 64-bit nonnegative integers?
 * 
 * @author yazhoucao
 * 
 */
public class Q1_Compute_Parity {
	String methodName = "parity";
	Class<?> c = Q1_Compute_Parity.class;

	public static void main(String[] args) {
		int x = 12; // 1100;
		System.out.println(~x);
		System.out.println((~(x - 1)));
		System.out.println(x & (~(x - 1)));
		AutoTestUtils.runTestClassAndPrint(Q1_Compute_Parity.class);
	}

	/**
	 * Naive solution
	 * Time: O(n), n bits.
	 */
	public static short parity(int data) {
		short count = 0;
		while (data != 0) {
			if ((data & 1) == 1)
				count++;
			data >>>= 1; // unsigned shift
		}
		return (short) (count % 2);
	}

	/**
	 * Time: O(n), n bits
	 * Directly calculate the result by using xor.
	 * Trick: xor - 1&0, 0&1 = 1; 0&0, 1&1 = 0;
	 * It's useful to calculate odd or even number.
	 * Don't have to do mod and type conversion.
	 */
	public static short parity_improved(int data) {
		short res = 0;
		while (data != 0) {
			res ^= data & 1; // xor is the key
			data >>>= 1;
		}
		return res;
	}

	/**
	 * Time: O(k), k is the number of 1 in data
	 * 
	 * Trick: data & (data-1) can effectively drop the lowest bit 1
	 */
	public static short parity_improved2(int data) {
		short res = 0;
		while (data != 0) {
			res ^= 1; // just a counter
			data &= data - 1; // key: drops the lowest bit 1 of data
		}
		return res;
	}

	/**
	 * Precompute and cache all the numbers, we can compute the parity of a
	 * 64-bit integer by grouping its bits into four nonoverlapping 16 bit
	 * words, computing the parity of each subset, and XORing these four
	 * results.
	 */
	private static short[] precomputedParity;

	static {
		precomputedParity = new short[1 << 16];
		for (int i = 0; i < 1 << 16; i++)
			precomputedParity[i] = parity_improved2(i);
	}

	/**
	 * Because we just calculate the number of bit 1, don't differentiate the
	 * higher or lower significance, therefore we can split the number into
	 * several parts and calculate them separately and then aggregate them by
	 * XOR ^.
	 * Time: O(1)
	 */
	public static short parity_precompute(int data) {
		final int WORD_SIZE = 16;
		final int BIT_MASK = 0xFFFF;
		return (short) (precomputedParity[(data >> WORD_SIZE) & BIT_MASK] ^ precomputedParity[data
				& BIT_MASK]);
	}

	/****************** Unit Test ******************/

	public short invokeMethod(Method m, int data) {
		try {
			Object res = m.invoke(null, data);
			return (short) res;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	@Test
	public void test_1() {
		int data = 85;		// 1010101
		for (Method m : AutoTestUtils.findMethod(methodName, c))
			assertTrue(invokeMethod(m, data) == 0);
	}

	@Test
	public void test_2() {
		int data = 0;
		for (Method m : AutoTestUtils.findMethod(methodName, c))
			assertTrue(invokeMethod(m, data) == 0);
	}

	@Test
	public void test_3() {
		int data = 12; // 1100
		for (Method m : AutoTestUtils.findMethod(methodName, c))
			assertTrue(invokeMethod(m, data) == 0);
	}

	@Test
	public void test_4() {
		int data = Integer.MAX_VALUE;
		for (Method m : AutoTestUtils.findMethod(methodName, c))
			assertTrue(invokeMethod(m, data) == 1);
	}

	@Test
	public void test_5() {
		int data = Integer.MAX_VALUE - 1;
		for (Method m : AutoTestUtils.findMethod(methodName, c))
			assertTrue(invokeMethod(m, data) == 0);
	}
}
