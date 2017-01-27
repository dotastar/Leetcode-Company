package interview.epi.chapter10_binary_trees;

import interview.epi.chapter10_binary_trees.BinaryTreeWithParentPrototype.BinaryTree;

/**
 * If the tree is mutable, we can do inorder traversal in O(1) space - this is
 * the Morris traversal described on Page 80. The Morris traversal does not
 * require that nodes have parent fields.
 * 
 * Problem 10.9
 * Design an iterative algorithm that enumerates the nodes inorder and uses O(1)
 * space. Your algorithm cannot modify the tree.
 * 
 * @author yazhoucao
 * 
 */
public class Q9_Implement_An_Inorder_Traversal_With_O1_Space {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * The key is to know which path it is (is it form a left child or right
	 * child?), by using a prev node to store previously visited node.
	 */
	
	public static <T> void inOrderTraversal2(BinaryTree<T> node) {
		BinaryTree<T> prev = null, curr = node, next;
		while(curr!=null){
			if(prev==null ||  prev.getLeft()==curr || prev.getRight()==curr){	//move down
				if(curr.getLeft()==null){	//down right
					visit(curr);
					next = curr.getRight()!=null ? curr.getRight() : curr.getParent();
				}else
					next = curr.getLeft();
			}else if(prev==curr.getLeft()){	// up from left
				visit(curr);
				next = curr.getRight()!=null ? curr.getRight() : curr.getParent();
			}else	//up from right
				next = curr.getParent();

			prev = curr;
			curr = next;
		}
	}
	

	private static <T> void visit(BinaryTree<T> node) {
		System.out.print(node.getData());
	}
}
