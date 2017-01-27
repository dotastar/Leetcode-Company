package interview.leetcode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Implement Trie (Prefix Tree)
 * 
 * Implement a trie with insert, search, and startsWith methods.
 * 
 * Note:
 * You may assume that all inputs are consist of lowercase letters a-z.
 * 
 * @author yazhoucao
 *
 */
public class Implement_Trie {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Implement_Trie.class);
	}

	class TrieNode {
		// Initialize your data structure here.
		char val;
		int prefixes;
		boolean hasWord;
		TrieNode[] children;

		public TrieNode() {
			this('\0');
		}

		public TrieNode(char cVal) {
			val = cVal;
			prefixes = 0;
			hasWord = false;
			children = new TrieNode[26];
		}
	}

	public class Trie {
		private TrieNode root;

		public Trie() {
			root = new TrieNode();
		}

		// Inserts a word into the trie.
		public void insert(String word) {
			TrieNode curr = root;
			for (int i = 0; i < word.length(); i++) {
				char ci = word.charAt(i);
				int idx = ci - 'a';
				if (curr.children[idx] == null)
					curr.children[idx] = new TrieNode(ci);
				curr = curr.children[idx];
				curr.prefixes++;
			}
			curr.hasWord = true;
		}

		// Returns if the word is in the trie.
		public boolean search(String word) {
			TrieNode curr = findNode(word);
			return curr != null && curr.hasWord;
		}

		// Returns if there is any word in the trie
		// that starts with the given prefix.
		public boolean startsWith(String prefix) {
			TrieNode curr = findNode(prefix);
			return curr != null && curr.prefixes > 0;
		}

		private TrieNode findNode(String word) {
			TrieNode curr = root;
			for (int i = 0; i < word.length(); i++) {
				char ci = word.charAt(i);
				int idx = ci - 'a';
				if (curr.children[idx] == null)
					return null;
				curr = curr.children[idx];
			}
			return curr;
		}
	}

	// Your Trie object will be instantiated and called as such:
	// Trie trie = new Trie();
	// trie.insert("somestring");
	// trie.search("key");

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}
}
