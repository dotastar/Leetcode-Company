package interview.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Given an array S of n integers, are there elements a, b, c, and d in S such
 * that a + b + c + d = target? Find all unique quadruplets in the array which
 * gives the sum of target.
 * 
 * Note: Elements in a quadruplet (a,b,c,d) must be in non-descending order.
 * (ie, a ≤ b ≤ c ≤ d) The solution set must not contain duplicate quadruplets.
 * For example, given array S = {1 0 -1 0 -2 2}, and target = 0.
 * 
 * A solution set is: (-1, 0, 0, 1) (-2, -1, 1, 2) (-2, 0, 0, 2)
 * 
 * @author yazhoucao
 * 
 */
public class Sum4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] num1 = new int[] { -3, -2, -1, 0, 0, 1, 2, 3 };
		int[] num2 = new int[] { 0, 0, 0, 0 };
		// int[] num3 = new int[] { -3, -2, -1, 0, 0, 1, 2, 3 };
		for (List<Integer> seq : fourSum(num1, 0))
			System.out.println(seq.toString());
		// [[-3,-2,2,3],[-3,-1,1,3],[-3,0,0,3],[-3,0,1,2],
		// [-2,-1,0,3],[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
		System.out.println("===========================================");

		for (List<Integer> seq : fourSum(num2, 0))
			System.out.println(seq.toString());
		System.out.println("===========================================");

	}

	/**
	 * Solution with a HashSet to avoid duplicates
	 */
	public static List<List<Integer>> fourSum(int[] num, int target) {
		Arrays.sort(num);
		Set<List<Integer>> set = new HashSet<List<Integer>>();
		for (int i = 0; i < num.length - 1; i++) {
			for (int j = i + 1; j < num.length; j++) {
				int sum = num[i] + num[j];
				int l = j + 1;
				int r = num.length - 1;
				while (l < r) {
					if (sum + num[l] + num[r] > target) {
						r--;
					} else if (sum + num[l] + num[r] < target) {
						l++;
					} else {
						List<Integer> sequence = new ArrayList<Integer>();
						sequence.add(num[i]);
						sequence.add(num[j]);
						sequence.add(num[l]);
						sequence.add(num[r]);
						set.add(sequence);
						r--;
						l++;
					}
				}
			}
		}
		List<List<Integer>> res = new ArrayList<List<Integer>>(set);
		return res;
	}

	/**
	 * Solution without using HashSet to avoid duplicates
	 * 
	 */
	public List<List<Integer>> fourSum2(int[] num, int target) {
		Arrays.sort(num);
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		int len = num.length;
		for (int i = 0; i < len - 3; i++) {
			if (i != 0 && num[i] == num[i - 1])
				continue;	//avoid duplicates
			for (int j = i + 1; j < len - 2; j++) {
				if (j != i + 1 && num[j] == num[j - 1])
					continue;	//avoid duplicates
				int l = j + 1;
				int r = len - 1;
				while (l < r) {
					if (l != j + 1 && num[l] == num[l - 1]) {
						l++;	//avoid duplicates
						continue;
					}

					int sum = num[i] + num[j] + num[l] + num[r];
					if (sum > target) {
						r--;
					} else if (sum < target) {
						l++;
					} else {
						List<Integer> comb = new ArrayList<Integer>();
						comb.add(num[i]);
						comb.add(num[j]);
						comb.add(num[l]);
						comb.add(num[r]);
						res.add(comb);
						l++;
						r--;
					}
				}
			}
		}

		return res;
	}
}
