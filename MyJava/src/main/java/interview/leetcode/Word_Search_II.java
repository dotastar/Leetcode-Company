package interview.leetcode;

import static org.junit.Assert.assertEquals;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Given a 2D board and a list of words from the dictionary, find all words in
 * the board.
 * 
 * Each word must be constructed from letters of sequentially adjacent cell,
 * where "adjacent" cells are those horizontally or vertically neighboring. The
 * same letter cell may not be used more than once in a word.
 * 
 * For example,
 * Given words = ["oath","pea","eat","rain"] and board =
 * 
 * [
 * ['o','a','a','n'],
 * ['e','t','a','e'],
 * ['i','h','k','r'],
 * ['i','f','l','v']
 * ]
 * 
 * Return ["eat","oath"].
 * 
 * Note:
 * You may assume that all inputs are consist of lowercase letters a-z.
 * 
 * 
 * click to show hint.
 * 
 * You would need to optimize your backtracking to pass the larger test. Could
 * you stop backtracking earlier?
 * 
 * If the current candidate does not exist in all words' prefix, you could stop
 * backtracking immediately. What kind of data structure could answer such query
 * efficiently? Does a hash table work? Why or why not? How about a Trie? If you
 * would like to learn how to implement a basic trie, please work on this
 * problem: Implement Trie (Prefix Tree) first.
 * 
 * 
 * 
 * @author yazhoucao
 *
 */
public class Word_Search_II {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Word_Search_II.class);
	}

	public List<String> findWords(char[][] board, String[] words) {
		List<String> res = new ArrayList<>();
		Node root = buildTrie(words);

		StringBuilder container = new StringBuilder();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				search(board, i, j, root, container, res);
			}
		}

		return res;
	}

	// dfs the matrix with the trie information
	private void search(char[][] board, int i, int j, Node curr, StringBuilder path,
			List<String> res) {
		int m = board.length, n = board.length == 0 ? 0 : board[0].length;
		if (i < 0 || j < 0 || i >= m || j >= n || board[i][j] == '#')
			return;
		Node next = curr.children[board[i][j] - 'a'];
		if (next == null) // no corresponding trie node
			return;

		path.append(board[i][j]); // record current path
		char old = board[i][j];
		board[i][j] = '#'; // mark as visited
		if (next.hasWord) {
			res.add(path.toString());
			next.hasWord = false; // delete the word, to avoid duplicates
		}

		search(board, i + 1, j, next, path, res);
		search(board, i - 1, j, next, path, res);
		search(board, i, j + 1, next, path, res);
		search(board, i, j - 1, next, path, res);

		board[i][j] = old; // unmark, recover it
		path.setLength(path.length() - 1);
	}

	// build trie
	private Node buildTrie(String[] words) {
		Node root = new Node();
		for (String word : words)
			addWord(root, word);
		return root;
	}

	private void addWord(Node root, String word) {
		for (int i = 0; i < word.length(); i++) {
			char wi = word.charAt(i);
			int idx = wi - 'a';
			if (root.children[idx] == null)
				root.children[idx] = new Node();
			root = root.children[idx];
		}
		root.hasWord = true;
	}

	private static class Node {
		boolean hasWord;
		Node[] children;

		public Node() {
			hasWord = false;
			children = new Node[26];
		}
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}
}
