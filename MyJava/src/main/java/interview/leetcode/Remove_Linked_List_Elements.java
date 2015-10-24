package interview.leetcode;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Remove all elements from a linked list of integers that have value val.
 * 
 * Example
 * Given: 1 --> 2 --> 6 --> 3 --> 4 --> 5 --> 6, val = 6
 * Return: 1 --> 2 --> 3 --> 4 --> 5
 * 
 * @author yazhoucao
 *
 */
public class Remove_Linked_List_Elements {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Remove_Linked_List_Elements.class);
	}

	/**
	 * Insert node to a new node except for the node has value-val.
	 */
	public ListNode removeElements(ListNode head, int val) {
		ListNode prehead = new ListNode(0);
		ListNode tail = prehead, curr = head;
		while (curr != null) {
			if (curr.val != val) {
				tail.next = curr;
				tail = tail.next;
			}
			curr = curr.next;
		}
		tail.next = null;
		return prehead.next;
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertTrue(true);
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
