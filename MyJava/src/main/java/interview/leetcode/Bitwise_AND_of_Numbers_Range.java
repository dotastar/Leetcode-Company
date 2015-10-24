package interview.leetcode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Given a range [m, n] where 0 <= m <= n <= 2147483647, return the bitwise AND
 * of all numbers in this range, inclusive.
 * 
 * For example, given the range [5, 7], you should return 4.
 * 
 * @author yazhoucao
 *
 */
public class Bitwise_AND_of_Numbers_Range {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Bitwise_AND_of_Numbers_Range.class);
	}

	/**
	 * Naive solution, bitwise And every number from m to n, time: O(n - m)
	 */

	/**
	 * Time: O(1)
	 * 
	 * let's assume that m < n, so there must be a bit that m = xxxx0yyyyy and n
	 * = xxxx1zzzzz, where the x part are the same and the y/z part can be
	 * anything.
	 * 
	 * The goal of this problem is to calculate all the bitwise and of the
	 * numbers between m and n, which must include k = xxxx100000, so obviously
	 * the answer must be xxxx000000.
	 */
	public int rangeBitwiseAnd(int m, int n) {
		if (m == n) {
			return m;
		}
		// The highest bit of 1 in diff is the highest changed bit.
		int diff = m ^ n;
		// Index is the index of the highest changed bit. Starting at 1.
		// All bits on the left of index must be the same in the range [m, n]
		int index = (int) (Math.log(diff) / Math.log(2)) + 1;
		// System.out.println("Diff:" + diff + ", " + Integer.toBinaryString(m) + " ^ " + Integer.toBinaryString(n) + " = " + Integer.toBinaryString(diff) + "\tindex:" + index);
		// Eliminate the changed part.
		m = m >> index;
		return m << index;
	}

	@Test
	public void test1() {
		int m = 5;
		int n = 17;
		int res = rangeBitwiseAnd(m, n);
		System.out.println(res);
		assertTrue(true);
	}

	@Test
	public void test2() {
		int m = 224;
		int n = 250;
		int res = rangeBitwiseAnd(m, n);
		System.out.println(res);
		assertTrue(true);
	}
}
