package interview.epi.chapter10_binary_trees;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Problem 10.8
 * Design a function that efficiently computes the k-th node appearing in an
 * inorder traversal. Specifically, your function should take as input a binary
 * tree T and an integer k. Each node has a size field, which is the number of
 * nodes in the subtree rooted at that node. What is the time complexity of your
 * function?
 * 
 * @author yazhoucao
 * 
 */
public class Q8_Compute_The_Kth_Node_In_An_Inorder_Traversal {
	static Class<?> c = Q8_Compute_The_Kth_Node_In_An_Inorder_Traversal.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Choose the path by the size of left/right child:
	 * 1.k <= left, in left branch
	 * 2.k == left+1, current parent node.
	 * 3.k > left+1, in right branch
	 * 
	 * Time: O(lgn), n is the nubmer of nodes.
	 * Space: O(lgn)
	 */
	public static Node<Integer> findKthNodeBinaryTree(Node<Integer> node, int k) {
		if (node == null || k < 1)
			return null;
		if ((node.left == null && k == 1)
				|| (node.left != null && k - node.left.size == 1))
			return node;
		if (node.left != null && k <= node.left.size)
			return findKthNodeBinaryTree(node.left, k);
		else {
			k = node.left == null ? k - 1 : k - 1 - node.left.size;
			return findKthNodeBinaryTree(node.right, k);
		}
	}

	/**
	 * Iterative fashion, Time: O(lgn), Space: O(1).
	 */
	public static Node<Integer> findKthNodeBinaryTree_Improved(
			Node<Integer> node, int k) {
		while (node != null) {
			int leftSize = node.left != null ? node.left.size : 0;
			if (k == leftSize + 1)
				return node;
			else if (k <= leftSize) {
				node = node.left;
			} else {
				k -= 1 + leftSize;
				node = node.right;
			}
		}
		return null;
	}

	/****************** Unit Test ******************/

	@Test
	public void test0() {
		// size field
		// 6
		// 2 3
		// 1 1 1
		//
		// data field
		// 3
		// 2 5
		// 1 4 6
		Node<Integer> root = new Node<>();
		root.size = 6;
		root.data = 3;
		root.left = new Node<>();
		root.left.size = 2;
		root.left.data = 2;
		root.left.left = new Node<>();
		root.left.left.size = 1;
		root.left.left.data = 1;
		root.right = new Node<>();
		root.right.size = 3;
		root.right.data = 5;
		root.right.left = new Node<>();
		root.right.left.size = 1;
		root.right.left.data = 4;
		root.right.right = new Node<>();
		root.right.right.size = 1;
		root.right.right.data = 6;
		// should throw
		assertTrue(null == findKthNodeBinaryTree(root, 0));
		// should output 1
		assertTrue(findKthNodeBinaryTree(root, 1).data == 1);
		System.out.println((findKthNodeBinaryTree(root, 1)).data);
		// should output 2
		assertTrue(findKthNodeBinaryTree(root, 2).data == 2);
		System.out.println((findKthNodeBinaryTree(root, 2)).data);
		// should output 3
		assertTrue(findKthNodeBinaryTree(root, 3).data == 3);
		System.out.println((findKthNodeBinaryTree(root, 3)).data);
		// should output 4
		assertTrue(findKthNodeBinaryTree(root, 4).data == 4);
		System.out.println((findKthNodeBinaryTree(root, 4)).data);
		// should output 5
		assertTrue(findKthNodeBinaryTree(root, 5).data == 5);
		System.out.println((findKthNodeBinaryTree(root, 5)).data);
		// should output 6
		assertTrue(findKthNodeBinaryTree(root, 6).data == 6);
		System.out.println((findKthNodeBinaryTree(root, 6)).data);
		// should throw
		assertTrue(null == findKthNodeBinaryTree(root, 7));
	}

	public static class Node<T> {
		public T data;
		public Node<T> left, right;
		public int size;
	}
}
