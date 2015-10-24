package interview.company.epic;

import java.util.ArrayList;
import java.util.List;

/**
 * The decimal and octal values of some numbers are both palindromes sometimes.
 * Find such numbers within a given range.
 * 
 * @author yazhoucao
 * 
 */
public class OctalAndDecimalPalindrome {

	public static void main(String[] args) {
		OctalAndDecimalPalindrome o = new OctalAndDecimalPalindrome();
		for (int i = 0; i < 150; i++) {
			System.out.println(o.findPalindromes(i).toString());
		}
	}

	public List<Integer> findPalindromes(int max) {
		List<Integer> res = new ArrayList<>();
		if (max < 8) {
			for (int i = 0; i <= max; i++)
				res.add(i);
			return res;
		}

		for (int i = 0; i <= max; i++) {
			if (isDecimalPalindrome(i) && isOctalPalindrome(i))
				res.add(i);
		}
		return res;
	}

	private boolean isDecimalPalindrome(int num) {
		int reversed = 0;
		int original = num;
		while (num != 0) {
			reversed = reversed * 10 + num % 10;
			num /= 10;
		}
		return original == reversed;
	}

	private boolean isOctalPalindrome(int num) {
		String s = Integer.toOctalString(num);
		int l = 0, r = s.length() - 1;
		while (l < r) {
			if (s.charAt(l) != s.charAt(r))
				return false;
			l++;
			r--;
		}
		return true;
	}
}
