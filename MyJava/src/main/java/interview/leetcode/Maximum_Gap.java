package interview.leetcode;

import java.util.Arrays;

/**
 * Given an unsorted array, find the maximum difference between the successive
 * elements in its sorted form.
 * 
 * Try to solve it in linear time/space.
 * 
 * Return 0 if the array contains less than 2 elements.
 * 
 * You may assume all elements in the array are non-negative integers and fit in
 * the 32-bit signed integer range.
 * 
 * @author yazhoucao
 * 
 */
public class Maximum_Gap {

	public static void main(String[] args) {
		assert maximumGap2(new int[] { 2, 99999999 }) == 99999997;
		assert maximumGap2(new int[] { 1, 1, 1, 1, 1, 5, 5, 5, 5, 5 }) == 4;
		assert maximumGap2(new int[] { 1, 1000 }) == 999;
		System.out.println();
		System.out.println(Math.ceil(2.1) + "," + Math.ceil(2.8));
	}

	/**
	 * Time: O(n), Space: O(n) solution.
	 * Idea: like bucket sort
	 * Suppose there are N elements and they range from A to B.
	 * 
	 * Then the maximum gap will be no smaller than ceiling[(B - A) / (N - 1)]
	 * 
	 * Let the length of a bucket to be len = ceiling[(B - A) / (N - 1)], then
	 * we will have at most num = (B - A) / len + 1 of bucket
	 * 
	 * for any number K in the array, we can easily find out which bucket it
	 * belongs by calculating loc = (K - A) / len and therefore maintain the
	 * maximum and minimum elements in each bucket.
	 * 
	 * Since the maximum difference between elements in the same buckets will be
	 * at most len - 1, so the final answer will not be taken from two elements
	 * in the same buckets.
	 * 
	 * For each non-empty buckets p, find the next non-empty buckets q, then
	 * q.min - p.max could be the potential answer to the question. Return the
	 * maximum of all those values.
	 */
	public static int maximumGap2(int[] num) {
		if (num.length < 2)
			return 0;
		// find the range of num[]
		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		for (int i = 0; i < num.length; i++) {
			min = Math.min(num[i], min);
			max = Math.max(num[i], max);
		}

		// create and fill in the bucket
		final int BucketLen = (int) Math.ceil((max - min)
				/ (float) (num.length - 1));
		final int Size = (max - min) / BucketLen + 1;
		// a flag to differentiate initialized and uninitialized buckets
		boolean[] initialized = new boolean[Size];
		int[][] buckets = new int[2][Size]; // first row is max, second is min
		for (int i = 0; i < num.length; i++) {
			int idx = (num[i] - min) / BucketLen; // idx of bucket
			if (initialized[idx]) {
				buckets[0][idx] = Math.max(num[i], buckets[0][idx]);
				buckets[1][idx] = Math.min(num[i], buckets[1][idx]);
			} else {
				buckets[0][idx] = buckets[1][idx] = num[i];
				initialized[idx] = true;
			}
		}

		// calculate gap between each neighbor buckets
		int first = 0;
		// move to the index of the first bucket that is not empty
		while (!initialized[first])
			first++;
		int gap = buckets[0][first] - buckets[1][first];
		for (int prevMax = buckets[0][first], i = first + 1; i < Size; i++) {
			if (initialized[i]) {
				gap = Math.max(buckets[1][i] - prevMax, gap);
				prevMax = buckets[0][i];
			}
		}
		return gap;
	}

	/**
	 * Sort and calculate gap between each neighbor's
	 * Time: O(nlogn), Space: O(1).
	 */
	public int maximumGap(int[] num) {
		Arrays.sort(num);
		int gap = 0;
		for (int i = 1; i < num.length; i++) {
			if (num[i] - num[i - 1] > gap)
				gap = num[i] - num[i - 1];
		}
		return gap;
	}
}
