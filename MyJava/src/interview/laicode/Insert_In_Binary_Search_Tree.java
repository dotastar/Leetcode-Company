package interview.laicode;

import interview.laicode.utils.LaiCodeUtils;
import interview.laicode.utils.TreeNode;

/**
 * Insert In Binary Search Tree
 * Easy
 * Data Structure
 * 
 * Insert a key in a binary search tree if the binary search tree does not
 * already contain the key. Return the root of the binary search tree.
 * 
 * Assumptions:
 * There are no duplicate keys in the binary search tree.
 * If the key is already existed in the binary search tree, you do not need to
 * do anything.
 * 
 * @author yazhoucao
 * 
 */
public class Insert_In_Binary_Search_Tree {

	static Insert_In_Binary_Search_Tree o = new Insert_In_Binary_Search_Tree();

	public static void main(String[] args) {
		// 5,3,9,1,4,8,15,#,#,#,#,#,#,12
		String[] data = { "5", "3", "9", "1", "4", "8", "15", "#", "#", "#",
				"#", "#", "#", "12" };
		TreeNode root1 = LaiCodeUtils.deserializeTree(data);
		System.out.println(LaiCodeUtils.serializeTree(o.insert(root1, 10)));
	}

	public TreeNode insert(TreeNode root, int key) {
		if (root == null)
			return new TreeNode(key);
		if (root.key < key) {
			root.right = insert(root.right, key);
		} else if (root.key > key) {
			root.left = insert(root.left, key);
		}
		return root;
	}

}
