package interview.leetcode;

/**
 * Given a sorted linked list, delete all duplicates such that each element
 * appear only once.
 * 
 * For example,
 * 
 * Given 1->1->2, return 1->2.
 * 
 * Given 1->1->2->3->3, return 1->2->3.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Remove_Duplicates_from_Sorted_List {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Second time
	 */
	public ListNode deleteDuplicates2(ListNode head) {
		if (head == null)
			return null;
		ListNode curr = head;
		while (curr.next != null) {
			if (curr.next.val == curr.val)
				curr.next = curr.next.next;
			else
				curr = curr.next;
		}
		return head;
	}

	/**
	 * two pointers walk through the list,
	 * the prev stores the last unrepeated value,
	 * the curr stores the current node
	 */
	public ListNode deleteDuplicates(ListNode head) {
		ListNode curr = head;
		ListNode pre = head;
		while (curr != null) {
			if (pre.val == curr.val) {
				pre.next = curr.next;
			} else {
				pre = curr;
			}
			curr = curr.next;
		}
		return head;
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
