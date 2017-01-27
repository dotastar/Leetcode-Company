package general.datastructure;

public interface IHeap<T> {
	public void offer(T elem);

	public T poll();

	public T peek();

	public int update(int idx, Integer elem);

	public int size();

	public boolean isEmpty();
}
