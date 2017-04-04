package interview.company.others;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.junit.Test;

/**
 * Given K sorted arrays, merge them to one array
 * 
 * Connectifier
 * 
 * @author yazhoucao
 *
 */
public class MergeKArrays {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(MergeKArrays.class);
	}

	/**
	 * Min-Heap
	 * 
	 * Time: O(klogn), n = total # of elements, k = # of arrays
	 */
	public int[] mergeArrays(int[][] arrays) {
		if (arrays == null)
			return new int[0];
		int n = getTotalLength(arrays);
		int[] res = new int[n];
		PriorityQueue<Node> minHeap = new PriorityQueue<Node>(new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				return n1.getValue() - n2.getValue();
			}
		});
		// initialize minHeap
		for (int i = 0; i < arrays.length; i++) {
			if (arrays[i].length > 0)
				minHeap.add(new Node(arrays[i]));
		}

		// poll from minHeap, merging
		int i = 0; // idx for res array
		while (!minHeap.isEmpty()) {
			Node minNode = minHeap.poll();
			res[i++] = minNode.next();
			if (minNode.hasNext())
				minHeap.add(minNode);
		}

		return res;
	}

	private int getTotalLength(int[][] arrays) {
		int length = 0;
		for (int i = 0; i < arrays.length; i++)
			length += arrays[i].length;
		return length;
	}

	private static class Node {
		int[] a;
		int idx;

		public Node(int[] array) {
			a = array;
			idx = 0;
		}

		public int getValue() {
			return a[idx];
		}

		public boolean hasNext() {
			return idx < a.length;
		}

		public int next() {
			if (hasNext())
				return a[idx++];
			throw new IndexOutOfBoundsException();
		}
	}

	@Test
	public void test1() {
		int[][] arrays = { { 1, 3, 5, 6, 78, 111 }, {}, { 5, 7, 11, 22, 31, 61, 77 }, {},
				{ 0, 11, 94 }, { 0 } };
		int[] res = mergeArrays(arrays);
		assertTrue(isSorted(res));
	}

	@Test
	public void test2() {
		int[][] arrays = { { 111 }, { 1, 3, 5, 6, 78, 111 }, {},
				{ 5, 7, 11, 22, 31, 61, 77 }, {}, { 0, 11, 94 }, { 0 } };
		int[] res = mergeArrays(arrays);
		assertTrue(isSorted(res));
	}

	@Test
	public void test3() {
		int[][] arrays = { { 1, 3, 5, 7, 9 }, { 0, 2, 4, 6, 8 } };
		int[] res = mergeArrays(arrays);
		assertTrue(isSorted(res));
	}

	public boolean isSorted(int[] A) {
		for (int i = 1; i < A.length; i++) {
			if (A[i] < A[i - 1])
				return false;
		}
		return true;
	}
}
