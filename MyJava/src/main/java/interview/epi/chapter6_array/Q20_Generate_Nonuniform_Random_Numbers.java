package interview.epi.chapter6_array;

import interview.AutoTestUtils;
import interview.epi.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

/**
 * Problem 6.20
 * Given a set T of n distinct real numbers { t0, t1, t2, ..., tn-1 } and
 * probabilities p0, p1, p2, ..., pn-1, which sum up to 1. Assume that
 * t0 < t1 < ... < tn-1. Given a random number generator that produces values in
 * [0,1] uniformly, how would you generate a number in T according to the
 * specified probabilities?
 * 
 * @author yazhoucao
 * 
 */
public class Q20_Generate_Nonuniform_Random_Numbers {
	static String methodName = "nonuniformRandomNumberGeneration";
	static Class<?> c = Q20_Generate_Nonuniform_Random_Numbers.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);

		/************* Test for bsearchRange() *************/
		double[] arr2 = { 0.7537424049223119, 0.9722134043237963,
				0.9882921426813501, 0.9926719295401996, 0.9967492130347728,
				0.9983624218619668, 0.9999653545207491, 0.9999694983935403,
				0.9999711427630388, 0.9999949148096311, 0.9999982869701197, 1.0 };
		assert bsearchRange(arr2, 0.93d) == 1;

		double[] arr1 = { 1, 3, 5, 9 };
		assert bsearchRange(arr1, 0) == 0;
		assert bsearchRange(arr1, 2) == 1;
		assert bsearchRange(arr1, 10) == 3;

		double[] arr = { 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1 };
		assert bsearchRange(arr, 0.75) == 7;

	}

	/**
	 * Time: O(n), n = length(T) = length(P).
	 */
	public static double nonuniformRandomNumberGeneration(List<Double> T,
			List<Double> P) {
		assert T.size() == P.size();
		Random ran = new Random();
		double choosen = ran.nextDouble();
		double p = 0;
		for (int i = 0; i < P.size(); i++) {
			p += P.get(i);
			if (choosen < p)
				return T.get(i);
		}
		throw new RuntimeException("Probabilities of P don't sum to 1: " + p);
	}

	/**
	 * Construct an array of cumulative probability in precomputation step.
	 * Binary search the randomly generated real number sits in which range.
	 * 
	 * Precompute: O(n) time, O(n) space.
	 * 
	 * Algorithm: O(lgn) time.
	 */
	public static double nonuniformRandomNumberGeneration_improved(
			List<Double> T, List<Double> P) {
		// precomputation
		assert T.size() == P.size();
		double[] arrP = generateCumulativeArray(P);

		// binary search
		Random ran = new Random();
		double val = ran.nextDouble();
		int idx = bsearchRange(arrP, val);
		if (idx >= 0 && idx < T.size())
			return T.get(idx);
		else
			return -1;
	}

	/**
	 * Binary search a range, a range is [ a, b ).
	 */
	public static int bsearchRange(double[] A, double val) {
		if (A.length == 0)
			return -1;
		if (A.length == 1)
			return val < A[0] ? 0 : -1;
		int l = 0;
		int r = A.length - 1;
		while (l <= r) {
			int mid = l + (r - l) / 2;
			if ((val < A[mid] && mid == 0) // leftmost
					|| (val >= A[mid] && mid == A.length - 1) // rightmost
					|| (val < A[mid] && val >= A[mid - 1])) {
				return mid;
			} else if (val >= A[mid]) {
				l = mid + 1;
			} else
				r = mid - 1;

		}
		return -1;
	}

	public static double[] generateCumulativeArray(List<Double> P) {
		double[] arr = new double[P.size()];
		arr[0] = P.get(0);
		for (int i = 1; i < arr.length; i++)
			arr[i] = arr[i - 1] + P.get(i);
		return arr;
	}

	/****************** Unit Test ******************/

	public Double invokeMethod(Method m, List<Double> T, List<Double> P) {
		try {
			Object[] args = new Object[] { T, P };
			Object res = m.invoke(null, args);
			return (Double) res;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return -1d;
		}
	}

	@Test
	public void test1() {
		Random gen = new Random();
		int n = gen.nextInt(50) + 1;
		List<Double> T = new ArrayList<>(n);
		Utils.iota(T, n, 0);
		List<Double> P = new ArrayList<>();
		double fullProb = 1.0;
		for (int i = 0; i < n - 1; ++i) {
			double pi = gen.nextDouble() * fullProb;
			P.add(pi);
			fullProb -= pi;
		}
		P.add(fullProb);

		Utils.simplePrint(T);
		System.out.println();

		Utils.simplePrint(P);
		System.out.println();
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			// System.out.println(nonuniformRandomNumberGeneration(T, P));
			// Test. Perform the nonuniform random number generation for
			// n * kTimes times and calculate the distribution of each bucket.
			int kTimes = 100000;
			int[] counts = new int[n];
			for (int i = 0; i < n * kTimes; ++i) {
				double t = invokeMethod(m, T, P);
				++counts[(int) t];
			}
			for (int i = 0; i < n; ++i) {
				System.out.println((double) (counts[i]) / (n * kTimes) + " "
						+ P.get(i));
				assert Math.abs(((double) counts[i]) / (n * kTimes) - P.get(i)) < 0.01;
			}
		}
	}

}
