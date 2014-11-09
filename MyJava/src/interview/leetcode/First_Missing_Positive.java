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

	public static void swap(int[] A, int i, int j) {
		int tmp = A[i];
		A[i] = A[j];
		A[j] = tmp;
	}
}
