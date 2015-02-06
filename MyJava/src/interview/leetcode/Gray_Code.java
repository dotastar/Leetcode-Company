package interview.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * The gray code is a binary numeral system where two successive values differ
 * in only one bit.
 * 
 * Given a non-negative integer n representing the total number of bits in the
 * code, print the sequence of gray code. A gray code sequence must begin with
 * 0.
 * 
 * For example, given n = 2, return [0,1,3,2].
 * 
 * Its gray code sequence is:
 * 
 * 00 - 0
 * 
 * 01 - 1
 * 
 * 11 - 3
 * 
 * 10 - 2
 * 
 * Note: For a given n, a gray code sequence is not uniquely defined.
 * 
 * For example, [0,2,3,1] is also a valid gray code sequence according to the
 * above definition.
 * 
 * For now, the judge is able to judge based on one instance of gray code
 * sequence. Sorry about that.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Gray_Code {

	public static void main(String[] args) {
		System.out.println(grayCode(0).toString());
		System.out.println(grayCode(1).toString());
		System.out.println(grayCode(2).toString());
		System.out.println(grayCode(3).toString());
		System.out.println(grayCode(4).toString());
	}

	/**
	 * The key is the rule of generating gray code, Time: O(2^n), Space: O(1).
	 * 
	 * The binary-reflected Gray code list for n bits can be generated
	 * recursively from the list for n−1 bits by reflecting the list (i.e.
	 * listing the entries in reverse order), concatenating the original list
	 * with the reversed list, prefixing the entries in the original list with a
	 * binary 0, and then prefixing the entries in the reflected list with a
	 * binary 1.
	 * 
	 * 3 bit gray code sequence:
	 * 
	 * 0 0 0
	 * 0 0 1
	 * 0 1 1
	 * 0 1 0
	 * 
	 * add 1 on no.n bit and reverse the order of above sequence, you will get:
	 * 
	 * 1 1 0
	 * 1 1 1
	 * 1 0 1
	 * 1 0 0
	 * 可以看到第n位的格雷码由两部分构成，一部分是n-1位格雷码，再加上 1<<(n-1)和n-1位格雷码的逆序的和。
	 * 
	 * See here:
	 * http://en.wikipedia.org/wiki/Gray_code
	 */
	public static List<Integer> grayCode(int n) {
		List<Integer> res = new ArrayList<Integer>();
		res.add(0);
		for (int i = 0; i < n; i++) {
			int size = res.size();
			for (int j = size - 1; j >= 0; j--) {
				int code = res.get(j);
				code |= (1 << i);
				res.add(code);
			}
		}
		return res;
	}

	/**
	 * Second time
	 */
	public List<Integer> grayCode2(int n) {
		List<Integer> codes = new ArrayList<>();
		codes.add(0);
		for (int i = 0; i < n; i++) {
			int len = codes.size();
			for (int j = len - 1; j >= 0; j--)
				codes.add(codes.get(j) | (1 << i));
		}
		return codes;
	}
}
