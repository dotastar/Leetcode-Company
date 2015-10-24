package interview.company.epic;

import org.junit.Test;

import interview.AutoTestUtils;

/**
 * Print all the increasing subsequence from the given range
 * 54782369862345 .. e.g: 5,7,8,9; 4,7,8,9; 2,3,6,9 ..
 * 
 * @author yazhoucao
 * 
 */
public class FindIncreasingSequence {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(FindIncreasingSequence.class);
	}

	/**
	 * Use next[] & prev[] construct a doubly linked list, it stores the index
	 * of next/previous greater number.
	 */
	public void find(int[] A) {
		int[] next = new int[A.length];
		int[] prev = new int[A.length];
		// construct next & prev
		for (int i = 0; i < A.length; i++) {
			int j = i + 1;
			while (j < A.length && A[i] >= A[j])
				j++;
			next[i] = j;
			if (j < A.length)
				prev[j] = i;
		}
		// print all subsequences
		for (int i = 0; i < A.length; i++) {
			if (prev[i] != 0)
				continue; // prev[i]==0 means it is a head of a list
			int j = i;
			while (j != A.length) {
				System.out.print(A[j]);
				j = next[j];
			}
			System.out.println();
		}
	}

	@Test
	public void test1() {
		int[] A = { 5, 4, 7, 8, 2, 3, 6, 9, 8, 6, 2, 3, 4, 5 };
		find(A);
	}
}
