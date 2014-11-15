package general.princeton.algorithms;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Super class of Max/Min Heap.
 * Array representation, dynamic size.
 * 
 * Notice:
 * 1.swim() - move up and sink() - move down is the key.
 * 2.carefully deal with the boundary case of swim() and sink().
 * 3.use comparator to implement both MaxHeap and MinHeap, comparator is the
 * only difference between them.
 * 
 * @author yazhoucao
 * 
 * @param <T>
 */
public class BinaryHeap<T extends Comparable<T>> {
	private T[] pq; // priority queue
	private Comparator<T> cmp;
	private int size;
	private int capacity;

	@SuppressWarnings("unchecked")
	public BinaryHeap(int capct, Comparator<T> cmp) {
		this.capacity = capct < 1 ? 1 : capct;
		this.cmp = cmp;
		// need n+1 size, cause first entry will not be used
		pq = (T[]) new Comparable[capacity + 1];
	}

	private void swap(int i, int j) {
		T tmp = pq[i];
		pq[i] = pq[j];
		pq[j] = tmp;
	}

	private int compare(int i, int j) {
		return cmp.compare(pq[i], pq[j]);
	}

	/**
	 * Move up to root from the bottom node pq[k], balance the heap-order
	 */
	private void swim(int k) {
		int parent = k / 2;
		// compare>0 if i>parent(by default) --> MaxHeap
		// compare>0 if i<parent --> MinHeap
		while (k > 1 && compare(k, parent) > 0) {
			swap(k, parent);
			k = parent;
			parent /= 2;
		}
	}

	/**
	 * Move down to bottom from the upper node pq[k], balance the head-order
	 */
	private void sink(int k) {
		int son = 2 * k;
		while (son <= size) {
			if (son != size && compare(son + 1, son) > 0)
				son++; // select the greater/less son. (greater is MaxHeap)
			if (compare(son, k) <= 0)
				break; // selected son is less/greater than k.(less is MaxHeap)

			swap(k, son); // swap(move up) node son with node k
			k = son;
			son *= 2;
		}
	}

	private void resize(int newcapacity) {
		BinaryHeap<T> newheap = new BinaryHeap<T>(newcapacity, cmp);
		for (int i = 1; i < pq.length; i++)
			newheap.pq[i] = pq[i];
		this.capacity = newheap.capacity;
		this.pq = newheap.pq;
	}

	public void insert(T elem) {
		if (elem == null)
			return;
		if (size == capacity)
			resize(2 * capacity);

		pq[++size] = elem;
		swim(size); // balance the order from the end (bottom)
		assert checkHeapOrder();
	}

	public T pop() {
		if (isEmpty())
			throw new NoSuchElementException("Priority queue underflow");
		T res = pq[1];
		swap(1, size);
		pq[size--] = null;
		sink(1); // balance the order from root
		assert checkHeapOrder();
		return res;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public T peek() {
		if (isEmpty())
			throw new NoSuchElementException("Priority queue underflow");
		return pq[1];
	}

	public int capacity() {
		return capacity;
	}

	public int size() {
		return size;
	}

	public Iterator<T> iterator() {
		return new HeapIterator();
	}

	private class HeapIterator implements Iterator<T> {
		BinaryHeap<T> copy;

		public HeapIterator() {
			copy = new BinaryHeap<T>(size, cmp);
			for (int i = 0; i <= size; i++)
				copy.pq[i] = pq[i];
			copy.size = size;
			copy.capacity = size;
			assert checkHeapOrder(copy);
		}

		@Override
		public boolean hasNext() {
			return !copy.isEmpty();
		}

		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return copy.pop();
		}
	}

	/******************* For Debug only *******************/

	private boolean checkHeapOrder(BinaryHeap<T> heap) {
		return heap.checkHeapOrder(1);
	}

	private boolean checkHeapOrder() {
		return checkHeapOrder(1);
	}

	private boolean checkHeapOrder(int k) {
		int son = 2 * k;
		if (son > size)
			return true;
		if (compare(k, son) < 0)
			return false;
		if (son != size && compare(k, son + 1) < 0)
			return false;
		return checkHeapOrder(son) && checkHeapOrder(son + 1);
	}

}
