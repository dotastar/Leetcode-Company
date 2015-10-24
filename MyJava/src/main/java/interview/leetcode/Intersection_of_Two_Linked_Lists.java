package interview.leetcode;

/**
 * Write a program to find the node at which the intersection of two singly
 * linked lists begins.
 * 
 * Notes:
 * If the two linked lists have no intersection at all, return null.
 * The linked lists must retain their original structure after the function
 * returns.
 * You may assume there are no cycles anywhere in the entire linked structure.
 * Your code should preferably run in O(n) time and use only O(1) memory.
 * 
 * @author yazhoucao
 * 
 */
public class Intersection_of_Two_Linked_Lists {

	public static void main(String[] args) {
		ListNode lA = new ListNode(2);
		lA.next = new ListNode(3);

		ListNode lB = new ListNode(3);

		System.out.println(getIntersectionNode(lA, lB));
	}

	/**
	 * O(n+m) running time, O(1) memory
	 * First advancing through the longer list by nLa-nLb (suppose nLa > nLb),
	 * and then advancing through both lists in lock-step, stopping at the first
	 * common node.
	 */
	public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
		int lenA = getLength(headA), lenB = getLength(headB);
		int diff = lenB - lenA;
		if (diff < 0) {
			ListNode temp = headA;
			headA = headB;
			headB = temp;
			diff = -diff;
		}
		for (int i = 0; i < diff; i++)
			headB = headB.next;
		while (headA != headB) {
			headA = headA.next;
			headB = headB.next;
		}
		return headA == headB ? headA : null;
	}

	private static int getLength(ListNode h) {
		int i = 0;
		while (h != null) {
			h = h.next;
			i++;
		}
		return i;
	}

	/**
	 * A more concise solution
	 * 
	 * Two pointer solution (O(n+m) running time, O(1) memory):
	 * Maintain two pointers pA and pB initialized at the head of A and B,
	 * respectively. Then let them both traverse through the lists, one node at
	 * a time.
	 * When pA reaches the end of a list, then redirect it to the head of B
	 * (yes, B, that's right.); similarly when pB reaches the end of a list,
	 * redirect it the head of A.
	 * If at any point pA meets pB, then pA/pB is the intersection node.
	 * 
	 * To see why the above trick would work, consider the following two lists:
	 * A = {1,3,5,7,9,11} and B = {2,4,9,11}, which are intersected at node '9'.
	 * Since B.length (=4) < A.length (=6), pB would reach the end of the merged
	 * list first, because pB traverses exactly 2 nodes less than pA does. By
	 * redirecting pB to head A, and pA to head B, we now ask pB to travel
	 * exactly 2 more nodes than pA would. So in the second iteration, they are
	 * guaranteed to reach the intersection node at the same time.
	 * If two lists have intersection, then their last nodes must be the same
	 * one. So when pA/pB reaches the end of a list, record the last element of
	 * A/B respectively. If the two last elements are not the same one, then the
	 * two lists have no intersections.
	 */
	public static ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
		if (headA == null || headB == null)
			return null;
		boolean oneRoundA = false;
		boolean oneRoundB = false;
		ListNode p1 = headA, p2 = headB;
		while (true) {
			if (p1 == null) {
				p1 = headB;
				oneRoundA = true;
			} else
				p1 = p1.next;

			if (p2 == null) {
				p2 = headA;
				oneRoundB = true;
			} else
				p2 = p2.next;

			if (oneRoundA && oneRoundB) {
				if (p1 == null || p2 == null)
					return null;
				if (p1 == p2)
					return p1;
			}
		}
	}

	/**
	 * follow up: what if there may be cycles in the lists.
	 * 
	 * @author yazhoucao
	 * 
	 */

	/**
	 * Case analysis:
	 * First, detecting cycles:
	 * 1. if only one has a cycle (A or B), there is no overlap
	 * 2. if both no cycles, the same as original
	 * 3. if both has a cycle, then the cycles must be identical, the problem
	 * now is to find a common overlapped point to use it as an end, then we can
	 * use the above method which is advance the longer one and then lock-step
	 * advance together until meet. And we can use the same method in case 2 to
	 * find the anyone of the two begin point as an end. (Actually one of them
	 * is the result.)
	 * 
	 * Details see p239 EPI
	 * 
	 * @author yazhoucao
	 * 
	 */

	public static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
			next = null;
		}

		public String toString() {
			return String.valueOf(val);
		}
	}
}
