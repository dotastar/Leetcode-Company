package interview.company.others;
import java.rmi.UnexpectedException;
import java.util.Stack;

/**
 * Input an arithmetic expression with ONLY +, -, * and /, parse the expression
 * and calculate the result.
 * Input: 5.6 / 0.7 * 2 – 3.5
 * Output: 12.5
 * Input: -1.3 + 5.1 / 3 – 0.8
 * Output: -0.4
 * 
 * @author yazhoucao
 * 
 */
public class Parse_Arithmetic_Expression {

	public static final void main(String[] args) {
		if(args.length>0)
			System.out.println(solution(args[0]));
//		System.out.println((int) '-'); // different notation of same subtraction
//		System.out.println((int) '–');
//		System.out.println(solution("-1.3 + 5.1 / 3 – 0.8"));		//0.4
//		System.out.println(solution("5.6 / 0.7 * 2 – 3.5"));		//12.5
//		System.out.println(solution("5.6 / 0.7 * 2 * 3.5 / 112"));	//0.5
//		System.out.println(solution("1.0"));						//1
//		System.out.println(solution("-5.6 / -0.7 * -2 * -3.5"));	//56
	}

	/**
	 * Time: O(n), Space: O(n),  n = expr.length().
	 */
	public static double solution(String expr) {
		String[] tokens = expr.trim().split(" ");
		Stack<Double> numbs = new Stack<Double>();
		Stack<Character> ops = new Stack<Character>();
		// parsing and calculating multiplication and division
		for (String token : tokens) {
			if (token.length() == 0)
				continue;
			if (isOp(token)) {
				ops.push(token.charAt(0));
			} else {
				assert isNumber(token);
				numbs.push(Double.valueOf(token));
				if (!ops.isEmpty() && (ops.peek() == '*' || ops.peek() == '/')) {
					calculate(numbs, ops);
				}
			}
		}

		// calculating add and sub
		while (!ops.isEmpty() && !numbs.isEmpty()) {
			assert numbs.size() >= 2;
			calculate(numbs, ops);
		}
		assert numbs.size() == 1;
		return numbs.isEmpty() ? 0 : numbs.pop();
	}

	/**
	 * Calculate the result based on the two stacks
	 */
	private static void calculate(Stack<Double> numbs, Stack<Character> ops) {
		double res = 0;
		try {
			if (ops.size() == 0 || numbs.size() < 2)
				throw new UnexpectedException(
						"Parsing Error, Stack size is wrong: ops.size-"
								+ ops.size() + " numbs.size-" + numbs.size());
			double b = numbs.pop();
			double a = numbs.pop();
			res = op(a, b, ops.pop());
		} catch (UnexpectedException e) {
			e.printStackTrace();
		}
		numbs.push(res);
	}

	/**
	 * Given specific operation, two operands, return the calculation result
	 */
	private static double op(double a, double b, char op)
			throws UnexpectedException {
		switch (op) {
		case '*':
			return a * b;
		case '/':
			if (b == 0)
				return 0;
			else
				return a / b;
		case '+':
			return a + b;
		case '-':
			return a - b;
		case '–':
			return a - b;
		default:
			throw new UnexpectedException("Operation Unexpected: " + op);
		}
	}

	private static boolean isNumber(String s) {
		int countDot = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (i == 0 && c == '-')
				continue;
			else if (c == '.' && countDot <= 1) {
				countDot++;
				continue;
			}

			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

	private static boolean isOp(String s) {
		if (s.length() != 1)
			return false;
		char op = s.charAt(0);
		return op == '*' || op == '/' || op == '+' || op == '-' || op == '–';
	}

}
