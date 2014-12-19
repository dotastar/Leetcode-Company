package interview.leetcode;

/**
 * Given an unsorted integer array, find the first missing positive integer.
 * 
 * For example, Given [1,2,0] return 3, and [3,4,-1,1] return 2.
 * 
 * Your algorithm should run in O(n) time and uses constant space.
 * 
 * @author yazhoucao
 * 
 */
public class First_Missing_Positive {

	public static void main(String[] args) {
		System.out.println(firstMissingPositive(new int[] { 1, 1 }));
	}

	/**
	 * Time: 2n = O(n)
	 * 
	 * The first missing positive value must be in the range of 1...n+1, it is
	 * n+1 when every entry value in the array A[i] = i+1, if there is one or
	 * more duplicate values, the missing value must be less than n+1.
	 * 
	 * A[i] == A[A[i]] is the result of swap(A, i, A[i]),
	 * 
	 * if(A[i]==A[A[i]]) == true, it means the position A[i] is going to be put
	 * is already have the right number, don't swap them again,
	 * 
	 * it make sure it will stop when situation like A[]={ 1, 1 } happens, and
	 * condition ( A[i] == i ) won't work on this situation
	 * 
	 * ( A[i] == i ) == true means the current position i have the right number
	 */
	public static int firstMissingPositive(int[] A) {
		if (A.length == 0)
			return 1;
		int i = 0;
		while (i < A.length) {
			if (A[i] < 0 || A[i] >= A.length || A[i] == A[A[i]] || A[i] == i)
				i++;
			else
				swap(A, i, A[i]); // stay where it is
		}

		for (int j = 1; j < A.length; j++) {
			if (A[j] != j)
				return j;
		}
		// because we put A[i] in i position, so we ignored A[0],
		// but A[0] could be the value A.length, if it is the case,
		// then A.length+1 should be returned
		return A[0] == A.length ? A.length + 1 : A.length;
	}

	/**
	 * Same solution, more comments, from EPI
	 */
	public static int firstMissingPositive2(int[] A) {
		// Record which values are present by writing A[i] to index A[i] - 1 if
		// A[i] is between 1 and A.length, inclusive. We save the value at index
		// A[i] - 1 by swapping it with the entry at i. If A[i] is negative or
		// greater than n, we just advance i.
		int i = 0;
		while (i < A.length) {
			if (A[i] > 0 && A[i] <= A.length && A[A[i] - 1] != A[i]) {
				swap(A, A[i] - 1, i);
			} else {
				++i;
			}
		}

		// Second pass through A to search for the first index i such that
		// A[i] != i+1, indicating that i + 1 is absent. If all numbers between
		// 1 and A.length are present, the smallest missing positive is
		// A.length + 1.
		for (i = 0; i < A.length; ++i) {
			if (A[i] != i + 1) {
				return i + 1;
			}
		}
		return A.length + 1;
	}

	public static void swap(int[] A, int i, int j) {
		int tmp = A[i];
		A[i] = A[j];
		A[j] = tmp;
	}

}
