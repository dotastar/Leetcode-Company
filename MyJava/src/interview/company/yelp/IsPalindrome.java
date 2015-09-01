package interview.company.yelp;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class IsPalindrome {

	public static void main(String args[]) throws Exception {
		String input = null;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			input = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (input == null)
			System.out.println("No input");
		else
			System.out.println(isPalindrome(input));
	}

	public static boolean isPalindrome(String S) {
		Map<Character, Integer> cmap = new HashMap<Character, Integer>();
		int oddCnt = 0;
		for (int i = 0; i < S.length(); i++) {
			char ci = S.charAt(i);
			Integer cnt = cmap.get(ci);
			cnt = cnt == null ? 1 : cnt + 1;
			cmap.put(ci, cnt);
			oddCnt = cnt % 2 == 1 ? oddCnt + 1 : oddCnt - 1;
		}
		return oddCnt <= 1;
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertEquals(true, true);
	}
}
