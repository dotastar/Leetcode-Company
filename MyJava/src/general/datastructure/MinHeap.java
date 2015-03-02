package general.datastructure;

import java.util.Comparator;

public class MinHeap<T extends Comparable<T>> extends BinaryHeap<T> {
	
	public MinHeap(int capacity) {
		super(capacity, new Comparator<T>() {
			// o1 < o2 --> compare() > 0
			public int compare(T o1, T o2) {
				if(o1==null || o2==null)
					return 0;
				return o2.compareTo(o1);
			}
		});
	}

	public T min() {
		return poll();
	}
}
