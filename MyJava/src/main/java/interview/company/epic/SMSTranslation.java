package interview.company.epic;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import org.junit.Test;

/**
 * SMS Problem
 * 1 - NULL, 2 - ABC, 3 - DEF, 4 - GHI, 5 - JKL, 6 - MON, 7 - PQRS, 8 - TUV, 9 -
 * WXYZ, * - <Space>, # - <Break>
 * We must convert the numbers to text.
 * E.g
 * I/P  O/P
 * 22 - B
 * 23 - AD
 * 223 - BD
 * 22#2 - BA (# breaks the cycle)
 * 3#33 - DE
 * 2222 - 2
 * 2222#2 - 2A
 * 22222 - A (cycle must wrap around)
 * 222222 - B
 * 
 * @author yazhoucao
 * 
 */
public class SMSTranslation {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(SMSTranslation.class);
	}

	public String translate(String in) {
		StringBuilder sb = new StringBuilder();
		String[] keyMap = { "", "", "ABC2", "DEF3", "GHI4", "JKL5", "MON6",
				"PQRS7", "TUV8", "WXYZ9" };

		char curr = in.charAt(0);
		int cnt = 0;
		for (int i = 1; i < in.length(); i++) {
			curr = in.charAt(i);
			char prev = in.charAt(i - 1);
			if (curr != prev) {
				if (prev != '*' && prev != '#') {
					String chars = keyMap[prev - '0'];
					if (chars.length() > 0)
						sb.append(chars.charAt(cnt % chars.length()));
				} else if (prev == '*')
					sb.append(' ');
				cnt = 0;
			} else if (prev == '*')
				sb.append(' ');
			else if (prev != '#')
				cnt++;
		}
		if (curr == '*')
			sb.append(' ');
		else if (curr != '#') {
			String chars = keyMap[curr - '0'];
			if (chars.length() > 0)
				sb.append(chars.charAt(cnt % chars.length()));
		}

		return sb.toString();
	}

	@Test
	public void test1() {
		String res = translate("******");
		assertTrue(res, res.equals("      "));
	}

	@Test
	public void test2() {
		String res = translate("2#22#222#2222#22222#");
		assertTrue(res, res.equals("ABC2A"));
	}

	@Test
	public void test3() {
		String res = translate("2222#2");
		assertTrue(res, res.equals("2A"));
	}

	@Test
	public void test4() {
		String res = translate("2*2*2*2***");
		assertTrue(res, res.equals("A A A A   "));
	}

	@Test
	public void test5() {
		String res = translate("223456677");
		assertTrue(res, res.equals("BDGJOQ"));
	}
}
