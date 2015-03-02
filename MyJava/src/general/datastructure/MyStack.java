package general.datastructure;

public interface MyStack<T> {
	public void push(T t);

	public T pop();

	public T peek();

	public boolean isEmpty();

	public int size();
}
