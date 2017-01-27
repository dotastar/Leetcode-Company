package interview.leetcode;

import java.util.LinkedList;
import java.util.Queue;


/**
 * Given a binary tree
   struct TreeLinkNode {
      TreeLinkNode *left;
      TreeLinkNode *right;
      TreeLinkNode *next;
    }
 * 
 * Populate each next pointer to point to its next right node. If there
 * is no next right node, the next pointer should be set to NULL.
 * 
 * Initially, all next pointers are set to NULL.
 * 
 * Note:
 * You may only use constant extra space. You may assume that it is a perfect
 * binary tree (ie, all leaves are at the same level, and every parent has two
 * children).  
 * 
 * 
 * For example, Given the following perfect binary tree,
         1
       /  \
      2    3
     / \  / \
    4  5  6  7

 * After calling your function, the tree should look like:
         1 -> NULL
       /  \
      2 -> 3 -> NULL
     / \  / \
    4->5->6->7 -> NULL
 * 
 * @author yazhoucao
 * 
 */
public class Populating_Next_Right_Pointers_in_Each_Node {

	public static void main(String[] args) {

	}

    /**
     * Third time, BFS traversal by level
     */
    public void connect3(TreeLinkNode root) {
        if(root==null)
            return;
        Queue<TreeLinkNode> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            int len = q.size(); // for each level
            for(int i=0; i<len; i++){
                TreeLinkNode curr = q.poll();
                if(curr.left!=null)
                    q.add(curr.left);
                if(curr.right!=null)
                    q.add(curr.right);
                    
                curr.next = i==len-1 ? null : q.peek();	// link to the next
            }
        }
    }

	/**
	 * Second time practice, more BFS than above solution.
	 * More elegant.
	 */
    public void connect2(TreeLinkNode root) {
        if(root==null)
            return;
        Queue<TreeLinkNode> q = new LinkedList<TreeLinkNode>();
        q.add(root);
        while(!q.isEmpty()){
            TreeLinkNode curr = q.poll();
            if(curr==null||curr.left==null||curr.right==null)  continue;
            curr.left.next = curr.right;
            curr.right.next = curr.next==null?null:curr.next.left;
            q.add(curr.left);
            q.add(curr.right);
        }
    }
    
	/**
	 * Version 2
	 * Just connect leftchild to rightchlild of current node, 
	 * and rightchild to next node's leftchild recursively.
	 * 
	 * It's like DFS. Time: O(n), n is the number of nodes.
	 * Same time complexity.
	 */
	public static void connect(TreeLinkNode root) {
        if(root==null || root.left==null)
            return;
        root.left.next = root.right;
        root.right.next = root.next==null ? null : root.next.left;	// connect to sibling
        connect(root.left);
        connect(root.right);
	}
	


	/**
	 * Version 1
	 * recursively connect the current level of all siblings 
	 * form the first left node
	 * 
	 * It's like BFS. Time: O(n), n is the number of nodes
	 * 
	 */
	public static void connect0(TreeLinkNode node) {
		if(node==null) 
			return;
		if(node.left==null||node.right==null)
			return;

	    TreeLinkNode p = node;
		while(p!=null){	//connect nodes in next level
			p.left.next = p.right;
			if(p.next!=null)
			    p.right.next = p.next.left;
			 else
			    p.right.next = null;
			p = p.next;
		}
		connect0(node.left);
	}
	
	public class TreeLinkNode {
		int val;
		TreeLinkNode left, right, next;

		TreeLinkNode(int x) {
			val = x;
		}
	}
}
