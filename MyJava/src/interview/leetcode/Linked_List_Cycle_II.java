package interview.leetcode;

/**
 * Given a linked list, return the node where the cycle begins. If there is no
 * cycle, return null.
 * 
 * Follow up: Can you solve it without using extra space?
 * 
 * @author yazhoucao
 * 
 */
public class Linked_List_Cycle_II {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ListNode l1 = new ListNode(1);
		l1.next = new ListNode(2);
		l1.next.next = l1;
		detectCycle(l1);
	}

	public static ListNode detectCycle(ListNode head) {
		ListNode fast = head, slow = head;
		while (fast != null && fast.next != null) {
			fast = fast.next.next;
			slow = slow.next;
			if (fast == slow) { // fast meets slow, means cycle exists
				fast = head; // set fast to start point
				while (fast != slow) { // the point they meet is the begin point
					fast = fast.next;
					slow = slow.next;
				}
				return slow;
			}
		}
		return null;
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
