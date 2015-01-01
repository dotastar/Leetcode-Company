package interview.epi.chaper11_heap;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import org.junit.Test;

public class Q1_Merge_Sorted_Files {

	static Class<?> c = Q1_Merge_Sorted_Files.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Time: O(nlg(k)), k = sortedArrays.length, Space: O(k)
	 * 
	 * Default PriorityQueue of Java is a min heap.
	 */
	public static List<Integer> mergeSortedArrays(int[][] sortedArrays) {
		PriorityQueue<ArrIterator> heap = new PriorityQueue<>();
		for (int i = 0; i < sortedArrays.length; i++) {
			if (sortedArrays[i].length > 0)
				heap.add(new ArrIterator(sortedArrays[i], i));
		}

		List<Integer> res = new ArrayList<>();
		while (!heap.isEmpty()) {
			ArrIterator min = heap.poll();
			res.add(min.next());
			if (min.hasNext())
				heap.add(min);
		}
		return res;
	}

	public static class ArrIterator implements Comparable<ArrIterator> {
		int[] arr;
		int curr;
		int arrNo;

		public ArrIterator(int[] arr, int arrNo) {
			this.arr = arr;
			this.arrNo = arrNo;
			curr = 0;
		}

		@Override
		public int compareTo(ArrIterator o) {
			return arr[curr] - o.arr[o.curr];
		}

		public boolean hasNext() {
			return curr < arr.length;
		}

		public int next() {
			return arr[curr++];
		}

		public void reset() {
			curr = 0;
		}
	}

	/****************** Unit Test ******************/

	@Test
	public void test0() {
		Random rnd = new Random();
		for (int times = 0; times < 10; ++times) {
			int n = rnd.nextInt(10) + 1;
			int[][] S = new int[n + 1][];
			for (int i = 0; i < n; ++i) {
				S[i] = new int[rnd.nextInt(100)];
				for (int j = 0; j < S[i].length; ++j) {
					S[i][j] = rnd.nextInt(10000);
				}
				Arrays.sort(S[i]);
			}
			S[n] = new int[0]; // add an empty array for test

			List<Integer> ans = mergeSortedArrays(S);
			System.out.println(ans.toString());
			// check is sorted
			for (int i = 1; i < ans.size(); ++i) {
				assertTrue(ans.get(i - 1) <= ans.get(i));
			}
		}
	}
}
