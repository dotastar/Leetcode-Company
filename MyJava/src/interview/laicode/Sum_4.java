package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;

/**
 * 4 Sum
 * Fair
 * Data Structure
 * 
 * Determine if there exists a set of four elements in a given array that sum to
 * the given target number.
 * 
 * Assumptions
 * 
 * The given array is not null and has length of at least 4
 * 
 * Examples
 * 
 * A = {1, 2, 2, 3, 4}, target = 9, return true(1 + 2 + 2 + 4 = 8)
 * 
 * A = {1, 2, 2, 3, 4}, target = 12, return false
 * 
 * @author yazhoucao
 * 
 */
public class Sum_4 {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Sum_4.class);
	}

	/**
	 * Time: n^2 + n^2 * log(n^2) + n^2 = O(n^2 * logn), Space: O(n^2)
	 * Binary reduction, convert it to a two sum
	 * 1.form an n^2 array of pairs of A,
	 * 2.sort the pair array by its sum value,
	 * 3.then do a two sum in the pairs array.
	 */
	public boolean exist(int[] A, int target) {
		Arrays.sort(A);
		// Sn = n * (An + 1) / 2
		int len = (A.length - 1) * (A.length - 1 + 1) / 2;
		Pair[] pairs = new Pair[len];
		for (int i = 0, idx = 0; i < A.length - 1; i++) {
			for (int j = i + 1; j < A.length; j++)
				pairs[idx++] = new Pair(A[i] + A[j], i, j);
		}
		// sorting
		Arrays.sort(pairs, new Comparator<Pair>() {
			@Override
			public int compare(Pair p1, Pair p2) {
				return p1.val - p2.val;
			}
		});

		// two pairs sum, O(n^2)
		int l = 0, r = len - 1;
		while (l < r) {
			int sum = pairs[l].val + pairs[r].val;
			if (sum == target) {
				Pair left = pairs[l], right = pairs[r];
				if (left.i != right.i && left.i != right.j && left.j != right.i
						&& left.j != right.j)
					return true;
				if ((pairs[l + 1].val - pairs[l].val) <= (pairs[r].val - pairs[r - 1].val))
					l++;
				else
					r--;
			} else if (sum > target)
				r--;
			else
				l++;
		}
		return false;
	}

	@Test
	public void test1() {
		int[] A = new int[0];
		boolean ans = false;
		assertTrue(exist(A, 0) == ans);
	}

	@Test
	public void test2() {
		int[] A = new int[] { 1, 2, 2, 3, 4 };
		int target = 9;
		boolean ans = true;
		assertTrue(exist(A, target) == ans);
	}

	@Test
	public void test3() {
		int[] A = new int[] { 1, 2, 2, 3, 4 };
		int target = 12;
		boolean ans = false;
		assertTrue(exist(A, target) == ans);
	}

	public static class Pair {
		int val;
		int i;
		int j;

		public Pair(int val, int i, int j) {
			this.val = val;
			this.i = i;
			this.j = j;
		}
	}
}
