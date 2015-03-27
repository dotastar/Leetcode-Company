package interview.laicode;

import org.junit.Test;

import interview.AutoTestUtils;
import interview.laicode.utils.ListNode;

/**
 * 
 * Quick Sort Linked List
 * Hard
 * Data Structure
 * 
 * Given a singly-linked list, where each node contains an integer value, sort
 * it in ascending order. The quick sort algorithm should be used to solve this
 * problem.
 * 
 * Examples
 * 
 * null, is sorted to null
 * 1 -> null, is sorted to 1 -> null
 * 1 -> 2 -> 3 -> null, is sorted to 1 -> 2 -> 3 -> null
 * 4 -> 2 -> 6 -> -3 -> 5 -> null, is sorted to -3 -> 2 -> 4 -> 5 -> 6
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Quick_Sort_Linked_List {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Quick_Sort_Linked_List.class);
	}

	public ListNode quickSort(ListNode head) {
		if (head == null || head.next == null)
			return head;

		int pivot = head.value; // use head as pivot
		ListNode l1 = new ListNode(0), l2 = new ListNode(0);
		ListNode curr1 = l1, curr2 = l2, curr = head.next;
		while (curr != null) { // partitioning to two lists
			if (curr.value < pivot) {
				curr1.next = curr;
				curr1 = curr1.next;
			} else {
				curr2.next = curr;
				curr2 = curr2.next;
			}
			curr = curr.next;
		}
		// break the two list
		curr1.next = head;
		head.next = null;
		curr2.next = null;
		// recursively sort the two sublists
		ListNode res = quickSort(l1.next);
		head.next = quickSort(l2.next);

		return res;
	}

	@Test
	public void test1() {
		ListNode n1 = new ListNode(14);
		n1.next = new ListNode(12);
		n1.next.next = new ListNode(5);
		n1.next.next.next = new ListNode(2);
		n1.next.next.next.next = new ListNode(7);

		ListNode res = quickSort(n1);
		while (res != null) {
			System.out.print(res.value + " -> ");
			res = res.next;
		}
		System.out.println();
	}
}
