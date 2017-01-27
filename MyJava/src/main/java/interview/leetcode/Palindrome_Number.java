package interview.leetcode;

/**
 * Determine whether an integer is a palindrome. Do this without extra space.
 * 
 * click to show spoilers.
 * 
 * Some hints: Could negative integers be palindromes? (ie, -1)
 * 
 * If you are thinking of converting the integer to string, note the restriction
 * of using extra space.
 * 
 * You could also try reversing an integer. However, if you have solved the
 * problem "Reverse Integer", you know that the reversed integer might overflow.
 * How would you handle such case?
 * 
 * There is a more generic way of solving this problem.
 * 
 * @author yazhoucao
 * 
 */
public class Palindrome_Number {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(isPalindrome(1));
	}

	public static boolean isPalindrome(int x) {
		if (x < 0)
			return false;
		int reversed = 0, num = x;
		while (num != 0) {
			int digit = num % 10;
			if (reversed > (Integer.MAX_VALUE - digit) / 10)
				return false; // overflow
			reversed = reversed * 10 + digit;
			num /= 10;
		}
		return reversed == x;
	}

}
