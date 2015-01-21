package interview.epi.chapter6_array;

import java.util.Arrays;

/**
 * Problem 6.17
 * Design an algorithm that creates uniformly random permutations of {0, 1, ...,
 * n-1}. You are given a random number generator that returns integers in the
 * set {0, 1, ..., n-1} with equal probability; use as few calls to it as
 * possible.
 * 
 * @author yazhoucao
 * 
 */
public class Q17_Compute_A_Random_Permutation {

	public static void main(String[] args) {
		int[] A = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		for(int i=0; i<20; i++)
			System.out.println(Arrays.toString(computeRandomPerm(A)));
	}

	/**
	 * A special case of solution 6.16, when k = A.length
	 */
	public static int[] computeRandomPerm(int[] A) {
		return Q16_Sample_Offline_Data.offlineSampling(A, A.length);
	}
}
