package interview.laicode;

/**
 * All Unique Characters I
 * Easy
 * Data Structure
 * 
 * Determine if the characters of a given string are all unique.
 * 
 * Assumptions
 * 
 * The only set of possible characters used in the string are 'a' - 'z', the 26
 * lower case letters.
 * The given string is not null.
 * 
 * Examples
 * 
 * the characters used in "abcd" are unique
 * the characters used in "aba" are not unique
 * 
 * 
 * @author yazhoucao
 * 
 */
public class All_Unique_Characters_I {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public boolean allUnique(String word) {
		int[] dict = new int[8];
		for (int i = 0; i < word.length(); i++) {
			int chVal = word.charAt(i);
			int idx = chVal / 32;
			int offset = chVal % 32;
			if (((dict[idx] >> offset) & 1) == 1)
				return false;
			dict[idx] |= 1 << offset;
		}
		return true;
	}
}
