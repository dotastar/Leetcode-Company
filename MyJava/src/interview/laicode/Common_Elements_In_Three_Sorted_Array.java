package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * 
 * Common Elements In Three Sorted Array
 * Fair
 * Data Structure
 * 
 * Find all common elements in 3 sorted arrays.
 * 
 * Assumptions
 * 
 * The 3 given sorted arrays are not null
 * There could be duplicate elements in each of the arrays
 * 
 * Examples
 * 
 * A = {1, 2, 2, 3}, B = {2, 2, 3, 5}, C = {2, 2, 4}, the common elements are
 * [2, 2]
 * 
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Common_Elements_In_Three_Sorted_Array {

	public static void main(String[] args) {
		AutoTestUtils
				.runTestClassAndPrint(Common_Elements_In_Three_Sorted_Array.class);
	}

	/**
	 * 
	 * A: 1 2 2 3 5
	 * ## ^
	 * B: 2 2 3 5
	 * ## ^
	 * C: 2 2 5
	 * ## ^
	 */
	public List<Integer> common(int[] a, int[] b, int[] c) {
		List<Integer> res = new ArrayList<>();
		for (int i = 0, j = 0, k = 0; i < a.length && j < b.length
				&& k < c.length;) {
			if (a[i] == b[j] && b[j] == c[k]) {
				res.add(a[i++]);
				j++;
				k++;
			} else {
				if (a[i] < b[j]) {
					if (a[i] < c[k])
						i++;
					else
						k++;
				} else {
					if (b[j] < c[k])
						j++;
					else
						k++;
				}
			}
		}
		return res;
	}

	@Test
	public void test1() {
		int[] A = { 1, 2, 2, 3, 5 };
		int[] B = { 2, 2, 3, 5 };
		int[] C = { 2, 2, 5 };
		List<Integer> rawRes = common(A, B, C);
		Integer[] res = rawRes.toArray(new Integer[0]);
		Integer[] ans = { 2, 2, 5 };
		assertTrue("Wrong: " + Arrays.toString(res), Arrays.equals(res, ans));
	}
}
