package interview.leetcode;

/**
 * Given a linked list, remove the nth node from the end of list and return its
 * head.
 * 
 * For example, Given linked list: 1->2->3->4->5, and n = 2.
 * 
 * After removing the second node from the end, the linked list becomes
 * 1->2->3->5. Note: Given n will always be valid. Try to do this in one pass.
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Remove_Nth_Node_From_End_of_List {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ListNode head = new ListNode(1);
		// head.next = new ListNode(2);
		// head.next.next =new ListNode(3);
		removeNthFromEnd(head, 1);
	}

	/**
	 * Second time
	 */
	public ListNode removeNthFromEnd2(ListNode head, int n) {
		assert head != null; // if head is null, delete will have problem
		ListNode prehead = new ListNode(0);
		prehead.next = head;
		ListNode curr = prehead, preNth = prehead;
		for (int i = 0; i < n && curr.next != null; i++)
			curr = curr.next;
		while (curr.next != null) {
			curr = curr.next;
			preNth = preNth.next;
		}
		preNth.next = preNth.next.next; // delete
		return prehead.next;
	}

	/**
	 * Set two pointers, first pointer go n step first, and then second and
	 * first pointers go at the same time, and they always have a distance of n
	 * nodes, when first pointer reaches the end, the second pointer is the node
	 * to delete
	 * 
	 * @return
	 */
	public static ListNode removeNthFromEnd(ListNode head, int n) {
		ListNode prehead = new ListNode(0);
		prehead.next = head;
		ListNode curr = head;
		while (curr != null && n > 0) {
			curr = curr.next;
			n--;
		}

		ListNode preN = prehead;
		while (curr != null) {
			curr = curr.next;
			preN = preN.next;
		}
		preN.next = preN.next.next;
		return prehead.next;
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
