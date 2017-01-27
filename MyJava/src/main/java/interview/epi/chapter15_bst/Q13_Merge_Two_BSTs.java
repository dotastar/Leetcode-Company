package interview.epi.chapter15_bst;

import interview.AutoTestUtils;
import interview.epi.chapter10_binary_trees.BinaryTreePrototypeTemplate.BinaryTree;

/**
 * Let A and B be BSTs. Design an algorithm that merges them in O(n) time, where
 * n is the total number of nodes in the BSTs.
 * 
 * @author yazhoucao
 * 
 */
public class Q13_Merge_Two_BSTs {

	static Class<?> c = Q13_Merge_Two_BSTs.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Use methods in Problem 15.11 and Problem 15.12.
	 * 1.Convert the two BSTs to two doubly linked list in place. (15.12)
	 * 2.Merge those two sorted linked list.
	 * 3.Convert the resulting list back to a BST in place. (15.11)
	 */
	public static BinaryTree<Integer> mergeTwoBSTs(BinaryTree<Integer> A,
			BinaryTree<Integer> B) {
		A = bstToDoublyLinkedList(A);
		B = bstToDoublyLinkedList(B);
		A.getLeft().setRight(null);
		B.getLeft().setRight(null);
		A.setLeft(null);
		B.setLeft(null);
		int ALength = countLength(A);
		int BLength = countLength(B);
		return buildSortedDoublyLinkedList(mergeTwoSortedLinkedLists(A, B),
				ALength + BLength);
	}


	private static BinaryTree<Integer> head;

	private static BinaryTree<Integer> buildSortedDoublyLinkedList(
			BinaryTree<Integer> L, int n) {
		head = L;
		return buildSortedDoublyLinkedListHelper(0, n);
	}

	// Build a BST from the (s + 1)-th to the e-th node in L.
	private static BinaryTree<Integer> buildSortedDoublyLinkedListHelper(int s,
			int e) {
		if (s >= e) {
			return null;
		}
		int m = s + ((e - s) / 2);
		BinaryTree<Integer> left = buildSortedDoublyLinkedListHelper(s, m);
		BinaryTree<Integer> curr = new BinaryTree<>(head.getData());
		head = head.getRight();
		curr.setLeft(left);
		curr.setRight(buildSortedDoublyLinkedListHelper(m + 1, e));
		return curr;
	}

	// Transform a BST into a circular sorted doubly linked list in-place,
	// return the head of the list.
	private static <T> BinaryTree<T> bstToDoublyLinkedList(BinaryTree<T> n) {
		// Empty subtree.
		if (n == null) {
			return null;
		}
		// Recursively build the list from left and right subtrees.
		BinaryTree<T> lHead = bstToDoublyLinkedList(n.getLeft());
		BinaryTree<T> rHead = bstToDoublyLinkedList(n.getRight());
		// Append n to the list from left subtree.
		BinaryTree<T> lTail = null;
		if (lHead != null) {
			lTail = lHead.getLeft();
			lTail.setRight(n);
			n.setLeft(lTail);
			lTail = n;
		} else {
			lHead = lTail = n;
		}
		// Append the list from right subtree to n.
		BinaryTree<T> rTail = null;
		if (rHead != null) {
			rTail = rHead.getLeft();
			lTail.setRight(rHead);
			rHead.setLeft(lTail);
		} else {
			rTail = lTail;
		}
		rTail.setRight(lHead);
		lHead.setLeft(rTail);
		return lHead;
	}

	// Count the list length till end.
	private static <T> int countLength(BinaryTree<T> L) {
		int len = 0;
		while (L != null) {
			++len;
			L = L.getRight();
		}
		return len;
	}

	// @exclude
	// Merge two sorted linked lists, return the head of list.
	private static BinaryTree<Integer> mergeTwoSortedLinkedLists(
			BinaryTree<Integer> A, BinaryTree<Integer> B) {
		BinaryTree<Integer> dummyHead = new BinaryTree<>();
		BinaryTree<Integer> current = dummyHead;
		BinaryTree<Integer> p1 = A;
		BinaryTree<Integer> p2 = B;
		while (p1 != null && p2 != null) {
			if (p1.getData().compareTo(p2.getData()) < 0) {
				current.setRight(p1);
				p1 = p1.getRight();
			} else {
				current.setRight(p2);
				p2 = p2.getRight();
			}
			current = current.getRight();
		}
		if (p1 != null) {
			current.setRight(p1);
		}
		if (p2 != null) {
			current.setRight(p2);
		}
		return dummyHead.getRight();
	}
}
