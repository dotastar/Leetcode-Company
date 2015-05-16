package interview.company.others;

import interview.AutoTestUtils;
import interview.utils.ListNode;

import org.junit.Test;

/**
 * “Given a linked list, swap every two adjacent nodes and return its head.”
 * 
 * Excerpt From: Yichao, Xiami, XiaoXiao & Fei Dong. “程序员面试白皮书.” iBooks.
 * https://itun.es/us/zcOJ6.l
 * 
 * @author yazhoucao
 * 
 */
public class SwapTwoAdajacentNodes {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(SwapTwoAdajacentNodes.class);
	}

	/**
	 * pre -> 2 -> 1 -> null
	 * ^ ^
	 * curr nextCurr
	 */
	public ListNode swapTwoAdajcent(ListNode head) {
		ListNode prehead = new ListNode(0);
		prehead.next = head;
		ListNode prev = prehead, curr = head;
		while (curr != null && curr.next != null) {
			ListNode nextCurr = curr.next.next;
			prev.next = curr.next;
			curr.next.next = curr;
			curr.next = nextCurr;

			prev = curr;
			curr = nextCurr;
		}
		return prehead.next;
	}

	/**
	 * Template of swapping any two nodes:
	 * 1.Swap the next pointer of their preceding nodes
	 * 2.Swap the next pointer of their nodes
	 */
	public ListNode swapTwoAdajcent2(ListNode head) {
		ListNode prehead = new ListNode(0);
		prehead.next = head;
		ListNode prev = prehead, node1 = head;
		while (node1 != null && node1.next != null) {
			ListNode node2 = node1.next; // must cache node2
			// step 1
			ListNode temp = prev.next;
			prev.next = node1.next;
			node1.next = temp;
			// step 2
			temp = node1.next;
			node1.next = node2.next;
			node2.next = temp;

			prev = node1;
			node1 = node1.next;
		}
		return prehead.next;
	}

	@Test
	public void test1() {
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		head.next.next.next.next = new ListNode(5);
		head.next.next.next.next.next = new ListNode(6);

		ListNode res = swapTwoAdajcent2(head);
		ListNode.printList(res);
	}

	@Test
	public void test2() {
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		head.next.next.next.next = new ListNode(5);

		ListNode res = swapTwoAdajcent2(head);
		ListNode.printList(res);
	}
}
