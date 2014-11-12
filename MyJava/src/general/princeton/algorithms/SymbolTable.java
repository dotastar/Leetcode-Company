package general.princeton.algorithms;

public interface SymbolTable<K, V> {
	public int size();

	public boolean isEmpty();

	public boolean contains(K k);

	public V get(K k);

	public void put(K k, V v);

	public void delete(K k);

	public Iterable<K> keys();

}
