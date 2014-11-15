package general.princeton.algorithms;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class BinaryHeapTest {

	private String type = "min";

	public static void main(String[] args) {
		Result res = JUnitCore.runClasses(BinaryHeapTest.class);
		for (Failure f : res.getFailures()) {
			System.out.println(f.toString());
			System.out.println(f.getTrace().toString());
		}
		System.out.println("Total Tests: " + res.getRunCount());
		System.out.println("Failures : " + res.getFailureCount());
	}

	/**
	 * Heap factory
	 */
	public <T extends Comparable<T>> BinaryHeap<T> createHeap(String type) {
		int defaultSize = 10;
		return createHeap(type, defaultSize);
	}

	public <T extends Comparable<T>> BinaryHeap<T> createHeap(String type,
			int size) {
		switch (type) {
		case "max":
			return new MaxHeap<T>(size);
		case "min":
			return new MinHeap<T>(size);
		default:
			throw new IllegalArgumentException("Illegal type: " + type);
		}
	}

	@Test
	public void testInsert_Normal_Max_Int() {
		BinaryHeap<Integer> h = createHeap("max");
		h.insert(1);
		assertTrue(h.peek() == 1);
		h.insert(3);
		assertTrue(h.peek() == 3);
		h.insert(0);
		assertTrue(h.peek() == 3);
		h.insert(-1);
		assertTrue(h.peek() == 3);
		h.insert(4);
		assertTrue(h.peek() == 4);
	}

	@Test
	public void testInsert_Normal_Max_Char() {
		BinaryHeap<Character> h = createHeap("max");
		h.insert('o');
		assertTrue(h.peek() == 'o');
		h.insert('p');
		assertTrue(h.peek() == 'p');
		h.insert('q');
		assertTrue(h.peek() == 'q');
		h.insert('b');
		assertTrue(h.peek() == 'q');
		h.insert('x');
		assertTrue(h.peek() == 'x');
	}
	
	@Test
	public void testInsert_Normal_Min_Int() {
		BinaryHeap<Integer> h = createHeap("min");
		h.insert(1);
		assertTrue(h.peek() == 1);
		h.insert(3);
		assertTrue(h.peek() == 1);
		h.insert(0);
		assertTrue(h.peek() == 0);
		h.insert(-1);
		assertTrue(h.peek() == -1);
	}
	

	@Test
	public void testInsert_Normal_Min_Char() {
		BinaryHeap<Character> h = createHeap("min");
		h.insert('o');
		assertTrue(h.peek() == 'o');
		h.insert('p');
		assertTrue(h.peek() == 'o');
		h.insert('q');
		assertTrue(h.peek() == 'o');
		h.insert('b');
		assertTrue(h.peek() == 'b');
		h.insert('x');
		assertTrue(h.peek() == 'b');
	}
	

	@Test
	public void testInsert_Resize() {
		BinaryHeap<Integer> h = createHeap(type, 10);
		for(int i=0; i<10; i++)
			h.insert(i);
		assert h.capacity()==10;
		h.insert(11);
		assert h.capacity()==20;
	}

	@Test
	public void testInsert_Null() {
		BinaryHeap<Character> h = createHeap(type);
		h.insert(null);
	}
	

	@Test(expected=NoSuchElementException.class)
	public void testPop_Empty() {
		BinaryHeap<Integer> h = createHeap(type, 0);
		h.pop();
	}

	@Test
	public void testPop_InsertAndPop_Min() {
		BinaryHeap<Integer> h = createHeap("min", 10);
		for(int i=0; i<10; i++){
			h.insert(i);
			assert h.peek()==0;
		}
		
		for(int i=0; i<10; i++)	
			assertTrue(h.pop()==i);
	}
	
	@Test
	public void testPop_InsertAndPop_Max() {
		BinaryHeap<Integer> h = createHeap("max", 10);
		for(int i=0; i<10; i++){
			h.insert(i);
			assert h.peek()==i;
		}
		
		for(int i=9; i>=0; i--)	
			assertTrue(h.pop()==i);
	}

	@Test(expected=NoSuchElementException.class)
	public void testPeek_Empty() {
		BinaryHeap<Integer> h = createHeap(type, 0);
		h.peek();
	}

	@Test
	public void testPeek_NonEmpty() {
		BinaryHeap<Integer> h = createHeap(type, 5);
		h.insert(1);
		assert (h.peek() == 1);
	}

	@Test
	public void testCapacity_Zero() {
		BinaryHeap<Integer> h = createHeap(type, 0);
		assertTrue(h.capacity() == 1);
	}

	@Test
	public void testCapacity_Negative() {
		BinaryHeap<Integer> h = createHeap(type, -10);
		assertTrue(h.capacity() == 1);
	}

	@Test
	public void testCapacity_Positive() {
		BinaryHeap<Integer> h = createHeap(type, 33);
		assertTrue(h.capacity() == 33);
	}

	@Test
	public void testSize_Add() {
		BinaryHeap<Integer> h = createHeap(type, 10);
		h.insert(1);
		h.insert(3);
		h.insert(0);
		h.insert(-1);
		assertTrue(h.size() == 4);
	}

	@Test
	public void testSize_AddDelete() {
		BinaryHeap<Integer> h = createHeap(type, 10);
		h.insert(1);
		h.insert(3);
		h.insert(0);
		h.insert(-1);
		h.pop();
		assertTrue(h.size() == 3);
		h.pop();
		assertTrue(h.size() == 2);
		h.pop();
		assertTrue(h.size() == 1);
	}

	@Test
	public void testIterator_Min(){
		BinaryHeap<Integer> h = createHeap("min");
		for(int i=0; i<30; i++)
			h.insert(i);
		Iterator<Integer> iter = h.iterator();
		int i=0;
		while(iter.hasNext()){
			Integer val = iter.next();
			assert val!=null;
			assertTrue(val==i++);
		}
			
	}
	
	@Test
	public void testIterator_Max(){
		BinaryHeap<Integer> h = createHeap("max");
		for(int i=0; i<30; i++)
			h.insert(i);
		Iterator<Integer> iter = h.iterator();
		int i=29;
		while(iter.hasNext()){
			Integer val = iter.next();
			assert val!=null;
			assertTrue(val==i--);
		}
			
	}
}
