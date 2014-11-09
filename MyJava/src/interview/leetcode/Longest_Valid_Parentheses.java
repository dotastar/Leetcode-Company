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
					extraRight = i;	//update separate point
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
	
	
	/**
	 * Same, second time practice
	 */
    public int longestValidParentheses2(String s) {
        int len = s.length();
        int start = 0;
        int max = 0;
        Stack<Integer> idx = new Stack<Integer>();
        for(int i=0; i<len; i++){
            if(s.charAt(i)=='('){
                idx.push(i);
            }else{
                if(idx.isEmpty()){
                    start = i+1;
                }else{
                    idx.pop();
                    int left = idx.isEmpty()?start:idx.peek()+1;
                    int length = i-left+1;
                    max = length>max?length:max;
                }
            }
        }
        return max;
    }
}
