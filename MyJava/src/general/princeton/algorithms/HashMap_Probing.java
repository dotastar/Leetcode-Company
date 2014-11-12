package general.princeton.algorithms;


/**
 * Linear probing hash table implementation,
 * also called closed hashing/open addressing
 * 
 * @author yazhoucao
 * 
 */
public class HashMap_Probing<K, V> implements SymbolTable<K, V>  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	private static final int INIT_CAPACITY = 7;
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

	}

	private int hash(K k) {
		return k.hashCode() % capacity;
	}

	public boolean contains(K k) {
		int idx = hash(k);
		return false;
	}

	public V get(K k) {
		return null;
	}

	public void put(K k, V v) {

	}

	public Iterable<K> keys() {
		return null;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {

		return false;
	}

	public void delete(K k) {

	}
}
