package interview.epi.chapter13_hashtable;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class Q11_Find_The_Longest_Subarray_With_Distinct_Entries {

	static Class<?> c = Q11_Find_The_Longest_Subarray_With_Distinct_Entries.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Sliding window
	 * Keep moving right forward, whenever match an element in distinct, move
	 * left forward to the index of the matched in distinct, and then delete the
	 * old index and insert the new index of the same element.
	 */
	public int[] findRange(int[] A) {
		int[] res = { -1, -1 };
		int maxLen = 0;
		Map<Integer, Integer> distincts = new HashMap<>();
		for (int l = 0, r = 0; r < A.length; r++) {
			if (distincts.containsKey(A[r])) {
				int end = distincts.get(A[r]);
				for (; l <= end; l++)
					distincts.remove(A[l]);
			}
			distincts.put(A[r], r);

			if (r - l + 1 > maxLen) {
				maxLen = r - l + 1;
				res[0] = l;
				res[1] = r;
			}
		}

		return res;
	}

	@Test
	public void test1() {
		int[] A = { 5, 7, 5, 11, 13, 2, 11, 19, 2, 11 };
		int[] res = findRange(A);
		assertTrue(res[0] == 1 && res[1] == 5);
	}

	@Test
	public void test2() {
		int[] A = { 5, 5, 5, 5, 5, 5, 5, 5 };
		int[] res = findRange(A);
		assertTrue(res[0] == 0 && res[1] == 0);
	}

	@Test
	public void test3() {
		int[] A = { 5, 7, 5, 11, 7, 2, 5, 19, 20, 21 };
		int[] res = findRange(A);
		assertTrue(res[0] == 3 && res[1] == 9);
	}

	@Test
	public void test4() {
		int[] A = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[] res = findRange(A);
		assertTrue(res[0] == 0 && res[1] == 9);
	}
}
