package interview.leetcode;

/**
 * Given a linked list, reverse the nodes of a linked list k at a time and
 * return its modified list.
 * 
 * If the number of nodes is not a multiple of k then left-out nodes in the end
 * should remain as it is.
 * 
 * You may not alter the values in the nodes, only nodes itself may be changed.
 * 
 * Only constant memory is allowed.
 * 
 * For example, Given this linked list: 1->2->3->4->5
 * 
 * For k = 2, you should return: 2->1->4->3->5
 * 
 * For k = 3, you should return: 3->2->1->4->5
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Reverse_Nodes_in_k_Group {

	public static void main(String[] args) {
		Reverse_Nodes_in_k_Group obj = new Reverse_Nodes_in_k_Group();
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		// head.next.next = new ListNode(3);
		// head.next.next.next = new ListNode(4);
		// head.next.next.next.next = new ListNode(5);
		// head.next.next.next.next.next = new ListNode(6);

		ListNode res = obj.reverseKGroup3(head, 2);
		printList(res);

		ListNode head1 = new ListNode(1);
		head1.next = new ListNode(2);
		ListNode res1 = obj.reverseKGroup2(head1, 2);
		printList(res1);
	}

	/**
	 * Recursive fashion
	 */
	public ListNode reverseKGroup3(ListNode head, int k) {
		if (head == null || head.next == null || k == 1)
			return head;
		ListNode end = head;
		for (int i = 1; i < k; i++) { // traverse k nodes
			end = end.next;
			if (end == null) // there are less than k nodes
				return head;
		}
		ListNode nxt = end.next; // the (k+1)th node
		ListNode prev = null, curr = head;
		while (curr != nxt) { // reversing
			ListNode nxtCurr = curr.next;
			curr.next = prev;
			prev = curr;
			curr = nxtCurr;
		}
		// reverse next k nodes, connect new tail(current) with new head(next)
		head.next = reverseKGroup3(nxt, k);
		return prev; // return the new head
	}

	/**
	 * Iterative fashion, write code in one function
	 */
	public ListNode reverseKGroup2(ListNode head, int k) {
		ListNode prehead = new ListNode(0);
		prehead.next = head;
		ListNode start = prehead, end = head;
		int i = 0;
		while (end != null) {
			i++;
			end = end.next;
			if (i % k == 0) { // reversing from start+1 to end-1
				ListNode tail = start.next;
				ListNode curr = tail.next;
				while (curr != end) { // stop condition, important!
					// cache for others and
					// assign for tail when curr reaches to end
					tail.next = curr.next;
					curr.next = start.next;
					start.next = curr;
					curr = tail.next;
				}
				start = tail;
			}
		}
		return prehead.next;
	}

	/**
	 * Iterative fashion, use reverse() as a subroutine
	 * Time : 2n = O(n)
	 */
	public ListNode reverseKGroup(ListNode head, int k) {
		ListNode prehead = new ListNode(0);
		prehead.next = head;
		ListNode start = prehead;
		ListNode end = head;
		int i = 0;
		while (end != null) {
			i++;
			if (i % k == 0) {// reverse k-length
				start = reverse(start, end.next);
				end = start.next;
			} else
				end = end.next;
		}
		return prehead.next;
	}

	/**
	 * Reverse a link list between start and end exclusively (start+1 to end-1)
	 * 
	 * @return the tail of reversed list
	 */
	public ListNode reverse(ListNode start, ListNode end) {
		ListNode tail = start.next; // first will be the tail
		ListNode p = tail.next;
		while (p != end) {
			// when this end, tail points to next,
			// it also caches the next node of p
			tail.next = p.next;
			p.next = start.next;
			// when this end, start points to head (was the tail)
			start.next = p;
			p = tail.next;
		}
		return tail;
	}

	private static void printList(ListNode res) {
		while (res != null) {
			System.out.print(res.toString() + " -> ");
			res = res.next;
			if (res == null)
				System.out.println("null");
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
