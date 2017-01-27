package interview.leetcode;

import java.io.UnsupportedEncodingException;

/**
 * Given two binary strings, return their sum (also a binary string).
 * 
 * For example, a = "11" b = "1" Return "100".
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Add_Binary {
	public static void main(String[] args) throws UnsupportedEncodingException {
		Add_Binary o = new Add_Binary();
		String res = o.addBinary3("0", "0");
		System.out.println(res.length());
		String ans = "0";
		assert res.equals(ans);
	}

	/**
	 * The fastest solution
	 * Don't reverse a, b and the result, directly start from back to front
	 */
	public String addBinary3(String a, String b) {
		int len = a.length() > b.length() ? a.length() : b.length();
		char[] res = new char[len + 1];
		int carry = 0;
		for (int i = a.length() - 1, j = b.length() - 1; i >= 0 || j >= 0; i--, j--) {
			int bitA = i >= 0 ? a.charAt(i) - '0' : 0;
			int bitB = j >= 0 ? b.charAt(j) - '0' : 0;
			int sum = bitA + bitB + carry;
			carry = sum / 2;
			sum %= 2;
			res[len--] = (char) ('0' + sum);
		}
		if (carry > 0)
			res[0] = '1'; // don't forget the last carry
		carry = carry ^ 1; // revert carry, use it as the offset of res
		return new String(res, carry, res.length - carry);
	}

	/**
	 * Don't reverse a and b, but reverse the result.
	 * For a and b, directly start from back to front
	 */
	public String addBinary2(String a, String b) {
		StringBuilder sb = new StringBuilder();
		int carry = 0;
		for (int i = a.length() - 1, j = b.length() - 1; i >= 0 || j >= 0; i--, j--) {
			int bitA = i >= 0 ? a.charAt(i) - '0' : 0;
			int bitB = j >= 0 ? b.charAt(j) - '0' : 0;
			int sum = bitA + bitB + carry;
			carry = sum / 2;
			sum %= 2;
			sb.append(sum);
		}
		if (carry > 0)
			sb.append(1);
		return sb.reverse().toString();
	}

	/**
	 * Reverse a and b, so that it is easier to manipulate
	 */
	public String addBinary(String a, String b) {
		a = new StringBuilder(a).reverse().toString();
		b = new StringBuilder(b).reverse().toString();
		StringBuilder sb = new StringBuilder();
		// use the greater one as length
		int len = a.length() > b.length() ? a.length() : b.length();
		int carry = 0;
		for (int i = 0; i < len; i++) {
			int bitA = i < a.length() ? a.charAt(i) - '0' : 0;
			int bitB = i < b.length() ? b.charAt(i) - '0' : 0;
			int sum = bitA + bitB + carry;
			carry = sum / 2;
			sum %= 2;
			sb.append(sum);
		}
		if (carry > 0)
			sb.append(1);
		return sb.reverse().toString();
	}
}
