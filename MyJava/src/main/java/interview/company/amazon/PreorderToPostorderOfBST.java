package interview.company.amazon;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.junit.Test;

/**
 * Given Preorder traversal of a BST, implement a function to find its Postorder
 * traversal.
 * 
 * @author yazhoucao
 * 
 */
public class PreorderToPostorderOfBST {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(PreorderToPostorderOfBST.class);
	}

	/**
	 * We can deduce the in-order traversal by sorting the numbers of preorder,
	 * and it is the an inorder array because it is a BST.
	 */
	public int[] convert(int[] preorder) {
		int len = preorder.length;
		int[] post = new int[len];
		TreeNode root = constructBSTbyPreorder(preorder);
		postorderTraverse(root, post);
		return post;
	}

	public TreeNode constructBSTbyPreorder(int[] preorder) {
		int len = preorder.length;
		int[] inorder = Arrays.copyOf(preorder, len);
		Arrays.sort(inorder);
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < len; i++)
			map.put(inorder[i], i);

		return construct(preorder, 0, inorder, 0, len - 1, map);
	}

	private void postorderTraverse(TreeNode root, int[] post) {
		if (root == null)
			return;
		Stack<TreeNode> stk = new Stack<TreeNode>();
		stk.push(root);
		TreeNode prev = null;
		int i = 0;
		while (!stk.isEmpty()) {
			TreeNode curr = stk.peek();
			if (prev == null || curr == prev.left || curr == prev.right) {
				if (curr.left != null) {
					stk.push(curr.left);
				} else if (curr.right != null) {
					stk.push(curr.right);
				} else
					post[i++] = stk.pop().val;
			} else if (prev == curr.left) {
				if (curr.right != null)
					stk.push(curr.right);
				else
					post[i++] = stk.pop().val;
			} else
				post[i++] = stk.pop().val;

			prev = curr;
		}
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

	@Test
	public void test1() {
		int[] preorder = { 2, 1, 4, 3 };
		int[] ans = { 1, 3, 4, 2 };
		System.out.println(Arrays.toString(convert(preorder)));
		assertTrue(Arrays.equals(ans, convert(preorder)));
	}

	@Test
	public void test2() {
		int[] preorder = { 1 };
		int[] ans = { 1 };
		System.out.println(Arrays.toString(convert(preorder)));
		assertTrue(Arrays.equals(ans, convert(preorder)));
	}

	@Test
	public void test3() {
		int[] preorder = { 6, 5, 4, 3, 2, 1 };
		int[] ans = { 1, 2, 3, 4, 5, 6 };
		System.out.println(Arrays.toString(convert(preorder)));
		assertTrue(Arrays.equals(ans, convert(preorder)));
	}

	@Test
	public void test4() {
		int[] preorder = { 4, 2, 1, 3, 6, 5, 7 };
		int[] ans = { 1, 3, 2, 5, 7, 6, 4 };
		System.out.println(Arrays.toString(convert(preorder)));
		assertTrue(Arrays.equals(ans, convert(preorder)));
	}

	private static class TreeNode {
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
