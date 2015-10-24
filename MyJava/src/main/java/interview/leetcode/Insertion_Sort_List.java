package interview.leetcode;

/**
 * Sort a linked list using insertion sort.
 * 
 * @author yazhoucao
 * 
 */
public class Insertion_Sort_List {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ListNode head = new ListNode(1);
		head.next = new ListNode(9);
		head.next.next = new ListNode(5);
		head.next.next.next = new ListNode(7);
		head.next.next.next.next = new ListNode(3);
		head.next.next.next.next.next = new ListNode(6);
		head.next.next.next.next.next.next = new ListNode(2);
		ListNode res = insertionSortList(head);
		while (res != null) {
			System.out.print(res.toString() + "->");
			res = res.next;
		}
		System.out.println("null");
	}

	/**
	 * Time: O(n^2)
	 */
	public static ListNode insertionSortList(ListNode head) {
		ListNode curr = head, prehead = new ListNode(0);
		while (curr != null) {
			ListNode prev = prehead, nxt = curr.next;
			// find insertion position
			while (prev.next != null && curr.val > prev.next.val)
				prev = prev.next;
			// insert
			curr.next = prev.next;
			prev.next = curr;
			// next to insert
			curr = nxt;
		}
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
