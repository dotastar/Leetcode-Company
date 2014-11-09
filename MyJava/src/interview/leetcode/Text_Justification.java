package interview.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an array of words and a length L, format the text such that each line
 * has exactly L characters and is fully (left and right) justified.
 * 
 * You should pack your words in a greedy approach; that is, pack as many words
 * as you can in each line. Pad extra spaces ' ' when necessary so that each
 * line has exactly L characters.
 * 
 * Extra spaces between words should be distributed as evenly as possible. If
 * the number of spaces on a line do not divide evenly between words, the empty
 * slots on the left will be assigned more spaces than the slots on the right.
 * 
 * For the last line of text, it should be left justified and no extra space is
 * inserted between words.
 * 
 * For example, words:
 * 
 * ["This", "is", "an", "example", "of", "text", "justification."] L: 16.
 * 
 * Return the formatted lines as:
 * 
 * [
 * 
 * "This    is    an",
 * 
 * "example  of text",
 * 
 * "justification.  "
 * 
 * ]
 * 
 * Note: Each word is guaranteed not to exceed L in length.
 * 
 * click to show corner cases.
 * 
 * Corner Cases:
 * 
 * A line other than the last line might contain only one word. What should you
 * do in this case? In this case, that line should be left-justified.
 * 
 * @author yazhoucao
 * 
 */
public class Text_Justification {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] words0 = { "" };
		int L0 = 0;
		List<String> res0 = fullJustify2(words0, L0);
		for (String line : res0)
			System.out.println(line + "\tsize:" + line.length());

		String[] words1 = { "This", "is", "an", "example", "of", "text",
				"justification." };
		int L1 = 16;
		List<String> res1 = fullJustify2(words1, L1);
		for (String line : res1)
			System.out.println(line + "\tsize:" + line.length());

		String[] words3 = { "This", "is", "an", "example", "of", "text",
				"justification." };
		int L3 = 14;
		List<String> res3 = fullJustify2(words3, L3);
		for (String line : res3)
			System.out.println(line + "\tsize:" + line.length());

		String[] words2 = { "Listen", "to", "many,", "speak", "to", "a", "few." };
		int L2 = 6;
		List<String> res2 = fullJustify2(words2, L2);
		for (String line : res2)
			System.out.println(line + "\tsize:" + line.length());

	}

	/**
	 * Same solution, second time practice
	 * Time: O(n), n = words.length,
	 * Space: O(n*k/L), k = average length of a word, which is to say
	 * the number of new lines.
	 */
	public static List<String> fullJustify2(String[] words, int L) {
		int space = 0; // current total word spaces taken
		int wordCnt = 0;// word count
		int start = 0; // start index of a new line
		List<String> res = new ArrayList<String>();
		for (int i = 0; i < words.length; i++) {
			String curr = words[i];
			int currLength = space + curr.length() + wordCnt;
			if (currLength <= L) {
				wordCnt++;
				space += curr.length();
			} else {
				int rest = L - space;
				int divided = wordCnt == 1 ? 1 : wordCnt - 1;
				int even = rest / divided;
				int spareIdx = start + rest % divided;
				StringBuilder sb = new StringBuilder();
				for (int j = start; j < i - 1; j++) {
					sb.append(words[j]);
					for (int k = 0; k < even; k++)
						sb.append(' ');
					if (j < spareIdx)
						sb.append(' ');
				}
				sb.append(words[i - 1]);
				if (sb.length() < L) {
					assert wordCnt == 1;
					for (int j = sb.length(); j < L; j++)
						sb.append(' ');
				}
				res.add(sb.toString());
				start = i; // start a newline from i
				wordCnt = 1;
				space = curr.length();
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = start; i < words.length; i++) {
			sb.append(words[i]);
			sb.append(' ');
		}
		if (sb.length() > L)
			sb.deleteCharAt(sb.length() - 1);
		for (int i = sb.length(); i < L; i++)
			sb.append(' ');
		res.add(sb.toString());
		return res;
	}

	/**
	 * First solution
	 * Time: O(n), n = words.length,
	 * Space: O(n*k/L), k = average length of a word, which is to say
	 * the number of new lines.
	 */
	public static List<String> fullJustify(String[] words, int L) {
		List<String> res = new ArrayList<String>();
		int last = 0;
		int currlen = 0;
		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			if (currlen + word.length() > L) {
				StringBuilder sb = new StringBuilder();
				int spaces = L - (currlen - 1); // how many spaces need
				// not count the rightmost word (it's for right-justified)
				int numWords = i - last - 1;
				if (numWords > 0) { // more than one word
					int evenSpaces = spaces / numWords; // evenly distributed
					int extraSpaces = spaces % numWords;
					for (int j = last; j < i; j++) {
						if (j == i - 1) // right-justified
							sb.append(words[j]);
						else { // left-justified
							sb.append(words[j] + ' ');
							for (int k = 0; k < evenSpaces; k++)
								sb.append(' ');
							// j-i : relative position
							if ((j - last) < extraSpaces)
								sb.append(' ');
						}
					}
				} else { // only one word
					sb.append(words[last]);
					for (int j = words[last].length(); j < L; j++)
						sb.append(' ');
				}

				res.add(sb.toString());
				last = i;
				currlen = word.length() + 1;
			} else { // currlen + word.length() < L
				currlen += word.length() + 1;
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = last; i < words.length; i++) {
			sb.append(words[i]);
			if (sb.length() < L)
				sb.append(' ');
		}

		for (int i = sb.length(); i < L; i++)
			sb.append(' ');

		res.add(sb.toString());

		return res;
	}
}
