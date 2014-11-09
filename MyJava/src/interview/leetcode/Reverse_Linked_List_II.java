package interview.leetcode;

/**
 * Reverse a linked list from position m to n. Do it in-place and in one-pass.
 * 
 * For example: Given 1->2->3->4->5->NULL, m = 2 and n = 4,
 * 
 * return 1->4->3->2->5->NULL.
 * 
 * Note: Given m, n satisfy the following condition: 1 ≤ m ≤ n ≤ length of list.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Reverse_Linked_List_II {

	public static void main(String[] args) {
		ListNode head0 = new ListNode(1);
		head0.next = new ListNode(2);
		// head0.next.next = new ListNode(3);
		// head0.next.next.next = new ListNode(4);
		// head0.next.next.next.next = new ListNode(5);
		ListNode res = reverseBetween2(head0, 1, 2);
		while (res != null) {
			System.out.print(res.toString() + " -> ");
			res = res.next;
			if (res == null)
				System.out.println("null");
		}

	}

	/**
	 * Same solution, another way of writing it
	 * 
	 */
	public static ListNode reverseBetween2(ListNode head, int m, int n) {
		if (m >= n)
			return head;
		ListNode prehead = new ListNode(0);
		prehead.next = head;
		ListNode prev = prehead;
		ListNode curr = head;
		ListNode cutoff = null;
		int i = 1;
		while (curr != null && i <= n) {
			if (i < m) { // haven't reached m
				prev = curr;
				curr = curr.next;
			} else { // between m and n, reversing
				if (i == m) {
					cutoff = prev; // record the cutoff point
				} else if (i == n) {
					// link node m to the next of node n
					cutoff.next.next = curr.next; // curr is node n
					cutoff.next = curr; // link the cutoff with node n
				}
				ListNode next = curr.next;
				curr.next = prev; // reversing

				prev = curr;
				curr = next;
			}
			i++;
		}
		return prehead.next;
	}

	/**
	 * Move to m, reversing m to n, then link the new head and tail again
	 * 
	 */
	public static ListNode reverseBetween(ListNode head, int m, int n) {
		ListNode prehead = new ListNode(0);
		prehead.next = head;
		ListNode preM = prehead;

		int idx = 0;
		ListNode p = prehead;
		while (idx + 1 <= m) { // move to m
			idx++;
			preM = p;
			p = p.next;
		}

		ListNode pre = p;
		while (idx <= n) { // reverse from m to n
			idx++;
			ListNode nxt = p.next;
			p.next = pre;
			pre = p;
			p = nxt;
		}
		// because there is a prehead, so preM.next can never be null!
		preM.next.next = p; // link tail, node M
		preM.next = pre; // link head, node N

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
