package interview.laicode;

/**
 * 
 Longest Ascending Subsequence
 * Fair
 * DP
 * 
 * Given an array A[0]...A[n-1] of integers, find out the length of the longest
 * ascending subsequence.
 * 
 * Assumptions
 * 
 * A is not null
 * 
 * Examples
 * Input: A = {5, 2, 6, 3, 4, 7, 5}
 * Output: 4
 * Because [2, 3, 4, 5] is the longest ascending subsequence.
 * 
 * @author yazhoucao
 * 
 */
public class Longest_Ascending_Subsequence {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * DP, Time: O(n^2)
	 * Base case, M[0] = 1
	 * Induction rule, M[i] : longest ascending subsequence end at i,
	 * M[i] = M[j] + 1, j is the max M[j] in all j = [0, i-1] such that
	 * A[j] < A[i]
	 **/
	public int longest(int[] A) {
		if (A.length == 0)
			return 0;
		int max = 1;
		int[] M = new int[A.length];
		M[0] = 1; // base case
		for (int i = 1; i < A.length; i++) {
			int maxJ = -1;
			for (int j = 0; j < i; j++) {
				if (A[i] > A[j] && (maxJ == -1 || M[j] > M[maxJ]))
					maxJ = j;
			}
			M[i] = maxJ == -1 ? 1 : M[maxJ] + 1;
			max = M[i] > max ? M[i] : max;
		}
		return max;
	}
}
