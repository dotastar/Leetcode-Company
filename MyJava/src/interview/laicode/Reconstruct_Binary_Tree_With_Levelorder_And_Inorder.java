package interview.laicode;

import interview.laicode.utils.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Reconstruct Binary Tree With Levelorder And Inorder
 * Hard
 * Recursion
 * 
 * Given the levelorder and inorder traversal sequence of a binary tree,
 * reconstruct the original tree.
 * 
 * Assumptions
 * 
 * The given sequences are not null and they have the same length
 * There are no duplicate keys in the binary tree
 * 
 * Examples
 * 
 * levelorder traversal = {5, 3, 8, 1, 4, 11}
 * 
 * inorder traversal = {1, 3, 4, 5, 8, 11}
 * 
 * the corresponding binary tree is
 * 
 * 5
 * 
 * / \
 * 
 * 3 8
 * 
 * / \ \
 * 
 * 1 4 11
 * 
 * How is the binary tree represented?
 * 
 * We use the pre order traversal sequence with a special symbol "#" denoting
 * the null node.
 * 
 * For Example:
 * 
 * The sequence [1, 2, #, 3, 4, #, #, #] represents the following binary tree:
 * 
 * 1
 * 
 * / \
 * 
 * 2 3
 * 
 * /
 * 
 * 4
 * 
 * @author yazhoucao
 * 
 */
public class Reconstruct_Binary_Tree_With_Levelorder_And_Inorder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Time: O(nlogn)
	 * 1.In-order traversal array gives us the relative position of root node
	 * and its left/ right children.
	 * 2.Level-order array gives us the root node which is the first element
	 * 3.Recursively split the level order list into two sublists, one for
	 * constructing the left subtree, the other for right subtree.
	 * 4.At each recursion, we only construct the current root node, which can
	 * be easily found because it is the first entry of the current list
	 * 
	 * 5 3 8 1 4 11
	 * ^
	 * 1 3 4 5 8 11
	 * ^
	 */
	public TreeNode reconstruct(int[] in, int[] level) {
		int size = in.length;
		Map<Integer, Integer> idxMap = new HashMap<>(size);
		List<Integer> levelValues = new ArrayList<>(size);
		for (int i = 0; i < in.length; i++) {
			idxMap.put(in[i], i);
			levelValues.add(level[i]);
		}
		return construct(levelValues, idxMap);
	}

	/**
	 * reconstruct the subtree based on the level order data and inorder index
	 * map
	 */
	private TreeNode construct(List<Integer> level, Map<Integer, Integer> inIdxMap) {
		if (level.isEmpty())
			return null;
		TreeNode root = new TreeNode(level.get(0));
		int rootIdx = inIdxMap.get(root.key);
		List<Integer> leftNodes = new ArrayList<Integer>();
		List<Integer> rightNodes = new ArrayList<Integer>();
		// splitting the current level-order list
		for (int i = 1; i < level.size(); i++) {
			Integer value = level.get(i);
			if (inIdxMap.get(value) < rootIdx)
				leftNodes.add(value);
			else
				rightNodes.add(value);
		}
		// recursively constructing its children
		root.left = construct(leftNodes, inIdxMap);
		root.right = construct(rightNodes, inIdxMap);
		return root;
	}

	public void printBST(TreeNode root) {
		Stack<TreeNode> stk = new Stack<>();
		TreeNode p = root;
		while (!stk.isEmpty() || p != null) {
			if (p != null) {
				stk.push(p);
				p = p.left;
			} else {
				p = stk.pop();
				System.out.print(p + "\t");
				p = p.right;
			}
		}
	}
}
