package interview.leetcode;

/**
 * Given a linked list, determine if it has a cycle in it.
 * 
 * Follow up: Can you solve it without using extra space?
 * 
 * @author yazhoucao
 * 
 */
public class Linked_List_Cycle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public boolean hasCycle(ListNode head) {
		if (head == null)
			return false;
		if (head.next == null)
			return false;

		ListNode fast = head;
		ListNode slow = head;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (fast == slow)
				return true;
		}
		return false;
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
