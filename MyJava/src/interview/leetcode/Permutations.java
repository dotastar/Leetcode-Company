package interview.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a collection of numbers, return all possible permutations.
 * 
 * For example, [1,2,3] have the following permutations: [1,2,3], [1,3,2],
 * [2,1,3], [2,3,1], [3,1,2], and [3,2,1].
 * 
 * @author yazhoucao
 * 
 */
public class Permutations {

	public static void main(String[] args) {
		List<List<Integer>> lists = permute(new int[] { 1, 2, 3 });
		for (List<Integer> l : lists)
			System.out.println(l.toString());
	}

	/**
	 * Recursion, Time: O(n!) factorial, Space: O(lg(n))
	 * 
	 * Idea: Swap each element with each element after it.
	 */
	public static List<List<Integer>> permute(int[] num) {
		List<List<Integer>> lists = new ArrayList<List<Integer>>();
		if (num.length == 0)
			return lists;
		perm(lists, num, 0);
		return lists;
	}

	public static void perm(List<List<Integer>> perms, int[] num, int start) {
		if (start == num.length) {
			List<Integer> list = new ArrayList<Integer>();
			for (int i : num)
				list.add(i);
			perms.add(list);
		}
		// swap 'start' and its element after it
		for (int i = start; i < num.length; i++) {
			swap(num, i, start);
			perm(perms, num, start + 1);
			swap(num, start, i);
		}
	}

	/**
	 * Iteration, Time: Exponential, Space: O(1)
	 */
	public static List<List<Integer>> permute2(int[] num) {
		List<List<Integer>> perms = new ArrayList<List<Integer>>();
		perms.add(new ArrayList<Integer>());
		// for each number, add it to old permutations to generate new perms
		for (int n : num) {
			int len = perms.size();
			// generate permutations with the current number and permutation
			for (int j = 0; j < len; j++) {
				List<Integer> perm = perms.get(j);
				// insert number into every possible position
				for (int i = 0; i < perm.size(); i++) {
					List<Integer> newperm = new ArrayList<Integer>(perm);
					newperm.add(i, n); // insert into position i with number n
					perms.add(newperm);
				}
				perm.add(n);
			}
		}
		return perms;
	}

	private static void swap(int[] num, int i, int j) {
		int tmp = num[i];
		num[i] = num[j];
		num[j] = tmp;
	}
}
