package interview.company.epic;

import java.util.ArrayList;
import java.util.List;

/**
 * Goldbach's conjecture : Every even integer greater than 2 can be expressed as
 * the sum of two primes.
 * Write a function which takes a number as input, verify if is an even number
 * greater than 2 and also print at least one pair of prime numbers.
 * 
 * @author yazhoucao
 * 
 */
public class TwoPrimesSum {

	public static void main(String[] args) {
		TwoPrimesSum o = new TwoPrimesSum();
		o.twoPrimes(200);
	}

	/**
	 * Calculate all prime numbers given a max bound + two sum
	 */
	public void twoPrimes(int num) {
		if (num < 3 || num % 2 == 1) {
			System.err.println("invalid input!");
			return;
		}
		// calculate all primes below num
		List<Integer> primes = generateAllPrimes(num);
		// two sum
		int l = 0, r = primes.size() - 1;
		while (l < r) {
			int sum = primes.get(l) + primes.get(r);
			if (sum == num) {
				System.out.println(String.format("Prime pair:<%d,%d>", primes.get(l), primes.get(r)));
				l++;
				r--;
			} else if (sum < num)
				l++;
			else
				r--;
		}
	}

	private List<Integer> generateAllPrimes(int maxBound) {
		List<Integer> primes = new ArrayList<>();
		primes.add(2);
		for (int i = 3; i < maxBound; i += 2) {
			boolean isPrime = true;
			for (int p : primes) {
				if (i % p == 0) {
					isPrime = false;
					break;
				}
			}
			if (isPrime)
				primes.add(i);
		}
		return primes;
	}
}
