package interview.epi.chapter13_hashtable;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Write a function which takes as input a set of integers represented by an
 * array A, and returns the size of a biggest range [x...y] contained in the
 * set. For example: {3, -2, 7, 9, 8, 1, 2, 0}, the longest contained range is
 * [0...3], so the length is 4.
 * 
 * @author yazhoucao
 * 
 */
public class Q12_Find_The_Length_Of_A_Longest_Contained_Range {

	static Class<?> c = Q12_Find_The_Length_Of_A_Longest_Contained_Range.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Naive solution, sort the array, and increment the length if is continuous
	 * Time: O(nlogn)
	 */

	/**
	 * Idea:
	 * Sorting is not essential to the functioning of the algorithm.
	 * What we need to compute the length of the longest range ending at A[i] is
	 * whether A[i]-1 is present in A, and if so, what the length of the longest
	 * range ending at A[i]-1 is. We can use HashMap to store the elements so
	 * that speed up the search of lower and upper bound.
	 * Time: O(n), since we add and remove all elements form the set only once.
	 */
	public int findLongestRangeLength(int[] A) {
		Set<Integer> unvisited = new HashSet<>();
		for (int a : A)
			unvisited.add(a);

		int maxLength = 1;
		for (int a : A) {
			if (unvisited.contains(a)) {
				unvisited.remove(a);
				int count = 1;
				// search the lower bound of a
				int val = a - 1;
				while (unvisited.contains(val)) {
					unvisited.remove(val);
					val--;
					count++;
				}

				// search the upper bound of a
				val = a + 1;
				while (unvisited.contains(val)) {
					unvisited.remove(val);
					val++;
					count++;
				}

				maxLength = count > maxLength ? count : maxLength;
			}
		}
		return maxLength;
	}

	@Test
	public void test1() {
		int[] A = { 3, -2, 7, 9, 8, 1, 2, 0 };
		int res = findLongestRangeLength(A);
		assertTrue(res == 4);
	}

	@Test
	public void test2() {
		int[] A = { 3, -2, 7, 9, 8, 1, 6, 0 };
		int res = findLongestRangeLength(A);
		assertTrue(res == 4);
	}

	@Test
	public void test3() {
		int[] A = { 3, 4, 7, 5, 8, 1, 2, 6 };
		int res = findLongestRangeLength(A);
		assertTrue(res == 8);
	}

	@Test
	public void test4() {
		int[] A = { 3, 11, 5, 9, 7, 1, -1, 3 };
		int res = findLongestRangeLength(A);
		assertTrue(res == 1);
	}

	@Test
	public void test5() {
		int[] A = { 3, 3, 3, 3, 3, 3, 3, 3 };
		int res = findLongestRangeLength(A);
		assertTrue(res == 1);
	}
}
