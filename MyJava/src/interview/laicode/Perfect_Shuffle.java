package interview.laicode;

import java.util.Random;

/**
 * 
 Perfect Shuffle
 * Fair
 * Probability
 * 
 * Given an array of integers (without any duplicates), shuffle the array such
 * that all permutations are equally likely to be generated.
 * 
 * Assumptions
 * 
 * The given array is not null
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Perfect_Shuffle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * end = A.length - 1
	 * while (end > 0)
	 * Choose one randomly from [0, end], put it in end, and end--;
	 * 
	 * Probability:
	 * 1/n * (1/n-1 * n-1/n) * (1/n-2 * n-2/n) * .... * (1/1 * 1/n)
	 **/
	public void shuffle(int[] A) {
		Random ran = new Random();
		for (int i = A.length - 1; i > 0; i--) {
			swap(A, i, ran.nextInt(i + 1));
		}
	}

	private void swap(int[] A, int i, int j) {
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}

}
