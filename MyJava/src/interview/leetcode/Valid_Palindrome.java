package interview.leetcode;

/**
 * Given a string, determine if it is a palindrome, considering only
 * alphanumeric characters and ignoring cases.
 * 
 * For example, "A man, a plan, a canal: Panama" is a palindrome. "race a car"
 * is not a palindrome.
 * 
 * Note: Have you consider that the string might be empty? This is a good
 * question to ask during an interview.
 * 
 * For the purpose of this problem, we define empty string as valid palindrome.
 * 
 * @author yazhoucao
 * 
 */
public class Valid_Palindrome {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(isPalindrome("A man, a plan, a canal: Panama"));
		System.out.println(isPalindrome("1a2"));
	}

	/**
	 * Use Character.isLetterOrDigit() to deal with corner cases
	 */
	public static boolean isPalindrome(String s) {
		int l = 0;
		int r = s.length() - 1;
		s = s.toLowerCase();
		while (l < r) {
			while (l < r && !Character.isLetterOrDigit(s.charAt(l)))
				l++;
			while (l < r && !Character.isLetterOrDigit(s.charAt(r)))
				r--;
			if (s.charAt(l) != s.charAt(r))
				return false;
			l++;
			r--;
		}
		return true;
	}

	/**
	 * Use regular expression, a more elegant way to deal with corner cases
	 * 
	 * But it is slower than above solution in real time running on LeetCode OJ.
	 */
	public static boolean isPalindrome2(String s) {
		s = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
		int l = 0;
		int r = s.length() - 1;
		while (l < r) {
			if (s.charAt(l) != s.charAt(r))
				return false;
			l++;
			r--;
		}
		return true;
	}
}
