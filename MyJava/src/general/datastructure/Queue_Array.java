package general.datastructure;

public class Queue_Array<T> implements IQueue<T> {
	private T[] arr;
	private int head, tail; // tail is not the last stored element,
							// is the next available slot (last+1).

	@SuppressWarnings("unchecked")
	public Queue_Array(int size) {
		arr = (T[]) new Object[size];
		head = 0;
		tail = 0;
	}

	public void offer(T t) {
		if (size() >= arr.length - 2)
			resize(arr.length * 2);
		arr[tail] = t;
		tail = tail == arr.length - 1 ? 0 : tail + 1;
	}

	public T poll() {
		if (size() < arr.length / 4)
			resize(arr.length / 2);
		T res = arr[head];
		arr[head] = null;
		head = head == arr.length - 1 ? 0 : head + 1;
		return res;
	}

	public T peek() {
		return arr[head];
	}

	public int size() {
		return tail >= head ? tail - head : arr.length - head + tail;
	}

	private void resize(int newsize) {
		assert size() <= newsize;
		@SuppressWarnings("unchecked")
		T[] newarr = (T[]) new Object[newsize];
		if (head <= tail) {
			for (int i = 0, h = head; h <= tail; i++, h++)
				newarr[i] = arr[h];
		} else {
			int i = 0;
			for (int h = head; h < arr.length; h++, i++)
				newarr[i] = arr[h];
			for (int h = 0; h <= tail; h++, i++)
				newarr[i] = arr[h];
		}
		tail = size();
		head = 0;
		arr = newarr;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}
}
