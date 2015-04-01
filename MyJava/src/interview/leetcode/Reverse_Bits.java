package interview.leetcode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * 
 * Reverse bits of a given 32 bits unsigned integer.
 * 
 * For example, given input 43261596 (represented in binary as
 * 00000010100101000001111010011100), return 964176192 (represented in binary as
 * 00111001011110000010100101000000).
 * 
 * Follow up:
 * If this function is called many times, how would you optimize it?
 * 
 * @author yazhoucao
 * 
 */
public class Reverse_Bits {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Reverse_Bits.class);
	}

	public int reverseBits(int n) {
		int res = 0;
		for (int i = 0; i < 32; i++) {
			res <<= 1;
			res |= (n & 1);
			System.out.println(res + ", " + n);
			n >>>= 1;
		}
		return res;
	}

	@Test
	public void test1() {
		int n = 1;
		int ans = Integer.MIN_VALUE;
		int res = reverseBits(n);
		assertTrue("Wrong: " + res, res == ans);
	}

	@Test
	public void test2() {
		int n = Integer.MIN_VALUE;
		int ans = 1;
		int res = reverseBits(n);
		assertTrue("Wrong: " + res, res == ans);
	}
}
