package interview.company.others;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.Random;

import org.junit.Test;

/**
 * Two elements a[i] and a[j] form an inversion if a[i] > a[j] and i < j
 * 
 * Inversion Count for an array indicates – how far (or close) the array is from
 * being sorted. If array is already sorted then inversion count is 0. If array
 * is sorted in reverse order that inversion count is the maximum.
 * 
 * http://www.geeksforgeeks.org/counting-inversions/
 * 
 * @author yazhoucao
 *
 */
public class InversionPairs {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(InversionPairs.class);
	}

	/**
	 * Exactly merge sort
	 */
	public int invCount(int[] A) {
		int[] temp = new int[A.length];
		return invCount(A, temp, 0, A.length - 1);
	}

	/**
	 * merge sort while counting inversion pairs
	 */
	private int invCount(int[] A, int[] temp, int l, int r) {
		if (l >= r)
			return 0;

		int mid = l + (r - l) / 2;
		int invCnt = invCount(A, temp, l, mid) + invCount(A, temp, mid + 1, r);
		invCnt += merge(A, temp, l, mid, r);
		return invCnt;
	}

	/**
	 * merge [l, mid] and [mid+1, r]
	 */
	private int merge(int[] A, int[] temp, int l, int mid, int r) {
		int invCnt = 0;
		System.arraycopy(A, l, temp, l, mid - l + 1);
		for (int k = l, i = l, j = mid + 1; k <= r; k++) {
			if (i > mid || (j <= r && temp[i] > A[j])) {
				if (i <= mid)
					// count left part because we are skipping j
					invCnt += mid - i + 1;
				A[k] = A[j++];
			} else
				A[k] = temp[i++];
		}
		return invCnt;
	}

	/**
	 * Naive solution
	 */
	public int invCount_naive(int[] A) {
		int invCnt = 0;
		for (int i = 0; i < A.length; i++) {
			for (int j = i + 1; j < A.length; j++) {
				if (A[i] > A[j])
					invCnt++;
			}
		}
		return invCnt;
	}

	@Test
	public void test1() {
		int[] A = { 1 };
		int ans = 0;
		int res = invCount(A);
		assertTrue("Wrong answer: " + res, ans == res);
	}

	@Test
	public void test2() {
		int[] A = { 2, 1 };
		int ans = 1;
		int res = invCount(A);
		assertTrue("Wrong answer: " + res, ans == res);
	}

	@Test
	public void test3() {
		int[] A = { 5, 2, 4, 3, 6, 1, 8, 0 };
		int ans = invCount_naive(A);
		int res = invCount(A);
		assertTrue("Wrong answer: " + res, ans == res);
	}

	@Test
	public void test_Random() {
		final int times = 50, valueRange = 100000;
		final int min_size = 1, max_size = 50;
		Random ran = new Random();
		for (int i = 0; i < times; i++) {
			// constructing input A
			int size = min_size + ran.nextInt(max_size);
			int[] A = new int[size];
			for (int j = 0; j < size; j++)
				A[j] = ran.nextInt(valueRange);

			// testing
			int ans = invCount_naive(A);
			int res = invCount(A);
			assertTrue("Wrong answer: " + res, ans == res);
		}
	}

}
