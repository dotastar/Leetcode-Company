package interview.laicode;

/**
 * 
 * Largest Square Of 1s
 * Hard
 * DP
 * 
 * Determine the largest square of 1s in a binary matrix (a binary matrix only
 * contains 0 and 1), return the length of the largest square.
 * 
 * Assumptions
 * 
 * The given matrix is not null and guaranteed to be of size N * N, N >= 0
 * 
 * Examples
 * 
 * { {0, 0, 0, 0},
 * 
 * {1, 1, 1, 1},
 * 
 * {0, 1, 1, 1},
 * 
 * {1, 0, 1, 1}}
 * 
 * the largest square of 1s has length of 2
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Largest_Square_Of_1s {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * DP
	 * Base case (size = 1): M[i][j] = matrix[i][j]
	 * Induction rule: M[i][j] = matrix[i][j] == 1 ? min(M[i-1][j], M[i][j-1],
	 * M[i-1][j-1]) + 1 : 0;
	 **/
	public int largest(int[][] matrix) {
		int N = matrix.length;
		int[][] M = new int[N][N];
		int max = 0;
		// initialization, base case
		for (int i = 0; i < N; i++) {
			M[i][0] = matrix[i][0];
			M[0][i] = matrix[0][i];
			if (max == 0 && (M[i][0] == 1 || M[i][0] == 1))
				max = 1;
		}
		// induction
		for (int i = 1; i < N; i++) {
			for (int j = 1; j < N; j++) {
				M[i][j] = matrix[i][j] == 1 ? min(M[i - 1][j], M[i][j - 1],
						M[i - 1][j - 1]) + 1 : 0;
				max = M[i][j] > max ? M[i][j] : max;
			}
		}
		return max;
	}

	private int min(int a, int b, int c) {
		int less = a < b ? a : b;
		return less < c ? less : c;
	}
}
