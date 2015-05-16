package interview.utils;

public class ListNode {
	public int value;
	public ListNode next;

	public ListNode(int x) {
		value = x;
		next = null;
	}

	public String toString() {
		return Integer.toString(value);
	}

	public static void printList(ListNode head) {
		while (head != null) {
			System.out.print(head + "--> ");
			head = head.next;
		}
		System.out.println();
	}
}
