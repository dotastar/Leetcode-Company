package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * 
 Shift Position
 * Fair
 * Data Structure
 * 
 * Given an integer array A, A is sorted in ascending order first then shifted
 * by an arbitrary number of positions, For Example, A = {3, 4, 5, 1, 2}
 * (shifted left by 2 positions). Find the index of the smallest number.
 * 
 * Assumptions
 * 
 * There are no duplicate elements in the array
 * 
 * Examples
 * 
 * A = {3, 4, 5, 1, 2}, return 3
 * A = {1, 2, 3, 4, 5}, return 0
 * 
 * Corner Cases
 * 
 * What if A is null or A is of zero length? We should return -1 in this case.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Shift_Position {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Shift_Position.class);
	}

	/**
	 * Find the shift position
	 * Case study:
	 * 1.A[l] < A[r] : monotonic, return l;
	 * 2.A[l] > A[r] : array is shifted,
	 * 	a.A[l] < A[mid] : shifted index should be in right
	 *  b.A[l] > A[mid] : shifted index should be in left
	 */
	public int shiftPosition(int[] array) {
		if (array == null || array.length == 0)
			return -1;
		int l = 0, r = array.length - 1;
		while (l < r - 1) {
			int mid = l + (r - l) / 2;
			if (array[l] < array[r])
				break;
			if (array[mid] < array[l])
				r = mid;
			else
				l = mid;
		}
		if (array[l] < array[r])
			return l;
		else
			return r;
	}

	/**
	 * More concise
	 * There is only one way that l can move: when A[mid] > A[r],
	 * otherwise move r = mid.
	 */
	public int shiftPosition2(int[] array) {
		if (array == null || array.length == 0)
			return -1;
		int l = 0, r = array.length - 1;
		while (l < r - 1) {
			int mid = l + (r - l) / 2;
			if (array[mid] > array[r])
				l = mid;
			else
				r = mid;
		}
		if (array[l] < array[r])
			return l;
		else
			return r;
	}

	@Test
	public void test1() {
		int[] A = { 4, 5, 6, 7, 1, 2 };
		int res = shiftPosition(A);
		assertTrue(res == 4);
	}
}
