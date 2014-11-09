package interview.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
		System.out.println(generateParenthesis2(3).toString());
	}

	/**
	 * Recursion, Time: O(2^n), Space: O(n).
	 */
	public static List<String> generateParenthesis(int n) {
		List<String> parens = new ArrayList<String>();
		if (n == 0)
			return parens;
		addParenthesis(parens, new char[2*n], 0, 0);
		return parens;
	}

	public static void addParenthesis(List<String> parens, char[] chs,
			int lCount, int rCount) {
		int len = chs.length;
		if (lCount+rCount == len) {
			parens.add(new String(chs));
			return;
		}
		int idx = lCount+rCount;
		
		if (lCount < len/2) {
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
    public static List<String> generateParenthesis2(int n) {
        List<String> res = new ArrayList<String>();
        generate(n, n, new Stack<Character>(), res);
        return res;
    }
    
    public static void generate(int left, int right, Stack<Character> paren, List<String> res){
        if(left==0 && right==0){
            StringBuilder sb = new StringBuilder();
            for(Character c : paren)
                sb.append(c);
            res.add(sb.toString());
            return;
        }
        
        if(right>0 && left<right){
            paren.push(')');
            generate(left, right-1, paren, res);
            paren.pop();
        }
        
        if(left>0 && left<=right){
            paren.push('(');
            generate(left-1, right, paren, res);
            paren.pop();
        }
    }
}
