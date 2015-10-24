package interview.leetcode;



/**
 * Given a binary tree containing digits from 0-9 only, each root-to-leaf path
 * could represent a number.
 * 
 * An example is the root-to-leaf path 1->2->3 which represents the number 123.
 * 
 * Find the total sum of all root-to-leaf numbers.
 * 
 * For example,
 * 
    1
   / \
  2   3
 * 
 * The root-to-leaf path 1->2 represents the number 12. The root-to-leaf path
 * 1->3 represents the number 13.
 * 
 * Return the sum = 12 + 13 = 25.
 * 
 * @author yazhoucao
 * 
 */
public class Sum_Root_to_Leaf_Numbers {

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.right.left = new TreeNode(4);
		root.right.left.right = new TreeNode(5);
		
	}
	

	/**
	 * Recursion
	 * 
	 */
    public int sumNumbers(TreeNode root) {
        return sumNumbers(root, 0);
    }
    
    private int sumNumbers(TreeNode root, int sum){
        if(root==null)  return 0;
        if(root.left==null && root.right==null)
            return sum*10+root.val;
        sum  = sum*10 + root.val;
        return sumNumbers(root.left, sum) + sumNumbers(root.right, sum);
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
