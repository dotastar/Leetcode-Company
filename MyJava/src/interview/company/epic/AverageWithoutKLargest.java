package interview.company.epic;

import java.util.PriorityQueue;

/**
 * Write a program to display the average for the list of numbers by omitting
 * the three largest number in the series.
 * 
 * E.g:3,6,12,55,289,600,534,900,172. avg=(3+6+12+55+289+172)/6 and eliminating
 * 534,900,600
 * 
 * @author yazhoucao
 * 
 */
public class AverageWithoutKLargest {

	public static void main(String[] args) {
		AverageWithoutKLargest o = new AverageWithoutKLargest();
		int[] A = { 3, 6, 12, 55, 289, 600, 534, 900, 172 };
		System.out.println(o.average(A, 3));
	}

	/**
	 * Time: O(nlgk), n = A.length, k = 3 in this case
	 */
	public int average(int[] A, int k) {
		PriorityQueue<Integer> minHeap = new PriorityQueue<>();
		int sum = 0;
		for (int a : A) {
			sum += a;
			if (minHeap.size() > k && a > minHeap.peek())
				minHeap.poll();
			minHeap.add(a);
		}

		while (!minHeap.isEmpty())
			sum -= minHeap.poll();

		return sum / (A.length - 3);
	}
}
