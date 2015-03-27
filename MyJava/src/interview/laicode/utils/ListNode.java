package interview.laicode.utils;

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
}
