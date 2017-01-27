package interview.company.epic;

import static org.junit.Assert.*;

import org.junit.Test;

import interview.AutoTestUtils;

/**
 * In 1-9 keypad one key is not working. If some one enters a password then not
 * working key will not be entered. You have given expected password and entered
 * password. Check that entered password is valid or not.
 * E.g: entered 164, expected 18684 (you need to take care as when u enter 18684
 * and 164 only both will be taken as 164 input)
 * 
 * @author yazhoucao
 * 
 */
public class GuessPasswordOnKeyPad {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(GuessPasswordOnKeyPad.class);
	}

	/**
	 * Traverse both of them at the same time, if their characters are not same,
	 * then it must be the fault key, or if there already exists one fault key,
	 * then the password is wrong.
	 * Keep traversing until either is end. Then if the expected still have
	 * characters not checked, keep checking the rest, the rest characters must
	 * be all fault keys.
	 * 
	 * Time: O(n), n = length(expected).
	 */
	public boolean isValidGuess(String entered, String expected) {
		char faultKey = '\0';
		int i = 0, j = 0; // i for entered, j for expected
		for (; i < entered.length() && j < expected.length(); i++, j++) {
			if (entered.charAt(i) != expected.charAt(j)) {
				if (faultKey == '\0') {
					faultKey = expected.charAt(j);
				} else if (faultKey != expected.charAt(j))
					return false;

				i--;
			}
		}
		while (j < expected.length() && expected.charAt(j) == faultKey)
			j++;
		return (i == entered.length() && j == expected.length()) ? true : false;
	}

	@Test
	public void test1() { // input case
		String entered = "164";
		String expected = "18684";
		assertTrue(isValidGuess(entered, expected));
	}

	@Test
	public void test2() { // the first is fault key
		String entered = "164";
		String expected = "8186848";
		assertTrue(isValidGuess(entered, expected));
	}

	@Test
	public void test3() { // failed case, two fault keys
		String entered = "164";
		String expected = "186847777";
		assertTrue(!isValidGuess(entered, expected));
	}

	@Test
	public void test4() { // failed case, the order of keys is wrong
		String entered = "164";
		String expected = "146";
		assertTrue(!isValidGuess(entered, expected));
	}
}
