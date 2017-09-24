package general.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static general.algorithms.Sortings.Record.Type.Type1;
import static general.algorithms.Sortings.Record.Type.Type2;
import static general.algorithms.Sortings.Record.Type.Type3;
import static org.junit.Assert.assertTrue;

public class Sortings {

	public static void main(String[] args) {
		sortByJava();

		Result res = JUnitCore.runClasses(Sortings.class);
		for (Failure f : res.getFailures()) {
			System.err.println(f.toString());
			System.err.println(f.getTrace().toString());
		}
		System.out.println("Total Tests: " + res.getRunCount());
		System.out.println("Failures : " + res.getFailureCount());

	}

	@AllArgsConstructor @Data
	public static class Record {
		public int numValue;
		public boolean boolVal;
		public Type type;

		enum Type {
			Type1,
			Type2,
			Type3
		}
	}

	private static void sortByJava() {
		List<Record> records = new ArrayList<>();
		records.add(new Record(3, true, Type3));
		records.add(new Record(1, false, Type1));
		records.add(new Record(1, true, Type1));
		records.add(new Record(1, true, Type2));
		records.add(new Record(2, true, Type3));
		records.add(new Record(5, true, Type1));
		records.add(new Record(4, false, Type2));

		System.out.println("Before sorting:");
		System.out.println(records.stream().map(record -> record.toString() + "\n").reduce(String::concat));

		records.sort(Comparator.comparing(Record::isBoolVal)
				.reversed()
				.thenComparing(Record::getType)
				.thenComparing(Record::getNumValue));

		System.out.println("Printing sorted order:");
		System.out.println(records.stream().map(record -> record.toString() + "\n").reduce(String::concat));
	}

	/************************** Quick Sort **************************/
	public static class QuickSort {
		private QuickSort() {
		}

		private static Random ran = new Random();

		/**
		 * QuickSort Array
		 * Time: average O(nlog), worst O(n^2)
		 * Unstable
		 */
		public static void quickSort(int[] A) {
			quickSort(A, 0, A.length - 1);
		}

		private static void quickSort(int[] A, int l, int r) {
			if (l >= r)
				return;
			int pivot = l + ran.nextInt(r - l);
			int mid = partition(A, l, r, pivot);
			quickSort(A, l, mid - 1);
			quickSort(A, mid + 1, r);
		}

		private static int partition(int[] A, int l, int r, int pivot) {
			assert l < r;
			int pivotVal = A[pivot];
			swap(A, l, pivot);
			int i = l + 1;
			while (i <= r) {
				if (A[i] < pivotVal)
					i++;
				else
					swap(A, i, r--);
			}
			swap(A, i - 1, l);
			return i - 1;
		}

		/**
		 * QuickSort Linked List
		 */
		public static ListNode quickSortList(ListNode head) {
			if (head == null || head.next == null)
				return head;
			ListNode pivot = head; // use head as pivot
			// partition list
			ListNode l1 = new ListNode(0), l2 = new ListNode(0);
			ListNode curr1 = l1, curr2 = l2;
			while (head.next != null) {
				if (head.next.val < pivot.val) {
					curr1.next = head.next;
					curr1 = curr1.next;
				} else {
					curr2.next = head.next;
					curr2 = curr2.next;
				}
				head = head.next;
			}
			// append pivot to the end of l1
			curr1.next = pivot;
			pivot.next = null;
			curr2.next = null;

			l1 = quickSortList(l1.next);
			l2 = quickSortList(l2.next);
			pivot.next = l2;
			return l1;
		}
	}

	/************************** Merge Sort **************************/
	public static class MergeSort {
		private MergeSort() {
		}

		/**
		 * Time: O(logn) (Worst case), Space: O(n).
		 * Stable sort
		 */
		public static int[] mergeSort(int[] A) {
			msort(A, new int[A.length], 0, A.length - 1);
			return A;
		}

		private static void msort(int[] A, int[] aux, int l, int r) {
			if (l >= r)
				return;
			int mid = l + (r - l) / 2;
			msort(A, aux, l, mid);
			msort(A, aux, mid + 1, r);
			merge(A, aux, l, mid, r);
		}

		private static void merge(int[] A, int[] aux, int l, int m, int r) {
			assert isSorted(A, l, m);
			assert isSorted(A, m + 1, r);
			System.arraycopy(A, l, aux, l, r - l + 1);
			for (int i = l, j = m + 1, k = l; k <= r; k++) {
				if (i > m || (j <= r && aux[j] < aux[i]))
					A[k] = aux[j++];
				else
					A[k] = aux[i++];
			}
			assert isSorted(A, l, r);
		}

		/**
		 * MergeSort Linked List
		 */
		public ListNode mergeSortList(ListNode head) {
			if (head == null || head.next == null)
				return head;
			// break list in half
			ListNode mid = head, fast = head.next;
			while (fast != null && fast.next != null) {
				fast = fast.next.next;
				mid = mid.next;
			}
			ListNode l1 = head, l2 = mid.next;
			mid.next = null; // break l1 and l2
			l2 = mergeSortList(l2);
			l1 = mergeSortList(l1);

			// merge l1, l2
			ListNode prehead = new ListNode(0);
			ListNode curr = prehead;
			while (l1 != null && l2 != null) {
				if (l1.val < l2.val) {
					curr.next = l1;
					l1 = l1.next;
				} else {
					curr.next = l2;
					l2 = l2.next;
				}
				curr = curr.next;
			}
			if (l1 != null)
				curr.next = l1;
			else if (l2 != null)
				curr.next = l2;
			return prehead.next;
		}
	}

	/************************** Unit Test **************************/
	@Test
	public void testQuickSort1() {
		int[] A = { 5, 91, 2, 13, 25, 2, 33, 8, 16, 9, 68, 11, 3 };
		int[] res = Arrays.copyOf(A, A.length);
		QuickSort.quickSort(A);
		System.out.println(Arrays.toString(A));
		Arrays.sort(res);
		assertTrue(Arrays.toString(A), Arrays.equals(A, res));
	}

	@Test
	public void testMergeSort1() {
		int[] A = { 5, 91, 2, 13, 25, 2, 33, 8, 16, 9, 68, 11, 3 };
		int[] res = Arrays.copyOf(A, A.length);
		MergeSort.mergeSort(A);
		Arrays.sort(res);
		assertTrue(Arrays.toString(A), Arrays.equals(A, res));
	}

	@Test
	public void testMergeSort2() {
		int[] A = { 0, 2, 1 };
		int[] res = Arrays.copyOf(A, A.length);
		MergeSort.mergeSort(A);
		Arrays.sort(res);
		assertTrue(Arrays.toString(A), Arrays.equals(A, res));
	}

	/***********************************************************************
	 * Check if array is sorted - useful for debugging
	 ***********************************************************************/
	public static boolean isSorted(int[] a) {
		return isSorted(a, 0, a.length - 1);
	}

	private static boolean isSorted(int[] a, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++)
			if (a[i] < a[i - 1])
				return false;
		return true;
	}

	/************************** Utilities **************************/
	private static void swap(int[] A, int i, int j) {
		if (i != j) {
			int temp = A[i];
			A[i] = A[j];
			A[j] = temp;
		}
	}

	public static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
			next = null;
		}
	}
}
