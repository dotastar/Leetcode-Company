package interview.leetcode;

/**
 * Validate if a given string is numeric.
 * 
 * Some examples:
 * 
 * "0" => true
 * 
 * " 0.1 " => true
 * 
 * "abc" => false
 * 
 * "1 a" => false
 * 
 * "2e10" => true
 * 
 * Note: It is intended for the problem statement to be ambiguous. You should
 * gather all requirements up front before implementing one.
 * 
 * @author yazhoucao
 * 
 */
public class Valid_Number {

	public static void main(String[] args) {

		String regex = "[+-]?([.]?[0-9]+|[0-9]+[.]?)[0-9]*([Ee][+-]?[0-9]+)?";
		assert "0.8".matches(regex) == true;
		assert "".matches(regex) == false;
		assert "e9".matches(regex) == false;
		assert "-9e9".matches(regex) == true;
		assert "-9.e9".matches(regex) == true;
		assert ".1".matches(regex) == true;
		assert "1.".matches(regex) == true;
		assert ".".matches(regex) == false;

		System.out.print(isNumber("  0  ") + "\t"); // true
		System.out.print(isNumber(".1") + "\t"); // true
		System.out.print(isNumber("3.") + "\t"); // true
		System.out.print(isNumber("-20") + "\t"); // true
		System.out.print(isNumber("+0.2") + "\t"); // true
		System.out.print(isNumber("+.8") + "\t"); // true
		System.out.print(isNumber("2e10") + "\t"); // true, 2 * 10^10
		System.out.print(isNumber("2.1235123e10") + "\t"); // true
		System.out.print(isNumber("+1901235121.1") + "\t"); // true
		System.out.println();

		System.out.print(isNumber(" E. ") + "\t"); // false
		System.out.print(isNumber(" .E ") + "\t"); // false
		System.out.print(isNumber(" .E1 ") + "\t"); // false
		System.out.print(isNumber(" 0E ") + "\t"); // false
		System.out.print(isNumber(" E ") + "\t"); // false
		System.out.print(isNumber(" . ") + "\t"); // false
		System.out.print(isNumber(" .1. ") + "\t"); // false
		System.out.print(isNumber(" E. ") + "\t"); // false
		System.out.print(isNumber(" 1 0 ") + "\t"); // false, no space in
													// between
		System.out.print(isNumber("f10z") + "\t"); // false, z is invalid
		System.out.print(isNumber("#3/1.0") + "\t"); // false, other characters
		System.out.print(isNumber("1 0") + "\t"); // false, no space in between
		System.out.print(isNumber("abc") + "\t"); // false, no letters
		System.out.println();

	}

	/**
	 * Use several variables to represent the state of the program,
	 * the variables below are enough to verify whether the current state is
	 * valid or not:
	 * 1.dot, e, sign : '.', 'e', '+'/'-' has appeared or not
	 * 2.digit: how many digits have appeared
	 * 3.prev: the previous character is
	 * 
	 * Key: In order to make program clear to easy, we only consider(verify) the
	 * state of characters before the current character, the characters
	 * after will be left for the next characters to consider.
	 * 
	 * Rules: c = current character
	 * 1.if c == digit: valid, next.
	 * 
	 * 2.if c == dot:
	 * a).it can only appear once, e.g.".12.2"
	 * b).it can't be appear after e has appeared
	 * c).it can be at the beginning or the end only if there exists digits
	 * after or before, i==s.length()-1 && digit>0, e.g.".", valid: "1.", ".1"
	 * 
	 * 3.if c == e:
	 * a).it can only appear once, e.g.13e2e5
	 * b).it can't be the first, i!=start, e.g."e25"
	 * c).it can't be the last, i!= s.length()-1, e.g."25e"
	 * d).it can't be immediate after a invalid '.', prev=='.' && digit==0,
	 * e.g.".e2", (a valid dot should be prefix or suffix more than digits)
	 * 
	 * 4.if c == '+'/'-', e.g.valid: "12e+5"
	 * a).it can only appear once, e.g."12e+2-3"
	 * b).it can only appear after e has appeared and it can't be the end (there
	 * should be digits after it), e.g."12e+"
	 */
	public boolean isNumber2(String s) {
		s = s.trim().toLowerCase();
		if (s.length() == 0)
			return false;
		int start = 0;
		if (s.charAt(0) == '+' || s.charAt(0) == '-') {
			if (s.length() == 1)
				return false;
			start++;
		}

		boolean dot = false, e = false, sign = false;
		int digit = 0, end = s.length() - 1;
		char prev = '\0';
		for (int i = start; i < s.length(); i++) {
			char c = s.charAt(i);
			if (!Character.isDigit(c)) {
				if (c == 'e') {
					if (i == start || e || i == end || (digit == 0 && prev == '.'))
						return false;
					e = true;
				} else if (c == '.') {
					if (dot || e || (i == end && digit == 0))
						return false;
					dot = true;
				} else if (c == '+' || c == '-') {
					if (prev == 'e' && i != end && !sign)
						sign = true;
					else
						return false;
				} else
					return false;
			} else
				digit++;
			prev = c;
		}
		return true;
	}

	/**
	 * Regular expression
	 */
	public static boolean isNumber(String s) {
		s = s.trim().toLowerCase();
		if (s.length() == 0)
			return false;
		String regex = "[+|-]?(\\d+\\.?|\\.\\d+)\\d*(e[+|-]?\\d+)?";
		return s.matches(regex);
	}
}
