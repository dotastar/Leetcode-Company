package interview.laicode;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Kth Smallest With Only 2, 3 As Factors
 * Fair
 * Data Structure
 * 
 * Find the Kth smallest number s such that s = 2 ^ x * 3 ^ y, x >= 0 and y >=
 * 0, x and y are all integers.
 * 
 * Assumptions
 * 
 * K >= 1
 * 
 * Examples
 * 
 * the smallest is 1
 * the 2nd smallest is 2
 * the 3rd smallest is 3
 * the 5th smallest is 2 * 3 = 6
 * the 6th smallest is 2 ^ 3 * 3 ^ 0 = 8
 * 
 * @author yazhoucao
 * 
 */
public class Kth_Smallest_With_Only_2_3_As_Factors {

	public static void main(String[] args) {
		
	}

	public int kth(int k) {
		PriorityQueue<Pair> q = new PriorityQueue<Pair>(k,
				new Comparator<Pair>() {
					@Override
					public int compare(Pair p1, Pair p2) {
						return p1.val - p2.val;
					}
				});
		Set<Pair> visited = new HashSet<Pair>();
		int res = 1;
		q.add(new Pair(1, 0, 0));
		while (k > 0 && !q.isEmpty()) {
			Pair currMin = q.poll();
			if (visited.add(currMin)) {
				k--;
				res = currMin.val;
				q.add(new Pair(currMin.val * 2, currMin.x + 1, currMin.y));
				q.add(new Pair(currMin.val * 3, currMin.x, currMin.y + 1));
			}
		}
		return res;
	}

	public static class Pair {
		int val;
		int x;
		int y;

		public Pair(int val, int x, int y) {
			this.val = val;
			this.x = x;
			this.y = y;
		}

		public int hashCode() {
			return 31 * x + y;
		}

		public boolean equals(Object obj) {
			if (obj instanceof Pair) {
				Pair other = (Pair) obj;
				return this.x == other.x && this.y == other.y;
			}
			return false;
		}
	}
}
