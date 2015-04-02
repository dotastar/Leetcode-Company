package interview.laicode;

import interview.AutoTestUtils;
import interview.laicode.utils.ListNode;

import org.junit.Test;

/**
 * Reverse Linked List
 * Easy
 * Data Structure
 * 
 * Reverse a singly-linked list.
 * 
 * Examples
 * 
 * L = null, return null
 * L = 1 -> null, return 1 -> null
 * L = 1 -> 2 -> 3 -> null, return 3 -> 2 -> 1 -> null
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Reverse_Linked_List {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Reverse_Linked_List.class);
	}

	/**
	 * 1 <- 2 <- 3 <- 4
	 * 
	 **/
	public ListNode reverse(ListNode head) {
		// base case
		if (head == null || head.next == null)
			return head;

		ListNode prev = null;
		while (head != null) {
			ListNode nxt = head.next;
			head.next = prev;
			prev = head;
			head = nxt;
		}
		return prev;
	}

	@Test
	public void test1() {
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		ListNode newhead = reverse(head);
		while (newhead != null) {
			System.out.print(newhead.toString() + "->");
			newhead = newhead.next;
		}
		System.out.println();
	}
}
