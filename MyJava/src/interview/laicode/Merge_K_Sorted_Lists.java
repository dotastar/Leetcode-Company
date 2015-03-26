package interview.laicode;

import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.Test;

/**
 * 
 * Merge K Sorted Lists
 * Fair
 * Data Structure
 * 
 * Merge K sorted lists into one big sorted list in ascending order.
 * 
 * Assumptions
 * 
 * None of the lists is null.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Merge_K_Sorted_Lists {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Merge_K_Sorted_Lists.class);
	}

	public ListNode merge(List<ListNode> A) {
		if (A.size() == 0)
			return null;
		ListNode prehead = new ListNode(0);
		ListNode curr = prehead;
		PriorityQueue<ListNode> minHeap = new PriorityQueue<>(A.size(),
				new Comparator<ListNode>() {
					@Override
					public int compare(ListNode n1, ListNode n2) {
						return n1.value - n2.value;
					}
				});

		for (ListNode node : A)
			if (node != null)
				minHeap.add(node);

		while (!minHeap.isEmpty()) {
			ListNode minNode = minHeap.poll();
			curr.next = minNode;
			curr = curr.next;
			if (minNode.next != null)
				minHeap.add(minNode.next);
		}

		return prehead.next;
	}

	@Test
	public void test1() {
		ListNode n1 = new ListNode(1);
		n1.next = new ListNode(2);
		n1.next.next = new ListNode(5);
		n1.next.next.next = new ListNode(12);
		n1.next.next.next.next = new ListNode(15);

		ListNode n2 = new ListNode(-1);
		n2.next = new ListNode(4);
		n2.next.next = new ListNode(5);
		n2.next.next.next = new ListNode(8);
		n2.next.next.next.next = new ListNode(13);

		ListNode n3 = new ListNode(0);
		n3.next = new ListNode(3);
		n3.next.next = new ListNode(6);
		n3.next.next.next = new ListNode(7);
		n3.next.next.next.next = new ListNode(9);

		List<ListNode> A = new ArrayList<>();
		A.add(n1);
		A.add(n2);
		A.add(n3);

		ListNode res = merge(A);
		while (res != null) {
			System.out.print(res.value + " -> ");
			res = res.next;
		}
		System.out.println();
	}

	class ListNode {
		public int value;
		public ListNode next;

		public ListNode(int value) {
			this.value = value;
			next = null;
		}

		public String toString() {
			return String.valueOf(value);
		}
	}
}
