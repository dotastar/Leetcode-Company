package general.datastructure;

import java.util.Arrays;

/**
 * Linear probing hash table implementation,
 * also called closed hashing/open addressing
 * 
 * 1.put can insert, update, delete
 * 2.put, delete should consider resize
 * 3.be careful about the LOAD_FACTOR, shouldn't be greater than 1
 * 4.It's prone to enter infinite loop state in findKeyIndex() function,
 * be careful to deal with the capacity, resizing logic, or add a count to
 * detect such state and throw an exception.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class HashMap_Probing<K, V> implements SymbolTable<K, V> {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private static final int INIT_CAPACITY = 7;
	private static final float LOAD_FACTOR = 0.75f;

	private int size;
	private int capacity;

	private K[] keys;
	private V[] values;

	public HashMap_Probing() {
		this(INIT_CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public HashMap_Probing(int capacity) {
		this.capacity = capacity;
		size = 0;
		keys = (K[]) new Object[capacity];
		values = (V[]) new Object[capacity];
	}

	private void resize(int capacity) {
		HashMap_Probing<K, V> tmp = new HashMap_Probing<K, V>(capacity);
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] != null)
				tmp.put(keys[i], values[i]);
		}

		this.capacity = tmp.capacity;
		keys = tmp.keys;
		values = tmp.values;
	}

	private int hash(K k) {
		return (k.hashCode() & 0x7fffffff) % capacity;
	}

	/**
	 * Keep looping the array until find the same key or an empty spot.
	 */
	private int findKeyIndex(K k) {
		int idx = hash(k);
		int cnt = 0;
		while (keys[idx] != null && !keys[idx].equals(k)){
			idx = idx == capacity - 1 ? 0 : idx + 1;
			if( ++cnt > capacity)
				throw new AssertionError("Error! Enter inifinite loop.");
		}
		return idx;
	}

	private void deleteByIdx(int idx) {
		if (keys[idx] != null) {
			keys[idx] = null;
			values[idx] = null;
			size--;
		}
	}

	/**
	 * Integrity check, don't check after each put() because integrity not
	 * maintained during a delete()
	 */
	private boolean check() {
		if (size / capacity > LOAD_FACTOR) {
			System.err.println("Hash table size exceeded.\n size:" + size
					+ " capacity:" + capacity + "> LOAD_FACTOR(" + LOAD_FACTOR
					+ ")");
			return false;
		}

		// check that each key in table can be found by get()
		for (int i = 0; i < capacity; i++) {
			if (keys[i] != null) {
				K k = keys[i];
				V v = get(k);
				if (!values[i].equals(v)) {
					System.err.println("Key-Value pair is not consistent: key:"
							+ k.toString() + " get() value:" + v.toString()
							+ " original value:" + values[i].toString());
					return false;
				}
			}
		}
		return true;
	}

	public boolean contains(K k) {
		return get(k) != null;
	}

	public V get(K k) {
		if (k == null)
			return null;
		int idx = findKeyIndex(k);
		return values[idx];
	}

	public void put(K k, V v) {
		if (k == null)
			return;
		if (size >= capacity * LOAD_FACTOR)
			resize(capacity * 2);

		int idx = findKeyIndex(k);
		if (v != null) {
			if (keys[idx] == null) { // insert
				keys[idx] = k;
				size++;
			}
			values[idx] = v; // update
		} else if (keys[idx] != null)
			deleteByIdx(idx);
	}

	public void delete(K k) {
		if (k == null)
			return;

		int idx = findKeyIndex(k);
		deleteByIdx(idx);

		// halves size of array if it's 12.5% full or less
		if (capacity > 0 && 8 * size <= capacity)
			resize(capacity / 2);

		assert check();
	}

	public Iterable<K> keys() {
		return Arrays.asList(keys);
	}

	public int size() {
		return size;
	}

	public int capacity() {
		return capacity;
	}

	public boolean isEmpty() {
		return size == 0;
	}

}
