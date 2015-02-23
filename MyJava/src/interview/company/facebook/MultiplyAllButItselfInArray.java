package interview.company.facebook;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.Arrays;

import org.junit.Test;

/**
 * Given an array of numbers, return array of products of all other numbers (no
 * division)
 * 
 * Input : [1, 2, 3, 4, 5]
 * Output: [(2*3*4*5), (1*3*4*5), (1*2*4*5), (1*2*3*5), (1*2*3*4)]
 * = [120, 60, 40, 30, 24]
 * 
 * You must do this in O(N) without using division.
 * 
 * @author yazhoucao
 * 
 */
public class MultiplyAllButItselfInArray {

	static Class<?> c = MultiplyAllButItselfInArray.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Time: O(n), Space: O(n)
	 */
	public int[] multiply(int[] A) {
		int[] res = new int[A.length];
		int product = 1; // product of 0 to i-1
		for (int i = 0; i < A.length; i++) { // start from 0
			res[i] = product;
			product *= A[i];
		}

		product = A[A.length - 1]; // product of i to n-1
		for (int i = A.length - 2; i >= 0; i--) { // start from n-2
			res[i] *= product;
			product *= A[i];
		}
		System.out.println(Arrays.toString(res));
		return res;
	}

	@Test
	public void test1() {
		int[] A = { 1, 2, 3, 4, 5 };
		int[] ans = { 120, 60, 40, 30, 24 };
		assertTrue(Arrays.equals(multiply(A), ans));
	}

	@Test
	public void test2() {
		int[] A = { 1, 2, 3, 4, 5, 6 };
		int[] ans = { 720, 360, 240, 180, 144, 120 };
		assertTrue(Arrays.equals(multiply(A), ans));
	}

	@Test
	public void test3() {
		int[] A = { 2, 1, 6, 3, 5, 4 };
		int[] ans = { 360, 720, 120, 240, 144, 180 };
		assertTrue(Arrays.equals(multiply(A), ans));
	}

	@Test
	public void test4() {
		int[] A = { 2, 6, 3, 5, 4 };
		int[] ans = { 360, 120, 240, 144, 180 };
		assertTrue(Arrays.equals(multiply(A), ans));
	}
}
