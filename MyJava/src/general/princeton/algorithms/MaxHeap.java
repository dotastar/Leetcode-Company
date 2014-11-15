package general.princeton.algorithms;

import java.util.Comparator;

public class MaxHeap<T extends Comparable<T>> extends BinaryHeap<T> {

	public MaxHeap(int capacity) {
		super(capacity, new Comparator<T>() {
			// o1 > o2 --> compare() > 0
			public int compare(T o1, T o2) {
				if(o1==null || o2==null)
					return 0;
				return o1.compareTo(o2);
			}
		});
	}

	public T max() {
		return pop();
	}
}
