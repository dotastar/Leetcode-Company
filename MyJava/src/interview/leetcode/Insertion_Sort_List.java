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

	public static ListNode insertionSortList(ListNode head) {
		ListNode pre = new ListNode(Integer.MIN_VALUE);
		while (head != null) { // insert head to the pre list
			ListNode prev = pre;
			ListNode curr = pre.next; // find the position in pre list
			while (curr != null && head.val > curr.val) {
				prev = curr;
				curr = curr.next;
			}
			ListNode nxt = head.next;
			prev.next = head;	//inserting
			head.next = curr;
			head = nxt;
		}
		return pre.next;
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
