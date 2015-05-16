package interview.leetcode;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Test;

/**
 * Given a binary tree, imagine yourself standing on the right side of it,
 * return the values of the nodes you can see ordered from top to bottom.
 * 
 * For example:
 * Given the following binary tree,
 * 
 ** 1 <---
 * / \
 * 2 3 <---
 * \ \
 * 5 4 <---
 * 
 * You should return [1, 3, 4].
 * 
 * 
 * @author yazhoucao
 *
 */
public class Binary_Tree_Right_Side_View {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Binary_Tree_Right_Side_View.class);
	}

	/**
	 * BFS, when traversing each level, record the last element of each level
	 * Time: O(n)
	 */
	public List<Integer> rightSideView(TreeNode root) {
		List<Integer> res = new ArrayList<>();
		if (root == null)
			return res;
		Queue<TreeNode> q = new LinkedList<>();
		q.add(root);
		while (!q.isEmpty()) {
			int size = q.size();
			TreeNode last = null;
			for (int i = 0; i < size; i++) {
				last = q.poll();
				if (last.left != null)
					q.add(last.left);
				if (last.right != null)
					q.add(last.right);
			}
			res.add(last.val);
		}
		return res;
	}

	@Test
	public void test1() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.right = new TreeNode(5);
		root.right.right = new TreeNode(4);
		List<Integer> res = rightSideView(root);
		List<Integer> ans = Arrays.asList(1, 3, 4);
		assertTrue(res.equals(ans));
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
