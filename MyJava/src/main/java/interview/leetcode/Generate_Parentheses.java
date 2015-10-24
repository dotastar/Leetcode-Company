package interview.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Given n pairs of parentheses, write a function to generate all combinations
 * of well-formed parentheses.
 * 
 * For example, given n = 3, a solution set is:
 * 
 * "((()))", "(()())", "(())()", "()(())", "()()()"
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Generate_Parentheses {

	public static void main(String[] args) {
		Generate_Parentheses o = new Generate_Parentheses();
		System.out.println(o.generateParenthesis2(3).toString());
	}

	/**
	 * Recursion, Time: O(2^n), Space: O(n).
	 */
	public List<String> generateParenthesis(int n) {
		List<String> parens = new ArrayList<String>();
		if (n == 0)
			return parens;
		addParenthesis(parens, new char[2 * n], 0, 0);
		return parens;
	}

	public void addParenthesis(List<String> parens, char[] chs, int lCount, int rCount) {
		int len = chs.length;
		if (lCount + rCount == len) {
			parens.add(new String(chs));
			return;
		}
		int idx = lCount + rCount;

		if (lCount < len / 2) {
			chs[idx] = '(';
			addParenthesis(parens, chs, lCount + 1, rCount);
		}

		if (rCount < lCount) {
			chs[idx] = ')';
			addParenthesis(parens, chs, lCount, rCount + 1);
		}
	}

	/**
	 * Recursion, same solution, second time practice
	 */
	public List<String> generateParenthesis2(int n) {
		List<String> res = new ArrayList<>();
		generate(n, n, new StringBuilder(), res);
		return res;
	}

	/**
	 * 0.l == 0, use ')'
	 * 1.l < r, use either '(' or ')'
	 * 2.l == r, use '('
	 * 
	 */
	private void generate(int l, int r, StringBuilder sb, List<String> res) {
		if (l == 0 && r == 0) {
			res.add(sb.toString());
			return;
		}

		int oldLen = sb.length();

		if (l > 0 && l <= r) {
			sb.append('(');
			generate(l - 1, r, sb, res);
			sb.setLength(oldLen);
		}
		if (l == 0 || l < r) {
			sb.append(')');
			generate(l, r - 1, sb, res);
			sb.setLength(oldLen);
		}
	}
}
