package interview.epi.chaper6_array;

import java.util.Arrays;
import java.util.Random;

/**
 * Problem 6.16
 * Let A be an array whose entries are all distinct. Implement an algorithm that
 * takes A and an integer k and returns a subset of k elements of A. All subsets
 * should be equally likely. Use as few calls to the random number generator as
 * possible and use O(1) additional storage.
 * 
 * @author yazhoucao
 * 
 */
public class Q16_Sample_Offline_Data {
	static String methodName = "offlineSampling";
	static Class<?> c = Q16_Sample_Offline_Data.class;

	public static void main(String[] args) {
		int[] A = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
				18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30 };

		for (int i = 0; i < 10; i++) {
			System.out.println(Arrays.toString(offlineSampling(A, 5)));
		}
	}

	public static int[] offlineSampling(int[] A, int k) {
		Random ran = new Random();
		for (int i = 0; i < k; i++) {
			int choosen = i + ran.nextInt(A.length - i);
			swap(A, i, choosen);
		}
		return Arrays.copyOf(A, k);
	}

	private static void swap(int[] A, int i, int j) {
		int tmp = A[i];
		A[i] = A[j];
		A[j] = tmp;
	}
}
