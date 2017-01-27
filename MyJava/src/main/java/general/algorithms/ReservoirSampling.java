package general.algorithms;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

/**
 * Reservoir sampling
 * 
 * http://en.wikipedia.org/wiki/Reservoir_sampling
 * 
 * @author yazhoucao
 *
 */
public class ReservoirSampling {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(ReservoirSampling.class);
	}

	/**
	 * Only need O(n) time to scan through one data set to do sampling.
	 * 
	 * It can be shown that when the algorithm has finished executing, each item
	 * in S (dataset) has equal probability (i.e. k/length(S)) of being chosen
	 * for the reservoir.
	 * 
	 * Time: O(n), Space: O(k)
	 * 
	 * @author yazhoucao
	 *
	 */
	public static class Reservoir {
		private int k;
		private int size = 0; // how many data have been read
		private Random ran;
		private int[] samples;

		public Reservoir(int k) {
			this.k = k;
			size = 0;
			ran = new Random();
			samples = new int[k];
		}

		/**
		 * Read current data in.
		 * 
		 * For all values[i], the ith element of S is chosen to be included in
		 * the reservoir with probability k/i (or k/size).
		 * 
		 * At each iteration the jth element of the reservoir array is chosen to
		 * be replaced with probability 1/k * k/i, which simplifies to 1/i.
		 * 
		 * Proof of every element has equal probability to be chose by
		 * induction:
		 * After the (i-1)th round:
		 * The probability of a number being in the reservoir array is k/(i-1).
		 * The probability of the number being replaced in the ith round is 1/i,
		 * the probability that it survives the ith round is (i-1)/i.
		 * 
		 * Thus, the probability for a given number is in the reservoir after
		 * the ith round is (k/(i-1)) * ((i-1)/i)=k/i.
		 * 
		 * 
		 */
		public void read(int value) {
			if (size < k)
				samples[size] = value;
			else { // replace elements with gradually decreasing probability
				int j = ran.nextInt(size + 1); // range: [0, size]
				if (j < k)
					samples[j] = value;
			}

			size++;
		}

		public int[] sample() {
			return Arrays.copyOf(samples, k);
		}
	}

	@Test
	public void test1() {
		// TODO Write input, result, answer
		assertTrue(true);
	}
}
