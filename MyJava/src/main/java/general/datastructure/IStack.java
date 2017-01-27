package general.datastructure;

public interface IStack<T> {
	public void push(T t);

	public T pop();

	public T peek();

	public boolean isEmpty();

	public int size();
}
