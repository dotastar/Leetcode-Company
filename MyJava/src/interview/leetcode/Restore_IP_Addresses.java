package interview.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

		System.out.println(restoreIpAddresses("25525511135").toString());
		// Expected: ["255.255.11.135", "255.255.111.35"]

		System.out.println(o.restoreIpAddresses2("172162541").toString());
		System.out.println(o.restoreIpAddresses2("010010").toString());
		// Expected: ["0.10.0.10","0.100.1.0"]
	}

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
	public static List<String> restoreIpAddresses(String s) {
		List<String> res = new ArrayList<String>();
		Stack<String> ip = new Stack<String>();
		cutIP(s, 0, ip, res);
		return res;
	}

	public static void cutIP(String s, int start, Stack<String> ip,
			List<String> res) {
		if (ip.size() == 3) { // leaf
			if (s.length() - start <= 3) {
				String last = s.substring(start);
				// pruning, number like 010 will be ignored
				if (last.charAt(0) != '0' || last.length() <= 1) {
					int val = Integer.valueOf(last);
					if (val <= 255) {
						res.add(ip.get(0) + ip.get(1) + ip.get(2)
								+ Integer.toString(val));
					}
				}
			}
			return;
		}

		for (int i = start + 1; i < s.length(); i++) {
			// pruning, substring length>3 and number like 010 will be ignored,
			// ignore splitting substring begin with '0' can avoid repeated
			// cases
			if (i - start <= 3 && (s.charAt(start) != '0' || i - start == 1)) {
				String part = s.substring(start, i);
				int num = Integer.valueOf(part);
				if (num <= 255) { // pruning
					ip.push(Integer.toString(num) + '.');
					cutIP(s, i, ip, res);
					ip.pop();
				}
			}
		}
	}

	/**
	 * Second time practice
	 */
	public List<String> restoreIpAddresses2(String s) {
		List<String> res = new ArrayList<String>();
		restoreIp(0, new StringBuilder(), s, res);
		return res;
	}

	public void restoreIp(int part, StringBuilder sb, String s, List<String> res) {
		if (part == 4) {
			if (sb.length() - part == s.length()) {
				sb.deleteCharAt(sb.length() - 1);
				res.add(sb.toString());
				sb.append('.');
			}
			return;
		}

		int appendCount = 0;
		int idx = sb.length() - part; // the number of part means the number of
										// '.'
		for (int i = 0; i < 3; i++) {
			if (idx + i >= s.length())
				break;
			char c = s.charAt(idx + i);
			int sblen = sb.length();
			if (i == 1 && sb.charAt(sblen - 1) == '0')
				break;
			if (i == 2) {
				int num = Integer.valueOf(sb.substring(sblen - 2) + c);
				if (num > 255 || sb.charAt(sblen - 2) == '0')
					break;
			}
			appendCount++;
			sb.append(c);
			sb.append('.');
			assert lastValid(sb) : sb.toString();
			restoreIp(part + 1, sb, s, res);
			sb.deleteCharAt(sb.length() - 1); // delete the '.'
		}
		while (appendCount-- > 0)
			sb.deleteCharAt(sb.length() - 1);
	}

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
