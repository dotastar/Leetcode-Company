package general.datastructure;

public interface IQueue<T> {
	public void offer(T ele);

	public T poll();

	public T peek();

	public boolean isEmpty();

	public int size();
}
