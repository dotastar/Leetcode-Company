package interview.leetcode;

/**
 * Sort a linked list in O(n log n) time using constant space complexity.
 * 
 * @author yazhoucao
 * 
 */
public class Sort_List {

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
		ListNode res = sortList(head);
		while (res != null) {
			System.out.print(res.toString() + "->");
			res = res.next;
		}
		System.out.println("null");
	}

	/**
	 * Selection sort
	 * Time: O(n^2)
	 */
	public ListNode sortList_Insertion(ListNode head) {
		ListNode dummy = new ListNode(0);
		ListNode tail = dummy;
		ListNode prehead = new ListNode(0);
		prehead.next = head;
		while (prehead.next != null) {
			ListNode prev = prehead;
			ListNode curr = prehead.next;
			ListNode smallest = curr;
			while (curr.next != null) {
				if (curr.val < curr.next.val)
					smallest = prev;
				curr = curr.next;
				prev = prev.next;
			}
			ListNode deleted = delete(smallest);
			tail.next = deleted;
			tail = tail.next;
		}
		return dummy.next;
	}

	private ListNode delete(ListNode prev) {
		ListNode deleted = prev.next;
		if (deleted != null)
			prev.next = deleted.next;
		return deleted;
	}

	/**
	 * Merge sort
	 * 
	 * 1.break list in middle recursively
	 * 
	 * 2.merge them recursively begin from the minimal length list
	 * 
	 * @param head
	 * @return
	 */
	public static ListNode sortList(ListNode head) {
		if (head == null || head.next == null)
			return head;
		ListNode fast = head;
		ListNode slow = head;
		// break the list into two in the middle
		while (fast.next != null && fast.next.next != null) {
			slow = slow.next; // must be next and next.next, important,
			fast = fast.next.next; // make sure fast and slow.next is not null!
		}

		ListNode l2 = sortList(slow.next); // split second half of the list
		slow.next = null; // cut the list in the middle
		ListNode l1 = sortList(head); // split first half of the list

		return merge(l1, l2);
	}

	public static ListNode merge(ListNode l1, ListNode l2) {
		ListNode pre = new ListNode(0);
		ListNode curr = pre;
		while (l1 != null && l2 != null) {
			if (l1.val > l2.val) {
				curr.next = l2;
				l2 = l2.next;
			} else {
				curr.next = l1;
				l1 = l1.next;
			}
			curr = curr.next;
		}
		if (l1 != null)
			curr.next = l1;
		else
			curr.next = l2;

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
