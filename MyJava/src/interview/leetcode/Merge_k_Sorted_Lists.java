package interview.leetcode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Merge k sorted linked lists and return it as one sorted list. Analyze and
 * describe its complexity.
 * 
 * @author yazhoucao
 * 
 */
public class Merge_k_Sorted_Lists {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<ListNode> testlist = new ArrayList<ListNode>();
		testlist.add(null);
		testlist.add(new ListNode(1));
		testlist.add(new ListNode(2));
		testlist.add(null);
		testlist.add(new ListNode(3));
		testlist.add(new ListNode(4));
		testlist.add(null);
		testlist.add(null);
		//it is very inefficient to remove null in this fasion
		System.out.println(testlist.toString());
		for (int i = 0; i < testlist.size(); i++)
			testlist.remove(i);
		System.out.println(testlist.toString());
	}

	/**
	 * Min head
	 * 
	 * Time: klg(k) + n*lg(k) = O(n*lg(k)), n is the total number of nodes,
	 * Space: O(lg(k))
	 * 
	 * l = average length of lists, then n = l*k
	 * 
	 */
	public static ListNode mergeKLists(List<ListNode> lists) {
		int k = lists.size();
		if (k == 0)
			return null;
		PriorityQueue<ListNode> q = new PriorityQueue<ListNode>(k,
				new Comparator<ListNode>() { // min heap
					@Override
					public int compare(ListNode o1, ListNode o2) {
						return o1.val - o2.val;
					}
				});

		for (ListNode khead : lists)
			if (khead != null)
				q.add(khead);
		ListNode pre = new ListNode(0);
		ListNode curr = pre;
		while (!q.isEmpty()) {
			ListNode min = q.poll();
			if (min.next != null)
				q.add(min.next);
			curr.next = min;
			curr = curr.next;
		}
		return pre.next;
	}

	/**
	 * Naive solution, compare all the k nodes each time
	 * 
	 * Time: n * 2k = O(nk)
	 * 
	 */
	public ListNode mergeKLists2(List<ListNode> lists) {
		if (lists == null || lists.size() == 0)
			return null;

		ListNode prehead = new ListNode(0);
		ListNode curr = prehead;
		List<ListNode> nonNullList = new ArrayList<ListNode>();
		for (ListNode head : lists)
			if (head != null)
				nonNullList.add(head);

		while (nonNullList.size() > 0) {
			int k = nonNullList.size();
			int min = k - 1;
			for (int i = 0; i < k; i++) { // O(k)
				ListNode head = nonNullList.get(i);
				if (head.val < nonNullList.get(min).val) {
					min = i;
				}
			}
			ListNode minNode = nonNullList.get(min);
			nonNullList.remove(min); // this has an O(k) overhead
			if (minNode.next != null)
				nonNullList.add(minNode.next);

			curr.next = minNode;
			curr = curr.next;
		}
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
