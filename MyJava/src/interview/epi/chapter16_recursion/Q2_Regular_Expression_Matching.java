package interview.epi.chapter16_recursion;

public class Q2_Regular_Expression_Matching {

	public static boolean isMatch(String r, String s) {
		// Case (2.) : starts with '^'.
		if (r.charAt(0) == '^') {
			return isMatchHere(r.substring(1), s);
		}
		for (int i = 0; i <= s.length(); ++i) {
			if (isMatchHere(r, s.substring(i))) {
				return true;
			}
		}
		return false;
	}

	private static boolean isMatchHere(String r, String s) {
		// Case (1.)
		if (r.isEmpty()) {
			return true;
		}
		// Case (2) : ends with '$'.
		if ("$".equals(r)) {
			return s.isEmpty();
		}
		// Case (4.)
		if (r.length() >= 2 && r.charAt(1) == '*') {
			for (int i = 0; i < s.length() && (r.charAt(0) == '.' || r.charAt(0) == s.charAt(i)); ++i) {
				if (isMatchHere(r.substring(2), s.substring(i + 1))) {
					return true;
				}
			}
			return isMatchHere(r.substring(2), s);
		}
		// Case (3.)
		return !s.isEmpty()
				&& (r.charAt(0) == '.' || r.charAt(0) == s.charAt(0))
				&& isMatchHere(r.substring(1), s.substring(1));
	}
}
