package interview.company.epic;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Given number of digits of a phone number and number of disallowed digits as
 * input, find all permutations of numbers which do not have two adjacent
 * numbers the same, i.e. 1232 is allowed but not 1223. Disallowed digits can be
 * up to 3, and can be included along with the phone number. Also the phone
 * number should start with 4 if it contains the number 4.
 * 
 * 
 * Print all valid phone numbers of length n subject to following constraints:
 * If a number contains a 4, it should start with 4
 * No two consecutive digits can be same
 * Three digits (e.g. 7,2,9) will be entirely disallowed, take as input
 * 
 * @author yazhoucao
 * 
 */
public class EnumerateAllValidPhoneNumber {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(EnumerateAllValidPhoneNumber.class);
	}

	/**
	 * This solution didn't enumerate repeated numbers
	 */
	public List<int[]> findNumber(int n, int[] disallowed) {
		List<int[]> combinations = new ArrayList<>();
		int[] allowed = disallowToAllow(disallowed);
		combination(n, 0, allowed, new int[n], combinations);

		List<int[]> res = new ArrayList<>();
		for (int[] comb : combinations) {
			int idx = Arrays.binarySearch(comb, 4);
			if (idx >= 0) {
				swap(comb, 0, idx);
				permutation(comb, 1, res);
			} else
				permutation(comb, 0, res);
		}
		return res;
	}

	/**
	 * allowed choose n
	 */
	public void combination(int n, int start, int[] allowed, int[] choosen,
			List<int[]> res) {
		if (n == 0) {
			res.add(Arrays.copyOf(choosen, choosen.length));
			return;
		}

		for (int i = start; i < allowed.length; i++) {
			choosen[choosen.length - n] = allowed[i];
			combination(n - 1, i + 1, allowed, choosen, res);
			choosen[choosen.length - n] = 0;
		}

	}

	/**
	 * given the numbers (num), enumerate all the permutations
	 */
	public void permutation(int[] num, int start, List<int[]> res) {
		if (start == num.length) {
			res.add(Arrays.copyOf(num, num.length));
			return;
		}

		for (int i = start; i < num.length; i++) {
			swap(num, i, start);
			permutation(num, start + 1, res);
			swap(num, start, i);
		}
	}

	private int[] disallowToAllow(int[] disallowed) {
		int[] allowed = new int[10 - disallowed.length];
		for (int i = 0, j = 0; i < 10 && j < allowed.length; i++) {
			boolean allow = true;
			for (int k = 0; k < disallowed.length; k++) {
				if (i == disallowed[k]) {
					allow = false;
					break;
				}
			}
			if (allow)
				allowed[j++] = i;
		}
		return allowed;
	}

	private void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	@Test
	public void test_findNumber() {
		int[] disallowed = { 2, 5, 7 };
		List<int[]> res = findNumber(2, disallowed);
		for (int[] phone : res)
			System.out.println(Arrays.toString(phone));

	}

	@Test
	public void test_permutation() {
		List<int[]> res = new ArrayList<>();
		int[] arr = { 1, 2, 3, 4 };
		permutation(arr, 0, res);
		assertTrue(res.size() == 24);
		// for (int[] phone : res)
		// System.out.println(Arrays.toString(phone));
	}

	@Test
	public void test_combination() {
		List<int[]> res = new ArrayList<>();
		combination(4, 0, new int[] { 1, 2, 3, 4, 5, 6 }, new int[4], res);
		assertTrue(res.size() == 15);
		// for (int[] phone : res)
		// System.out.println(Arrays.toString(phone));

	}
}
