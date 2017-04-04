package interview.company.zenefits;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Zenefits
 * 
 * 
 * Given a matrix and a number k, numbers in rows and columns are sorted, but
 * the last element of ith row is not guaranteed less than the first element of
 * (i+1)th row, the same as columns.
 * 
 * Given an n x n matrix, where every row and column is sorted in increasing
 * order. Given a number x, how to decide whether this x is in the matrix. The
 * designed algorithm should have linear time complexity.
 * 
 * www.geeksforgeeks.org/search-in-row-wise-and-column-wise-sorted-matrix/
 * 
 * @author yazhoucao
 *
 */
public class SearchInSortedMatrix {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(SearchInSortedMatrix.class);
	}

	/**
	 * Time: O(n)
	 * 
	 * 1) Start with top right element
	 * 2) Loop: compare this element e with x
	 * ….i) if they are equal then return its position
	 * …ii) e < x then move it to down (if out of bound of matrix then break
	 * return false)
	 * ..iii) e > x then move it to left (if out of bound of matrix then break
	 * return false)
	 * 3) repeat the i), ii) and iii) till you find element or returned false
	 */
	public boolean search(int[][] A, int target) {
		int m = A.length, n = m == 0 ? 0 : A[0].length;
		int i = 0, j = n - 1;
		while (i < m && j >= 0) {
			if (A[i][j] == target)
				return true;
			else if (target > A[i][j])
				i++;
			else
				j--;
		}
		return false;
	}

	@Test
	public void test1() {
		int[][] mat = {

		{ 10, 20, 30, 40 },

		{ 15, 25, 35, 45 },

		{ 27, 29, 37, 48 },

		{ 32, 33, 39, 50 } };

		int target = 15;

		assertTrue(search(mat, target));
	}

	@Test
	public void test2() {
		int[][] mat = {

		{ 10, 20, 30, 40 },

		{ 15, 25, 35, 45 },

		{ 27, 29, 37, 48 },

		{ 32, 33, 39, 50 } };

		int target = 49;

		assertTrue(!search(mat, target));
	}

	@Test
	public void test3() {
		int[][] mat = {

		{ 10, 20, 30, 40 },

		{ 15, 25, 35, 45 },

		{ 27, 29, 37, 48 },

		{ 32, 33, 39, 50 } };

		int target = 32;

		assertTrue(search(mat, target));
	}
}
