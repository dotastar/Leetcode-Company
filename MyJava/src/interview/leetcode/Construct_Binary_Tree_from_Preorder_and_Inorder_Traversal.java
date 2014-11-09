package interview.leetcode;

import java.util.Map;


/**
 * Given preorder and inorder traversal of a tree, construct the binary tree.
 * 
 * Note: You may assume that duplicates do not exist in the tree.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Construct_Binary_Tree_from_Preorder_and_Inorder_Traversal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        return build(preorder, 0, preorder.length-1, inorder, 0, inorder.length-1);
    }
    
    /**
     * Time: O(nlog(n)), Space: O(1).
     */
    public static TreeNode build(int[] preorder, int prestart, int preend, int[] inorder, int instart, int inend){
    	if(instart>inend)
    		return null;
    	int rootval = preorder[prestart];
    	int idx = 0;	//subroot idx
    	for(int i=instart; i<=inend; i++){
    		if(inorder[i] == rootval){
    			idx = i;
    			break;
    		}
    	}
    	int leftLen = idx-instart;
    	TreeNode subroot = new TreeNode(rootval);
    	subroot.left = build(preorder, prestart+1, prestart+leftLen-1, inorder, instart, idx-1);
    	subroot.right = build(preorder, prestart+leftLen+1, preend, inorder, idx+1, inend);
    	return subroot;
    }
    
    
    /**
     * Time: O(n), Space: O(n).
     */
    public static TreeNode build(int[] preorder, int prestart, int preend, int[] inorder, int instart, int inend, Map<Integer, Integer> inIdxMap){
    	if(instart>inend)
    		return null;
    	int rootval = preorder[prestart];
    	int idx = inIdxMap.get(rootval);	//subroot idx
    	int leftLen = idx-instart;
    	TreeNode subroot = new TreeNode(rootval);
    	subroot.left = build(preorder, prestart+1, prestart+leftLen-1, inorder, instart, idx-1);
    	subroot.right = build(preorder, prestart+leftLen+1, preend, inorder, idx+1, inend);
    	return subroot;
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
