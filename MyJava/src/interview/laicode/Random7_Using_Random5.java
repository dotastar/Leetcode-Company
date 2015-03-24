package interview.laicode;

import java.util.Random;

/**
 * 
 * Random7 Using Random5
 * Fair
 * Probability
 * 
 * Given a random generator random5(), the return value of random5() is 0 - 4
 * with equal probability. Use random5() to implement random7().
 * 
 * @author yazhoucao
 * 
 */
public class Random7_Using_Random5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Thought, expand the random value range by convert it to a 2D problem.
	 * Create 5*5 2D array with values 0 ~ 24
	 * 0 1 2 3 4
	 * 5 6 7 8 9
	 * ...
	 * ...
	 * 
	 * Use random5() once to select row no., second time to select a column no.
	 */
	public int random7() {
		int num = 0;
		do { // create a number within range 0 ~ 21
			num = 5 * RandomFive.random5() + RandomFive.random5();
		} while (num > 21);
		return num % 7;
	}

	public static class RandomFive {
		private static Random ran = new Random();

		public static int random5() {
			return ran.nextInt(5);
		}
	}
}
