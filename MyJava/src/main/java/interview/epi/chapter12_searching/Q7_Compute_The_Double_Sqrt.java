package interview.epi.chapter12_searching;

import interview.AutoTestUtils;

/**
 * Implement a function which takes as input a floating point variable x and
 * returns sqrt(x).
 * 
 * @author yazhoucao
 * 
 */
public class Q7_Compute_The_Double_Sqrt {

	static Class<?> c = Q7_Compute_The_Double_Sqrt.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	private static enum Ordering {
		SMALLER, EQUAL, LARGER
	}

	/**
	 * The precision is a problem when multiply double, solution is to define a
	 * compare method to ensure the precision.
	 */
	public double sqrt(double x) {
		// Decides the search range according to x's value relative to 1.0.
		double left, right;
		if (x < 1.0) {
			left = x;
			right = 1.0;
		} else { // x >= 1.0.
			left = 1.0;
			right = x;
		}
		// Keeps searching if left < right.
		while (compare(left, right) == Ordering.SMALLER) {
			double mid = left + 0.5 * (right - left);
			double midSquared = mid * mid;
			if (compare(midSquared, x) == Ordering.EQUAL) {
				return mid;
			} else if (compare(midSquared, x) == Ordering.LARGER) {
				right = mid;
			} else {
				left = mid;
			}
		}
		return left;
	}

	/**
	 * If -EPSILON <= diff <= EPSILON, then it is equal,
	 * else if diff > EPSILON, it is larger,
	 * else diff < -EPSILON, which is smaller.
	 */
	private Ordering compare(double a, double b) {
		final double EPSILON = 0.00001;
		// Uses normalization for precision problem.
		double diff = (a - b) / b; // this is the key
		return diff < -EPSILON ? Ordering.SMALLER
				: (diff > EPSILON ? Ordering.LARGER : Ordering.EQUAL);
	}
}
