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
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		head.next.next.next.next = new ListNode(5);
		head.next.next.next.next.next = new ListNode(6);

		ListNode res = obj.reverseKGroup2(head, 3);
		printList(res);

		ListNode head1 = new ListNode(1);
		head1.next = new ListNode(2);
		ListNode res1 = obj.reverseKGroup2(head1, 3);
		printList(res1);
	}

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
				while (curr != end) {
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
