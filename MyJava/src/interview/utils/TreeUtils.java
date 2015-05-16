package interview.utils;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Test;

public class TreeUtils {

	private final static String EMPTY_TREENODE = "#";

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(TreeUtils.class);
	}

	public static TreeNode deserializeTree(String[] data) {
		if (data == null || data.length == 0 || data[0].equals(EMPTY_TREENODE))
			return null;
		TreeNode root = new TreeNode(Integer.valueOf(data[0]));
		Queue<TreeNode> q = new LinkedList<>();
		q.offer(root);
		for (int i = 1; i < data.length; i+=2) {
			TreeNode node = q.poll();
			if (node == null) {
				q.add(null);
				q.add(null);
			} else {
				node.left = data[i].equals(EMPTY_TREENODE) ? null : new TreeNode(Integer.valueOf(data[i]));
				if (i + 1 < data.length)
					node.right = data[i + 1].equals(EMPTY_TREENODE) ? null : new TreeNode(Integer.valueOf(data[i + 1]));
				q.add(node.left);
				q.add(node.right);
			}
		}
		return root;
	}
	
	public static List<String> serializeTree(TreeNode root) {
		List<String> res = new ArrayList<>();
		Queue<TreeNode> q = new LinkedList<>();
		q.add(root);
		int levelLength = 1, nodeCnt = 1;
		while(!q.isEmpty() && nodeCnt > 0){
			nodeCnt = 0;
			for(int i=0; i<levelLength; i++){
				TreeNode node = q.poll();
				if(node==null){
					res.add(EMPTY_TREENODE);
					q.add(null);
					q.add(null);
				}else{
					res.add(String.valueOf(node.key));
					q.add(node.left);
					q.add(node.right);
					
					if(node.left!=null)
						nodeCnt++;
					if(node.right!=null)
						nodeCnt++;;
				}
			}
			levelLength *= 2;
		}
		return res;
	}
	
	
	@Test
	public void serializeAndDeserialize_SimpleTest(){
		String[] data = {"5","#","9","#","#","8","15","#","#","#","#","#","#","12"};
		TreeNode node = deserializeTree(data);
		List<String> res = serializeTree(node);
		for(int i=0; i<data.length; i++)
			assertTrue(data[i].equals(res.get(i)));
	}
}
