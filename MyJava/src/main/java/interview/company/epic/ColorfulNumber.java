package interview.company.epic;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

/**
 * Colorful Number:
 * A number can be broken into different sub-sequence parts. Suppose, a number
 * 3245 can be broken into parts like 3 2 4 5 32 24 45 324 245. And this number
 * is a colorful number, since product of every digit of a sub-sequence are
 * different. That is, 3 2 4 5 (3*2)=6 (2*4)=8 (4*5)=20 (3*2*4)= 24 (2*4*5)= 40
 * But 326 is not a colorful number as it generates 3 2 6 (3*2)=6 (2*6)=12.
 * You have to write a function that tells if the given number is a colorful
 * number or not.
 * 
 * @author yazhoucao
 * 
 */
public class ColorfulNumber {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(ColorfulNumber.class);
	}

	/**
	 * Intuitive solution: calculate every subsequence and its product,
	 * check if already exists using hashset.
	 * 
	 * Time: O(n^2)~O(n^3), n is the number of digits; Space: O(n^2)
	 * Why?
	 * Cause the time equals to:
	 * the number of subsequences * the product calculation cost,
	 * if n=5, then there are total 5+4+3+2+1 number of sequences,
	 * so the total number of subsequences is n^2.
	 */
	public boolean isColorful(int num) {
		List<Integer> digits = new ArrayList<>();
		while (num > 0) {
			digits.add(num % 10);
			num /= 10;
		}

		Set<Integer> products = new HashSet<Integer>(digits);
		// different lengths of subsequences
		for (int length = 2; length <= digits.size(); length++) {
			// different subsequences of the same length
			for (int seqStart = 0; seqStart <= digits.size() - length; seqStart++) {
				int product = 1; // calculate its product
				for (int i = seqStart; i < seqStart + length; i++)
					product *= digits.get(i);
				if (products.contains(product))
					return false;
				else
					products.add(product);
			}
		}
		return true;
	}

	@Test
	public void test1() {
		int num = 3245;
		assertTrue(isColorful(num));
	}

	@Test
	public void test2() {
		int num = 326;
		assertTrue(!isColorful(num));
	}

	@Test
	public void test3() {
		int num = 9;
		assertTrue(isColorful(num));
	}

	@Test
	public void test4() {
		int num = 11;
		assertTrue(!isColorful(num));
	}

	@Test
	public void test5() {
		int num = 12;
		assertTrue(!isColorful(num));
	}

	@Test
	public void test6() {
		int num = 6789;
		assertTrue(isColorful(num));
	}
}
