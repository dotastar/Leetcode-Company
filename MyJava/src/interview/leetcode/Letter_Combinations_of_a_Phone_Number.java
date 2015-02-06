package interview.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given a digit string, return all possible letter combinations that the number
 * could represent.
 * 
 * A mapping of digit to letters (just like on the telephone buttons) is given
 * below.
 * 
 * Input:Digit string "23"
 * 
 * Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 * 
 * Note: Although the above answer is in lexicographical order, your answer
 * could be in any order you want.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Letter_Combinations_of_a_Phone_Number {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Combination of different char list
	 */
	private final static String[] combs = { "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };

	public List<String> letterCombinations(String digits) {
		List<String> res = new ArrayList<String>();
		combinations(res, digits, 0, new StringBuilder());
		return res;
	}

	private void combinations(List<String> ans, String digits, int idx, StringBuilder sb) {
		if (idx >= digits.length()) {
			ans.add(sb.toString());
			return;
		}
		int num = digits.charAt(idx) - '0';
		if (num < 0 || num > 9)
			return;
		String comb = combs[num];
		for (int i = 0; i < comb.length(); i++) {
			sb.append(comb.charAt(i));
			combinations(ans, digits, idx + 1, sb);
			sb.deleteCharAt(sb.length() - 1);
		}
	}

	/**
	 * Same solution, second time practice
	 */
	public List<String> letterCombinations2(String digits) {
		Map<Character, String> map = new HashMap<Character, String>();
		map.put('2', "abc");
		map.put('3', "def");
		map.put('4', "ghi");
		map.put('5', "jkl");
		map.put('6', "mno");
		map.put('7', "pqrs");
		map.put('8', "tuv");
		map.put('9', "wxyz");

		List<String> res = new ArrayList<String>();
		char[] comb = new char[digits.length()];
		combine(digits, 0, comb, res, map);
		return res;
	}

	public void combine(String digits, int start, char[] comb,
			List<String> res, Map<Character, String> map) {
		if (start == digits.length()) {
			res.add(new String(comb));
			return;
		}
		char digit = digits.charAt(start);
		String letters = map.get(digit);
		if (letters != null) {
			for (int i = 0; i < letters.length(); i++) {
				comb[start] = letters.charAt(i);
				combine(digits, start + 1, comb, res, map);
			}
		}
	}
}
