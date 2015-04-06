package interview.laicode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Majority Number III
 * Hard
 * Data Structure
 * 
 * Given an integer array of length L, find all numbers that occur more than 1/K
 * * L times if any exist.
 * 
 * Assumptions
 * 
 * The given array is not null or empty
 * K >= 2
 * 
 * Examples
 * 
 * A = {1, 2, 1, 2, 1}, K = 3, return [1, 2]
 * A = {1, 2, 1, 2, 3, 3, 1}, K = 4, return [1, 2, 3]
 * A = {2, 1}, K = 2, return []
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Majority_Number_III {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Naive solution
	 */
	public List<Integer> majority(int[] A, int k) {
		double threshold = A.length / (double) k;
		Set<Integer> resSet = new HashSet<>();
		Map<Integer, Integer> cntMap = new HashMap<>();
		for (int a : A) {
			Integer cnt = cntMap.get(a);
			if (cnt == null)
				cnt = 0;
			cntMap.put(a, ++cnt);
			if (cnt > threshold)
				resSet.add(a);
		}
		return new ArrayList<Integer>(resSet);
	}
}
