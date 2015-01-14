package interview.epi.chapter13_hashtable;

import interview.AutoTestUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * You are given an array A of n Strings. Compute the K strings that appear most
 * frequently in A.
 * 
 * @author yazhoucao
 * 
 */
public class Q6_Compute_The_K_Most_Frequent_Queries {

	static Class<?> c = Q6_Compute_The_K_Most_Frequent_Queries.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Suppose there are n Strings and m distinct Strings.
	 */

	/**
	 * Naive way to solve it is HashMap + Sorting the HashMap entries.
	 * Time: O(n + mlogm), Space: O(n).
	 */

	/**
	 * A better way is HashMap + MinHeap.
	 * Time: O(n + mlogk), Space: O(n).
	 */

	/**
	 * The ideal way, HashMap + QuickSelect, Time: O(n+m), Space: O(n).
	 * Idea: HashMap count the occurrence, then use quickSelect to sort roughly,
	 * and return the top k elements.
	 */
	public Query[] findKMostFrequent(String[] A, int k) {
		Map<String, Integer> cnts = new HashMap<>();
		for (String s : A) {
			if (!cnts.containsKey(s))
				cnts.put(s, 1);
			else
				cnts.put(s, cnts.get(s) + 1);
		}
		int i = 0;
		Query[] res = new Query[cnts.size()];
		for (Entry<String, Integer> e : cnts.entrySet()) {
			res[i++] = new Query(e.getKey(), e.getValue());
		}

		quickSelect(res, k);
		return Arrays.copyOf(res, k);
	}

	/**
	 * Quick sort subroutine, equals to a roughly sort of top k elements.
	 * Return the k-th largest element.
	 * Time: O(n).
	 */
	private <T extends Comparable<T>> T quickSelect(T[] A, int k) {
		int l = 0, r = A.length - 1;
		Random ran = new Random();
		while (l <= r) {
			int pivotIdx = ran.nextInt(r - l + 1) + l;
			int partIdx = partition(A, pivotIdx, l, r);
			if (partIdx == k - 1)
				return A[partIdx];
			else if (partIdx > k - 1)
				r = partIdx - 1;
			else
				l = partIdx + 1;
		}
		throw new RuntimeException("no k-th node in the array");
	}

	/**
	 * Swap greater to left, decreasing order
	 */
	private <T extends Comparable<T>> int partition(T[] A, int idx, int l, int r) {
		T pivot = A[idx];
		swap(A, idx, l);
		int available = l + 1;
		for (int i = l + 1; l <= r; i++) {
			if (pivot.compareTo(A[i]) > 0)
				swap(A, i, available);
		}
		swap(A, l, available - 1);
		return available - 1;
	}

	private <T> void swap(T[] A, int i, int j) {
		if (i != j) {
			T temp = A[i];
			A[i] = A[j];
			A[j] = temp;
		}
	}

	public static class Query implements Comparable<Query> {
		public String query;
		public int count;

		public Query(String q, int cnt) {
			query = q;
			count = cnt;
		}

		@Override
		public int compareTo(Query o) {
			return count - o.count;
		}
	}

}
