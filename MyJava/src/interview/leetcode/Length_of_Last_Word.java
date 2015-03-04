package interview.leetcode;

/**
 * Given a string s consists of upper/lower-case alphabets and empty space
 * characters ' ', return the length of last word in the string.
 * 
 * If the last word does not exist, return 0.
 * 
 * Note: A word is defined as a character sequence consists of non-space
 * characters only.
 * 
 * For example, Given s = "Hello World", return 5.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Length_of_Last_Word {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s0 = "a b c asdf gasdg 1234";
		String s1 = "a";
		String s2 = "";
		String s3 = "   .   .  ";
		String s4 = "a  b   ";
		System.out.print(lengthOfLastWord2(s0) + "\t");
		System.out.print(lengthOfLastWord2(s1) + "\t");
		System.out.print(lengthOfLastWord2(s2) + "\t");
		System.out.print(lengthOfLastWord(s3) + "\t");
		System.out.print(lengthOfLastWord2(s4) + "\t");

	}

	/**
	 * Better solution
	 */
	public static int lengthOfLastWord2(String s) {
		int count = 0;
		for (int i = s.length() - 1; i >= 0; i--) {
			if (s.charAt(i) != ' ') {
				count++;
			} else if (count > 0) // key
				break;
		}
		return count;
	}

	/**
	 * General template solution
	 */
	public static int lengthOfLastWord(String s) {
		int len = 0;
		for (int i = 0; i < s.length();) {
			len = 0;
			while (i < s.length() && s.charAt(i) != ' ') {
				i++;
				len++;
			}
			while (i < s.length() && s.charAt(i) == ' ')
				i++;
		}
		return len;
	}

}
