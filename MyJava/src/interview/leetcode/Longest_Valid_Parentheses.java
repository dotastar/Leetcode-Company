package interview.leetcode;

import java.util.Stack;

/**
 * Given a string containing just the characters '(' and ')', find the length of
 * the longest valid (well-formed) parentheses substring.
 * 
 * For "(()", the longest valid parentheses substring is "()", which has length
 * = 2.
 * 
 * Another example is ")()())", where the longest valid parentheses substring is
 * "()()", which has length = 4.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Longest_Valid_Parentheses {

	public static void main(String[] args) {
		Longest_Valid_Parentheses o = new Longest_Valid_Parentheses();
		System.out.println(o.longestValidParentheses("(()")); // 2
		System.out.println(o.longestValidParentheses("()(()"));// 2
		System.out.println(o.longestValidParentheses("()((())"));// 4
		System.out.println(o.longestValidParentheses("(()()"));// 4
	}

	/**
	 * Use a Stack to store the index of each Char, case analysis:
	 * 0.If stk.isEmpty, push
	 * 1.Current char is '(', push
	 * 2.Current char is ')':
	 * peek the stack, if top is '(', pop it out, calculate length
	 * else is ')', push current in
	 **/
	public int longestValidParentheses2(String S) {
		Stack<Integer> stk = new Stack<>();
		int maxLength = 0;
		for (int i = 0; i < S.length(); i++) {
			if (stk.isEmpty() || S.charAt(i) == '(')
				stk.push(i);
			else { // current is ')'
				if (S.charAt(stk.peek()) == '(') {
					stk.pop();
					// calculate current length
					int length = stk.isEmpty() ? i + 1 : i - stk.peek();
					maxLength = Math.max(length, maxLength);
				} else
					stk.push(i);
			}
		}
		return maxLength;
	}

	/**
	 * (()()
	 * ^
	 * Use a Stack to store the index of parentheses.
	 * Traverse the input String s, the current char is si.
	 * Cases:
	 * 0.stack empty, push into stack
	 * 1.si == '(', push into stack
	 * 2.si == ')', peek stack,
	 * if matches the top, pop() and calculate length, else push into stack
	 */
	public int longestValidParentheses(String s) {
		int max = 0;
		Stack<Integer> stk = new Stack<Integer>();
		for (int i = 0; i < s.length(); i++) {
			char curr = s.charAt(i);
			if (curr == ')' && !stk.isEmpty() && s.charAt(stk.peek()) == '(') {
				stk.pop();
				int length = stk.isEmpty() ? i + 1 : i - stk.peek();
				max = length > max ? length : max;
			} else
				stk.push(i);
		}
		return max;
	}

}
