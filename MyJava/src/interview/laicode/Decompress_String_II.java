package interview.laicode;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * 
 * Decompress String II
 * Hard
 * String
 * 
 * Given a string in compressed form, decompress it to the original string. The
 * adjacent repeated characters in the original string are compressed to have
 * the character followed by the number of repeated occurrences.
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
 * “a1c0b2c4” → “abbcccc”
 * 
 * @author yazhoucao
 * 
 */
public class Decompress_String_II {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(Decompress_String_II.class);
	}

	/**
	 * 1.calculate its size, and see if needs to allocate more space
	 * 2.decompress, do it in-place, two pointers
	 */
	public String decompress(String input) {
		int size = 0;
		for (int i = 1; i < input.length(); i += 2) {
			size += input.charAt(i) - '0';
		}

		char[] res;
		if (size > input.length())
			res = new char[size]; // allocate new spaces
		else
			res = input.toCharArray(); // do it in-place

		int end = 0;
		for (int i = 1; i < input.length(); i += 2) {
			int cnt = input.charAt(i) - '0';
			while (cnt-- > 0)
				res[end++] = input.charAt(i - 1);
		}

		return String.valueOf(res, 0, end);
	}

	@Test
	public void test1() {
		String input = "a1c0b2c4";
		String res = decompress(input);
		String ans = "abbcccc";
		assertTrue("Wrong: " + res, ans.equals(res));
	}
}
