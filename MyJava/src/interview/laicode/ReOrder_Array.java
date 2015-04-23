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

	/**
	 * Recursively switching parts
	 * Time: O(nlogn)
	 */
	public int[] reorder(int[] A) {
		reorder(A, 0, A.length - 1);
		return A;
	}

	/**
	 * Reorder array[l, r]
	 * Base case: when there are only one or two elements, e.g. N1, N2
	 * Recursive rule:
	 * 
	 * 1.If array size is odd, then convert it to an even size by moving right
	 * side by one
	 * 
	 * 2.If array size is even:
	 * 2a.Separate the array to four parts evenly by three positions:
	 * leftmid, mid, rightmid, notice all these three positions should be prone
	 * to rear half (when the size is even), this is very important!
	 * 
	 * All can be easily done by adding l + size/2, where size = r - l + 1
	 * which is prone to rear half (r - l is prone to front half)
	 * 
	 * 2b.Make these three position to be prone to rear half make things easy:
	 * it make sure it has a consistent switching when the left and right part
	 * has an odd size and even size.
	 * 
	 * 2c.Switching the second half of left part with the first half of right
	 * part. If left and right is even size, it is easy. If it is odd, it makes
	 * an uneven switching, the right part to be switched is shorter than the
	 * left. E.g, switching A B C | 1 2 3 --> A | 1 | B C | 2 3
	 * 
	 * 3.After switching, find the switching boundary, recursively reorder
	 * A[l, boundary - 1] and A[boundary, r], notice boundary is not mid!
	 * The boundary can be decided by checking l + 2 * (leftmid - l), where
	 * (leftmid - l) is the size from l to leftmid
	 */
	private void reorder(int[] A, int l, int r) {
		if (l >= r - 1)
			return;
		int size = r - l + 1;
		if (size % 2 == 1) { // odd number size
			reorder(A, l, r - 1); // convert to an even number reorder()
		} else { // even number size
			int mid = l + size / 2; // mid is relatively rear, 1234, mid = 3
			int leftmid = l + size / 4; // same as leftmid, rightmid
			int rightmid = l + (size * 3) / 4;
			reverse(A, leftmid, mid - 1);
			reverse(A, mid, rightmid - 1);
			reverse(A, leftmid, rightmid - 1); // DE123 -> 123DE

			reorder(A, l, l + 2 * (leftmid - l) - 1);
			reorder(A, l + 2 * (leftmid - l), r);
		}
	}

	private void reverse(int[] A, int l, int r) {
		while (l < r) {
			int temp = A[l];
			A[l] = A[r];
			A[r] = temp;
			l++;
			r--;
		}
	}

	@Test
	public void test1() {
		int[] A = { 1, 2, 3 }; // corner case
		int[] ans = { 1, 2, 3 };
		int[] res = reorder(A);
		assertTrue("Wrong: " + Arrays.toString(res), Arrays.equals(ans, res));
	}

	@Test
	public void test2() {
		int[] A = { 1, 2 }; // base case
		int[] ans = { 1, 2 };
		int[] res = reorder(A);
		assertTrue("Wrong: " + Arrays.toString(res), Arrays.equals(ans, res));
	}

	@Test
	public void test3() { // even-odd
		int[] A = { 1, 2, 3, 4, 5, 6 };
		int[] ans = { 1, 4, 2, 5, 3, 6 };
		int[] res = reorder(A);
		assertTrue("Wrong: " + Arrays.toString(res), Arrays.equals(ans, res));
	}

	@Test
	public void test4() { // even-even
		int[] A = { 1, 2, 3, 4, 5, 6, 7, 8 };
		int[] ans = { 1, 5, 2, 6, 3, 7, 4, 8 };
		int[] res = reorder(A);
		assertTrue("Wrong: " + Arrays.toString(res), Arrays.equals(ans, res));
	}

	@Test
	public void test5() { // odd
		int[] A = { 1, 2, 3, 4, 5, 6, 7 };
		int[] ans = { 1, 4, 2, 5, 3, 6, 7, };
		int[] res = reorder(A);
		assertTrue("Wrong: " + Arrays.toString(res), Arrays.equals(ans, res));
	}

	@Test
	public void test6() { // even-odd
		int[] A = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };
		int[] ans = { 1, 8, 2, 9, 3, 10, 4, 11, 5, 12, 6, 13, 7, 14 };
		int[] res = reorder(A);
		assertTrue("Wrong: " + Arrays.toString(res), Arrays.equals(ans, res));
	}
}
