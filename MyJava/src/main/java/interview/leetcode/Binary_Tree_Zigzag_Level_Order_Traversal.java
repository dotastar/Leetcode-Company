package interview.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Given a binary tree, return the zigzag level order traversal of its nodes'
 * values. (ie, from left to right, then right to left for the next level and
 * alternate between).
 * 
 * For example: Given binary tree {3,9,20,#,#,15,7},
 * 
 * 3
 * / \
 * 9 20
 * / \
 * 15 7
 * 
 * return its zigzag level order traversal as:
 * [
 * [3],
 * [20,9],
 * [15,7]
 * ]
 * 
 * @author yazhoucao
 * 
 */
public class Binary_Tree_Zigzag_Level_Order_Traversal {

	public static void main(String[] args) {
		Binary_Tree_Zigzag_Level_Order_Traversal o = new Binary_Tree_Zigzag_Level_Order_Traversal();
		TreeNode root = new TreeNode(0);
		root.left = new TreeNode(1);
		root.right = new TreeNode(2);
		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(4);
		root.right.left = new TreeNode(5);
		root.right.right = new TreeNode(6);
		root.left.left.right = new TreeNode(7);
		root.right.left.right = new TreeNode(8);

		List<List<Integer>> res = o.zigzagLevelOrder(root);
		for (List<Integer> list : res)
			System.out.println(list.toString());
	}

	/**
	 * Key data structure: Deque
	 * Time: O(n)
	 */
	public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
		List<List<Integer>> res = new ArrayList<>();
		if (root == null)
			return res;
		Deque<TreeNode> q = new ArrayDeque<>();
		q.add(root);
		boolean reverse = false;
		while (!q.isEmpty()) {
			int size = q.size();
			List<Integer> row = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				TreeNode node = reverse ? q.pollLast() : q.pollFirst();
				row.add(node.val);
				if (reverse) {
					if (node.right != null)
						q.addFirst(node.right);
					if (node.left != null)
						q.addFirst(node.left);
				} else {
					if (node.left != null)
						q.addLast(node.left);
					if (node.right != null)
						q.addLast(node.right);
				}
			}
			res.add(row);
			reverse = !reverse;
		}
		return res;
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
