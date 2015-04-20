package interview.laicode;

import static org.junit.Assert.*;

import java.util.Arrays;

import interview.AutoTestUtils;

import org.junit.Test;

/**
 * ReOrder Array
 * Hard
 * Recursion
 * 
 * Given an array of elements, reorder it as follow:
 * 
 * { N1, N2, N3, …, N2k } → { N1, Nk+1, N2, Nk+2, N3, Nk+3, … , Nk, N2k }
 * 
 * { N1, N2, N3, …, N2k+1 } → { N1, Nk+1, N2, Nk+2, N3, Nk+3, … , Nk, N2k, N2k+1
 * }
 * 
 * Try to do it in place.
 * 
 * Assumptions
 * 
 * The given array is not null
 * 
 * Examples
 * 
 * { 1, 2, 3, 4, 5, 6} → { 1, 4, 2, 5, 3, 6 }
 * 
 * { 1, 2, 3, 4, 5, 6, 7, 8 } → { 1, 5, 2, 6, 3, 7, 4, 8 }
 * 
 * { 1, 2, 3, 4, 5, 6, 7 } → { 1, 4, 2, 5, 3, 6, 7 }
 * 
 * 
 * @author yazhoucao
 * 
 */
public class ReOrder_Array {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(ReOrder_Array.class);
	}

	public int[] reorder(int[] A) {

		return A;
	}

	private void reorder(int[] A, int l, int r) {
		if (l > r - 1)
			return;
		int mid = l + (r - l) / 2;
		int lmid = l + (mid - l) / 2;
		int rmid = mid + 1 + (r - mid - 1) / 2;

	}

	private void reverse(int[] A, int l, int r) {

	}

	@Test
	public void test1() {
		int[] A = { 1 };
		int[] ans = { 1 };
		int[] res = reorder(A);
		assertTrue(Arrays.equals(ans, res));
	}

	@Test
	public void test2() {
		int[] A = { 1, 2 };
		int[] ans = { 1, 2 };
		int[] res = reorder(A);
		assertTrue(Arrays.equals(ans, res));
	}

	@Test
	public void test3() {
		int[] A = { 1, 2, 3, 4, 5, 6 };
		int[] ans = { 1, 4, 2, 5, 3, 6 };
		int[] res = reorder(A);
		assertTrue(Arrays.equals(ans, res));
	}

	@Test
	public void test4() {
		int[] A = { 1, 2, 3, 4, 5, 6, 7, 8 };
		int[] ans = { 1, 5, 2, 6, 3, 7, 4, 8 };
		int[] res = reorder(A);
		assertTrue(Arrays.equals(ans, res));
	}

}
