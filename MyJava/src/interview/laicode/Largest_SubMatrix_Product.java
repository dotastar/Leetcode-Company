package interview.laicode;

import java.util.Arrays;

/**
 * Largest SubMatrix Product
 * Hard
 * DP
 * 
 * Given a matrix that contains doubles, find the submatrix with the largest
 * product.
 * 
 * Return the product of the submatrix.
 * 
 * Assumptions
 * 
 * The given double matrix is not null and has size of M * N, where M >= 1 and N
 * >= 1
 * 
 * Examples
 * 
 * { {1, -0.2, -1},
 * 
 * {1, -1.5, 1},
 * 
 * {0, 0, 1} }
 * 
 * the largest submatrix product is 1 * 1 = 1.
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Largest_SubMatrix_Product {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Time: O(m*m*n), Space: O(n)
	 * Same as largest submatrix sum
	 * Try every top and bottom rows combinations, then two borders are fixed,
	 * converted it to a max subarray product problem.
	 */
	public double largest(double[][] A) {
		int M = A.length, N = A[0].length;
		double[] cur = new double[N];
		double res = Integer.MIN_VALUE;
		for (int start = 0; start < M; start++) {
			Arrays.fill(cur, 1);
			for (int i = start; i < M; i++) {
				multiply(cur, A[i]);
				res = Math.max(res, maxSubarrayProduct(cur));
			}
		}
		return res;
	}

	private void multiply(double[] cur, double[] mul) {
		for (int i = 0; i < cur.length; i++) {
			cur[i] *= mul[i];
		}
	}

	private double maxSubarrayProduct(double[] cur) {
		if (cur.length == 0)
			return Double.MIN_VALUE;
		double max = cur[0], min = cur[0], res = cur[0];
		for (int i = 1; i < cur.length; i++) {
			double maxCopy = max;
			max = Math.max(cur[i], Math.max(cur[i] * max, cur[i] * min));
			min = Math.min(cur[i], Math.min(cur[i] * maxCopy, cur[i] * min));
			res = Math.max(max, res);
		}
		return res;
	}
}
