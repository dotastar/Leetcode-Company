package interview.leetcode;

/**
 * Given a non-negative number represented as an array of digits, plus one to
 * the number.
 * 
 * The digits are stored such that the most significant digit is at the head of
 * the list.
 * 
 * @author yazhoucao
 * 
 */
public class Plus_One {

	public static void main(String[] args) {

	}

	public int[] plusOne(int[] digits) {
		for (int i = digits.length - 1; i >= 0; i--) {
			digits[i]++;
			if (digits[i] > 9) { // if loop continue, there must be a carry = 1
				digits[i] = 0;
			} else
				return digits; // otherwise, add 1 and return
		}
		// for end, it must be overflowed
		int[] num_new = new int[digits.length + 1];
		num_new[0] = 1;
		return num_new;
	}

}
