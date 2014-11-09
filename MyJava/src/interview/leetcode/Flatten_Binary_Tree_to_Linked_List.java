package interview.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Given a binary tree, flatten it to a linked list in-place.
 * 
 * For example, Given
 * 1
 * / \
 * 2 5
 * / \ \
 * 3 4 6
 * 
 * The flattened tree should look like:
 * 
 * 1
 * \
 * 2
 * \
 * 3
 * \
 * 4
 * \
 * 5
 * \
 * 6
 * 
 * Hints:
 * 
 * If you notice carefully in the flattened tree, each node's
 * right child points to the next node of a pre-order traversal.
 * 
 * @author yazhoucao
 * 
 */
public class Flatten_Binary_Tree_to_Linked_List {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	/**
	 * Thought: iteratively preorder traverse the tree, record its previous
	 * visited node, set prev.right point to the current visited node.
	 * 
	 * Time: O(n), Space: O(log(n))
	 */
	public void flatten_Iter2(TreeNode root) {
		if (root == null || (root.left == null && root.right == null))
			return;
		Stack<TreeNode> stk = new Stack<TreeNode>();
		stk.push(root);
		TreeNode prev = root;
		while (!stk.isEmpty()) {
			TreeNode curr = stk.pop();
			if (curr != null) {
				stk.push(curr.right);
				stk.push(curr.left);
				prev.left = null;
				prev.right = curr;
				prev = curr;
			}
		}
	}

	/**
	 * Recursion, simple preorder traversal
	 * Time: O(n), Space: O(n)
	 */
	public void flatten(TreeNode root) {
		if (root == null)
			return;
		List<TreeNode> list = new ArrayList<TreeNode>();
		preorderAdd(root, list);
		for (int i = 0; i < list.size() - 1; i++) {
			TreeNode curr = list.get(i);
			curr.left = null;
			curr.right = list.get(i + 1);
		}
		list.get(list.size() - 1).right = null;
	}

	public void preorderAdd(TreeNode node, List<TreeNode> list) {
		if (node == null)
			return;
		list.add(node);
		preorderAdd(node.left, list);
		preorderAdd(node.right, list);
	}

	
	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}

		public String toString() {
			return Integer.toString(val);
		}
	}
}
