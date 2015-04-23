package interview.laicode;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.Test;

/**
 * 
 * All Valid Permutations Of Parentheses II
 * Hard
 * Recursion
 * 
 * Get all valid permutations of l pairs of (), m pairs of [] and n pairs of {}.
 * 
 * Assumptions
 * 
 * l, m, n >= 0
 * 
 * Examples
 * 
 * l = 1, m = 1, n = 0, all the valid permutations are ["()[]", "([])", "[()]",
 * "[]()"]
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class All_Valid_Permutations_Of_Parentheses_II {

	public static void main(String[] args) {
		StringBuilder sb =null;
		
		AutoTestUtils
				.runTestClassAndPrint(All_Valid_Permutations_Of_Parentheses_II.class);
	}

	private final static char[][] parentheses = { { '(', ')' }, { '[', ']' },
			{ '{', '}' } };

	public List<String> validParentheses(int l, int m, int n) {
		List<String> res = new ArrayList<>();
		// enumerate(res, new StringBuilder(), new Stack<Character>(), l, l, m,
		// m, n, n);
		int[][] nums = { { l, l }, { m, m }, { n, n } };
		enumerate(res, new StringBuilder(), new Stack<Character>(), nums);
		return res;
	}

	/**
	 * More concise subroutine
	 */
	public void enumerate(List<String> res, StringBuilder parens,
			Stack<Character> needMatch, int[][] nums) {
		if (isFinished(nums)) {
			res.add(parens.toString());
			return;
		}
		for (int i = 0; i < nums.length; i++) {
			char leftParen = parentheses[i][0], rightParen = parentheses[i][1];
			if (nums[i][0] > 0) {
				parens.append(leftParen);
				needMatch.push(leftParen);
				nums[i][0]--;
				enumerate(res, parens, needMatch, nums);
				nums[i][0]++;
				needMatch.pop();
				parens.setLength(parens.length() - 1);
			}
			if (nums[i][1] > nums[i][0] && !needMatch.isEmpty()
					&& needMatch.peek() == leftParen) {
				parens.append(rightParen);
				needMatch.pop();
				nums[i][1]--;
				enumerate(res, parens, needMatch, nums);
				nums[i][1]++;
				needMatch.push(leftParen);
				parens.setLength(parens.length() - 1);
			}
		}
	}

	private boolean isFinished(int[][] nums) {
		for (int i = 0; i < nums.length; i++) {
			if (nums[i][0] != 0 || nums[i][1] != 0)
				return false;
		}
		return true;
	}

	/**
	 * Naive subroutine, very verbose
	 */
	public void enumerate(List<String> res, StringBuilder parens,
			Stack<Character> needMatch, int l1, int r1, int l2, int r2, int l3,
			int r3) {
		if (l1 == 0 && r1 == 0 && l2 == 0 && r2 == 0 && l3 == 0 && r3 == 0) {
			res.add(parens.toString());
			return;
		}
		if (l1 > 0) {
			parens.append('(');
			needMatch.push('(');
			enumerate(res, parens, needMatch, l1 - 1, r1, l2, r2, l3, r3);
			needMatch.pop();
			parens.setLength(parens.length() - 1);
		}
		if (r1 > l1 && !needMatch.isEmpty() && needMatch.peek() == '(') {
			parens.append(')');
			needMatch.pop();
			enumerate(res, parens, needMatch, l1, r1 - 1, l2, r2, l3, r3);
			needMatch.push('(');
			parens.setLength(parens.length() - 1);
		}

		if (l2 > 0) {
			parens.append('[');
			needMatch.push('[');
			enumerate(res, parens, needMatch, l1, r1, l2 - 1, r2, l3, r3);
			needMatch.pop();
			parens.setLength(parens.length() - 1);
		}
		if (r2 > l2 && !needMatch.isEmpty() && needMatch.peek() == '[') {
			parens.append(']');
			needMatch.pop();
			enumerate(res, parens, needMatch, l1, r1, l2, r2 - 1, l3, r3);
			needMatch.push('[');
			parens.setLength(parens.length() - 1);
		}

		if (l3 > 0) {
			parens.append('{');
			needMatch.push('{');
			enumerate(res, parens, needMatch, l1, r1, l2, r2, l3 - 1, r3);
			needMatch.pop();
			parens.setLength(parens.length() - 1);
		}
		if (r3 > l3 && !needMatch.isEmpty() && needMatch.peek() == '{') {
			parens.append('}');
			needMatch.pop();
			enumerate(res, parens, needMatch, l1, r1, l2, r2, l3, r3 - 1);
			needMatch.push('{');
			parens.setLength(parens.length() - 1);
		}
	}

	@Test
	public void test1() {
		int l = 2, m = 0, n = 1;
		List<String> res = validParentheses(l, m, n);
		for (String paren : res)
			System.out.println(paren.toString());
		assertTrue(res.size() == 15);
	}
}
