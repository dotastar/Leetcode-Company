package interview.leetcode;

/**
 * Given a singly linked list L: L0→L1→…→Ln-1→Ln, reorder it to:
 * L0→Ln→L1→Ln-1→L2→Ln-2→…
 * 
 * You must do this in-place without altering the nodes' values.
 * 
 * For example, Given {1,2,3,4}, reorder it to {1,4,2,3}.
 * 
 * @author yazhoucao
 * 
 */
public class Reorder_List {

	public static void main(String[] args) {
		ListNode head = new ListNode(9);
		head.next = new ListNode(8);
		head.next.next = new ListNode(11);
		head.next.next.next = new ListNode(7);
		head.next.next.next.next = new ListNode(2);
		head.next.next.next.next.next = new ListNode(5);
		head.next.next.next.next.next.next = new ListNode(1);
		head.next.next.next.next.next.next.next = new ListNode(3);
		head.next.next.next.next.next.next.next.next = new ListNode(6);
		head.next.next.next.next.next.next.next.next.next = new ListNode(10);
		head.next.next.next.next.next.next.next.next.next.next = new ListNode(4);
		reorderList(head);
		while (head != null) {
			System.out.print(head.toString() + "->");
			head = head.next;
		}
		System.out.println("null");
	}

	/**
	 * Thought:
	 * 
	 * 1.break the list in two in middle
	 * 
	 * 2.reverse the second half of the list
	 * 
	 * 3.merge them in one
	 * 
	 * Time: 1*n for finding middle, n/2 for reversing the second half, n/2 for
	 * merging, n + n/2 + n/2 = 2n = O(n)
	 * 
	 */
	public static void reorderList(ListNode head) {
		if (head == null || head.next == null)
			return;
		/************ break ************/
		ListNode fast = head, slow = head; // find middle/middle left
		while (fast.next != null && fast.next.next != null) {
			fast = fast.next.next;
			slow = slow.next;
		}
		ListNode l1 = head, l2 = slow.next;
		slow.next = null; // break the list

		/************ reverse ************/
		ListNode prev = null;
		while (l2 != null) { // reverse
			ListNode nxt = l2.next;
			l2.next = prev;
			prev = l2;
			l2 = nxt;
		}

		/************ merge ************/
		l2 = prev;
		while (l2 != null) {
			ListNode nxt1 = l1.next;
			ListNode nxt2 = l2.next;
			l1.next = l2;
			l2.next = nxt1;
			l1 = nxt1;
			l2 = nxt2;
		}
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
