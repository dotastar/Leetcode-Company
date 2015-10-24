package interview.company.zenefits;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;
import interview.utils.TreeNode;

import java.util.Stack;

import org.junit.Test;

/**
 * 
 * Find a pair with given sum in a Balanced BST
 * Input: A binary search tree, consisting of integers. And a number k
 * 
 * Output:
 * True if there are two nodes a and b, such that a.value + b.value = k
 * False otherwise
 * 
 * 
 * Zenefits
 * 
 * http://www.geeksforgeeks.org/find-a-pair-with-given-sum-in-bst/
 * 
 * @author yazhoucao
 *
 */
public class TwoSumOfBST {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(TwoSumOfBST.class);
	}

	/**
	 * The Brute Force Solution is to consider each pair in BST and check
	 * whether the sum equals to X. The time complexity of this solution will be
	 * O(n^2).
	 */

	/**
	 * A Better Solution is to create an auxiliary array and store Inorder
	 * traversal of BST in the array. The array will be sorted as Inorder
	 * traversal of BST always produces sorted data. Once we have the Inorder
	 * traversal, we can pair in O(n) time (See this for details). This solution
	 * works in O(n) time, but requires O(n) auxiliary space.
	 */

	/**
	 * We traverse BST in Normal Inorder and Reverse Inorder simultaneously. In
	 * reverse inorder, we start from the rightmost node which is the maximum
	 * value node. In normal inorder, we start from the left most node which is
	 * minimum value node. We add sum of current nodes in both traversals and
	 * compare this sum with given target sum. If the sum is same as target sum,
	 * we return true.
	 */
	Stack<TreeNode> nextStk;
	Stack<TreeNode> prevStk;

	public boolean hasTwoSum(TreeNode root, int k) {
		if (root == null)
			return false;

		// initialize two iterator
		TreeNode forward = root;
		nextStk = new Stack<TreeNode>();
		while (forward.left != null) {
			nextStk.push(forward);
			forward = forward.left;
		}
		TreeNode backward = root;
		prevStk = new Stack<TreeNode>();
		while (backward.right != null) {
			prevStk.push(backward);
			backward = backward.right;
		}

		// two sum
		while (forward != backward) {
			int sum = forward.key + backward.key;
			if (sum == k)
				return true;
			else if (sum < k) {
				forward = next(nextStk, forward);
			} else {
				backward = prev(prevStk, backward);
			}
		}

		return false;
	}

	private TreeNode next(Stack<TreeNode> stk, TreeNode curr) {
		if (curr == null)
			return null;
		// get the next
		if (curr.right != null) {
			curr = curr.right;
			while (curr.left != null) {
				stk.push(curr);
				curr = curr.left;
			}
		} else
			curr = stk.isEmpty() ? null : stk.pop();

		return curr;
	}

	private TreeNode prev(Stack<TreeNode> stk, TreeNode curr) {
		if (curr == null)
			return null;
		// get the previous of natural inorder traversal
		if (curr.left != null) {
			curr = curr.left;
			while (curr.right != null) {
				stk.push(curr);
				curr = curr.right;
			}
		} else
			curr = stk.isEmpty() ? null : stk.pop();
		return curr;
	}

	@Test
	public void test1() {
		TreeNode root = new TreeNode(5);
		root.left = new TreeNode(3);
		root.left.left = new TreeNode(1);
		root.left.left.right = new TreeNode(2);
		root.left.right = new TreeNode(4);
		root.right = new TreeNode(8);
		root.right.left = new TreeNode(7);
		root.right.left.left = new TreeNode(6);
		root.right.right = new TreeNode(10);
		root.right.right.left = new TreeNode(9);
		root.right.right.right = new TreeNode(11);
		root.right.right.right.right = new TreeNode(12);
		int k = 3;
		assertTrue(hasTwoSum(root, k));
	}

	@Test
	public void test2() {
		TreeNode root = new TreeNode(5);
		root.left = new TreeNode(3);
		root.left.left = new TreeNode(1);
		root.left.left.right = new TreeNode(2);
		root.left.right = new TreeNode(4);
		root.right = new TreeNode(8);
		root.right.left = new TreeNode(7);
		root.right.left.left = new TreeNode(6);
		root.right.right = new TreeNode(10);
		root.right.right.left = new TreeNode(9);
		root.right.right.right = new TreeNode(11);
		root.right.right.right.right = new TreeNode(12);
		int k = 23;
		assertTrue(hasTwoSum(root, k));
	}

	@Test
	public void test3() {
		TreeNode root = new TreeNode(5);
		root.left = new TreeNode(3);
		root.left.left = new TreeNode(1);
		root.left.left.right = new TreeNode(2);
		root.left.right = new TreeNode(4);
		root.right = new TreeNode(8);
		root.right.left = new TreeNode(7);
		root.right.left.left = new TreeNode(6);
		root.right.right = new TreeNode(10);
		root.right.right.left = new TreeNode(9);
		root.right.right.right = new TreeNode(11);
		root.right.right.right.right = new TreeNode(12);
		int k = 27;
		assertTrue(!hasTwoSum(root, k));
	}

	@Test
	public void test4() {
		TreeNode root = new TreeNode(5);
		root.left = new TreeNode(3);
		root.left.left = new TreeNode(1);
		root.left.left.right = new TreeNode(2);
		root.left.right = new TreeNode(4);
		root.right = new TreeNode(8);
		root.right.left = new TreeNode(7);
		root.right.left.left = new TreeNode(6);
		root.right.right = new TreeNode(10);
		root.right.right.left = new TreeNode(9);
		root.right.right.right = new TreeNode(11);
		root.right.right.right.right = new TreeNode(12);
		int k = 14;
		assertTrue(hasTwoSum(root, k));
	}

}
