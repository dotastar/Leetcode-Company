package interview.leetcode;

/**
 * You are given two linked lists representing two non-negative numbers. The
 * digits are stored in reverse order and each of their nodes contain a single
 * digit. Add the two numbers and return it as a linked list.
 * 
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 
 * Output: 7 -> 0 -> 8
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Add_Two_Numbers {

	public static void main(String[] args) {

	}

	/**
	 * Second time practice
	 */
	public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
		int carry = 0;
		ListNode head = new ListNode(0);
		ListNode curr = head;
		while (l1 != null || l2 != null) {
			int val1 = l1 == null ? 0 : l1.val;
			int val2 = l2 == null ? 0 : l2.val;
			int sum = val1 + val2 + carry;
			carry = sum / 10;
			curr.next = new ListNode(sum % 10);
			l1 = l1 == null ? null : l1.next;
			l2 = l2 == null ? null : l2.next;
			curr = curr.next;
		}
		if (carry > 0)
			curr.next = new ListNode(1);
		return head.next;
	}

	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode prehead = new ListNode(0);
		ListNode p = prehead;
		int carry = 0;
		while (l1 != null && l2 != null) {
			int sum = l1.val + l2.val + carry;
			carry = sum / 10;
			sum %= 10;
			p.next = new ListNode(sum);
			p = p.next;
			l1 = l1.next;
			l2 = l2.next;
		}

		ListNode rest = l1 != null ? l1 : l2;
		while (rest != null) {
			int sum = rest.val + carry;
			carry = sum / 10;
			sum %= 10;
			p.next = new ListNode(sum);
			p = p.next;
			rest = rest.next;
		}
		if (carry > 0)
			p.next = new ListNode(carry);
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
