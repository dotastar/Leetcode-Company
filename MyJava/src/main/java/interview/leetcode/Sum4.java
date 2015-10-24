package interview.leetcode;

import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

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
		AutoTestUtils.runTestClassAndPrint(Sum4.class);
	}

	/**
	 * n^2 * log(n) solution
	 */
	public List<List<Integer>> fourSum_Improved(int[] num, int target) {
		Set<List<Integer>> res = new HashSet<>();
		int length = ((num.length - 1) * num.length) / 2;
		Pair[] pairs = new Pair[length];
		for (int i = 0, k = 0; i < num.length - 1; i++) {
			for (int j = i + 1; j < num.length; j++)
				pairs[k++] = new Pair(i, j, num[i] + num[j]);
		}
		Arrays.sort(pairs, new Comparator<Pair>() {
			@Override
			public int compare(Pair p1, Pair p2) {
				return p1.val - p2.val;
			}
		});

		int l = 0, r = pairs.length - 1;
		while (l < r) {
			int sum = pairs[l].val + pairs[r].val;
			if (sum == target) {
				int lEnd = l + 1, rEnd = r - 1;
				while (lEnd < rEnd && pairs[lEnd].val == pairs[l].val)
					lEnd++;
				while (lEnd < rEnd && pairs[r].val == pairs[rEnd].val)
					rEnd--;
				for (int x = l; x < lEnd; x++) {
					for (int y = r; y > rEnd; y--) {
						if (checkNotOverlap(pairs[x], pairs[y])) {
							List<Integer> solu = new ArrayList<>();
							solu.add(num[pairs[x].i]);
							solu.add(num[pairs[x].j]);
							solu.add(num[pairs[y].i]);
							solu.add(num[pairs[y].j]);
							Collections.sort(solu);
							res.add(solu);
						}
					}
				}
				l = lEnd;
				r = rEnd;
			} else if (sum > target)
				r--;
			else
				l++;
		}
		return new ArrayList<List<Integer>>(res);
	}

	private boolean checkNotOverlap(Pair p1, Pair p2) {
		return (p1.i != p2.i && p1.i != p2.j && p1.j != p2.i && p1.j != p2.j);
	}

	public static class Pair {
		int val;
		int i;
		int j;

		public Pair(int i, int j, int val) {
			this.val = val;
			this.i = i;
			this.j = j;
		}

		public String toString() {
			return "<" + i + ", " + j + ">:" + val + " ";
		}
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
				continue; // avoid duplicates
			for (int j = i + 1; j < len - 2; j++) {
				if (j != i + 1 && num[j] == num[j - 1])
					continue; // avoid duplicates
				int l = j + 1;
				int r = len - 1;
				while (l < r) {
					if (l != j + 1 && num[l] == num[l - 1]) {
						l++; // avoid duplicates
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

	@Test
	public void test1() {
		System.out.println("===========================================");
		int[] num = { 2, 1, 0, -1 };
		int target = 2;
		List<List<Integer>> res = fourSum_Improved(num, target);
		System.out.println(res.toString()); // [ -1, 0, 1, 2 ]
	}

	@Test
	public void test2() {
		System.out.println("===========================================");
		int[] num1 = new int[] { -3, -2, -1, 0, 0, 1, 2, 3 };
		for (List<Integer> seq : fourSum_Improved(num1, 0))
			System.out.println(seq.toString());
		// [[-3,-2,2,3],[-3,-1,1,3],[-3,0,0,3],[-3,0,1,2],
		// [-2,-1,0,3],[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]

	}

	@Test
	public void test3() {
		System.out.println("===========================================");
		int[] num = new int[] { 0, 0, 0, 0 };
		int target = 0;
		List<List<Integer>> res = fourSum(num, target);
		System.out.println(res.toString());
	}

}
