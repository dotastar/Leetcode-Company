package interview.laicode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * Generalized Reservoir Sampling
 * Fair
 * Probability
 * 
 * Consider an unlimited flow of data elements. How do you sample k element from
 * this flow, such that at any point during the processing of the flow, you can
 * return a random set of k elements from the n elements read so far.
 * 
 * Assumptions
 * 
 * k >= 1
 * 
 * You will implement two methods for a sampling class:
 * 
 * read(int value) - read one number from the flow
 * sample() - return at any time the k samples as a list, return the list of all
 * values read when the number of values read so fas <= k.
 * 
 * You may need to add more fields for the class.
 * 
 * @author yazhoucao
 * 
 */
public class Generalized_Reservoir_Sampling {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static class Solution {
		private int k;
		private int size = 0;
		private Random ran;
		private List<Integer> samples;

		public Solution(int k) {
			this.k = k;
			size = 0;
			ran = new Random();
			samples = new ArrayList<>(k);
		}

		public void read(int value) {
			if (samples.size() < k) {
				samples.add(value);
			} else {
				for (int i = 0; i < samples.size(); i++) {
					int ranNum = ran.nextInt(size + 1);
					if (ranNum == size)
						samples.set(i, value);
				}
			}
			size++;
		}

		public List<Integer> sample() {
			return samples;
		}
	}
}
