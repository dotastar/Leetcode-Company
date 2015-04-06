package interview.laicode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Majority Number II
 * Fair
 * Data Structure
 * 
 * Given an integer array of length L, find all numbers that occur more than 1/3
 * * L times if any exist.
 * 
 * Assumptions
 * 
 * The given array is not null
 * 
 * Examples
 * 
 * A = {1, 2, 1, 2, 1}, return [1, 2]
 * A = {1, 2, 1, 2, 3, 3, 1}, return [1]
 * A = {1, 2, 2, 3, 1, 3}, return []
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Majority_Number_II {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Naive solution, Time: O(n), Space: O(n).
	 */
	public List<Integer> majority(int[] A) {
		double threshold = A.length / (double) 3;
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
