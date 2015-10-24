package interview.epi.chapter11_heap;

import interview.AutoTestUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.Test;

/**
 * Problem 11.3
 * The input consists of a very long sequence of numbers. Each number is at most
 * k positions away from its correctly sorted position. Design an algorithm that
 * outputs the numbers in the correct order and uses O(k) storage, independent
 * of the number of elements processed.
 * 
 * @author yazhoucao
 * 
 */
public class Q3_Sort_An_Almost_Sorted_Array {

	static Class<?> c = Q3_Sort_An_Almost_Sorted_Array.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Classic heap sort, Time: O(nlg(k)), Space: O(k).
	 * Why this is work? Because it says each number is at most k positions away
	 * from its correctly sorted position.
	 * So if we sort the 0th-kth elements within a min heap, then the first
	 * element is guaranteed to be the smallest (in the right position).
	 */
	public static void sortApproximatelySortedArray(InputStream sequence, int k) {
		PriorityQueue<Integer> heap = new PriorityQueue<>();
		try (ObjectInputStream oin = new ObjectInputStream(sequence)) {
			while (true) {
				heap.add((Integer) oin.readObject());
				if (heap.size() > k)
					System.out.print(heap.poll() + " ");
			}
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		while (!heap.isEmpty()) { // print the rest
			System.out.print(heap.poll() + " ");
		}
	}

	/****************** Unit Test ******************/

	@Test
	public void test1() { // simpleTest
		List<Integer> A = Arrays.asList(2, 1, 5, 4, 3, 9, 8, 7, 6);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			for (Integer a : A) {
				oos.writeObject(a);
			}
			ByteArrayInputStream sequence = new ByteArrayInputStream(
					baos.toByteArray());
			sortApproximatelySortedArray(sequence, 3);
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
	}

}
