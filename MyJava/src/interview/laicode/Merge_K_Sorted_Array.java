package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.junit.Test;

/**
 * 
 * Merge K Sorted Array
 * Fair
 * Data Structure
 * 
 * Merge K sorted array into one big sorted array in ascending order.
 * 
 * Assumptions
 * 
 * The input arrayOfArrays is not null, none of the arrays is null either.
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Merge_K_Sorted_Array {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Merge_K_Sorted_Array.class);
	}

	public int[] merge(int[][] A) {
		int m = A.length;
		int length = 0;
		for (int i = 0; i < m; i++)
			length += A[i].length;

		int[] res = new int[length];
		PriorityQueue<Stream> minHeap = new PriorityQueue<>(m,
				new Comparator<Stream>() {
					@Override
					public int compare(Stream s1, Stream s2) {
						return s1.peek() - s2.peek();
					}
				});

		for (int i = 0; i < m; i++) {
			if (A[i].length > 0)
				minHeap.add(new Stream(A[i]));
		}
		int i = 0;
		while (!minHeap.isEmpty()) {
			Stream minStrm = minHeap.poll();
			res[i++] = minStrm.next();
			if (minStrm.hasNext())
				minHeap.add(minStrm);
		}

		return res;
	}

	public static class Stream {
		int[] data;
		int i;

		public Stream(int[] data) {
			this.data = data;
			i = 0;
		}

		public int peek() {
			return i == data.length ? Integer.MAX_VALUE : data[i];
		}

		public boolean hasNext() {
			return i < data.length;
		}

		public int next() {
			if (hasNext())
				return data[i++];
			else
				return Integer.MAX_VALUE;
		}
	}

	@Test
	public void test1() {
		int[][] A = {

		{ 1, 5, 7, 12, 52, 61, 77 },

		{ 0, 2, 8, 10, 88 },

		{ 3, 4, 5, 6, 55, 66, 99 },

		{ -1, 19 } };

		int[] res = merge(A);
		assertTrue(isSorted(res));
	}

	private boolean isSorted(int[] A) {
		for (int i = 0; i < A.length - 1; i++) {
			if (A[i + 1] < A[i])
				return false;
		}
		return true;
	}
}
