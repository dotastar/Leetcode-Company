package interview.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Given a collection of numbers that might contain duplicates, return all
 * possible unique permutations.
 * 
 * For example, [1,1,2] have the following unique permutations:
 * 
 * [1,1,2], [1,2,1], and [2,1,1].
 * 
 * @author yazhoucao
 * 
 */
public class Permutations_II {

	public static void main(String[] args) {
		Permutations_II obj = new Permutations_II();
		List<List<Integer>> res = obj.permuteUnique(new int[] { 1, 2, 1 });
		for (List<Integer> perm : res)
			System.out.println(perm);
	}

	/**
	 * For each number in the array, swap it with every element after it.
	 * 
	 * To avoid duplicate, we need to check the existing sequence first.
	 * 
	 * Time: O(n!) factorial
	 */
	public List<List<Integer>> permuteUnique(int[] num) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		permute(num, 0, res);
		return res;
	}

	public void permute(int[] num, int start, List<List<Integer>> res) {
		if (start == num.length) {
			List<Integer> perm = new ArrayList<Integer>();
			for (int n : num)
				perm.add(n);
			res.add(perm);
			return;
		}
		// swap 'start' and its element after it
		for (int i = start; i < num.length; i++) {
			if (containsNotDuplicate(num, start, i)) {
				swap(num, i, start);
				permute(num, start + 1, res);
				swap(num, start, i);
			}
		}
	}

	public boolean containsNotDuplicate(int[] num, int start, int end) {
		for (int k = start; k <= end - 1; k++)
			if (num[k] == num[end])
				return false;
		return true;
	}

	
	/**
	 * Iteration, use HashSet to deduplicate
	 * 
	 */
    public List<List<Integer>> permuteUnique2(int[] num) {
        List<List<Integer>> perms = new ArrayList<List<Integer>>();
        perms.add(new ArrayList<Integer>());
        for(int n : num){
            Set<List<Integer>> current = new HashSet<List<Integer>>();
            for(List<Integer> perm : perms){
                for(int i=0; i<perm.size(); i++){
                    List<Integer> newperm = new ArrayList<Integer>(perm);
                    newperm.add(i, n);
                    current.add(newperm);
                }
                perm.add(n);
                current.add(perm);
            }
            perms = new ArrayList<List<Integer>>(current);
        }
        return perms;
    }
    
    
	private void swap(int[] num, int i, int j) {
		int tmp = num[i];
		num[i] = num[j];
		num[j] = tmp;
	}
}
