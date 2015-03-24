package interview.laicode;

import java.util.Random;

/**
 * Random1000 Using Random5
 * Fair
 * Probability
 * 
 * Given a random generator random5(), the return value of random5() is 0 - 4
 * with equal probability. Use random5() to implement random1000()
 * 
 * @author yazhoucao
 * 
 */
public class Random1000_Using_Random5 {

	public static void main(String[] args) {
		Random1000_Using_Random5 o = new Random1000_Using_Random5();
		for (int i = 0; i < 1000; i++) {
			System.out.println(o.random1000() + " ");
		}
	}

	public int random1000() {
		int res = 1000;
		while (res >= 1000) {
			res = 0;
			for (int i = 0; i < 10; i++) {
				res |= (random2() << i);
			}
		}
		return res;
	}

	private int random2() {
		int num = RandomFive.random5();
		while (num == 4)
			num = RandomFive.random5();
		return num % 2;
	}

	public static class RandomFive {
		private static Random ran = new Random();

		public static int random5() {
			return ran.nextInt(5);
		}
	}
}
