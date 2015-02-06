package interview.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Given a binary tree, flatten it to a linked list in-place.
 * 
 * For example, Given
 * 
         1
        / \
       2   5
      / \   \
     3   4   6
 * 
 * The flattened tree should look like:
 * 1
    \
     2
      \
       3
        \
         4
          \
           5
            \
             6
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
		Flatten_Binary_Tree_to_Linked_List o = new Flatten_Binary_Tree_to_Linked_List();
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		o.flatten3(root);
	}

	/**
	 * Pass the test
	 * Idea: variant of classical preorder traversal
	 * Go down through the left, when right is not null, push right to stack.
	 * This is more cache efficient than the below solution - flatten3
	 */
	public void flatten4(TreeNode root) {
		Stack<TreeNode> stk = new Stack<TreeNode>();
		TreeNode p = root;
		while (p != null) {
			if (p.right != null)
				stk.push(p.right);
			if (p.left != null) { // set left to right
				p.right = p.left;
				p.left = null;
			} else if (!stk.isEmpty()) // set to the next closest right node
				p.right = stk.pop();

			p = p.right; // traverse next
		}
	}

	/**
	 * Time Limit Exceeded
	 * Although TLE, it is very clear and concise
	 * Preorder traversal, set the current to the next
	 */
	public void flatten3(TreeNode root) {
		if (root == null)
			return;
		Stack<TreeNode> stk = new Stack<TreeNode>();
		stk.push(root);
		while (!stk.isEmpty()) {
			TreeNode curr = stk.pop();
			if (curr.right != null)
				stk.push(curr.right);
			if (curr.left != null)
				stk.push(curr.left);

			curr.right = stk.isEmpty() ? null : stk.peek();
		}
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
