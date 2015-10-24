package interview.epi.chapter6_array;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Problem 6.15
 * Design an O(n) algorithm for rotating an array A of n elements to the right
 * by i positions. Use O(1) space.
 * 
 * @author yazhoucao
 * 
 */
public class Q15_Rotate_An_Array {

	static String methodName = "rotateArray";
	static Class<?> c = Q15_Rotate_An_Array.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Reversal algorithm
	 * Time: O(n), Space: O(1).
	 * Better than juggling which has more overhead, and it's more clear.
	 * The trick is to do three reverse operation. One for the entire string,
	 * one from index 0 to k-1, and lastly index k to n-1. Magically, this will
	 * yield the correct rotated array, without any extra space!
	 * 
	 * Reverse a to get a'b.
	 * Reverse b to get a'b'.
	 * Reverse all to get (a'b')' = ba.
	 * 
	 */
	public static void rotateArray_Reversal(int i, int[] A) {
		i = i % A.length;
		reverse(A, 0, i - 1);
		reverse(A, i, A.length - 1);
		reverse(A, 0, A.length - 1);
	}

	private static void reverse(int[] A, int l, int r) {
		while (l < r)
			swap(A, l++, r--);
	}

	private static void swap(int[] A, int i, int j) {
		int tmp = A[i];
		A[i] = A[j];
		A[j] = tmp;
	}

	/**
	 * Juggling algorithm
	 * Time: O(n), Space: O(1)
	 * Rotates all of the elements in an array left by the given k value.
	 * If k is negative, it rotates the elements in the array right.
	 * This method modifies the array in place, so it does not return
	 * anything.
	 */
	public static void rotateArray_Juggling(int k, int[] array) {

		int n = array.length;
		// Handle negative k values (rotate to right)
		if (k < 0)
			k = n - Math.abs(k);

		// Ensure k is in interval [0, n)
		k = ((k % n) + n) % n;

		// Perform juggling algoritm
		for (int i = 0, gcd = gcd(k, n); i < gcd; i++) {
			int temp = array[i];
			int j = i;

			while (true) {
				int p = j + k;
				if (p >= n) 
					p = p - n;
				
				if (p == i) 
					break;
				
				array[j] = array[p];
				j = p;
			}
			array[j] = temp;
		}
	}

	/**
	 * Uses Euclid's algorithm to find the greatest common divisor of
	 * two numbers.
	 */
	public static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

	/****************** Unit Test ******************/

	public void invokeMethod(Method m, int[] A, int i) {
		try {
			Object[] args = new Object[] { i, A };
			m.invoke(null, args);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test1() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int[] A = { 1, 2, 3, 4, 5 };
			int i = 5;
			invokeMethod(m, A, i);
			int[] correct = { 1, 2, 3, 4, 5 };
			assertTrue(m.getName(), arrayEquals(A, correct));
		}
	}

	@Test
	public void test2() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int[] A = { 0, 1, 2, 3, 4, 5, 6 };
			int i = 2;
			invokeMethod(m, A, i);
			int[] correct = { 2, 3, 4, 5, 6, 0, 1 };
			assertTrue(m.getName(), arrayEquals(A, correct));
		}
	}

	@Test
	public void test3() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int[] A = { 0, 1, 2, 3, 4, 5, 6 };
			int i = 16;
			invokeMethod(m, A, i);
			int[] correct = { 2, 3, 4, 5, 6, 0, 1 };
			assertTrue(m.getName(), arrayEquals(A, correct));
		}
	}

	/**
	 * Array equal
	 */
	private boolean arrayEquals(int[] A, int[] B) {
		if (A.length != B.length)
			return false;
		for (int i = 0; i < A.length; i++) {
			if (A[i] != B[i])
				return false;
		}
		return true;
	}
}
