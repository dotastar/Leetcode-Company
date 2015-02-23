package interview.company.others;

import static org.junit.Assert.*;

import org.junit.Test;

import interview.AutoTestUtils;

public class Search_for_a_Range {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Search_for_a_Range.class);
	}

	/**
	 * Search for the index of the leftmost target, log(n)
	 * l could be exceeded A when target > A[n-1], l = A.length
	 */
	public int searchForLeftBorder(int[] A, int target) {
		int l = 0, r = A.length - 1;
		while (l <= r) {
			int mid = l + (r - l) / 2;
			if (target <= A[mid])
				r = mid - 1;
			else
				l = mid + 1;
		}
		return l < A.length && A[l] == target ? l : -1;
	}

	/******************* Unit Test of searchForLeftBorder() *******************/
	@Test
	public void testLeft1() {
		int[] A = {};
		int target = 1;
		assertTrue(searchForLeftBorder(A, target) == -1);
	}

	@Test
	public void testLeft2() {
		int[] A = { 1 };
		int target = 1;
		assertTrue(searchForLeftBorder(A, target) == 0);
	}

	@Test
	public void testLeft3() {
		int[] A = { 0, 1, 1 };
		int target = 1;
		assertTrue(searchForLeftBorder(A, target) == 1);
	}

	@Test
	public void testLeft4() {
		int[] A = { 1, 1 };
		int target = 0;
		assertTrue(searchForLeftBorder(A, target) == -1);
	}

	@Test
	public void testLeft5() {
		int[] A = { 1, 1 };
		int target = 2;
		assertTrue(searchForLeftBorder(A, target) == -1);
	}

	@Test
	public void testLeft6() {
		int[] A = { 1, 3, 5, 7 };
		int target = 4;
		assertTrue(searchForLeftBorder(A, target) == -1);
	}

	@Test
	public void testLeft7() {
		int[] A = { 0, 1, 2, 3, 4, 5, 5, 5, 5, 6, 7, 8 };
		int target = 5;
		assertTrue(searchForLeftBorder(A, target) == 5);
	}

	/**
	 * Search for the index of the rightmost target
	 */
	public int searchForRightBorder(int[] A, int target) {
		int l = 0, r = A.length - 1;
		while (l <= r) {
			int mid = l + (r - l) / 2;
			if (target >= A[mid])
				l = mid + 1;
			else
				r = mid - 1;
		}
		return r >= 0 && A[r] == target ? r : -1;
	}

	/******************* Unit Test of searchForRightBorder() *******************/
	@Test
	public void testRight1() {
		int[] A = {};
		int target = 1;
		assertTrue(searchForRightBorder(A, target) == -1);
	}

	@Test
	public void testRight2() {
		int[] A = { 1 };
		int target = 1;
		assertTrue(searchForRightBorder(A, target) == 0);
	}

	@Test
	public void testRight3() {
		int[] A = { 0, 1, 1 };
		int target = 1;
		assertTrue(searchForRightBorder(A, target) == 2);
	}

	@Test
	public void testRight4() {
		int[] A = { 1, 1 };
		int target = 0;
		assertTrue(searchForRightBorder(A, target) == -1);
	}

	@Test
	public void testRight5() {
		int[] A = { 1, 1 };
		int target = 2;
		assertTrue(searchForRightBorder(A, target) == -1);
	}

	@Test
	public void testRight6() {
		int[] A = { 1, 3 };
		int target = 2;
		assertTrue(searchForRightBorder(A, target) == -1);
	}

	@Test
	public void testRight7() {
		int[] A = { 0, 1, 2, 3, 4, 5, 5, 5, 5, 6, 7, 8 };
		int target = 5;
		assertTrue(searchForRightBorder(A, target) == 8);
	}

}
