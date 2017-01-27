package interview.company.epic;

/**
 * Generate all possible unique 4 digit numbers such that no two adjacent
 * numbers are the same and any number starting with 4 should end with a 4 . eg
 * 1234 , 1232 are both correct but 1223 is not .
 * 
 * @author yazhoucao
 * 
 */
public class GenerateNumbers {

	public static void main(String[] args) {
		GenerateNumbers o = new GenerateNumbers();
		o.generate();
	}

	/**
	 * Brute force: check every number between 1000 ~ 9999
	 */
	public void generate() {
		for (int currentNumber = 1000; currentNumber <= 9999; currentNumber++) {
			if (satisfyConstraint(currentNumber))
				System.out.println(currentNumber);
		}
	}

	private boolean satisfyConstraint(int number) {
		int[] digits = new int[4];
		for (int i = 0; i < 4; i++) {
			digits[i] = number % 10;
			number /= 10;
			if (i != 0 && digits[i] == digits[i - 1])
				return false;
		}

		if (digits[3] == 4)
			return digits[0] == 4;
		else
			return true;
	}
}
