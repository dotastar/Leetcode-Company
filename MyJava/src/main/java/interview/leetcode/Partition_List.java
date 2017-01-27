package interview.leetcode;

/**
 * Given a linked list and a value x, partition it such that all nodes less than
 * x come before nodes greater than or equal to x.
 * 
 * You should preserve the original relative order of the nodes in each of the
 * two partitions.
 * 
 * For example, Given 1->4->3->2->5->2 and x = 3, return 1->2->2->4->3->5.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Partition_List {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public ListNode partition(ListNode head, int x) {
		ListNode h1 = new ListNode(0), h2 = new ListNode(0);
		ListNode curr1 = h1, curr2 = h2;
		while (head != null) {
			if (head.val < x) {
				curr1.next = head;
				curr1 = curr1.next;
			} else {
				curr2.next = head;
				curr2 = curr2.next;
			}
			head = head.next;
		}
		curr1.next = h2.next; // link l1 to l2
		curr2.next = null; // set the tail of l2 to null, don't forget!
		return h1.next;
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
