package interview.company.facebook;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.junit.Test;

/**
 * Write an iterator for the postorder traversal of binary tree. The iterator
 * should print elements in order of postorder traversal on every call to it.
 * Eg. class iterator{
 * public:
 * 	next();
 * } T;
 * 
 * @author yazhoucao
 * 
 */
public class PostOrderBinaryTreeIterator {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(PostOrderBinaryTreeIterator.class);
	}

	public static class PostOrderIterator {

		private Stack<TreeNode> stk;
		private TreeNode prev;
		private TreeNode root;

		public PostOrderIterator(TreeNode root) {
			assert root != null;
			this.root = root;
			prev = null;
			stk = new Stack<TreeNode>();
			stk.push(root);
		}

		public TreeNode next() {
			while (!stk.isEmpty()) {
				TreeNode curr = stk.peek();
				if (prev == null || prev.left == curr || prev.right == curr) {
					if (curr.left != null)
						stk.push(curr.left);
					else if (curr.right != null)
						stk.push(curr.right);
					else
						break;
				} else if (prev == curr.left) {
					if (curr.right != null)
						stk.push(curr.right);
					else
						break;
				} else
					break;

				prev = curr;
			}
			prev = stk.isEmpty() ? null : stk.pop();
			return prev;
		}

		public boolean hasNext() {
			return !stk.isEmpty();
		}

		public void reset() {
			stk.clear();
			stk.push(root);
			prev = null;
		}
	}

	/********************* Unit Test *********************/

	@Test
	public void test1() {
		int[] preorder = { 2, 1, 4, 3 };
		int[] ans = { 1, 3, 4, 2 };
		TreeNode root = constructBSTbyPreorder(preorder);
		PostOrderIterator iter = new PostOrderIterator(root);
		for (int val : ans) {
			assertTrue(iter.hasNext());
			int res = iter.next().val;
			assertTrue(res == val);
		}
		assertTrue(!iter.hasNext());
	}

	@Test
	public void test2() {
		int[] preorder = { 1 };
		int[] ans = { 1 };
		TreeNode root = constructBSTbyPreorder(preorder);
		PostOrderIterator iter = new PostOrderIterator(root);
		for (int val : ans) {
			assertTrue(iter.hasNext());
			assertTrue(iter.next().val == val);
		}
		assertTrue(!iter.hasNext());
	}

	@Test
	public void test3() {
		int[] preorder = { 6, 5, 4, 3, 2, 1 };
		int[] ans = { 1, 2, 3, 4, 5, 6 };
		TreeNode root = constructBSTbyPreorder(preorder);
		PostOrderIterator iter = new PostOrderIterator(root);
		for (int val : ans) {
			assertTrue(iter.hasNext());
			assertTrue(iter.next().val == val);
		}
		assertTrue(!iter.hasNext());
	}

	@Test
	public void test4() {
		int[] preorder = { 4, 2, 1, 3, 6, 5, 7 };
		int[] ans = { 1, 3, 2, 5, 7, 6, 4 };
		TreeNode root = constructBSTbyPreorder(preorder);
		PostOrderIterator iter = new PostOrderIterator(root);
		for (int val : ans) {
			assertTrue(iter.hasNext());
			assertTrue(iter.next().val == val);
		}
		assertTrue(!iter.hasNext());
	}

	/********************* Test Utilities *********************/

	public TreeNode constructBSTbyPreorder(int[] preorder) {
		int len = preorder.length;
		int[] inorder = Arrays.copyOf(preorder, len);
		Arrays.sort(inorder);
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < len; i++)
			map.put(inorder[i], i);

		return construct(preorder, 0, inorder, 0, len - 1, map);
	}

	private TreeNode construct(int[] preorder, int i, int[] inorder, int m,
			int n, Map<Integer, Integer> idxMap) {
		if (m > n)
			return null;
		TreeNode root = new TreeNode(preorder[i]);
		int rootIdx = idxMap.get(preorder[i]);
		int leftLen = rootIdx - m + 1;
		root.left = construct(preorder, i + 1, inorder, m, rootIdx - 1, idxMap);
		root.right = construct(preorder, i + leftLen, inorder, rootIdx + 1, n,
				idxMap);
		return root;
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
