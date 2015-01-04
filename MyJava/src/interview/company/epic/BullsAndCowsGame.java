package interview.company.epic;

/**
 * The cows and bulls game, Player A chooses a word and player B guesses a word.
 * You say bulls when a character in the player B's guess match with a character
 * in player A's word and also it is in the correct position as in A's word. You
 * say cows, when a character in the player B's word match the character in
 * player A, but it is not in the correct position. The characters are case
 * insensitive. Given two words player A's and player B's,Write a function that
 * return the number of bulls and no of cows.
 * For example,
 * A - Picture B- Epic, bulls -0, cows - 4
 * A - forum B - four, bulls - 3 cows - 1
 * 
 * @author yazhoucao
 * 
 */
public class BullsAndCowsGame {

	public static void main(String[] args) {

	}

	public void charMatch(String A, String B) {
		int cows = 0, bulls = 0;
		if (A.length() > B.length()) {
			String temp = B;
			B = A;
			A = temp;
		} // make sure A is shorter than B

		int[] dict = new int[128];
		for (int i = 0; i < A.length(); i++) {
			if (A.charAt(i) == B.charAt(i))
				++bulls;
			++dict[A.charAt(i)];
		}

		for (int i = 0; i < B.length(); i++) {
			if (dict[B.charAt(i)] > 0) {
				--dict[B.charAt(i)];
				cows++;
			}
		}

		System.out.println("Buls:" + bulls + "\tCows:" + cows);
	}

}
