package interview.cc150.chapter1_4;
import general.datastructure.BNode;
import general.datastructure.BTreeOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class BinaryTreeOperation{
	

	/**
	 * 4.3 Given a sorted (increasing order) array with 
	 * unique integer elements, write an algorithm to 
	 * create a binary search tree with minimal height.
	 * @author yazhoucao
	 *
	 */
	public BTreeOperations<Integer> createMinimalBST(int[] sortedData){
		int end = sortedData.length-1;
		BNode<Integer> root = createMinimalBST(sortedData, 0, end);	
		BTreeOperations<Integer> bst = new BTreeOperations<Integer>(root);
		return bst;
	}
	
	private BNode<Integer> createMinimalBST(int[] sortedData, int start, int end){
		//Be careful with the boundary
		if(end<start)	return null;

		int mid = (start+end)/2;
		BNode<Integer> parent = new BNode<Integer>(sortedData[mid]);
		
		//exclude the mid point, because mid point is parent node
		parent.setLeft(createMinimalBST(sortedData, start, mid-1));
		parent.setRight(createMinimalBST(sortedData, mid+1, end));
		return parent;
	}


	/**
	 * 4.4 Given a binary tree, design an algorithm which creates 
	 * a linked list of all the nodes at each depth (e.g., if you 
	 * have a tree with depth D, you'll have D linked lists).
	 * 
	 * Version 1, recursive version
	 */
	public void linkNodes(BNode<Integer> node, ArrayList<LinkedList<BNode<Integer>>> lists, int depth){
		if(node==null) return;
		
		LinkedList<BNode<Integer>> list = null;
		if(depth>=lists.size()){
			list = new LinkedList<BNode<Integer>>();
			lists.add(list);
		}else
			list = lists.get(depth);
		
		list.add(node);
		
		linkNodes(node.getLeft(), lists, depth+1);
		linkNodes(node.getRight(), lists, depth+1);
	}
	
	
	/**
	 * Version 2, Iterative version
	 * It's similar to BFS, traverse each layer from top to bottom,
	 * in each layer, linked them together, and put them in a queue
	 * for next layer.
	 */
	public ArrayList<LinkedList<BNode<Integer>>> linkNodes(BTreeOperations<Integer> tree){
		ArrayList<LinkedList<BNode<Integer>>> lists = new ArrayList<LinkedList<BNode<Integer>>>();

		LinkedList<BNode<Integer>> currents = new LinkedList<BNode<Integer>>();
		currents.add(tree.getRoot());
		
		while(!currents.isEmpty()){
			lists.add(currents);
			
			//link nodes of current layer
			LinkedList<BNode<Integer>> nextLayer = new LinkedList<BNode<Integer>>();
			for(BNode<Integer> parent : currents){
				if(parent.getLeft()!=null)
					nextLayer.add(parent.getLeft());
				if(parent.getRight()!=null)
					nextLayer.add(parent.getRight());
			}
			
			currents = nextLayer;
		}
		
		return lists;
	}
	
	
	
	
	public boolean isAncestorOf(BNode<Integer> ancestor, BNode<Integer> child){
		if(ancestor==null || child==null) return false;
		if(ancestor.equals(child)) return true;
		return (isAncestorOf(ancestor.getLeft(), child) || isAncestorOf(ancestor.getRight(), child));
	}
	
	public static void main(String[] args){

		System.out.println("==================== 4.3 =======================");
		int[] data = new int[]{0,1,2,3,4,5,6,7,8,9};
		System.out.println("Create BinarySearchTree by sorted array: "+Arrays.toString(data));
		BinaryTreeOperation btOper = new BinaryTreeOperation();
		BTreeOperations<Integer> btree = btOper.createMinimalBST(data);
		System.out.println(btree.toString());
		System.out.println();
		
		System.out.println("==================== 4.4 =======================");
		System.out.println("Link nodes in the same depth of a binary tree, iterative version: ");
		ArrayList<LinkedList<BNode<Integer>>> layers = btOper.linkNodes(btree);
		for(LinkedList<BNode<Integer>> layer : layers){
			for(BNode<Integer> node : layer)
				System.out.print(node.toString()+"\t");
			System.out.println();
		}
		
		System.out.println("Link nodes in the same depth of a binary tree, recursive version: ");
		ArrayList<LinkedList<BNode<Integer>>> lists = new ArrayList<LinkedList<BNode<Integer>>>();
		btOper.linkNodes(btree.getRoot(), lists, 0);
		for(LinkedList<BNode<Integer>> layer : lists){
			for(BNode<Integer> node : layer)
				System.out.print(node.toString()+"\t");
			System.out.println();
		}
		System.out.println();
	
		System.out.println("==================== 4.5 =======================");
		int[] data1 = new int[]{0,1,2,3,4,5,6,7,8,9};
		btree = btOper.createMinimalBST(data1);
		System.out.println(Arrays.toString(data1));
		System.out.println("Whether is BST: "+btree.isBST(btree.getRoot(), Integer.MIN_VALUE, Integer.MAX_VALUE));
		int[] data2 = new int[]{1,8,3,5,6,9,10};
		btree = btOper.createMinimalBST(data2);
		System.out.println(Arrays.toString(data2));
		System.out.println("Whether is BST: "+btree.isBST(btree.getRoot(), Integer.MIN_VALUE, Integer.MAX_VALUE));

	}
}