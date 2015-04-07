package interview.laicode;

import interview.AutoTestUtils;
import interview.laicode.utils.TreeNode;

import java.util.Stack;

import org.junit.Test;

/**
 * Reconstruct Binary Search Tree With Preorder Traversal
 * Fair
 * Data Structure
 * 
 * Given the preorder traversal sequence of a binary search tree, reconstruct
 * the original tree.
 * 
 * Assumptions
 * 
 * The given sequence is not null
 * There are no duplicate keys in the binary search tree
 * 
 * Examples
 * 
 * preorder traversal = {5, 3, 1, 4, 8, 11}
 * 
 * The corresponding binary search tree is
 * 
 ** 5
 * 
 * / \
 * 
 * 3 8
 * 
 * / \ \
 * 
 * 1 4 11
 * 
 * How is the binary tree represented?
 * 
 * We use the pre order traversal sequence with a special symbol "#" denoting
 * the null node.
 * 
 * For Example:
 * 
 * The sequence [1, 2, #, 3, 4, #, #, #] represents the following binary tree:
 * 
 *** 1
 * 
 ** / \
 * 
 * 2 _ 3
 * 
 *** /
 * 
 * 4
 * 
 * @author yazhoucao
 * 
 */
public class Reconstruct_Binary_Search_Tree_With_Preorder_Traversal {

	static Class<?> c = Reconstruct_Binary_Search_Tree_With_Preorder_Traversal.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Time: O(n^2) worst case,
	 * O(nlogn) average case (if the tree has log(n) height)
	 */
	public TreeNode reconstruct(int[] pre) {
		return reconstruct(pre, 0, pre.length - 1);
	}

	private TreeNode reconstruct(int[] pre, int l, int r) {
		if (l > r)
			return null;

		TreeNode root = new TreeNode(pre[l]);
		int leftEnd = l;
		for (int i = l + 1; i <= r && pre[i] < root.key; i++) {
			leftEnd = i;
		}
		root.left = reconstruct(pre, l + 1, leftEnd);
		root.right = reconstruct(pre, leftEnd + 1, r);
		return root;
	}

	@Test
	public void test1() {
		int[] pre = { 5, 3, 1, 4, 8, 11 };
		TreeNode root = reconstruct(pre);
		printBST(root);
		System.out.println();
	}

	@Test
	public void test2() {
		int[] pre = { 5, 4, 3, 2, 1 };
		TreeNode root = reconstruct(pre);
		printBST(root);
		System.out.println();
	}

	public void printBST(TreeNode root) {
		Stack<TreeNode> stk = new Stack<>();
		TreeNode p = root;
		while (!stk.isEmpty() || p != null) {
			if (p != null) {
				stk.push(p);
				p = p.left;
			} else {
				p = stk.pop();
				System.out.print(p + "\t");
				p = p.right;
			}
		}
	}
}
