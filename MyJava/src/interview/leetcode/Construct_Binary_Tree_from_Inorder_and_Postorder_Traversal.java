package interview.leetcode;

import java.util.Map;

/**
 * Given inorder and postorder traversal of a tree, construct the binary tree.
 * 
 * Note: You may assume that duplicates do not exist in the tree.
 * 
 * @author yazhoucao
 * 
 */
public class Construct_Binary_Tree_from_Inorder_and_Postorder_Traversal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * This problem can be illustrated by using a simple example.
	 * 
	 * in-order: 4 2 5 (1) 6 7 3 8
	 * 
	 * post-order: 4 5 2 6 7 8 3 (1)
	 * 
	 * From the post-order array, we know that last element is the root. We can
	 * find the root in in-order array. Then we can identify the left and right
	 * sub-trees of the root from in-order array.
	 * 
	 * Using the length of left sub-tree, we can identify left and right
	 * sub-trees in post-order array. Recursively, we can build up the tree.
	 * 
	 * Time: O(nlog(n)), Space: O(1).
	 */
	public TreeNode buildTree(int[] inorder, int[] postorder) {
		return build(inorder, 0, inorder.length - 1, postorder, 0,
				postorder.length - 1);
	}

	public TreeNode build(int[] inorder, int instart, int inend,
			int[] postorder, int postart, int poend) {
		if (instart > inend)
			return null;
		TreeNode subroot = new TreeNode(postorder[poend]);
		int idx = 0;
		for (int i = instart; i <= inend; i++)
			if (inorder[i] == postorder[poend]) {
				idx = i;
				break;
			}
		int leftLen = idx - instart;
		// int rightLen = inend-idx+1;
		subroot.left = build(inorder, instart, idx - 1, postorder, postart,
				postart + leftLen - 1);
		subroot.right = build(inorder, idx + 1, inend, postorder, postart
				+ leftLen, poend - 1);
		return subroot;
	}

	/**
	 * Same Idea, Time improved by a HashMap which avoid loop and searching the
	 * value in inorder[], have to initialize Hashmap first.
	 * 
	 * Time: O(n), Space: O(n).
	 * 
	 */
	public TreeNode build(int[] inorder, int inBegin, int inEnd,
			int[] postorder, int poBegin, int poEnd,
			Map<Integer, Integer> inIdxMap) {
		if (inBegin > inEnd || poBegin > poEnd)
			return null;
		int rootVal = postorder[poEnd];
		TreeNode root = new TreeNode(rootVal);
		int i = inIdxMap.get(rootVal);
		int leftLen = i - inBegin;
		root.left = build(inorder, inBegin, i - 1, postorder, poBegin, poBegin
				+ leftLen - 1, inIdxMap);
		root.right = build(inorder, i + 1, inEnd, postorder, poBegin + leftLen,
				poEnd - 1, inIdxMap);
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
