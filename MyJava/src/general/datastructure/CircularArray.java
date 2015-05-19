package general.datastructure;

import static org.junit.Assert.assertTrue;
import interview.AutoTestUtils;

import java.util.Arrays;

import org.junit.Test;

/**
 * Design a de-queue with O(1) time complexity for insertions, removals on both
 * ends of the queue and read(i).
 * 
 * http://en.wikipedia.org/wiki/Circular_buffer
 * 
 * [] [] 1 5 2 4 3 8 6 [] []
 * ____ start _____ end
 * 
 * 
 * 1 2 4 [] [] [] [] [] -1 0
 * ^ ^
 * end start
 * 
 * @author yazhoucao
 *
 */
public class CircularArray {
	private final static int INIT_SIZE = 8;

	private int[] arr;
	private int start; // point to the starting element
	private int size; // end element = (start + size - 1) % arr.length

	public CircularArray() {
		arr = new int[INIT_SIZE];
		start = 0;
		size = 0;
	}

	public void insertOnLeft(int ele) {
		if (size == arr.length)
			resize(size * 2);
		start = start == 0 ? arr.length - 1 : start - 1;
		arr[start] = ele;
		size++;
	}

	public void insertOnRight(int ele) {
		if (size == arr.length)
			resize(size * 2);
		size++;
		arr[(start + size - 1) % arr.length] = ele;
	}

	public int read(int index) {
		if (index < 0 || index >= arr.length)
			throw new AssertionError("Index out of bound.");
		return arr[index];
	}

	public void deleteLeft() {
		if (size > 0) {
			arr[start] = 0;
			start = start == arr.length - 1 ? 0 : start + 1;
			size--;
		}
	}

	public void deleteRight() {
		if (size > 0) {
			arr[(start + size - 1) % arr.length] = 0;
			size--;
		}
	}

	public String toString() {
		return Arrays.toString(arr);
	}

	private void resize(int newSize) {
		int[] newarr = new int[newSize];
		System.arraycopy(arr, start, newarr, 0, arr.length - start);
		System.arraycopy(arr, 0, newarr, arr.length - start, start + size - arr.length);
		arr = newarr;
		start = 0;
	}

	/********************** For Test **********************/

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(CircularArray.class);
	}

	@Test
	public void test1() {
		CircularArray ca = new CircularArray();
		ca.insertOnLeft(1);
		ca.insertOnLeft(2);
		ca.insertOnRight(3);
		ca.insertOnRight(4);
		System.out.println(ca.toString());
		assertTrue(ca.read(1) == 4);
		assertTrue(ca.read(7) == 1);
		ca.deleteLeft();
		ca.deleteLeft();
		assertTrue(ca.read(0) == 3);
		assertTrue(ca.read(1) == 4);
		ca.insertOnLeft(5);
		assertTrue(ca.read(7) == 5);
		System.out.println(ca.toString());
	}

	@Test
	public void test2() { // Test resize()
		CircularArray ca = new CircularArray();
		ca.insertOnLeft(1);
		ca.insertOnLeft(2);
		ca.insertOnRight(3);
		ca.insertOnRight(4);
		ca.insertOnLeft(5);
		ca.insertOnLeft(6);
		ca.insertOnLeft(7);
		ca.insertOnLeft(8);
		ca.insertOnRight(9);
		System.out.println(ca.toString());
		assertTrue(ca.read(0) == 8);
		assertTrue(ca.read(8) == 9);
	}

	@Test
	public void test3() { // Test insertOnRight()
		CircularArray ca = new CircularArray();
		for (int i = 1; i <= 9; i++)
			ca.insertOnRight(i);

		System.out.println(ca.toString());
		assertTrue(ca.read(0) == 1);
		assertTrue(ca.read(8) == 9);
	}

	@Test
	public void test4() { // Test insertOnLeft()
		CircularArray ca = new CircularArray();
		for (int i = 1; i <= 9; i++)
			ca.insertOnLeft(i);

		System.out.println(ca.toString());
		assertTrue(ca.read(0) == 8);
		assertTrue(ca.read(15) == 9);
	}
}
