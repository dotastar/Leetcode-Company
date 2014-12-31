package interview.leetcode;

import java.util.HashMap;
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
    	subroot.left = build(preorder, prestart+1, prestart+leftLen, inorder, instart, idx-1);
    	subroot.right = build(preorder, prestart+leftLen+1, preend, inorder, idx+1, inend);
    	return subroot;
    }
    
    
    
    /**
     * Time: O(n), Space: O(n).
     */
    public static TreeNode buildTree2(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inorderIdxMap = new HashMap<>();
    	for(int i=0; i<inorder.length; i++)
    		inorderIdxMap.put(inorder[i], i);
        return reconstruct(preorder, 0, preorder.length-1, inorder, 0, inorder.length-1, inorderIdxMap);
    }
    
    private static TreeNode reconstruct(int[] preorder, int prel, int prer, int[] inorder, int inl, int inr, Map<Integer, Integer> idxMap){
        if(inl>inr)
            return null;
        int rootVal = preorder[prel];
        int rootIdx = idxMap.get(rootVal);
        int leftSize = rootIdx-inl;
        TreeNode root = new TreeNode(rootVal);
        root.left = reconstruct(preorder, prel+1, prel+leftSize, inorder, inl, rootIdx-1, idxMap);
        root.right = reconstruct(preorder, prel+leftSize+1, prer, inorder, rootIdx+1, inr, idxMap);
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
