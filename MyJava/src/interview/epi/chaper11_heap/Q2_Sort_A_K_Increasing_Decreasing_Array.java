package interview.epi.chaper11_heap;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

import org.junit.Test;

/**
 * Problem 11.2
 * Design an efficient algorithm for sorting a k-increasing-decreasing array.
 * You are given another array of the same size that the result should be
 * written to, and you can use O(k) additional storage.
 * 
 * @author yazhoucao
 * 
 */
public class Q2_Sort_A_K_Increasing_Decreasing_Array {

	static Class<?> c = Q2_Sort_A_K_Increasing_Decreasing_Array.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Similar to Problem 11.1, just notice here we have decreasing and
	 * increasing arrays.
	 * 
	 * Time: O(nln(k)), Space: O(k).
	 */
	public int[] sortKIncreasingDecreasingArray(int[] A, int k) {
		assert A.length % k == 0;
		int[] res = new int[A.length];
		PriorityQueue<ArrIter> heap = new PriorityQueue<ArrIter>();
		boolean increasing = true;
		for (int i = 0; i < A.length; i += k) {
			if (increasing)
				heap.add(new ArrIter(A, i, i + k - 1, increasing));
			else
				heap.add(new ArrIter(A, i + k - 1, i, increasing));
			increasing = !increasing;
		}
		for (int i = 0; i < A.length; i++) {
			ArrIter min = heap.poll();
			res[i] = min.next();
			if (min.hasNext())
				heap.add(min);
		}
		return res;
	}

	/****************** Unit Test ******************/

	@Test
	public void test0() {
		Random rnd = new Random();
		for (int times = 0; times < 10; ++times) {
			int n = rnd.nextInt(10) + 1;
			int k = rnd.nextInt(10) + 1;
			int[] A = new int[n * k];
			int[] temp = new int[k];
			int idx = 0;
			boolean increasing = true;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < k; j++)
					temp[j] = rnd.nextInt(1000);
				Arrays.sort(temp);
				if (!increasing)
					reverse(temp);
				increasing = !increasing;

				System.arraycopy(temp, 0, A, idx, k);
				idx += k;
			}
			System.out.println("K:"+k+"\tInput:"+Arrays.toString(A));
			int[] ans = sortKIncreasingDecreasingArray(A, k);
			System.out.println(Arrays.toString(ans));
			// check is sorted
			for (int i = 1; i < ans.length; ++i) {
				assertTrue(ans[i - 1] <= ans[i]);
			}
		}
	}

	private void reverse(int[] arr) {
		int l = 0, r = arr.length - 1;
		while (l < r) {
			int tmp = arr[l];
			arr[l] = arr[r];
			arr[r] = tmp;
			l++;
			r--;
		}
	}

	public static class ArrIter implements Comparable<ArrIter> {
		boolean isIncreasing;
		int start, end;
		int[] arr;
		int curr;

		public ArrIter(int[] arr, int start, int end, boolean increasing) {
			this.arr = arr;
			this.start = start;
			this.end = end;
			curr = start;
			this.isIncreasing = increasing;
		}

		public boolean hasNext() {
			if (isIncreasing)
				return curr <= end;
			else
				return curr >= end;
		}

		public int next() {
			if (isIncreasing)
				return arr[curr++];
			else
				return arr[curr--];
		}

		@Override
		public int compareTo(ArrIter o) {
			return arr[curr] - o.arr[o.curr];
		}
	}
}
