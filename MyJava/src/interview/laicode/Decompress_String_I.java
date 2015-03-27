package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * 
 * Decompress String I
 * Fair
 * String
 * 
 * Given a string in compressed form, decompress it to the original string. The
 * adjacent repeated characters in the original string are compressed to have
 * the character followed by the number of repeated occurrences. If the
 * character does not have any adjacent repeated occurrences, it is not
 * compressed.
 * 
 * Assumptions
 * 
 * The string is not null
 * 
 * The characters used in the original string are guaranteed to be ‘a’ - ‘z’
 * 
 * There are no adjacent repeated characters with length > 9
 * 
 * Examples
 * 
 * “acb2c4” → “acbbcccc”
 * 
 * @author yazhoucao
 * 
 */
public class Decompress_String_I {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Decompress_String_I.class);
	}

	/**
	 * 1.calculate size, and allocate spaces for the result
	 * 2.decompress the string
	 */
	public String decompress(String input) {
		int size = 0;
		for (int i = 0; i < input.length();) {
			char next = i == input.length() - 1 ? '\0' : input.charAt(i + 1);
			if (Character.isDigit(next)) {
				size += next - '0';
				i += 2;
			} else { // next is Letter or current is the last char
				size++;
				i++;
			}
		}
		if (size == input.length())
			return input;

		char[] res = new char[size];
		for (int i = 0, j = 0; i < input.length();) {
			char next = i == input.length() - 1 ? '\0' : input.charAt(i + 1);
			if (Character.isDigit(next)) {
				int length = (next - '0');
				while (length > 0) {
					res[j++] = input.charAt(i);
					length--;
				}
				i += 2;
			} else { // next is Letter or current is the last char
				res[j++] = input.charAt(i++);
			}
		}
		return String.valueOf(res);
	}

	@Test
	public void test1() {
		String input = "acb2c4";
		String res = decompress(input);
		String ans = "acbbcccc";
		assertTrue("Wrong: " + res, res.equals(ans));
	}

	@Test
	public void test2() {
		String input = "a9";
		String res = decompress(input);
		String ans = "aaaaaaaaa";
		assertTrue("Wrong: " + res, res.equals(ans));
	}

	@Test
	public void test3() {
		String input = "abcdefg";
		String res = decompress(input);
		String ans = "abcdefg";
		assertTrue("Wrong: " + res, res.equals(ans));
	}
}
