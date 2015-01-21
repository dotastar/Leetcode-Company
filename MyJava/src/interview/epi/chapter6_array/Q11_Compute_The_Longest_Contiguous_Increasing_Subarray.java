package interview.epi.chapter6_array;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;
import interview.epi.utils.Pair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import org.junit.Test;

/**
 * An array is increasing if each element is less than its succeeding element
 * except for the last element.
 * 
 * Problem 6.11
 * Implement an algorithm that takes as input an array A of n elements, and
 * returns the beginning and ending indices of a longest increasing sub-array of
 * A. For example, if A = [2, 11, 3, 5, 13, 7, 19, 17, 23], the longest
 * increasing sub-array is [3, 5, 13], and you should return (2,4).
 * 
 * @author yazhoucao
 * 
 */
public class Q11_Compute_The_Longest_Contiguous_Increasing_Subarray {
	static String methodName = "findLongestIncreasingSubarray";
	static Class<?> c = Q11_Compute_The_Longest_Contiguous_Increasing_Subarray.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Time: O(n)
	 */
	public static Pair<Integer, Integer> findLongestIncreasingSubarray(int[] A) {
		Pair<Integer, Integer> max = new Pair<Integer, Integer>(0, 0);
		int start = 0, end = 0;
		int longest = 1;

		for (int i = 1; i < A.length; i++) {
			if (A[i - 1] < A[i]) {
				end++;
			} else {
				start = i;
				end = i;
			}

			if (end - start + 1 > longest) {
				longest = end - start + 1;
				max.setFirst(start);
				max.setSecond(end);
			}
		}
		return max;
	}

	/****************** Unit Test ******************/

	@SuppressWarnings("unchecked")
	public Pair<Integer, Integer> invokeMethod(Method m, int[] A) {
		try {
			Object res = m.invoke(null, A);
			return (Pair<Integer, Integer>) res;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Test
	public void test0() {
		Random gen = new Random();
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			for (int times = 0; times < 1000; ++times) {
				int n = gen.nextInt(10000) + 1;
				int[] A = new int[n];
				for (int i = 0; i < n; ++i) {
					A[i] = gen.nextBoolean() ? -1 : 1 * gen.nextInt(n);
				}
				Pair<Integer, Integer> result = invokeMethod(m, A);
				int len = 1;
				for (int i = 1; i < A.length; ++i) {
					if (A[i] > A[i - 1]) {
						++len;
					} else {
						len = 1;
					}
					assertTrue(len <= result.getSecond() - result.getFirst()
							+ 1);
				}
			}
		}
	}

	@Test
	public void test1() {
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			int[] A = { 2, 11, 3, 5, 13, 7, 19, 17, 23 };
			Pair<Integer, Integer> res = invokeMethod(m, A);
			assertTrue(m.getName() + ": " + res,
					res.getFirst() == 2 && res.getSecond() == 4);
		}
	}

	@Test
	public void test2() {
		int[] A = { -1, -1 };
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			Pair<Integer, Integer> ans = invokeMethod(m, A);
			assertTrue(ans.getFirst() == 0 && ans.getSecond() == 0);
		}
	}

	@Test
	public void test3() {
		int[] A = { 1, 2, 3 };
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			Pair<Integer, Integer> ans = invokeMethod(m, A);
			assertTrue(ans.getFirst() == 0 && ans.getSecond() == 2);
		}

	}
}
