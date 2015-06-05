package general.algorithms;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import org.junit.Test;

public class HuffmanEncoding {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(HuffmanEncoding.class);
	}

	private static class TreeNode {
		private final int freq;
		private final char val;
		private TreeNode left;
		private TreeNode right;

		public TreeNode(char val, int freq) {
			this.val = val;
			this.freq = freq;
		}
	}

	TreeNode huffRoot;

	/**
	 * Encode S to a String represented bit array
	 * Time: O(max(m, nlogn)), m = total # S, n = unique # chars
	 * Space: n (map) + (2n-1) (tree) = O(n) (additional space)
	 */
	public String encoding(String S) {
		huffRoot = buildTree(S);
		Map<Character, String> codeTable = new HashMap<>();
		buildCodeTable(huffRoot, new StringBuilder(), codeTable);
		System.out.println("Code Table: " + codeTable.toString());
		StringBuilder encoded = new StringBuilder();
		for (int i = 0; i < S.length(); i++) {
			encoded.append(codeTable.get(S.charAt(i)));
		}
		return encoded.toString();
	}

	/**
	 * Build a lookup table from Character to String (represented as bits code).
	 * Idea: binary tree traversal, generate bit code when reach leaf
	 * Time: O(n), n = # total nodes
	 * Space: O(h), h = height of tree, stack space
	 */
	private void buildCodeTable(TreeNode node, StringBuilder curr,
			Map<Character, String> codeTable) {
		if (node == null)
			return;
		if (isLeaf(node)) {
			codeTable.put(node.val, curr.toString());
		} else {
			int oldLen = curr.length();
			curr.append('0');
			buildCodeTable(node.left, curr, codeTable);
			curr.setLength(oldLen);
			curr.append('1');
			buildCodeTable(node.right, curr, codeTable);
			curr.setLength(oldLen);
		}
	}

	/**
	 * Pre-process the input, construct and return a HuffMan tree
	 * Time: O(max(m, nlogn)), m = total # S, n = unique # chars
	 * Space: 2n = O(n)
	 */
	private TreeNode buildTree(String S) {
		assertTrue(S.length() > 0);
		// build character-count map
		Map<Character, Integer> charCnts = new HashMap<>();
		for (int i = 0; i < S.length(); i++) {
			char si = S.charAt(i);
			Integer cnt = charCnts.get(si);
			if (cnt == null)
				cnt = 0;
			charCnts.put(si, cnt + 1);
		}
		// build min-Heap
		PriorityQueue<TreeNode> leaves = new PriorityQueue<TreeNode>(charCnts.size(),
				new Comparator<TreeNode>() {
					@Override
					public int compare(TreeNode tn1, TreeNode tn2) {
						return tn1.freq == tn2.freq ? 0 : tn1.freq > tn2.freq ? 1 : -1;
					}
				});

		for (Entry<Character, Integer> ent : charCnts.entrySet()) {
			leaves.add(new TreeNode(ent.getKey(), ent.getValue()));
		}
		// build tree
		while (leaves.size() > 1) {
			TreeNode right = leaves.poll();
			TreeNode left = leaves.poll();
			TreeNode root = new TreeNode('\0', right.freq + left.freq);
			root.right = right;
			root.left = left;
			leaves.add(root);
		}
		return leaves.poll();
	}

	/**
	 * Decode a String represented bit array to original String
	 * Time: O(m), m = # total S
	 * Space: O(1)
	 */
	public String decoding(String data) {
		assertTrue("Build HuffMan tree first!", huffRoot != null);

		StringBuilder res = new StringBuilder();
		TreeNode curr = huffRoot;
		for (int i = 0; i < data.length();) {
			// find the right leaf
			while (!isLeaf(curr) && i < data.length()) {
				curr = data.charAt(i++) == '0' ? curr.left : curr.right;
			}
			res.append(curr.val); // append the leaf value
			curr = huffRoot; // reset to root and again
		}
		return res.toString();
	}

	private boolean isLeaf(TreeNode node) {
		return node != null && node.left == null && node.right == null;
	}

	/*********************************** Begin Test ***********************************/

	@Test
	public void test1() {
		String S = "aaaaaaaabbbbbbbccccd";
		String encodedRes = encoding(S);
		System.out.println(encodedRes);
		String decodedRes = decoding(encodedRes);
		System.out.println(decodedRes);
		assertTrue(S.equals(decodedRes));
		System.out.println();
	}

	@Test
	public void test2() {
		String S = "ABRACADABRA!";
		String encodedRes = encoding(S);
		System.out.println(encodedRes);
		String decodedRes = decoding(encodedRes);
		System.out.println(decodedRes);
		assertTrue(S.equals(decodedRes));
		System.out.println();
	}

	@Test
	public void test3() {
		String S = "it was the best of times it was the worst of times";
		String encodedRes = encoding(S);
		System.out.println(encodedRes);
		String decodedRes = decoding(encodedRes);
		System.out.println(decodedRes);
		assertTrue(S.equals(decodedRes));
		System.out.println();
	}

	@Test
	public void test4() {
		String S = "As a consequence of Shannon's source coding theorem, the entropy is a measure of the smallest codeword length that is theoretically possible for the given alphabet with associated weights. In this example, the weighted average codeword length is 2.25 bits per symbol, only slightly larger than the calculated entropy of 2.205 bits per symbol. So not only is this code optimal in the sense that no other feasible code performs better, but it is very close to the theoretical limit established by Shannon.";
		String encodedRes = encoding(S);
		System.out.println(encodedRes);
		String decodedRes = decoding(encodedRes);
		System.out.println(decodedRes);
		assertTrue(S.equals(decodedRes));
		System.out.println();
	}

	/*********************************** End Test ***********************************/
}
