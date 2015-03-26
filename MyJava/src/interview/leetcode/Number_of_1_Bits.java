package interview.leetcode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * 
 * Write a function that takes an unsigned integer and returns the number of ’1'
 * bits it has (also known as the Hamming weight).
 * 
 * For example, the 32-bit integer ’11' has binary representation
 * 00000000000000000000000000001011, so the function should return 3.
 * 
 * @author yazhoucao
 * 
 */
public class Number_of_1_Bits {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Number_of_1_Bits.class);
	}

	// you need to treat n as an unsigned value
	public int hammingWeight(int n) {
		int count = n < 0 ? 1 : 0;
		n &= 0x7fffffff;
		while (n > 0) {
			count++;
			n &= n - 1;
		}
		return count;
	}

	@Test
	public void test1() {
		int n = 0;
		int res = hammingWeight(n);
		int ans = 0;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test2() {
		int n = 1;
		int res = hammingWeight(n);
		int ans = 1;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test3() {
		int n = 0b010100010;
		int res = hammingWeight(n);
		int ans = 3;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test4() {
		int n = 0b10000000000000000000000000000000; // Same as Integer.MIN_VALUE
		int res = hammingWeight(n);
		int ans = 1;
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test5() {
		int n = Integer.MAX_VALUE;
		int res = hammingWeight(n);
		int ans = 31;
		assertTrue("Wrong: " + res, res == ans);
	}
}
