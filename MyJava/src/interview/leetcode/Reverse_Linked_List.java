package interview.leetcode;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * 
 * Reverse a singly linked list.
 * 
 * A linked list can be reversed either iteratively or recursively. Could you
 * implement both?
 * 
 * @author yazhoucao
 *
 */
public class Reverse_Linked_List {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Reverse_Linked_List.class);
	}

	/**
	 * Recursively
	 */
	public ListNode reverseList(ListNode head) {
		if (head == null || head.next == null)
			return head;
		ListNode newhead = reverseList(head.next);
		head.next.next = head;
		head.next = null;
		return newhead;
	}

	/**
	 * Iteratively
	 */
	public ListNode reverseList_Iter(ListNode head) {
		if (head == null || head.next == null)
			return head;
		ListNode prev = null, curr = head;
		while (curr != null) {
			ListNode nxt = curr.next;
			curr.next = prev;
			prev = curr;
			curr = nxt;
		}
		return prev;

	}

	@Test
	public void test1() {
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		head.next.next.next.next = new ListNode(5);
		ListNode res = reverseList_Iter(head);
		printList(res);
		assertTrue(true);
	}

	public static void printList(ListNode head) {
		while (head != null) {
			System.out.print(head + "--> ");
			head = head.next;
		}
		System.out.println();
	}

	public static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
			next = null;
		}

		public String toString() {
			return Integer.toString(val);
		}
	}

}
