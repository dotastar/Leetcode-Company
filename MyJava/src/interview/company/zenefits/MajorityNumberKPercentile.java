package interview.company.zenefits;

import static org.junit.Assert.assertEquals;
import interview.AutoTestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.junit.Test;

/**
 * Zenefits
 * Onsite
 * 
 * http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=133737&extra=
 * page%3D5%26filter%3Dsortid%26sortid%3D311%26sortid%3D311
 * 
 * Given an array of Integers, there is exactly one Integer that occurs more
 * than or equals to 1/3 of the size of the array.
 * For example, [1, 2, 3, 1, 5, 7, 7, 1, 5] will return 1. Find that Integer.
 * 
 * The average time complexity should be O(n),
 * and the space complexity should be O(1).
 * 
 * @author yazhoucao
 *
 */
public class MajorityNumberKPercentile {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(MajorityNumberKPercentile.class);
	}

	/**
	 * Find all possible k - 1 numbers, A[0], A[0 + 1/k], A[0 + 2/k], ...
	 * Store in a HashMap, scan A[], do a count aggregation
	 * Exclude those are not satisfied, return the first satisfied answer
	 */
	public int majority(int[] A, int k) {
		if (A == null || A.length == 0)
			return 0;
		int step = A.length / k;
		// find all possible numbers
		Map<Integer, Integer> candidates = new HashMap<>();
		for (int i = 0; i < A.length; i += step) {
			int num = quickSelect(A, i);
			Integer cnt = candidates.get(num);
			if (cnt != null)
				return num;
			candidates.put(num, 1);
		}

		// count candidates
		for (int i = 0; i < A.length; i++) {
			Integer cnt = candidates.get(A[i]);
			if (cnt != null)
				candidates.put(A[i], cnt + 1);
		}
		// return the satisfied majority
		for (Entry<Integer, Integer> kv : candidates.entrySet()) {
			if ((kv.getValue() - 1) * k >= A.length)
				return kv.getKey();
		}
		return -1; // no answer
	}

	Random ran = new Random();

	public int quickSelect(int[] A, int kSmallest) {
		int l = 0, r = A.length - 1;
		while (l <= r) {
			int pivot = l + ran.nextInt(r - l + 1);
			swap(A, l, pivot);
			int j = l + 1;
			for (int i = l + 1; i <= r; i++) {
				if (A[i] < A[l])
					swap(A, i, j++);
			}
			swap(A, l, j - 1);
			if (j - 1 == kSmallest)
				return A[j - 1];
			else if (j - 1 < kSmallest)
				l = j;
			else
				r = j - 2;
		}

		throw new RuntimeException("Cannot find top " + kSmallest);

	}

	private void swap(int[] A, int i, int j) {
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}

	@Test
	public void test1() {
		int[] A = { 1, 2, 3, 1, 5, 7, 7, 1, 5 };
		// 0 1 2 3 4 5 6 7 8
		// 1 1 1 2 3 5 5 7 7
		// 0 3 6
		int ans = 1;
		int res = majority(A, 3);
		assertEquals(ans, res);
	}

	@Test
	public void test2() {
		int[] A = { 2, 2, 5, 1 };
		int ans = 2;
		int res = majority(A, 3);
		assertEquals(ans, res);
	}

	@Test
	public void test3() {
		int[] A = { 7, 1, 7, 7, 61, 61, 61, 10, 10, 10, 61 };
		// 0 1 2 3 4 5 6 7 8 9 10
		// 1 7 7 7 10 10 10 61 61 61 61
		// 0 3 6 9
		int ans = 61;
		int res = majority(A, 3);
		assertEquals(ans, res);
	}

	@Test
	public void test4() {
		int[] A = { 2, 10, 2, 1, 2 };
		int ans = 2;
		int res = majority(A, 2);
		assertEquals(ans, res);
	}

	@Test
	public void test5() {
		int[] A = { 3, 1, 2, 3, 2, 3, 3, 4, 4, 4 };
		int ans = 3;
		int res = majority(A, 3);
		assertEquals(ans, res);
	}

}
