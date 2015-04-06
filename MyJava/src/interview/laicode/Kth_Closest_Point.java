package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import org.junit.Test;

/**
 * Kth Closest Point
 * Fair
 * Data Structure
 * 
 * Given three arrays sorted in ascending order. Pull one number from each array
 * to form a coordinate <x,y,z> in a 3D space. Find the coordinates of the
 * points that is k-th closest to <0,0,0>.
 * 
 * We are using euclidean distance here.
 * 
 * Assumptions
 * 
 * The three given arrays are not null or empty
 * K >= 1 and K <= a.length * b.length * c.length
 * 
 * Return
 * 
 * a size 3 integer list, the first element should be from the first array, the
 * second element should be from the second array and the third should be from
 * the third array
 * 
 * Examples
 * 
 * A = {1, 3, 5}, B = {2, 4}, C = {3, 6}
 * 
 * The closest is <1, 2, 3>, distance is sqrt(1 + 4 + 9)
 * 
 * The 2nd closest is <3, 2, 3>, distance is sqrt(9 + 4 + 9)
 * 
 * @author yazhoucao
 * 
 */
public class Kth_Closest_Point {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Kth_Closest_Point.class);
	}

	public int[] closest(int[] a, int[] b, int[] c, int k) {
		PriorityQueue<Triple> q = new PriorityQueue<Triple>(k,
				new Comparator<Triple>() {
					@Override
					public int compare(Triple p1, Triple p2) {
						if (p1.val == p2.val)
							return 0;
						else
							return p1.val > p2.val ? 1 : -1;
					}
				});
		Set<Triple> visited = new HashSet<Triple>();
		q.add(new Triple(a[0] * a[0] + b[0] * b[0] + c[0] * c[0], 0, 0, 0));
		int[] res = new int[3];
		while (k > 0 && !q.isEmpty()) {
			Triple curMin = q.poll();
			if (visited.add(curMin)) {
				k--;
				int x = curMin.x, y = curMin.y, z = curMin.z;
				if (k == 0) {
					res[0] = a[x];
					res[1] = b[y];
					res[2] = c[z];
				}
				if (x + 1 < a.length)
					q.add(new Triple(a[x + 1] * a[x + 1] + b[y] * b[y] + c[z] * c[z], x + 1, y, z));
				if (y + 1 < b.length)
					q.add(new Triple(a[x] * a[x] + b[y + 1] * b[y + 1] + c[z] * c[z], x, y + 1, z));
				if (z + 1 < c.length)
					q.add(new Triple(a[x] * a[x] + b[y] * b[y] + c[z + 1] * c[z + 1], x, y, z + 1));

			}
		}
		return res;
	}

	@Test
	public void test1() {
		int[] a = { 1, 4, 6 };
		int[] b = { 2, 5 };
		int[] c = { 1, 3 };
		int k = 10;
		int[] ans = { 4, 5, 3 };
		int[] res = closest(a, b, c, k);
		assertTrue("Wrong: " + Arrays.toString(res), Arrays.equals(ans, res));
	}

	public static class Triple {
		long val;
		int x;
		int y;
		int z;

		public Triple(long val, int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.val = val;
		}

		public int hashCode() {
			return 31 * 31 * x + 31 * y + z;
		}

		public boolean equals(Object obj) {
			if (obj instanceof Triple) {
				Triple other = (Triple) obj;
				return this.x == other.x && this.y == other.y
						&& this.z == other.z;
			}
			return false;
		}
	}
}
