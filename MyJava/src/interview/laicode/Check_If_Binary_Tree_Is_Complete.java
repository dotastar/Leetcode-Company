package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;
import interview.laicode.utils.LaiCodeUtils;
import interview.laicode.utils.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

/**
 * Data Structure
 * 
 * Check if a given binary tree is completed. A complete binary tree is one in
 * which every level of the binary tree is completely filled except possibly the
 * last level. Furthermore, all nodes are as far left as possible.
 * 
 * @author yazhoucao
 * 
 */
public class Check_If_Binary_Tree_Is_Complete {

	public static void main(String[] args) {
		AutoTestUtils
				.runTestClassAndPrint(Check_If_Binary_Tree_Is_Complete.class);
	}

	/**
	 * Second practice, more concise, compare in the current level
	 * 1.BFS traverse the tree
	 * 2.before check current node, first check null flag, if null flag is set,
	 * return false
	 * 3.else check current node, if current node is null, set a null flag
	 * 
	 */
	public boolean isCompleted2(TreeNode root) {
		if (root == null)
			return true;
		Queue<TreeNode> q = new LinkedList<>();
		q.add(root);
		boolean isEmpty = false;
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				TreeNode curr = q.poll();
				if (curr != null) {
					if (isEmpty)
						return false;
					q.add(curr.left);
					q.add(curr.right);
				} else
					isEmpty = true;
			}
		}
		return true;
	}

	public boolean isCompleted(TreeNode root) {
		if (root == null)
			return true;
		Queue<TreeNode> q = new LinkedList<>();
		q.add(root);
		boolean hasEmpty = false;
		while (!q.isEmpty()) {
			TreeNode node = q.poll();
			if (node.left != null) {
				if (hasEmpty)
					return false;
				q.add(node.left);
			} else
				hasEmpty = true;

			if (node.right != null) {
				if (hasEmpty)
					return false;
				q.add(node.right);
			} else
				hasEmpty = true;

		}
		return true;
	}

	@Test
	public void test1() {
		String[] data = { "4", "3", "7", "2", "1" };
		TreeNode root = LaiCodeUtils.deserializeTree(data);
		assertTrue(isCompleted(root));
	}

}
