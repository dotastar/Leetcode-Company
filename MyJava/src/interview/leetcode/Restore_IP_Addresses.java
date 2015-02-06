package interview.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a string containing only digits, restore it by returning all possible
 * valid IP address combinations.
 * 
 * For example: Given "25525511135",
 * 
 * return ["255.255.11.135", "255.255.111.35"]. (Order does not matter)
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Restore_IP_Addresses {

	public static void main(String[] args) {
		Restore_IP_Addresses o = new Restore_IP_Addresses();
		System.out.println(o.restoreIpAddresses2("172162541").toString());
		System.out.println(o.restoreIpAddresses2("010010").toString());
		// Expected: ["0.10.0.10","0.100.1.0"]

		System.out.println(o.restoreIpAddresses("25525511135").toString());
		// Expected: ["255.255.11.135", "255.255.111.35"]

		System.out.println(o.restoreIpAddresses2("172162541").toString());
	}
	
	/**
	 * Constraints:
	 * 1.two and three digits number can't start with '0'.
	 * 2.three digits number should less than or equals to 255
	 * 
	 * Pruning tips:
	 * 1.use partNo to count the no. of part of ip now is in partitioning,
	 * so the recursion depth can't be deeper than 4;
	 * 2.compare the length of rest of the string with the max/min number of 
	 * chars can partition can also help pruning.
	 */

	/**
	 * NP like problem
	 * 
	 * Recursion + Pruning, the key is how to split the String, solution uses a
	 * similar way like permutation
	 * 
	 * Because an IP only has 4 parts, so the recursion depth can't be more than
	 * 4 which limits the running time increasing.
	 * 
	 */
	public List<String> restoreIpAddresses(String s) {
		List<String> res = new ArrayList<>();
		partition(res, new StringBuilder(), s, 0, 0);
		return res;
	}

	private void partition(List<String> res, StringBuilder sb, String s, int start, int partNo) {
		int rest = s.length() - start;
		// no chars || finish 4 parts || rest are not enough || rest are too many
		if (rest == 0 || partNo == 4 || rest < (4 - partNo) || rest > (4 - partNo) * 3) {
			if (sb.length() > 0 && rest == 0 && partNo == 4)
				res.add(sb.substring(1));
			return;
		}

		int originalLen = sb.length();
		sb.append('.');
		char first = s.charAt(start);
		// i is offset from start, append one by one instead of substring
		for (int i = 0; i < 3 && start + i < s.length(); i++) {
			// two and three digits number can't start with '0'
			if (i > 0 && first == '0')	
				continue;
			// three digits number should less than or equals to 255
			if (i == 2 && (Integer.valueOf(s.substring(start, start + 3)) > 255))
				continue;
			sb.append(s.charAt(start + i));
			partition(res, sb, s, start + i + 1, partNo + 1);
		}
		sb.setLength(originalLen);
	}

	/**
	 * Second time practice
	 * Use char array to avoid do substring, more lightweight, but the logic is too complicate
	 */
	public List<String> restoreIpAddresses2(String s) {
		List<String> res = new ArrayList<>();
		partition(res, new StringBuilder(), s.toCharArray(), 0, 0);
		return res;
	}

	private void partition(List<String> res, StringBuilder sb, char[] s, int start, int partNo) {
		int rest = s.length - start;
		// used all the chars || finish 4 parts || rest chars are not enough ||
		// rest chars are too many
		if (s.length == start || partNo == 4 || rest < (4 - partNo)
				|| rest > (4 - partNo) * 3) {
			if (sb.length() > 0 && partNo == 4 && s.length == start)
				res.add(sb.substring(1));
			return;
		}

		int originalLen = sb.length();
		sb.append('.');
		char first = s[start];
		for (int i = 0; i < 3 && start + i < s.length; i++) {
			if (i == 0 || (first != '0' && (i == 1 || first == '1' || (first == '2' && (s[start + 1] < '5' || (s[start + 1] == '5' && s[start + 2] <= '5')))))) {
				sb.append(s[start + i]);
				partition(res, sb, s, start + 1 + i, partNo + 1);
			}
		}
		sb.setLength(originalLen);
	}

	/**
	 * For test
	 */
	public boolean lastValid(StringBuilder sb) {
		StringBuilder s = new StringBuilder();
		int i = sb.length() - 1;
		if (sb.charAt(sb.length() - 1) == '.')
			i--;
		int count = 0;
		while (count < 3 && i >= 0) {
			char c = sb.charAt(i);
			if (c == '.')
				break;
			s.append(c);
			count++;
			i--;
		}
		if (s.length() > 1 && s.charAt(s.length() - 1) == '0')
			return false;
		int value = Integer.valueOf(s.reverse().toString());
		return value <= 255 && value >= 0;
	}

}
