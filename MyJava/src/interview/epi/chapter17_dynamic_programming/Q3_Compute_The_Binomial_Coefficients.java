package interview.epi.chapter17_dynamic_programming;

import java.util.ArrayList;
import java.util.List;

/**
 * Design an efficient algorithm for computing C(n, k) which has the property
 * that it never overflows if C(n,k) can be represented as a 32-bit integer;
 * assume n and k are integers.
 * 
 * n choose k:
 * C(n, k) = n*n-1*n-2*...*(n-k+1) / k*k-1*k-1*....*3*2*1.
 * 
 * @author yazhoucao
 * 
 */
public class Q3_Compute_The_Binomial_Coefficients {

	public static void main(String[] args) {
		Q3_Compute_The_Binomial_Coefficients o = new Q3_Compute_The_Binomial_Coefficients();
		int n = 20;
		for (int k = 0; k <= n; k++) {
			int res = o.computeBinomialCoefficients2(n, k);
			int ans = checkAns(n, k);
			assert (ans == res);
			System.out.print(res + " ");
		}

	}

	/**
	 * A key formula: C(i, j) = C(i - 1, j) + C(i - 1, j - 1).
	 */
	public int computeBinomialCoefficients(int N, int K) {
		int[][] C = new int[N + 1][K + 1];
		C[0][0] = 1; // C(0, 0).
		// C(i, j) = C(i - 1, j) + C(i - 1, j - 1).
		for (int n = 1; n <= N; ++n) {
			C[n][0] = 1; // One way to select zero element.
			for (int k = 1; k <= n && k <= K; ++k) {
				C[n][k] = C[n - 1][k] + C[n - 1][k - 1];
			}
			// System.out.println("C("+n+"):\t"+Arrays.toString(C[n]));
		}
		return C[N][K];
	}

	/**
	 * Space improved
	 */
	public int computeBinomialCoefficients2(int N, int K) {
		int[] C = new int[K + 1];
		C[0] = 1; // C(0, 0).
		// C(i, j) = C(i - 1, j) + C(i - 1, j - 1).
		for (int n = 1; n <= N; ++n) {
			for (int k = Math.min(n, K); k >= 1; --k) {
				C[k] = C[k] + C[k - 1];
			}
			C[0] = 1; // One way to select zero element.
		}
		return C[K];
	}

	private static int checkAns(int n, int k) {
		List<Integer> number = new ArrayList<>();
		for (int i = 0; i < k; ++i) {
			number.add(n - i);
		}
		List<Integer> temp = new ArrayList<>();
		for (int i = 2; i <= k; ++i) {
			boolean find = false;
			for (int j = 0; j < number.size(); j++) {
				if ((number.get(j) % i) == 0) {
					number.set(j, number.get(j) / i);
					find = true;
					break;
				}
			}
			if (!find) {
				temp.add(i);
			}
		}
		int res = 1;
		for (int a : number) {
			res *= a;
		}
		for (int a : temp) {
			res /= a;
		}
		return res;
	}

}
