package interview.epi.chapter6_array;

import java.util.Arrays;


/**
 * A permutation can be specified by an array P, where P[i] represents the
 * location of the element at i in the permutation. For example, the array [2,
 * 0, 1, 3] represents the permutation that maps the element at location 0 to
 * location 2, the element at location 1 to location 0, ......
 * 
 * Problem 6.13
 * Given an array A of n elements and a permutation P, apply P to A using only
 * constant additional storage. Use A itself to store the result.
 * 
 * @author yazhoucao
 * 
 */
public class Q13_Permute_The_Elements_Of_An_Array {

	static String methodName = "applyPermutation";
	static Class<?> c = Q13_Permute_The_Elements_Of_An_Array.class;

	public static void main(String[] args) {
		int[] A = { 10, 20, 30, 40 };
		int[] p = { 2, 0, 1, 3 };
		applyPermutation1(p, A);
		System.out.println(Arrays.toString(A));
		
		 A = new int[] { 10, 20, 30, 40 };
		 p = new int[] { 2, 0, 1, 3 };
		 applyPermutation2(p, A);
		System.out.println(Arrays.toString(A));
			 
	}

	public static void applyPermutation1(int[] perm, int[] A) {
		int i = 0;
		while (i < A.length) {
			if (perm[i] != i){
				swap(A, i, perm[i]);
				swap(perm, i, perm[i]);
			}else
				i++;
		}
	}

	private static void swap(int[] A, int i, int j) {
		int tmp = A[i];
		A[i] = A[j];
		A[j] = tmp;
	}

	public static void applyPermutation2(int[] perm, int[] A) {
		for (int i = 0; i < A.length; ++i) {
			// Traverses the cycle to see if i is the minimum element.
			boolean isMin = true;
			int j = perm[i];
			while (j != i) {
				if (j < i) {
					isMin = false;
					break;
				}
				j = perm[j];
			}

			if (isMin) {
				int a = i;
				int temp = A[i];
				do {
					int nextA = perm[a];
					int nextTemp = A[nextA];
					A[nextA] = temp;
					a = nextA;
					temp = nextTemp;
				} while (a != i);
			}
		}
	}
}
