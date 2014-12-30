package interview.leetcode;

import java.util.Stack;


/**
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric
 * around its center).
 * 
 * For example, this binary tree is symmetric:
    1
   / \
  2   2
 / \ / \
3  4 4  3
 *
 * But the following is not:
 * 
    1
   / \
  2   2
   \   \
   3    3
 *
 * Note: Bonus points if you could solve it both recursively and iteratively.
 * 
 * @author yazhoucao
 * 
 */
public class Symmetric_Tree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Iterative fasion
	 */
	public static boolean isSymmetric_Iter(TreeNode root){
		if(root==null) return true;
		
		Stack<TreeNode> stk = new Stack<TreeNode>();
		stk.push(root.left);
		stk.push(root.right);
		
		while(!stk.empty()){
            TreeNode r = stk.pop();
            TreeNode l = stk.pop();
            if(l==null && r==null)
                continue;
            if(l!=null && r!=null && l.val==r.val){
                stk.push(l.left);
                stk.push(r.right);
                stk.push(l.right);
                stk.push(r.left);
            }else
                return false;
		}
		return true;
	}
	
	public static boolean isSymmetric(TreeNode root) {
	     if(root==null) return true;
	        return isSymmetric_Recur(root.left, root.right);
	}
	
	public static boolean isSymmetric_Recur(TreeNode l, TreeNode r){
		if(l==null&&r==null)
			return true;
		if((l==null&&r!=null)||(l!=null&&r==null))
			return false;
		if(l.val!=r.val)
			return false;
		
		return isSymmetric_Recur(l.left, r.right) && isSymmetric_Recur(l.right, r.left);
	}
	
	
	/**
	 * Second time practice
	 */
    public boolean isSymmetric2(TreeNode root) {
        return root==null ? true : isSymmetric2(root.left, root.right);
    }
    
    public boolean isSymmetric2(TreeNode left, TreeNode right){
        if(left==null && right==null)
            return true;
        if(left!=null && right!=null && left.val==right.val)
            return isSymmetric2(left.left, right.right) && isSymmetric2(left.right, right.left);
        else
            return false;
    }
	
	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) { val = x; }
		public String toString(){
			return Integer.toString(val);
		}
	}
}
