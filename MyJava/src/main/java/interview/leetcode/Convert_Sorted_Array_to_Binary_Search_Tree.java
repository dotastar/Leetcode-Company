package interview.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Given an array where elements are sorted in ascending order, convert it to a
 * height balanced BST.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Convert_Sorted_Array_to_Binary_Search_Tree {

	public static void main(String[] args) {
		int[] arr0 = new int[]{0};
		TreeNode root0 = sortedArrayToBST(arr0);
		printTree(root0);
		
		int[] arr1 = new int[]{1,2,3,4,5,6,7,8,9};
		TreeNode root1 = sortedArrayToBST(arr1);
		printTree(root1);
	}

	public static TreeNode sortedArrayToBST(int[] num) {
		return toBST_Recur(num, 0, num.length-1);
	}
	
	/**
	 * Recursion
	 * @return
	 */
	public static TreeNode toBST_Recur(int[] num, int l, int r){
		if(l>r)
			return null;
		int mid = (l+r)/2;
		TreeNode node = new TreeNode(num[mid]);
		node.left = toBST_Recur(num, l, mid-1);
		node.right = toBST_Recur(num, mid+1, r);	
		return node;
	}

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}
	
	
	public static void printTree(TreeNode root){
		Queue<TreeNode> q = new LinkedList<TreeNode>();
		q.add(root);
		while(!q.isEmpty()){
			int len = q.size();
			for(int i=0; i<len; i++){
				TreeNode node = q.poll();
				if(node==null)
					break;
				System.out.print(node.val+"\t");
				q.add(node.left);
				q.add(node.right);
			}
			System.out.println();
		}
	}
}
