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
		// TODO Auto-generated method stub
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
	 * 
	 * @param num
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

	
	public void swap(int[] num, int i, int j) {
		int tmp = num[i];
		num[i] = num[j];
		num[j] = tmp;
	}
}
