package interview.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Given a binary tree, return the bottom-up level order traversal of its nodes'
 * values. (ie, from left to right, level by level from leaf to root).
 * 
 * For example: Given binary tree {3,9,20,#,#,15,7},
 * 
   3
   / \
  9  20
    /  \
   15   7
 * 
 * return its bottom-up level order traversal as:
 * 
 * [
 * [15,7],
 * [9,20],
 * [3]
 * ]
 * 
 * @author yazhoucao
 * 
 */
public class Binary_Tree_Level_Order_Traversal_II {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * BFS
	 */
	public List<List<Integer>> levelOrderBottom(TreeNode root) {
		List<List<Integer>> lists = new ArrayList<List<Integer>>();
		if (root == null)
			return lists;

		Queue<TreeNode> q = new LinkedList<TreeNode>();
		q.add(root);
		while (!q.isEmpty()) { // BFS traversal all nodes
			List<Integer> level = new ArrayList<Integer>();
			int size = q.size();
			for (int i = 0; i < size; i++) {
				TreeNode node = q.poll();
				if (node != null) {
					level.add(node.val);
					q.add(node.left);
					q.add(node.right);
				}
			}
			if (level.size() > 0)
				lists.add(level);
		}

		// reverse the lists
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		for (int i = lists.size() - 1; i >= 0; i--)
			res.add(lists.get(i));
		return res;
	}

	/**
	 * Same solution, second practice
	 * 
	 */
	public List<List<Integer>> levelOrderBottom2(TreeNode root) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		if (root == null)
			return res;
		Queue<TreeNode> q = new LinkedList<TreeNode>();
		q.offer(root);
		while (!q.isEmpty()) {
			int size = q.size();
			List<Integer> row = new ArrayList<Integer>(size);
			for (int i = 0; i < size; i++) {
				TreeNode node = q.poll();
				if (node != null) {
					row.add(node.val);
					q.offer(node.left);
					q.offer(node.right);
				}
			}
			if (row.size() > 0)
				res.add(row);
		}
		int len = res.size();
		for (int i = 0; i < len / 2; i++) {
			List<Integer> row = res.get(i);
			res.set(i, res.get(len - 1 - i));
			res.set(len - 1 - i, row);
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
