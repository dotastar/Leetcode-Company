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
		assert "0.8".matches(regex)==true;
		assert "".matches(regex)==false;
		assert "e9".matches(regex)==false;
		assert "-9e9".matches(regex)==true;
		assert "-9.e9".matches(regex)==true;
		assert ".1".matches(regex)==true;
		assert "1.".matches(regex)==true;
		assert ".".matches(regex)==false;
		
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

	public static boolean isNumber(String s) {
		s = s.trim().toLowerCase();
		if (s.length() == 0)
			return false;
		String regex = "[+|-]?(\\d+\\.?|\\.\\d+)\\d*(e[+|-]?\\d+)?";
		return s.matches(regex);
	}
}
