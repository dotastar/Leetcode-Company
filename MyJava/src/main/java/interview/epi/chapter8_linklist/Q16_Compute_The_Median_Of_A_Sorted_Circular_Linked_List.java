package interview.epi.chapter8_linklist;

/**
 * Problem 8.15
 * Write a function that takes a pointer to an arbitrary node in a sorted
 * circular linked list, and returns the median of the linked list.
 * 
 * @author yazhoucao
 * 
 */
public class Q16_Compute_The_Median_Of_A_Sorted_Circular_Linked_List {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * First find the length of the list
	 * Then get the n/2-th element.
	 * 
	 */
	public static Integer findMedianSortedCircularLinkedList(Node<Integer> head) {
		Node<Integer> fast = head;
		Node<Integer> slow = head;

		while (fast != null && fast.next != null) {
			fast = fast.next.next;
			slow = slow.next;
			if (fast == head || (fast != null && fast.next == head))
				break;
		}

		return slow.data;
	}

	/**
	 * Solution from EPI
	 */
	public static double findMedianSortedCircularLinkedList2(Node<Integer> rNode) {
		if (rNode == null) {
			// no node in this linked list.
			throw new IllegalArgumentException("empty list");
		}

		// Checks all nodes are identical or not and identify the start of list.
		Node<Integer> curr = rNode;
		Node<Integer> start = rNode;
		int count = 0;
		do {
			++count;
			curr = curr.next;
			// start will point to the largest element in the list.
			if (start.data.compareTo(curr.data) <= 0) {
				start = curr;
			}
		} while (curr != rNode);
		// start's next is the begin of the list.
		start = start.next;

		// Traverses to the middle of the list and return the median.
		for (int i = 0; i < ((count - 1) >> 1); ++i) {
			start = start.next;
		}
		return (count & 1) != 0 ? start.data
				: 0.5 * (start.data + start.next.data);
	}

	public static class Node<T> {
		T data;
		Node<T> next;

		public Node(T valIn) {
			data = valIn;
			next = null;
		}
	}
}
