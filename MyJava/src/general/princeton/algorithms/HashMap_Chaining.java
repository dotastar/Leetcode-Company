package general.princeton.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Separate chaining hash table implementation,
 * also called open hashing/closed addressing
 * 
 * Notice:
 * 1.put(K, V) should consider resizing, and the case v is null
 * 2.LinkedList is better, it has rare need for random access
 * 3.K, V can be grouped together.
 * 4.Iterable<K> keys() is relatively more expensive(linear)
 * 
 * @author yazhoucao
 * 
 */
public class HashMap_Chaining<K, V> implements SymbolTable<K, V> {

	// largest prime <= 2^i for i = 3 to 31
	// not currently used for doubling and shrinking
	// private static final int[] PRIMES = {
	// 7, 13, 31, 61, 127, 251, 509, 1021, 2039, 4093, 8191, 16381,
	// 32749, 65521, 131071, 262139, 524287, 1048573, 2097143, 4194301,
	// 8388593, 16777213, 33554393, 67108859, 134217689, 268435399,
	// 536870909, 1073741789, 2147483647
	// };
	public static final int INIT_CAPACITY = 7;
	public static final int RESIZE_FACTOR = 10;

	private int size; // number of elements in the table
	private int capacity;// hash table size
	private List<Pair<K, V>>[] kvs;

	public HashMap_Chaining() {
		this(INIT_CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public HashMap_Chaining(int capacity) {
		this.capacity = capacity;
		size = 0;
		kvs = new List[capacity];
		for (int i = 0; i < capacity; i++)
			kvs[i] = new LinkedList<Pair<K, V>>();
	}

	/**
	 * Generate hash index by the key
	 * k must be not null.
	 */
	private int hash(K k) {
		return (k.hashCode() & 0x7fffffff) % capacity;
	}

	/**
	 * Get key-value pair by hash index and the key
	 * k must be not null.
	 */
	private Pair<K, V> getKV(int idx, K k) {
		for (Pair<K, V> pair : kvs[idx]) {
			if (pair.k.equals(k))
				return pair;

		}
		return null;
	}

	private void resize(int newsize) {
		HashMap_Chaining<K, V> tmp = new HashMap_Chaining<K, V>(newsize);
		for (int i = 0; i < kvs.length; i++) {
			List<Pair<K, V>> pairs = kvs[i];
			for (Pair<K, V> p : pairs)
				tmp.put(p.k, p.v);
		}

		this.size = tmp.size;
		this.capacity = tmp.capacity;
		this.kvs = tmp.kvs;
	}

	public boolean contains(K k) {
		return get(k) != null;
	}

	public V get(K k) {
		if (k == null)
			return null;
		int idx = hash(k);
		Pair<K, V> p = getKV(idx, k);
		return p == null ? null : p.v;
	}

	public void put(K k, V v) {
		if (k == null)
			return;
		if (v == null) {
			delete(k);
			return;
		}
		// double table size if average length of list >= RESIZE_FACTOR
		if (size >= RESIZE_FACTOR * capacity)
			resize(2 * capacity);

		int idx = hash(k);
		Pair<K, V> p = getKV(idx, k);
		if (p == null) { // insert
			kvs[idx].add(new Pair<K, V>(k, v));
			size++;
		} else
			p.v = v; // update
	}

	public Iterable<K> keys() {
		List<K> q = new ArrayList<K>(size);
		for (int i = 0; i < capacity; i++) {
			List<Pair<K, V>> pairs = kvs[i];
			for (Pair<K, V> pair : pairs)
				q.add(pair.k);
		}
		return q;
	}

	public void delete(K k) {
		if (k == null)
			return;

		int idx = hash(k);
		Pair<K, V> p = getKV(idx, k);
		if (p != null) {
			List<Pair<K, V>> pairs = kvs[idx];
			pairs.remove(p);
			size--;
		}

		// halve table size if average length of list <= 2
		if (capacity > INIT_CAPACITY && size / 2 <= capacity)
			resize(capacity / 2);
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public int capacity() {
		return capacity;
	}

	private static class Pair<K, V> {
		K k;
		V v;

		public Pair(K k, V v) {
			this.k = k;
			this.v = v;
		}
	}
}