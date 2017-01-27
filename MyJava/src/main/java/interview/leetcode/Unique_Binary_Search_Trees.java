package interview.leetcode;

/**
 * Given n, how many structurally unique BST's (binary search trees) that store
 * values 1...n?
 * 
 * For example, Given n = 3, there are a total of 5 unique BST's.
 * 
   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3
 * 
 * @author yazhoucao
 * 
 */
public class Unique_Binary_Search_Trees {

	public static void main(String[] args) {
		for(int i=1; i<7; i++)
			System.out.println(i+" nodes:  "+numTrees(i));
	}

	/**
     * M[i]: how many unique binary search trees when there are i nodes
     * Base case: M[0] = 1, M[1] = 1
     * Induction rule: M[i] =  root at 0th node  + ... + root at (i - 1)th node, 0 <= i <= n
     *                          /           \             /       \
     *                       M[0]    *     M[i-1]       M[i-1] * M[0]
	 *
	 *	E.g, n = 3
	 * 	 1          2            3
	 *  / \        / \          / \
	 * 0  M[2]   M[1] M[1]    M[2] M[0]
	 */
	
	/**
	 * Second time
	 */
    public int numTrees2(int n) {
        int[] M = new int[n+1];
        M[0] = 1;
        for(int num=1; num<=n; num++){
            int count = 0;
            for(int i=1; i<=num; i++){  // root is i
                // num of left subtree * num of right subtree
                count += M[i-1] * M[num-i];    
            }
            M[num] = count;
        }
        return M[n];
    }
	
	/**
	 * The number of unique BST trees 
	 * 
	 * this below is a[j]*a[i-j-1]
	 * 
	 *      no.j node (the root node)
	 *      
	 *   /		\
	 * 
	 * a[j]		a[i-j-1]
	 * 
	 * a[j] means the number of unique trees there is in left subtree 
	 * when there are j nodes in the left subtree
	 * 
	 * a[i-j-1] means the number of unique trees there is in right 
	 * subtree when there are i-j-1 nodes in the right subtree
	 * 
	 */
    public static int numTrees(int n) {
    	int[] a = new int[n+1];
    	a[0] = 1;
    	for (int i=1; i<n+1; i++) {
    		for (int j=0; j<i; j++) {
    			a[i] += a[j]*a[i-j-1];
    		}
    	}
    	return a[n];
    }
}
