package interview.epi.chapter17_dynamic_programming;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.Comparator;
import java.util.Random;

import org.junit.Test;

/**
 * Given a circular array A, compute its maximum subarray sum in O(n) time,
 * where n is the length of A. Can you devise an algorithm that takes O(n) time
 * and O(1) space.
 * 
 * @author yazhoucao
 * 
 */
public class Q11_Compute_The_Maximum_Subarray_Sum_In_A_Circular_Array {

	static Class<?> c = Q11_Compute_The_Maximum_Subarray_Sum_In_A_Circular_Array.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * The max sum of circular subarray is either the normal(traverse start from
	 * 0 to n-1) max sum or it is the total - normal min sum
	 */
	public int maxSubarraySumInCircular(Integer[] A) {
		int total = 0;
		for (int a : A)
			total += a;

		int maxSum = maxSubarraySum(A, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.intValue() - o2.intValue();
			}
		});
		int minSum = maxSubarraySum(A, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2.intValue() - o1.intValue();
			}
		});
		return Math.max(maxSum, total - minSum);
	}

	private int maxSubarraySum(Integer[] A, Comparator<Integer> cmp) {
		Integer max = A[0], sum = A[0];
		for (int i = 1; i < A.length; i++) {
			sum = cmp.compare(sum, 0) < 0 ? A[i] : sum + A[i];
			max = cmp.compare(sum, max) > 0 ? sum : max;
		}
		return max;
	}

	/**
	 * Brute force, O(n^2)
	 */
	private static int checkAns(Integer[] A) {
		int ans = 0;
		for (int start = 0; start < A.length; ++start) {
			int sum = 0;
			for (int len = 0; len < A.length; ++len) {
				sum += A[(start + len) % A.length];
				ans = Math.max(ans, sum);
			}
		}
		System.out.println("correct answer = " + ans);
		return ans;
	}

	@Test
	public void simpleTest() {
		Integer[] A = { 904, 40, 523, 12, -335, -385, -124, 481, -31 };
		int res = maxSubarraySumInCircular(A);
		assertTrue("wrong maximum sum = " + res, res == checkAns(A)); // 1929
	}

	@Test
	public void randomTest() {
		Random r = new Random();
		for (int times = 0; times < 100; ++times) {
			int n = r.nextInt(10000) + 1;
			Integer[] A = new Integer[n];
			for (int i = 0; i < n; i++)
				A[i] = r.nextInt(20001) - 10000;

			int res = maxSubarraySumInCircular(A);
			// System.out.println(A);
			assertTrue("wrong maximum sum = " + res, res == checkAns(A));
		}
	}
}