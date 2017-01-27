package interview.leetcode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * Design a data structure that supports the following two operations:
 * 
 * void addWord(word)
 * bool search(word)
 * 
 * search(word) can search a literal word or a regular expression string
 * containing only letters a-z or .. A . means it can represent any one letter.
 * 
 * For example:
 * 
 * addWord("bad")
 * addWord("dad")
 * addWord("mad")
 * search("pad") -> false
 * search("bad") -> true
 * search(".ad") -> true
 * search("b..") -> true
 * 
 * Note:
 * You may assume that all words are consist of lowercase letters a-z.
 * 
 * @author yazhoucao
 *
 */
public class Add_and_Search_Word_Data_structure_design {

	public static void main(String[] args) {
		AutoTestUtils
				.runTestClassAndPrint(Add_and_Search_Word_Data_structure_design.class);
	}

	public static class WordDictionary {

		Node root;

		public WordDictionary() {
			root = new Node();
		}

		// Adds a word into the data structure.
		public void addWord(String word) {
			Node curr = root;
			for (int i = 0; i < word.length(); i++) {
				char wi = word.charAt(i);
				int idx = wi - 'a';
				if (curr.children[idx] == null)
					curr.children[idx] = new Node();
				curr = curr.children[idx];
			}
			curr.hasWord = true;
		}

		// Returns if the word is in the data structure. A word could
		// contain the dot character '.' to represent any one letter.
		public boolean search(String word) {
			return search(word, 0, root);
		}

		private boolean search(String word, int i, Node curr) {
			if (i == word.length())
				return curr != null && curr.hasWord;
			if (curr == null)
				return false;
			char wi = word.charAt(i);
			int idx = wi - 'a';
			if (wi != '.') { // compare word[i] with one of curr.children
				Node child = curr.children[idx];
				return child != null ? search(word, i + 1, child) : false;
			} else {
				for (Node child : curr.children) {
					if (child != null && search(word, i + 1, child))
						return true;
				}
				return false;
			}
		}

	}

	private static class Node {
		boolean hasWord;
		Node[] children;

		public Node() {
			hasWord = false;
			children = new Node[26];
		}
	}

	// Your WordDictionary object will be instantiated and called as such:
	// WordDictionary wordDictionary = new WordDictionary();
	// wordDictionary.addWord("word");
	// wordDictionary.search("pattern");

	@Test
	public void test1() {
		WordDictionary dict = new WordDictionary();
		dict.addWord("at");
		dict.addWord("and");
		dict.addWord("an");
		dict.addWord("add");
		assertEquals(false, dict.search("a"));
		assertEquals(false, dict.search(".at"));
		dict.addWord("bat");
		assertEquals(true, dict.search(".at"));
		assertEquals(true, dict.search("an."));
		assertEquals(false, dict.search("a.d."));
		assertEquals(false, dict.search("b."));
		assertEquals(true, dict.search("a.d"));
		dict.search(".");

	}
}
