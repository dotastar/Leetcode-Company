package general.datastructure;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BTree<T extends Comparable<T>> {
	private int size;
	private BNode<T> root;
	
	public BTree(BNode<T> rootIn){
		root = rootIn;
	}
	
	public int size(){ return size; }
	public void setSize(int sizeIn){ size = sizeIn; }
	
	public BNode<T> getRoot(){ return root; }
	public void setRoot(BNode<T> rootIn){ root = rootIn; }
	
	public boolean isRoot(BNode<T> node){
		if(node.equals(root)) 
			return true; 
		else 
			return false; 	
	}
	
	public int getHeight(){
		List<Integer> heights = new ArrayList<Integer>();
		countHeight(heights, root, 1);
		return Collections.max(heights);
	}
	
	/**
	 * 4.1 Implement a function to check if a binary tree is balanced. 
	 * For the purposes of this question, a balanced tree is defined to 
	 * be a tree such that the heights of the two subtrees of any node 
	 * never differ by more than one.
	 * @return
	 */
	public boolean isBalanced(){	
		if(root==null) return true;
		
		List<Integer> heights = new ArrayList<Integer>();
		countHeight(heights, root, 1);
		
		int h = heights.get(0);
		for(int hCmp : heights){ 
			if(Math.abs(h-hCmp)>1)
				return false;
		}
		
		return true;
	}
	
	private void countHeight(List<Integer> heights, BNode<T> node, int count){
		if(node==null) return;
		
		if(node.getLeft()==null && node.getRight()==null)
			heights.add(count);		
		else{
			if(node.getLeft()!=null)
				countHeight(heights, node.getLeft(), count+1);
		
			if(node.getRight()!=null)
				countHeight(heights, node.getRight(), count+1);
		}			
	}
	
	/**
	 * 4.5 Check if a binary tree is a binary search tree.
	 * Definition of BST: left.data <= current.data < right.data
	 * The idea of Divide and conquer, split it to two subproblems,
	 * one is left subtree, the other is right subtree. 
	 * 
	 * Time O(n)
	 * Space O(log(n))
	 * 
	 * By BST's definition:
	 * any node is larger than all nodes in that node’s left subtree 
	 * and smaller than all nodes in that node’s right subtree.
	 * @return
	 */	
	public boolean isBST(BNode<T> node, T min, T max){
		if(node==null) return true;
		
		BNode<T> left = node.getLeft();
		BNode<T> right = node.getRight();
		
		T parent = node.getData();
		
		
		//parent belongs to the range (min, max) or equals to
		//parent >= left max && parent < right min
		if(parent.compareTo(min)>=0 && parent.compareTo(max)<0){
			//left subtree is a BST && right subtree is a BST 
			if(isBST(left,min,parent) && isBST(right, parent, max))
				return true;
			else
				return false;
		}else
			return false;
	
	}
	
	/******************** Pre-order traversal ********************/
	public void preorder(BNode<T> node){
		if(node==null) return;
		visit(node);
		preorder(node.getLeft());
		preorder(node.getRight());
	}
	
	public void preorder_iterative(BNode<T> node){
		if(node==null) return;
		Stack<BNode<T>> stk = new Stack<BNode<T>>();
		stk.push(node);
		
		while(!stk.empty()){
			BNode<T> parent = stk.pop();
			if(parent!=null){
				visit(parent);
				stk.push(parent.getRight());
				stk.push(parent.getLeft());
			}
		}
	}
	
	/******************** Post-order traversal ********************/
	public void postorder(BNode<T> node){
		if(node==null) return;
		postorder(node.getLeft());
		postorder(node.getRight());
		visit(node);
	}
	
	public void postorder_iterative(BNode<T> node){
		if(node==null) return;
		Stack<BNode<T>> stk = new Stack<BNode<T>>();
		stk.push(node);
		
		BNode<T> parent = node.getLeft();
		BNode<T> lastVisited = parent;
		while(parent!=null){
			//push in all left child
			while(parent.getLeft()!=null){
				stk.push(parent);
				parent = parent.getLeft();
			}//end, parent.left==null
			
			//right child is null or visited right child
			while(parent!=null && (parent.getRight()==null || parent.getRight().equals(lastVisited))){
				visit(parent);
				lastVisited = parent;
				
				if(stk.empty()) return;
				
				parent = stk.pop();	
			}

			stk.push(parent);
			parent = parent.getRight();
		}
		
	}
	
	/******************** In-order traversal ********************/
	public void inorder(BNode<T> node){
		if(node==null) return;
		inorder(node.getLeft());
		visit(node);
		inorder(node.getRight());
	}
	
	public void inorder_iterative(BNode<T> node){
		if(node==null) return;
		Stack<BNode<T>> stk = new Stack<BNode<T>>();
		stk.push(node);
		BNode<T> parent = node.getLeft();
		  
		while(!stk.empty() || parent!=null){
			//push all LeftChild
			while(parent!=null){
				stk.push(parent);
				parent = parent.getLeft();
			}//end, parent==null
			
			//when reach leaf(LeftChild==null),
			//it should be visited
			if(!stk.empty()){
				parent = stk.pop();
				visit(parent);
				parent = parent.getRight();
			}
		}
	}
	
	public void visit(BNode<T> node){
		/**
		 * Do something...
		 */
		System.out.print(node.getData().toString()+" ");
	}
	
	
	public String toString(){
		if(root==null) return "";
		Queue<BNode<T>> q = new LinkedList<BNode<T>>();
		q.add(root);
		StringBuffer sb = new StringBuffer();
		while(!q.isEmpty()){
			BNode<T> node = q.poll();
			sb.append("Subtree "+node.toString()+":");

			if(node.getLeft()!=null)
				q.add(node.getLeft());
			if(node.getRight()!=null)
				q.add(node.getRight());

			
			sb.append(node.getLeft());
			sb.append(",");
			sb.append(node.getRight());
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	
	public static void main(String[] args){
		//root
		BNode<Character> a = new BNode<Character>('A');
		BNode<Character> b = new BNode<Character>('B');
		BNode<Character> c = new BNode<Character>('C');
		BNode<Character> d = new BNode<Character>('D');
		BNode<Character> e = new BNode<Character>('E');
		BNode<Character> f = new BNode<Character>('F');
		BNode<Character> g = new BNode<Character>('G');
		BNode<Character> h = new BNode<Character>('H');

		a.setLeft(b);
		a.setRight(c);
		
		b.setLeft(d);
		b.setRight(e);
		
		c.setRight(g);
		e.setRight(f);
		g.setLeft(h);
		
		/* 
		 *      A
		 *     / \
		 *    B	  C
		 *   / \   \
		 *  D   E	G
		 * 		 \ /
		 * 		 F H
		*/
		
		BTree<Character> btree = new BTree<Character>(a);
		
		System.out.println("Initialize btree:");
		System.out.println(btree.toString());
		
		System.out.println("Pre order:");
		btree.preorder(btree.getRoot());
		System.out.println("\nIterative Pre order:");
		btree.preorder_iterative(btree.root);
		
		System.out.println("\n\nIn order:");
		btree.inorder(btree.getRoot());
		System.out.println("\nIterative In order:");
		btree.inorder_iterative(btree.root);
		
		System.out.println("\n\nPost order:");
		btree.postorder(btree.getRoot());
		System.out.println("\nIterative Post order:");
		btree.postorder_iterative(btree.root);
		
		System.out.println("\n\n");
		System.out.println("Is balanced: "+btree.isBalanced());
		
	}
}
