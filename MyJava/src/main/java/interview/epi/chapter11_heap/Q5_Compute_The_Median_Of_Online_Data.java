package interview.epi.chapter11_heap;

import interview.AutoTestUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

import org.junit.Test;

/**
 * Problem 11.5
 * You want to compute the running median of a sequence of numbers. The sequence
 * is presented to you in a streaming fashion -- you cannot back up to read an
 * earlier value, and you need to output the median after reading in each new
 * element.
 * 
 * Design an algorithm for computing the running median of a sequence. The time
 * complexity should be O(lgn) per element read in, where n is the number of
 * values read in up to that element.
 * 
 * @author yazhoucao
 * 
 */
public class Q5_Compute_The_Median_Of_Online_Data {

	static Class<?> c = Q5_Compute_The_Median_Of_Online_Data.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * PriorityQueue of Java of default is a min-heap, max-heap need a reverseOrder
	 * @param sequence
	 */
	public static void onlineMedian(InputStream sequence) {
		// minHeap stores the larger half seen so far.
		PriorityQueue<Integer> min = new PriorityQueue<>();
		// maxHeap stores the smaller half seen so far.
		PriorityQueue<Integer> max = new PriorityQueue<>(Collections.reverseOrder());

		Scanner s = new Scanner(sequence);
		while (s.hasNextInt()) {
			Integer num = s.nextInt();
			if (min.isEmpty() || num >= min.peek()) {
				min.add(num);
			} else
				max.add(num);

			if (min.size() > max.size() + 1)
				max.add(min.poll());
			if (max.size() > min.size())
				min.add(max.poll());

			int median = (max.size() == min.size()) ? (min.peek() + max.peek()) / 2 : min.peek();
			System.out.println(median + " ");
		}
		s.close();
	}

	@Test
	public void test0() {
		Random r = new Random();
		int num = r.nextInt(1000) + 1;
		List<Integer> stream = new ArrayList<>();
		for (int i = 0; i < num; ++i) {
			stream.add(r.nextInt(10000) + 1);
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(baos);
		try {
			for (Integer aStream : stream) {
				osw.write(aStream + " ");
			}
			osw.close();
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		onlineMedian(bais);
	}
}
