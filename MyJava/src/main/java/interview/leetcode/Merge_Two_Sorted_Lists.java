package interview.leetcode;

/**
 * Merge two sorted linked lists and return it as a new list. The new list
 * should be made by splicing together the nodes of the first two lists.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Merge_Two_Sorted_Lists {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ListNode l1 = new ListNode(5);
		ListNode l2 = new ListNode(1);
		l2.next = new ListNode(2);
		l2.next.next = new ListNode(4);
		ListNode res = mergeTwoLists2(l1, l2);
		while (res != null) {
			System.out.print(res.val + "->");
			res = res.next;
		}

	}

	/**
	 * Second time
	 */
	public static ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
		ListNode prehead = new ListNode(1);
		ListNode curr = prehead;
		while (l1 != null || l2 != null) {
			if (l2 == null || (l1 != null && l1.val <= l2.val)) {
				curr.next = l1;
				l1 = l1.next;
			} else {
				curr.next = l2;
				l2 = l2.next;
			}
			curr = curr.next;
		}
		return prehead.next;
	}

	/**
	 * Increasing Order
	 */
	public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		if (l1 == null)
			return l2;
		if (l2 == null)
			return l1;

		ListNode fakeHead = new ListNode(0);
		ListNode curr = fakeHead;

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

		return fakeHead.next;
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
