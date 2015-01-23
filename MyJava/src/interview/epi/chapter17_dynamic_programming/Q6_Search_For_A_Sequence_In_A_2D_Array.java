package interview.epi.chapter17_dynamic_programming;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.junit.Test;

/**
 * Design an algorithm that takes as arguments a 2D array A and a 1D array S,
 * and determines whether S appears in A. If S appears in A, print the sequence
 * of entries where it appears.
 * 
 * You can traverse adjacent entries in A, the adjacent to A[i][j] is its left,
 * right, up, down sides.
 * It is acceptable to visit an entry in A more than once.
 * 
 * @author yazhoucao
 * 
 */
public class Q6_Search_For_A_Sequence_In_A_2D_Array {

	static Class<?> c = Q6_Search_For_A_Sequence_In_A_2D_Array.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Dynamic Programming
	 * Cache the result that has previously calculated
	 * Key: the result can be represented by a triple(i,j,l) where i,j is the
	 * position of A, l is the position of S.
	 * 
	 * Time: O(mnl)
	 * 
	 */
	public boolean match_Improve(int[][] A, int[] S) {
		Set<Tuple> cache = new HashSet<Tuple>();
		Stack<Integer> path = new Stack<>();
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				if (match(A, S, i, j, path, cache))
					return true;
			}
		}
		return false;
	}

	private boolean match(int[][] A, int[] S, int i, int j,
			Stack<Integer> path, Set<Tuple> cache) {
		if (path.size() == S.length) {
			System.out.println(path.toString());
			return true;
		}
		int m = A.length, n = A[0].length, l = path.size();
		Tuple t = new Tuple(i, j, l);
		if (i >= 0 && j >= 0 && i < m && j < n && cache.add(t)
				&& A[i][j] == S[l]) {
			path.push(A[i][j]);
			if (match(A, S, i + 1, j, path) || match(A, S, i - 1, j, path)
					|| match(A, S, i, j + 1, path)
					|| match(A, S, i, j - 1, path))
				return true;
			path.pop();
		}
		return false;
	}

	/**
	 * Brute force recursion solution, traversal start at every point check if
	 * the path makes up S.
	 * Time: O(mn*(4^l)), m, n are A's dimensions, l = S.length.
	 */
	public boolean match(int[][] A, int[] S) {
		Stack<Integer> path = new Stack<>();
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				if (match(A, S, i, j, path))
					return true;
			}
		}
		return false;
	}

	private boolean match(int[][] A, int[] S, int i, int j, Stack<Integer> path) {
		if (path.size() == S.length) {
			System.out.println(path.toString());
			return true;
		}
		int m = A.length, n = A[0].length;
		if (i >= 0 && j >= 0 && i < m && j < n && A[i][j] == S[path.size()]) {
			path.push(A[i][j]);
			if (match(A, S, i + 1, j, path) || match(A, S, i - 1, j, path)
					|| match(A, S, i, j + 1, path)
					|| match(A, S, i, j - 1, path))
				return true;
			path.pop();
		}
		return false;
	}

	private static class Tuple {
		int a, b, c;

		public Tuple(int ain, int bin, int cin) {
			a = ain;
			b = bin;
			c = cin;
		}

		@Override
		public int hashCode() {
			return 31 * 31 * a + 31 * b + c;
		}
	}

	@Test
	public void test1() {
		int[][] A = { { 1, 2, 3 }, { 3, 4, 5 }, { 5, 6, 7 }, { 7, 8, 9 } };
		int[] S = { 4, 6, 4, 6, 4, 6, 4, 6, 4, 6 }; // two entries repeated
		assertTrue(match(A, S));
		assertTrue(match_Improve(A, S));
	}

	@Test
	public void test2() {
		int[][] A = { { 1, 2, 3 }, { 3, 4, 5 }, { 5, 6, 7 }, { 7, 8, 9 } };
		int[] S = { 7, 8, 9, 7, 6, 5, 7, 8, 9 }; // two rows cycled
		assertTrue(match(A, S));
		assertTrue(match_Improve(A, S));
	}

	@Test
	public void test3() {
		int[][] A = { { 1, 2, 3 }, { 3, 4, 5 }, { 5, 6, 7 }, { 7, 8, 9 } };
		int[] S = { 3, 5, 7, 9, 8, 6, 4, 2, 3 }; // two cols cycled
		assertTrue(match(A, S));
		assertTrue(match_Improve(A, S));
	}

	@Test
	public void test4() {
		int[][] A = { { 1, 2, 3 }, { 3, 4, 5 }, { 5, 6, 7 }, { 7, 8, 9 } };
		int[] S = { 9, 8, 7, 5, 6, 7, 5, 4, 3, 1, 2, 3 };
		assertTrue(match(A, S)); // a long sequence
		assertTrue(match_Improve(A, S));
	}

	@Test
	public void test5() {
		int[][] A = { { 1, 2, 3 }, { 3, 4, 5 }, { 5, 6, 7 }, { 7, 8, 9 } };
		int[] S = { 4, 3, 4, 4 };
		assertTrue(!match(A, S)); // a false example
		assertTrue(!match_Improve(A, S));
	}
}
