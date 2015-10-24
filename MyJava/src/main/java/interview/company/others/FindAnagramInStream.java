package interview.company.others;

import static org.junit.Assert.assertEquals;
import interview.AutoTestUtils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * 
 * Booking.com
 * 
 * Phone interview
 * 
 * Let's write a function that takes a string as its argument. Then, the
 * function reads text from a file character by character. It should stop
 * reading when an anagram of the string is found in the stream.
 * 
 * Example:
 * 
 * IN
 * word=cat
 * stream examples -
 * 
 * 	I: it was a true fact
 * 	                     ^
 * 	                     |
 *                      stop reading (act is an anagram of cat)
 *                      
 *  II: it is tactile sense
 *               ^
 *               |
 *             stop reading (tac is an anargam of cat)
 *             
 *  III: we found our cats
 *                       ^
 *                       |
 *                      stop reading (found cat)
 * 
 * getchar()
 * 
 * @author yazhoucao
 *
 */
public class FindAnagramInStream {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(FindAnagramInStream.class);
	}

	char[] data;
	int i;

	/**
	 * Assume we have a getChar() to read one character a time from the stream
	 */
	private char getChar() {
		return i < data.length ? data[i++] : '\0';
	}

	public void read(String target) {
		if (target == null || target.length() == 0)
			return;
		int len = target.length();
		Map<Character, Integer> window = new HashMap<>();
		StringBuilder word = new StringBuilder();
		char curr;
		do {
			curr = getChar();
			// read a word in
			while (curr != '\0' && curr != ' ') {
				word.append(curr);
				curr = getChar();
			}
			// compare if it contains an anagram
			if (word.length() >= len) {
				for (int i = 0; i < word.length(); i++) {
					// compare and add right, delete left
					char ci = word.charAt(i);
					if (isAnagram(target, window))
						return;
					if (i >= len) { // delete left
						char cleft = word.charAt(i - len);
						window.put(cleft, window.get(cleft) - 1);
					}
					// add right
					Integer cnt = window.get(ci);
					if (cnt == null)
						cnt = 0;
					window.put(ci, cnt + 1);
				}
			}
			word.setLength(0);
			window.clear();
		} while (curr != '\0');
	}

	private boolean isAnagram(String target, Map<Character, Integer> map) {
		// finish later
		// we will skip this part :)
		// TODO
		return true;
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}
}
