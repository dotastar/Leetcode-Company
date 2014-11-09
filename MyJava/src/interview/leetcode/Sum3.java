package interview.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an array S of n integers, are there elements a, b, c in S such that a +
 * b + c = 0? Find all unique triplets in the array which gives the sum of zero.
 * 
 * Note: Elements in a triplet (a,b,c) must be in non-descending order. (ie, a ≤
 * b ≤ c) The solution set must not contain duplicate triplets. For example,
 * given array S = {-1 0 1 2 -1 -4},
 * 
 * A solution set is: (-1, 0, 1) (-1, -1, 2)
 * 
 * @author yazhoucao
 * 
 */
public class Sum3 {

	public static void main(String[] args) {
		List<List<Integer>> res0 = threeSum(new int[] { 0, 0, 0, 0 });
		for (List<Integer> sum : res0)
			// [0, 0, 0]
			System.out.println(sum.toString() + "\n");

		List<List<Integer>> res1 = threeSum(new int[] { -2, 0, 0, 2, 2 });
		for (List<Integer> sum : res1)
			// [-2, 0, 2]
			System.out.println(sum.toString());

		List<List<Integer>> res2 = threeSum(new int[] { 1, -1, -1, 0 });
		for (List<Integer> sum : res2)
			// [-1,0,1]
			System.out.println(sum.toString());

	}

	
	
	
	/**
	 * Two pointers
	 * 
	 * Time O(n)
	 * 
	 * @param num
	 * @return
	 */
	public static List<List<Integer>> threeSum(int[] num) {
		Arrays.sort(num);
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		for (int i = 0; i < num.length - 2; i++) {
			if (i > 0 && num[i] == num[i - 1])
				continue; // remove duplicates
			int l = i + 1;
			int r = num.length - 1;

			while (l < r) {
				int sum = num[i] + num[l] + num[r];
				if (sum > 0) {
					do {
						r--; // remove duplicates
					} while (l < r && num[r] == num[r + 1]);
				} else if (sum < 0) {
					do {
						l++; // remove duplicates
					} while (l < r && num[l] == num[l - 1]);
				} else {
					List<Integer> nums = new ArrayList<Integer>();
					nums.add(num[i]);
					nums.add(num[l]);
					nums.add(num[r]);
					res.add(nums);
					do {
						r--;
					} while (l < r && num[r] == num[r + 1]);
					do {
						l++;
					} while (l < r && num[l] == num[l - 1]);
				}
			}
		}
		return res;
	}

	/**
	 * Same solution, second time wrritten
	 * 
	 * 
	 */
	public List<List<Integer>> threeSum2(int[] num) {
		Arrays.sort(num);
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		for (int i = 0; i < num.length - 2; i++) {
			if (i != 0 && num[i] == num[i - 1])
				continue; // ignore dup
			int l = i + 1;
			int r = num.length - 1;
			while (l < r) {
				if (l != i + 1 && num[l] == num[l - 1]) {
					l++; // ignore dup
					continue;
				}
				int sum = num[i] + num[l] + num[r];
				if (sum == 0) {
					List<Integer> comb = new ArrayList<Integer>();
					comb.add(num[i]);
					comb.add(num[l]);
					comb.add(num[r]);
					res.add(comb);
					l++;
					r--;
				} else if (sum > 0) {
					r--;
				} else
					l++;
			}
		}
		return res;
	}
}
