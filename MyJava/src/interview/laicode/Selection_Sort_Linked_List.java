package interview.laicode;

import interview.AutoTestUtils;
import interview.laicode.utils.ListNode;

import org.junit.Test;

/**
 * 
 * Selection Sort Linked List
 * Hard
 * Data Structure
 * 
 * Given a singly-linked list, where each node contains an integer value, sort
 * it in ascending order. The selectoin sort algorithm should be used to solve
 * this problem.
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
public class Selection_Sort_Linked_List {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Selection_Sort_Linked_List.class);
	}

	public ListNode selectionSort(ListNode head) {
		ListNode result = new ListNode(0);
		ListNode currResult = result;

		ListNode prehead = new ListNode(0);
		prehead.next = head;
		while (prehead.next != null) {
			ListNode prevMin = prehead;
			ListNode prev = prevMin;
			while (prev.next != null) {
				if (prev.next.value < prevMin.next.value)
					prevMin = prev;
				prev = prev.next;
			}
			ListNode minNode = prevMin.next;
			// delete from old list
			prevMin.next = prevMin.next.next;
			// insert to result lsit
			currResult.next = minNode;
			currResult = minNode;
			minNode.next = null;
		}
		return result.next;
	}
	
	@Test
	public void test1() {
		ListNode n1 = new ListNode(14);
		n1.next = new ListNode(12);
		n1.next.next = new ListNode(5);
		n1.next.next.next = new ListNode(2);
		n1.next.next.next.next = new ListNode(7);

		ListNode res = selectionSort(n1);
		while (res != null) {
			System.out.print(res.value + " -> ");
			res = res.next;
		}
		System.out.println();
	}
}
