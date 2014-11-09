package interview.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a collection of integers that might contain duplicates, S, return all
 * possible subsets.
 * 
 * Note: Elements in a subset must be in non-descending order. The solution set
 * must not contain duplicate subsets.
 * 
 * For example, If S = [1,2,2], a solution is: [ [2], [1], [1,2,2], [2,2],
 * [1,2], [] ]
 * 
 * Differences between I and II is if allow duplicates in the array num.
 * 
 * @author yazhoucao
 * 
 */
public class Subsets_II {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] num0 = new int[] { 2, 2 };

		List<List<Integer>> lists = subsetsWithDup2(num0);
		for (List<Integer> l : lists)
			System.out.println(l.toString());
	}

	/**
	 * Time: O(n!) factorial, Space: O(n)
	 * 
	 * Differences is that, if the neighbor two number are equal, we have to use
	 * previous(last round subsets) instead of lists (all previous subsets), and
	 * the current list is previous list + num[i]. If add all subsets in lists,
	 * it will generate duplicates. And the same for the single number subset.
	 * 
	 * In Subset I, current list is previous all lists + num[i], this is the
	 * difference.
	 * 
	 */
	public static List<List<Integer>> subsetsWithDup(int[] num) {
		List<List<Integer>> lists = new ArrayList<List<Integer>>();
		int len = num.length;
		Arrays.sort(num);

		List<List<Integer>> previous = new ArrayList<List<Integer>>();
		for (int i = 0; i < len; i++) {
			// get existing sets
			if (i == 0 || num[i] != num[i - 1] || previous.size() == 0) {
				previous = new ArrayList<List<Integer>>();
				for (List<Integer> prev : lists)
					previous.add(new ArrayList<Integer>(prev));
			}

			// add current number to each element of the set
			for (List<Integer> subset : previous) {
				subset.add(num[i]);
			}

			// add each single number as a set, only if current element is
			// different with previous
			if (i == 0 || num[i] != num[i - 1]) {
				List<Integer> single = new ArrayList<Integer>();
				single.add(num[i]);
				previous.add(single);
			}

			// add all set created in this iteration
			for (List<Integer> temp : previous) {
				lists.add(new ArrayList<Integer>(temp));
			}
		}

		lists.add(new ArrayList<Integer>());
		return lists;
	}

	/**
	 * Second practice, same solution, more compact code
	 */
	public static List<List<Integer>> subsetsWithDup2(int[] num) {
		Arrays.sort(num);
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		List<List<Integer>> current = new ArrayList<List<Integer>>();
		for (int i = 0; i < num.length; i++) {
			List<List<Integer>> previous;
			// if is dup, only count the subsets of last round
			if (i > 0 && num[i] == num[i - 1])
				previous = current;
			else
				previous = res; // not dup, count all the previous results

			current = new ArrayList<List<Integer>>();
			for (List<Integer> set : previous) {
				List<Integer> copy = new ArrayList<Integer>(set);
				copy.add(num[i]);
				current.add(copy);
			}

			if (i == 0 || num[i] != num[i - 1]) {	//not dup
				List<Integer> single = new ArrayList<Integer>();
				single.add(num[i]);
				current.add(single);
			}

			res.addAll(current);
		}
		res.add(new ArrayList<Integer>());
		return res;
	}

}
