package interview.company.epic;

import interview.AutoTestUtils;
import interview.epi.utils.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Write a program for a word search. If there is an NxN grid with one letter in
 * each cell. Let the user enter a word and the letters of the word are said to
 * be found in the grid either the letters match vertically, horizontally or
 * diagonally in the grid. If the word is found, print the coordinates of the
 * letters as output.
 * 
 * @author yazhoucao
 * 
 */
public class WordSearch {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(WordSearch.class);
	}

	public List<Pair<Integer, Integer>> findWord(char[][] mat, String word) {
		List<Pair<Integer, Integer>> res = new ArrayList<>();
		int N = mat.length;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(search(mat, i, j, word, res))
					return res;
			}
		}
		return res;
	}

	private boolean search(char[][] mat, int i, int j, String word,
			List<Pair<Integer, Integer>> res) {
		int len = word.length();
		int N = mat.length;
		if (i + len < N) { // check upward
			boolean match = true;
			for (int k = 0; k < len; k++) {
				if (word.charAt(k) != mat[i + k][j]) {
					match = false;
					break;
				}
			}
			if (match) {
				for (int k = 0; k < len; k++)
					res.add(new Pair<Integer, Integer>(i + k, j));
				return true;
			}
		}

		if (i - len >= 0) { // check downward
			boolean match = true;
			for (int k = 0; k < len; k++) {
				if (word.charAt(k) != mat[i - k][j]) {
					match = false;
					break;
				}
			}
			if (match) {
				for (int k = 0; k < len; k++)
					res.add(new Pair<Integer, Integer>(i - k, j));
				return true;
			}
		}

		if (j + len < N) { // check rightward
			boolean match = true;
			for (int k = 0; k < len; k++) {
				if (word.charAt(k) != mat[i][j + k]) {
					match = false;
					break;
				}
			}
			if (match) {
				for (int k = 0; k < len; k++)
					res.add(new Pair<Integer, Integer>(i, j + k));
				return true;
			}
		}

		if (j - len >= 0) { // check leftward
			boolean match = true;
			for (int k = 0; k < len; k++) {
				if (word.charAt(k) != mat[i][j - k]) {
					match = false;
					break;
				}
			}
			if (match) {
				for (int k = 0; k < len; k++)
					res.add(new Pair<Integer, Integer>(i, j + k));
				return true;
			}
		}

		if (i + len < N && j + len < N) { // check top right
			// ....
		}

		if (i + len < N && j - len >= 0) { // check top left
			// ....
		}
		
		if (i - len >= 0 && j + len < N) { // check bottom right
			// ....
		}

		if (i - len >= 0 && j - len >= 0) { // check bottom left
			// ....
		}
		return false;
	}

}
