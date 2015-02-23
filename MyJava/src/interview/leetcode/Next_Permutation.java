package interview.leetcode;

import java.util.Arrays;

/**
 * Implement next permutation, which rearranges numbers into the
 * lexicographically next greater permutation of numbers.
 * 
 * If such arrangement is not possible, it must rearrange it as the lowest
 * possible order (ie, sorted in ascending order).
 * 
 * The replacement must be in-place, do not allocate extra memory.
 * 
 * Here are some examples. Inputs are in the left-hand column and its
 * corresponding outputs are in the right-hand column.
 * 
 * 1,2,3 → 1,3,2
 * 
 * 3,2,1 → 1,2,3
 * 
 * 1,1,5 → 1,5,1
 * 
 * @author yazhoucao
 * 
 */
public class Next_Permutation {

	public static void main(String[] args) {
		Next_Permutation o = new Next_Permutation();
		int[] num0 = { 1, 2 };
		o.nextPermutation(num0);
		System.out.println(Arrays.toString(num0));

		int[] num1 = { 1, 2, 3 };
		o.nextPermutation(num1);
		System.out.println(Arrays.toString(num1));

		int[] num2 = { 2, 3, 1 };
		o.nextPermutation(num2);
		System.out.println(Arrays.toString(num2));
	}

	/**
	 * Second time
	 */
	public void nextPermutation3(int[] num) {
		// position to swap,
		// first number that is not in increasing order starts from the end
		int swapIdx = num.length - 2;
		while (swapIdx >= 0 && num[swapIdx] >= num[swapIdx + 1])
			swapIdx--;
		if (swapIdx >= 0) {
			// traverse back to the end, find the smallest number that is
			// greater than num[swap]
			int i = swapIdx + 1;
			while (i < num.length - 1 && num[i + 1] > num[swapIdx])
				i++;
			// increase the permutation by as little as possible.
			swap(num, i, swapIdx);
		}
		// reverse from swap+1 to end
		int l = swapIdx + 1, r = num.length - 1;
		while (l < r) {
			swap(num, l++, r--);
		}
	}

	/**
	 * The key insight is that we want to increase the permutation by as little
	 * as possible. We look at the entry e that appears just before the longest
	 * decreasing suffix. We swap e with that the smallest entry s in the suffix
	 * which is larger than e so as to minimize the change to the prefix. At
	 * last, we should reverse the decreasing suffix so that it is the smallest.
	 */
	public void nextPermutation(int[] num) {
		int start = num.length - 2;
		// find the first number which is not in increasing order from the end
		while (start >= 0 && num[start] >= num[start + 1]) {
			start--; // reverse order traverse
		}
		if (start >= 0) {
			int less = start + 1; // the idx less than num[start]
			// traverse back, find the first number that is less than num[start]
			while (less < num.length && num[less] > num[start])
				less++;
			less--; // the smallest number greater than num[start] is less-1
			swap(num, start, less);
		}
		start++;
		int end = num.length - 1;
		while (start < end) { // reverse all numbers from start to the end
			swap(num, start, end);
			start++;
			end--;
		}
	}

	/**
	 * Same solution, more concise, more comments, from EPI
	 */
	public int[] nextPermutation2(int[] p) {
		int k = p.length - 2;
		while (k >= 0 && p[k] >= p[k + 1]) {
			--k;
		}
		if (k == -1) {
			return new int[0]; // p is the last permutation.
		}

		// Swap the smallest entry after index k that is greater than p[k].
		// We exploit the fact that p[k + 1 : p.size() - 1] is decreasing so if
		// we search in reverse order, the first entry that is greater than p[k]
		// is the smallest such entry.
		for (int i = p.length - 1; i > k; --i) {
			if (p[i] > p[k]) {
				swap(p, k, i);
				break;
			}
		}

		// Since p[k + 1 : p.size() - 1] is in decreasing order, we can build
		// the smallest dictionary ordering of this subarray by reversing it.
		reverse(p, k + 1, p.length - 1);
		return p;
	}

	private void reverse(int[] p, int a, int b) {
		for (int i = a, j = b; i < j; ++i, --j) {
			swap(p, i, j);
		}
	}

	public void swap(int[] num, int i, int j) {
		int tmp = num[i];
		num[i] = num[j];
		num[j] = tmp;
	}
}
