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
 * 2.LinkedList is better, it is rarely need for random access
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
	private static final int INIT_CAPACITY = 31;

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
	
	private int hash(K k){
		return (k.hashCode() & 0x7fffffff) % capacity;
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
		int idx = hash(k);
		for (Pair<K, V> pair : kvs[idx]){
			if (pair.k.equals(k))
				return pair.v;

		}
		return null;
	}

	public void put(K k, V v) {
		if (v == null) {
			delete(k);
			return;
		}
		if (size >= 10 * capacity)
			resize(2 * capacity);

		int idx = hash(k);
		
		kvs[idx].add(new Pair<K, V>(k, v));
		size++;
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
		int idx = hash(k);
		List<Pair<K, V>> pairs = kvs[idx];
		for (int i = 0; i < pairs.size(); i++) {
			if (pairs.get(i).k.equals(k)) {
				pairs.remove(i);
				size--;
				break;
			}
		}

		if (capacity > INIT_CAPACITY && size <= 2 * capacity)
			resize(capacity / 2);
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
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