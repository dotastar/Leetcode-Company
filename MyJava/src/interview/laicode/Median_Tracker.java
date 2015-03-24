package interview.laicode;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * 
 * Median Tracker
 * Fair
 * Data Structure
 * 
 * Given an unlimited flow of numbers, keep track of the median of all elements
 * seen so far.
 * 
 * You will have to implement the following two methods for the class
 * 
 * read(int value) - read one value from the flow
 * median() - return the median at any time, return null if there is no value
 * read so far
 * 
 * Examples
 * 
 * read(1), median is 1
 * read(2), median is 1.5
 * read(3), median is 2
 * read(4), median is 2.5
 * ......
 * 
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Median_Tracker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static class Solution {
		private final static int DEFAULT_SIZE = 16;
		private PriorityQueue<Integer> minHalf;
		private PriorityQueue<Integer> maxHalf;

		public Solution() {
			minHalf = new PriorityQueue<Integer>(DEFAULT_SIZE,
					Collections.reverseOrder()); // max-heap
			maxHalf = new PriorityQueue<Integer>(DEFAULT_SIZE); // min-heap
		}

		/**
		 * Two cases:
		 * 1.maxHalf.size == minHalf.size, push max(minHalf.peek(), value) into
		 * maxHalf
		 * 2.maxHalf.size > minHalf.size, push max(maxHalf.peek(), value) into
		 * minHalf
		 * 
		 **/
		public void read(int value) {
			if (maxHalf.size() == minHalf.size()) {
				if (minHalf.size() > 0 && minHalf.peek() > value) {
					minHalf.offer(value);
					value = minHalf.poll();
				}
				maxHalf.offer(value);
			} else {
				if (maxHalf.peek() < value) {
					maxHalf.offer(value);
					value = maxHalf.poll();
				}
				minHalf.offer(value);
			}
		}

		public Double median() {
			if (maxHalf.size() == 0)
				return null;
			else if (maxHalf.size() == minHalf.size())
				return (maxHalf.peek() + minHalf.peek()) / (double) 2;
			else
				return maxHalf.peek().doubleValue();
		}
	}
}
