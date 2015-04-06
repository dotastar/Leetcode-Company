package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import org.junit.Test;

/**
 * Kth Smallest With Only 3, 5, 7 As Factors
 * Fair
 * Data Structure
 * 
 * Find the Kth smallest number s such that s = 3 ^ x * 5 ^ y * 7 ^ z, x > 0 and
 * y > 0 and z > 0, x, y, z are all integers.
 * 
 * Assumptions
 * 
 * K >= 1
 * 
 * Examples
 * 
 * the smallest is 3 * 5 * 7 = 105
 * the 2nd smallest is 3 ^ 2 * 5 * 7 = 315
 * the 3rd smallest is 3 * 5 ^ 2 * 7 = 525
 * the 5th smallest is 3 ^ 3 * 5 * 7 = 945
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Kth_Smallest_With_Only_3_5_7_As_Factors {

	public static void main(String[] args) {
		AutoTestUtils
				.runTestClassAndPrint(Kth_Smallest_With_Only_3_5_7_As_Factors.class);
	}

	public long kth(int k) {
		PriorityQueue<Triple> q = new PriorityQueue<Triple>(k,
				new Comparator<Triple>() {
					@Override
					public int compare(Triple p1, Triple p2) {
						return (int) (p1.val - p2.val);
					}
				});
		Set<Triple> visited = new HashSet<Triple>();
		long res = 1;
		q.add(new Triple(3 * 5 * 7, 1, 1, 1));
		while (k > 0 && !q.isEmpty()) {
			Triple currMin = q.poll();
			if (visited.add(currMin)) {
				k--;
				res = currMin.val;
				int x = currMin.x, y = currMin.y, z = currMin.z;
				q.add(new Triple(currMin.val * 3, x + 1, y, z));
				q.add(new Triple(currMin.val * 5, x, y + 1, z));
				q.add(new Triple(currMin.val * 7, x, y, z + 1));
			}
		}
		return res;
	}

	@Test
	public void test1() {
		int k = 40;
		long ans = 127575;
		long res = kth(k);
		assertTrue("Wrong: " + res, ans == res);
	}

	public static class Triple {
		long val;
		int x;
		int y;
		int z;

		public Triple(long val, int x, int y, int z) {
			this.val = val;
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public int hashCode() {
			return (int) val;
		}

		/**
		 * Because 3,5,7 are all prime numbers, their product must be unique
		 */
		public boolean equals(Object obj) {
			if (obj instanceof Triple) {
				Triple other = (Triple) obj;
				return this.val == other.val;
			}
			return false;
		}
	}
}
