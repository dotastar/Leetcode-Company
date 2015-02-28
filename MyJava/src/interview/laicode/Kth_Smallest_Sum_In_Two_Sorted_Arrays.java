package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import org.junit.Test;

/**
 * 
 * @author yazhoucao
 * 
 */
public class Kth_Smallest_Sum_In_Two_Sorted_Arrays {
	static Class<?> c = Kth_Smallest_Sum_In_Two_Sorted_Arrays.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * It's much like if we push all n*m pairs into the heap
	 * and then remove min k times. Only we don't need all n*m pairs.
	 **/
	public int kthSum(int[] A, int[] B, int k) {
		final int m = A.length, n = B.length;
		assert m + n >= k;
		Set<Entry> visited = new HashSet<>(); // for de-duplicates
		PriorityQueue<Entry> minHeap = new PriorityQueue<>();
		Entry res = new Entry(0, 0, A[0] + B[0]);
		minHeap.offer(res);
		while (k > 0) {
			Entry min = minHeap.poll();
			if (visited.contains(min))
				continue;
			// System.out.println(min);
			visited.add(min);
			res = min;
			k--;
			if (min.i + 1 < m)
				minHeap.offer(new Entry(min.i + 1, min.j, A[min.i + 1] + B[min.j]));
			if (min.j + 1 < n)
				minHeap.offer(new Entry(min.i, min.j + 1, A[min.i] + B[min.j + 1]));
		}
		return res.val;
	}

	@Test
	public void test1() {
		int[] A = { 1, 3, 5, 8, 9 };
		int[] B = { 2, 3, 4, 7 };
		assertTrue(kthSum(A, B, 11) == 10);
	}

	private static class Entry implements Comparable<Entry> {
		int i;
		int j;
		int val;

		public Entry(int i, int j, int val) {
			this.i = i;
			this.j = j;
			this.val = val;
		}

		public int compareTo(Entry other) {
			return this.val - other.val;
		}

		@Override
		public String toString() {
			return "[ " + String.valueOf(i) + ", " + String.valueOf(j) + ", "
					+ String.valueOf(val) + " ]";
		}

		@Override
		public int hashCode() {
			return i * 31 * 31 + j * 31 + val;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Entry) {
				Entry o = (Entry) obj;
				return i == o.i && j == o.j && val == o.val;
			} else
				return false;
		}
	}

}
