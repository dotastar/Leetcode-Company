package general.datastructure.test;

import general.datastructure.IHeap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MyHeapTester {
	public static void main(String[] args) {
		Result res = JUnitCore.runClasses(StkTest.class);
		for (Failure f : res.getFailures()) {
			System.err.println(f.toString() + "\n" + f.getTrace());
		}
		System.out.println(String.format("Finish! Total:%d Failed:%d",
				res.getRunCount(), res.getFailureCount()));
	}

	/***************** MinHeap Implementation *****************/
	public static class MyHeapImpl implements IHeap<Integer> {
		private int[] data;
		private int size;

		public MyHeapImpl() {
			data = new int[16];
			size = 0;
		}

		public MyHeapImpl(int[] data) {
			this.data = data;
			size = data.length;
			heapify();
		}

		private void siftUp(int idx) {
			while (idx >= 0) {
				int parentIdx = idx - 1 / 2;
				if (data[parentIdx] > data[idx]) {
					swap(data, idx, parentIdx);
					idx = parentIdx;
				} else
					break;
			}
		}

		private void siftDown(int idx) {
			while (idx < size) {
				int left = 2 * idx + 1;
				int right = 2 * idx + 2;
				int min = idx;
				if (left < size && data[left] < data[min])
					min = left;
				if (right < size && data[right] < data[min])
					min = right;
				swap(data, idx, min);
				idx = min;
			}
		}

		private void heapify() {
			for (int i = size / 2 - 1; i >= 0; i--)
				siftDown(i);
		}

		private void swap(int[] A, int i, int j) {
			int temp = A[i];
			A[i] = A[j];
			A[j] = temp;
		}

		@Override
		public void offer(Integer elem) {
			if (size() == data.length)
				throw new ArrayIndexOutOfBoundsException();

			data[size++] = elem;
			siftUp(size - 1);
		}

		@Override
		public Integer poll() {
			if (isEmpty())
				throw new ArrayIndexOutOfBoundsException();

			swap(data, 0, --size);
			siftDown(0);
			return data[size];
		}

		public int pollAt(int idx) {
			if (idx < 0 || idx >= data.length)
				throw new ArrayIndexOutOfBoundsException();
			int result = data[idx];
			size--;
			update(idx, data[size]);
			return result;
		}

		@Override
		public Integer peek() {
			if (isEmpty())
				throw new ArrayIndexOutOfBoundsException();
			return data[0];
		}

		@Override
		public int update(int idx, Integer value) {
			if (idx < 0 || idx >= data.length)
				throw new ArrayIndexOutOfBoundsException();
			int result = data[idx];
			data[idx] = value;
			// no need for if/else, they are mutually exclusion
			siftUp(idx);
			siftDown(idx);

			return result;
		}

		@Override
		public int size() {
			return size;
		}

		@Override
		public boolean isEmpty() {
			return size() == 0;
		}

	}

	/***************** Test *****************/
	private IHeap<Integer> heap;

	@Before
	public void init() {
		heap = new MyHeapImpl();
	}

	@Test
	public void test1() {
		heap.offer(1);
	}

}
