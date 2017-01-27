package interview.epi.chapter15_bst;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

public class Q7_Compute_The_Closest_Entries_In_Three_Sorted_Array {

	public static int findMinDistanceSortedArrays(
			List<? extends List<Integer>> arrs) {
		// Pointers for each of arrs.
		List<Integer> idx = new ArrayList<>(arrs.size());
		for (int i = 0; i < arrs.size(); i++)
			idx.add(0);

		int minDis = Integer.MAX_VALUE;
		NavigableSet<ArrData> currentHeads = new TreeSet<>();	// use BST
		// Each of arrs puts its minimum element into current_heads.
		for (int i = 0; i < arrs.size(); ++i) {
			if (idx.get(i) >= arrs.get(i).size())
				return minDis;
			currentHeads.add(new ArrData(i, arrs.get(i).get(idx.get(i))));
		}
		while (true) {
			minDis = Math.min(minDis, currentHeads.last().val - currentHeads.first().val);
			int tar = currentHeads.first().idx;
			// Return if there is no remaining element in one array.
			idx.set(tar, idx.get(tar) + 1);
			if (idx.get(tar) >= arrs.get(tar).size())
				return minDis;
			
			currentHeads.pollFirst();
			currentHeads.add(new ArrData(tar, arrs.get(tar).get(idx.get(tar))));
		}
	}
	
	private static class ArrData implements Comparable<ArrData> {
		public int idx;
		public int val;

		public ArrData(int idx, int val) {
			this.idx = idx;
			this.val = val;
		}

		@Override
		public int compareTo(ArrData o) {
			int result = Integer.valueOf(val).compareTo(o.val);
			if (result == 0) {
				result = Integer.valueOf(idx).compareTo(o.idx);
			}
			return result;
		}
	}

}
