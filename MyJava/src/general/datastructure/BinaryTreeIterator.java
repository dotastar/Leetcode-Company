package general.datastructure;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;
import interview.utils.TreeNode;
import java.util.Stack;

import org.junit.Test;

public class BinaryTreeIterator {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(BinaryTreeIterator.class);
	}

	public static class BTreeIterator {
		Stack<TreeNode> stk;
		TreeNode next; // next node to traverse

		public BTreeIterator(TreeNode root) {
			next = root;
			stk = new Stack<TreeNode>();

			while (next != null) {
				stk.push(next);
				next = next.left;
			}
			next = stk.pop();
		}

		public TreeNode next() {
			if (!hasNext())
				return null;
			TreeNode res = next;
			if (next.right != null) {
				next = next.right;
				while (next.left != null) {
					stk.push(next);
					next = next.left;
				}
			} else
				next = stk.isEmpty() ? null : stk.pop();

			return res;
		}

		public boolean hasNext() {
			return !stk.isEmpty() || next != null;
		}
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
		BTreeIterator iter = new BTreeIterator(root);
		for (int i = 1; i <= 12; i++) {
			assertTrue(iter.hasNext());
			assertTrue(iter.next().key == i);
		}
		assertTrue(!iter.hasNext());
	}

}
