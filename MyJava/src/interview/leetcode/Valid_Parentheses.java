package interview.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Given a string containing just the characters '(', ')', '{', '}', '[' and
 * ']', determine if the input string is valid.
 * 
 * 
 * The brackets must close in the correct order, "()" and "()[]{}" are all valid
 * but "(]" and "([)]" are not.
 * 
 * @author yazhoucao
 * 
 */
public class Valid_Parentheses {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(isValid("((")); // false
		System.out.println(isValid("([)]")); // false
		System.out.println(isValid("()[]{}")); // true
		System.out.println(isValid("([])")); // true
	}

	/**
	 * Note: there is an order to maintain, ([]) is OK, ([)] is not OK.
	 * 
	 */
	public static boolean isValid(String s) {
		int len = s.length();	
		Map<Character, Character> map = new HashMap<Character, Character>();
		map.put('(', ')');
		map.put('[', ']');
		map.put('{', '}');
 
		Stack<Character> stack = new Stack<Character>();
		for(int i=0; i<len; i++){
			char c = s.charAt(i);
			if(map.keySet().contains(c))
				stack.push(c);
			else if(map.values().contains(c)){
				if(!stack.isEmpty() && map.get(stack.peek())==c)
					stack.pop();
				else
					return false;
			}
		}
		
		return stack.isEmpty();
	}
	
	/**
	 * Second time practice
	 */
    public boolean isValid2(String s) {
        int len = s.length();
        Stack<Character> stk = new Stack<Character>();
        for(int i=0; i<len; i++){
            char c = s.charAt(i);
            if(c=='('||c=='{'||c=='[')
                stk.push(c);
            else if(stk.isEmpty()){
                return false;
            }else{//c== ']' || '}' || ')'
                switch(c){
                    case ')':
                        if('('!=stk.pop())
                            return false;
                        break;
                    case '}':
                        if('{'!=stk.pop())
                            return false;
                        break;
                    case ']':
                        if('['!=stk.pop())
                            return false;
                        break;
                }
            }
        }
        return stk.isEmpty();
    }

}
