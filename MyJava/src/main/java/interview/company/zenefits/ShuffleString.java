package interview.company.zenefits;

import interview.AutoTestUtils;

import java.util.Random;

import org.junit.Test;

/**
 * Zenefits
 * phone
 * 
 * Shuffle String
 * 
 * shuffle each ward in a String, shuffle every char in the word except for the
 * first and last char.
 * 
 * E.g.
 * Input: "I want to buy a cup of water"
 * Output: "I wnat to buy a cup of wtear"
 * 
 * @author yazhoucao
 *
 */
public class ShuffleString {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(ShuffleString.class);
	}

	/**
	 * Split to words, then shuffle each word
	 */
	public String shuffle(String S) {
		S = S.trim();
		if (S.length() == 0)
			return S;
		String[] words = S.split(" ");
		StringBuffer res = new StringBuffer();
		for (String word : words) {
			if (word.length() == 0)
				continue;
			else if (word.length() <= 3)
				res.append(word);
			else
				res.append(shuffleWord(word));
			res.append(' ');
		}
		return res.substring(0, res.length() - 1);
	}

	Random ran = new Random();

	private String shuffleWord(String word) {
		char[] w = word.toCharArray();
		for (int i = w.length - 2; i >= 1; i--) {
			int swapIdx = 1 + ran.nextInt(i);
			swap(w, swapIdx, i);
		}
		return new String(w);
	}

	/**
	 * Directly shuffle in the String
	 */
	public String shuffle2(String S) {
		S = S.trim();
		if (S.length() == 0)
			return S;
		// you are beautiful
		StringBuffer res = new StringBuffer();
		char[] s = S.toCharArray();
		for (int l = 0, r = 0; r <= s.length; r++) { // need to be r <= s.length
			if (r == s.length || s[r] == ' ') {
				if (r - l <= 3) { // don't need to shuffle
					while (l < r)
						res.append(s[l++]);
				} else { // shuffle
					res.append(s[l]);
					for (int i = l + 1; i < r - 1; i++) {
						int swapIdx = i + ran.nextInt(r - 1 - i); // [i, r - 2]
						swap(s, swapIdx, i);
						res.append(s[i]);
					}
					res.append(s[r - 1]);
				}
				// move out of spaces
				while (r < s.length && s[r] == ' ')
					r++;
				l = r;
				if (r != s.length)
					res.append(' ');
			}
		}

		return res.toString();
	}

	private void swap(char[] s, int i, int j) {
		char temp = s[i];
		s[i] = s[j];
		s[j] = temp;
	}

	@Test
	public void test1() {
		String S = "I want to buy a cup of water";
		System.out.println(shuffle2(S));
	}

	@Test
	public void test2() {
		String S = "you are beautiful";
		System.out.println(shuffle2(S));
	}

	@Test
	public void test3() {
		String S = " you  are      beautiful  ";
		System.out.println(shuffle2(S));
	}
}
