package interview.company.epic;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Find the seed of a number.
 * E.g : 1716 = 143*1*4*3 =1716 so 143 is the seed of 1716. find all possible
 * seed for a given number.
 * 
 * @author yazhoucao
 * 
 */
public class FindSeedNumber {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(FindSeedNumber.class);
	}

	/**
	 * Brute force, Time: O(sqrt(n))
	 * Try every number that is smaller than or equals to sqrt(n).
	 */
	public List<Integer> seedNumb(int n) {
		List<Integer> res = new ArrayList<>();
		for (int seed = 1; seed * 1 < n; seed++) {
			if (n % seed == 0) {
				int product = digitsProduct(seed);
				if (product == n)
					res.add(seed);
			}
		}
		return res;
	}

	private int digitsProduct(int num) {
		int product = num;
		while (num != 0) {
			product *= num % 10; // multiply the last digit
			num /= 10; // remove the digit
		}
		return product;
	}

	@Test
	public void test1() {
		List<Integer> res = seedNumb(1716);
		assertTrue(res.size() == 1);
		assertTrue(res.get(0) == 143);
	}

	@Test
	public void test2() {
		List<Integer> res = seedNumb(738);
		assertTrue(res.size() == 1);
		assertTrue(res.get(0) == 123);
	}

	@Test
	public void test3() {
		List<Integer> res = seedNumb(4977);
		assertTrue(res.size() == 2);
		assertTrue(res.get(0) == 79);
		assertTrue(res.get(1) == 711);
	}
}
