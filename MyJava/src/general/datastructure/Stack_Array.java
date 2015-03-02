package general.datastructure;

import java.util.Arrays;

public class Stack_Array<T> implements MyStack<T> {
	private static final int DEFAULT_SIZE = 10;
	private T[] arr;
	private int size;

	@SuppressWarnings("unchecked")
	public Stack_Array() {
		arr = (T[]) new Object[DEFAULT_SIZE];
		size = 0;
	}

	public void push(T t) {
		if (size >= arr.length)
			resize(arr.length * 2);
		arr[size++] = t;
	}

	public T pop() {
		if (size == 0)
			throw new RuntimeException("Can't pop, empty stack!");
		if (size <= arr.length / 4)
			resize(arr.length / 2);
		T res = arr[--size];
		arr[size] = null;
		return res;
	}

	public T peek() {
		if (size == 0)
			throw new RuntimeException("Can't peek, empty stack!");
		return arr[size - 1];
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	private void resize(int newsize) {
		T[] newarr = Arrays.copyOf(arr, newsize);
		;
		arr = newarr;
	}
}
