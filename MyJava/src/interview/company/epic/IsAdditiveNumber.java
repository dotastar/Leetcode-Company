package interview.company.epic;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import org.junit.Test;


/**
 * Additive numbers are defined to be a positive integer whose digits form an
 * additive sequence. E.g. 11235 (1+1=2, 1+2=3, 2+3=5). What makes it difficult
 * is that 12,122,436 is also one (12+12=24, 12+24=36).
 * 
 * Given a range of integers, find all the additive numbers in that range..
 * 
 */
public class IsAdditiveNumber {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(IsAdditiveNumber.class);
	}

	/**
	 * The first two number decided the rest if valid or not, so we just need to
	 * enumerate all the first two numbers.
	 */
	public boolean isAddictiveNumber(String num) {
		for (int len = 1; len <= num.length() / 3; len++) {
			int a = Integer.valueOf(num.substring(0, len));
			int b = Integer.valueOf(num.substring(len, 2 * len));
			int sumLength = numOfDigits(a + b);
			int idx = len * 2;
			boolean valid = idx + sumLength <= num.length();
			while (idx + sumLength <= num.length()) {
				int c = Integer.valueOf(num.substring(idx, idx + sumLength));
				if (c != a + b) {
					valid = false;
					break;
				}
				a = b;
				b = c;
				idx = idx + sumLength;
				sumLength = numOfDigits(a + b);
			}
			if (valid)
				return true;
		}
		return false;
	}

	private int numOfDigits(int n) {
		int cnt = 0;
		while (n != 0) {
			n /= 10;
			cnt++;
		}
		return cnt;
	}

	@Test
	public void test1() {
		String num = "11235";
		assertTrue(isAddictiveNumber(num));
	}

	@Test
	public void test2() {
		String num = "8917";
		assertTrue(isAddictiveNumber(num));
	}

	@Test
	public void test3() {
		String num = "12122436";
		assertTrue(isAddictiveNumber(num));
	}

	@Test
	public void test4() {
		String num = "1189100189";
		assertTrue(isAddictiveNumber(num));
	}

	@Test
	public void test5() {
		String num = "1123581321";
		assertTrue(isAddictiveNumber(num));
	}

	@Test
	public void test6() { // negative
		String num = "112356";
		assertTrue(!isAddictiveNumber(num));
	}
}
