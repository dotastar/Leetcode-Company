package interview.company.epic;

import static org.junit.Assert.*;

import org.junit.Test;

import interview.AutoTestUtils;

/**
 * Let the user enter a decimal number. The range allowed is 0.0001 to 0.9999.
 * Only four decimal places are allowed. The output should be an irreducible
 * fraction.
 * E.g: If the user enters 0.35, the irreducible fraction will be 7/20.
 * 
 * @author yazhoucao
 * 
 */
public class IrreducibleFraction {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(IrreducibleFraction.class);
	}

	/**
	 * Basically it's calculate the greatest common divisor
	 */
	public String getIrreducibleFraction(double num) {
		int n = (int) (num * 10000);
		int d = 10000;
		int gcd = getGCD(n, d);
		return (n / gcd) + "/" + (d / gcd);
	}

	private int getGCD(int a, int b) {
		if (a == 0)
			return b;
		while (a != b) {
			if (a > b)
				a -= b;
			else
				b -= a; // otherwise b > a
		}
		return a;
	}

	@Test
	public void test1() {
		assertTrue(getIrreducibleFraction(0.35).equals("7/20"));
	}

	@Test
	public void test2() {
		assertTrue(getIrreducibleFraction(0.125).equals("1/8"));
	}

	@Test
	public void test3() {
		assertTrue(getIrreducibleFraction(0.0125).equals("1/80"));
	}

	@Test
	public void test4() {
		assertTrue(getIrreducibleFraction(0.0001).equals("1/10000"));
	}

}
