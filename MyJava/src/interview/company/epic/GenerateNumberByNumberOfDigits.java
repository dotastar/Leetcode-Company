package interview.company.epic;

import java.util.ArrayList;
import java.util.List;

/**
 * Given n. Generate all numbers with number of digits equal to n, such that the
 * digit to the right is greater than the left digit (ai+1 > ai). E.g. if n=3
 * (123,124,125,……129,234,…..789)
 * 
 * @author yazhoucao
 * 
 */
public class GenerateNumberByNumberOfDigits {

	public static void main(String[] args) {
		GenerateNumberByNumberOfDigits o = new GenerateNumberByNumberOfDigits();
		System.out.println(o.generateNumbs(3).toString());
	}

	/**
	 * Combination, 9 choose 3
	 */
	public List<Integer> generateNumbs(int n) {
		List<Integer> res = new ArrayList<>();
		generate(n, 0, res);
		return res;
	}

	private void generate(int n, int num, List<Integer> res) {
		if (n == 0) {
			res.add(num);
			return;
		}
		int start = num % 10 + 1;
		for (int i = start; i < 10; i++)
			generate(n - 1, num * 10 + i, res);
	}
}
