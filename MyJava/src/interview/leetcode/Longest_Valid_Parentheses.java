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
		System.out.println(longestValidParentheses("(()")); // 2
		System.out.println(longestValidParentheses("()(()"));// 2
		System.out.println(longestValidParentheses("()((())"));// 4
		System.out.println(longestValidParentheses("(()()"));// 4
	}

	/**
	 * Use a Stack to store the index of each Char, case analysis:
	 * 0.If stk.isEmpty, push
	 * 1.Current char is '(', push
	 * 2.Current char is ')':
	 * 		peek the stack, if top is '(', pop it out, calculate length
	 * 						else is ')', push current in
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
	 * Traverse the String, use stack to store the index of '('
	 * 
	 * Time O(n)
	 * 
	 * The key is to differentiate the different cases and how to calculate the
	 * length of each
	 * 
	 * @return
	 */
	public static int longestValidParentheses(String s) {
		int max = 0;
		// index of the last extra ')', use it as an separate point to separate
		// previous valid parenthese group and future group
		int extraRight = -1;
		Stack<Integer> leftIdxs = new Stack<Integer>();

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				leftIdxs.push(i);
			} else { // is ')'
				if (leftIdxs.isEmpty()) {// 1)is an extra right parenthese
					extraRight = i; // update separate point
				} else { // 2). more than one '(' in stack
					leftIdxs.pop();
					int currValid;
					if (leftIdxs.isEmpty()) { // 2.1)nothing in the stack
						// *** valid is from extraRight to current
						currValid = i - extraRight;
					} else {// 2.2)still have '(' in stack
						// *** valid is from last pushed left to current
						currValid = i - leftIdxs.peek();
					}
					max = currValid > max ? currValid : max;
				}
			}
		}
		return max;
	}

}
