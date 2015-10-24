package interview.company.facebook;

import static org.junit.Assert.*;

import org.junit.Test;

import interview.AutoTestUtils;

/**
 * Given a string and some characters in a linked list write a function to
 * return if the string is a palindrome or not ignoring all the characters in
 * the linked list.
 * Signature: bool isPalindrome(char*, node*)
 * 
 * @author yazhoucao
 * 
 */
public class IsPalindromeOfLinkedList {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(IsPalindromeOfLinkedList.class);
	}

	/**
	 * Find the middle point, reverse the second half, and compare
	 */
	public boolean isPalindrome(ListNode head) {
		if (head == null)
			return true;
		// find the middle point
		ListNode fast = head;
		ListNode slow = head;
		while (fast.next != null && fast.next.next != null) {
			fast = fast.next.next;
			slow = slow.next;
		}
		if (fast.next != null) {
			fast = fast.next;
			slow = slow.next;
		}
		ListNode prehead = new ListNode('\0');
		ListNode newhead = slow;
		// copy and insert node into the head of new list from slow to fast
		// equals to reverse slow to fast and copy to a new list
		while (newhead != null) {
			ListNode copy = new ListNode(newhead.val);
			copy.next = prehead.next;
			prehead.next = copy;
			newhead = newhead.next;
		}

		newhead = prehead.next;
		while (newhead != null) { // compare
			if (newhead.val != head.val)
				return false;
			newhead = newhead.next;
			head = head.next;
		}
		return true;
	}

	@Test
	public void test1() {
		ListNode h = new ListNode('a');
		assertTrue(isPalindrome(h));
	}

	@Test
	public void test2() {
		ListNode h = new ListNode('a');
		h.next = new ListNode('b');
		assertTrue(!isPalindrome(h));
	}

	@Test
	public void test3() {
		ListNode h = new ListNode('a');
		h.next = new ListNode('b');
		h.next.next = new ListNode('c');
		h.next.next.next = new ListNode('c');
		h.next.next.next.next = new ListNode('b');
		h.next.next.next.next.next = new ListNode('a');
		assertTrue(isPalindrome(h));
	}

	@Test
	public void test4() {
		ListNode h = new ListNode('a');
		h.next = new ListNode('b');
		h.next.next = new ListNode('c');
		h.next.next.next = new ListNode('d');
		h.next.next.next.next = new ListNode('c');
		h.next.next.next.next.next = new ListNode('b');
		h.next.next.next.next.next.next = new ListNode('a');
		assertTrue(isPalindrome(h));
	}

	@Test
	public void test5() {
		ListNode h = new ListNode('a');
		h.next = new ListNode('a');
		h.next.next = new ListNode('a');
		h.next.next.next = new ListNode('1');
		h.next.next.next.next = new ListNode('a');
		h.next.next.next.next.next = new ListNode('a');
		assertTrue(!isPalindrome(h));
	}

	public static class ListNode {
		char val;
		ListNode next;

		ListNode(char x) {
			val = x;
			next = null;
		}

		public String toString() {
			return Character.toString(val);
		}
	}
}
