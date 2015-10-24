package interview.leetcode;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
 * Valid operators are +, -, *, /. Each operand may be an integer or another
 * expression. 
 * Some examples:
 *   ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
 *   ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
 * @author yazhoucao
 * 
 */
public class Evaluate_Reverse_Polish_Notation {
	
	public static void main(String[] args) {
		String[] s1  = {"2", "1", "+", "3", "*"};
		String[] s2 = {"4", "13", "5", "/", "+"};
		System.out.println(evalRPN(s1));
		System.out.println(evalRPN(s2));
	}

	
	public static int evalRPN(String[] tokens) {
		Set<String> arithOpers = initArithOperSet();
		
		Stack<Integer> nums = new Stack<Integer>();
		for (String token : tokens) {
			if(arithOpers.contains(token)){
				if(nums.size()>=2){
					int num2 = nums.pop();
					int num1 = nums.pop();
					nums.push(calculate(token, num1, num2));
				}
			}else
				nums.push(Integer.valueOf(token));
		}
		return nums.pop();
	}
	
	public static int calculate(String oper, int num1, int num2){
		switch(oper){
		case "+":
			return num1+num2;
		case "-":
			return num1-num2;
		case "*":
			return num1*num2;
		case "/":
			return num1/num2;
		case "%":
			return num1%num2;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	private static Set<String> initArithOperSet(){
		Set<String> arithOpers = new HashSet<String>();
		arithOpers.add("+");
		arithOpers.add("-");
		arithOpers.add("*");
		arithOpers.add("/");
		arithOpers.add("%");
		return arithOpers;
	}
}
