package interview.company.others;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;
import interview.utils.ListNode;

import org.junit.Test;

/**
 * 两个单链表（singly linked list），每一个节点里面一个0-9的数字，输入就相当于两个大数了。然后返回这两个数的和（一个新list）。
 * 这两个输入的list长度相等。要求算法在最好的情况下，只遍历两个list一次 ，最差的情况下两遍。
 * 分析：遇到一个面试题，首先，要澄清和理解题意，确保你的理解和面试官的本意一致。题中的单链表，可不可以原地修改？是从高位到低位，还是低位到高位？
 * 如果是从低位到高位，那么问题很简单，是不是？只要两个指针移动（因为是等长的），对应位置相加，同时记录是否有进位，产生的结果存入新的链表中。
 * 
 * 如果是从高到低，问题就复杂了，进位是万恶之源。这时，也许我们会想到reverse两个单链表（其实，这也是一道很好的面试题，如何做？考虑递归和递推两种算法）
 * ，但这样做，是不是最好最坏情形都得遍历两次？好像不合题意。
 * 
 * @author yazhoucao
 *
 */
public class LinkedListAddition {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(LinkedListAddition.class);
	}

	/**
	 * Time: O(n), Space: O(1)
	 * Use one additional pointer points to the node that can carry which its
	 * value < 9
	 * 
	 * Traverse the two list at the same time, and add the result to a new Node
	 * 
	 * If sum < 10, very easy just advance to the next node,
	 * Else, carry node++, and make all the nodes between carry and curr node to
	 * 0 cause they all should be 0 (9 + 1). Then move the carry node to next
	 * node that can carry which must be the curr node.
	 * 
	 * 1 8 1
	 * 8 1 9
	 * ^
	 */
	public ListNode add2(ListNode l1, ListNode l2) {
		ListNode prehead = new ListNode(0);
		ListNode curr = prehead, carry = prehead;
		ListNode curr1 = l1, curr2 = l2;
		while (curr1 != null && curr2 != null) {
			curr.next = new ListNode(curr1.value + curr2.value);
			curr = curr.next;
			if (curr.value >= 10) {
				curr.value %= 10;
				carry.value += 1;
				while (carry.next != curr) {
					carry = carry.next;
					carry.value = 0;
				}
			} else if (curr.value < 9) {
				carry = curr;
			}
			curr1 = curr1.next;
			curr2 = curr2.next;
		}
		return prehead.value == 0 ? prehead.next : prehead;
	}

	/**
	 * Time: O(n), Space: O(n) because of recursion
	 * Add two number represented as list (from highest significant bit to
	 * lowest bit
	 */
	public ListNode add(ListNode l1, ListNode l2) {
		ListNode head = add_Recur(l1, l2);
		// handle the case that the value of head need to carry
		if (head != null && head.value >= 10) {
			ListNode newhead = new ListNode(head.value / 10);
			head.value %= 10;
			newhead.next = head;
			head = newhead;
		}
		return head;
	}

	/**
	 * Recursively add each node and add the carry bit of next node
	 * It can't handle the case if the head need to carry
	 */
	private ListNode add_Recur(ListNode l1, ListNode l2) {
		// assume they have the same length
		if (l1 == null && l2 == null)
			return null;
		ListNode next = add_Recur(l1.next, l2.next);
		ListNode curr = new ListNode(0);
		if (next != null) {
			curr.value += next.value / 10;
			next.value %= 10;
			curr.next = next;
		}
		curr.value += l1.value + l2.value;
		return curr;
	}

	@Test
	public void test1() {
		ListNode l1 = new ListNode(1);
		l1.next = new ListNode(8);
		l1.next.next = new ListNode(1);

		ListNode l2 = new ListNode(8);
		l2.next = new ListNode(1);
		l2.next.next = new ListNode(9);
		// 181 + 819 = 1000
		ListNode res = add2(l1, l2);
		ListNode.printList(res);
		assertTrue(true);
	}

	@Test
	public void test2() {
		ListNode l1 = new ListNode(2);
		l1.next = new ListNode(3);
		l1.next.next = new ListNode(3);

		ListNode l2 = new ListNode(5);
		l2.next = new ListNode(2);
		l2.next.next = new ListNode(9);
		// 233 + 529 = 762
		ListNode res = add2(l1, l2);
		ListNode.printList(res);
		assertTrue(true);
	}

	@Test
	public void test3() {
		ListNode l1 = new ListNode(2);
		l1.next = new ListNode(3);
		l1.next.next = new ListNode(3);
		l1.next.next.next = new ListNode(3);
		l1.next.next.next.next = new ListNode(9);

		ListNode l2 = new ListNode(7);
		l2.next = new ListNode(6);
		l2.next.next = new ListNode(4);
		l2.next.next.next = new ListNode(4);
		l2.next.next.next.next = new ListNode(9);
		// 23339 + 76449 = 99788
		ListNode res = add2(l1, l2);
		ListNode.printList(res);
		assertTrue(true);
	}

}
