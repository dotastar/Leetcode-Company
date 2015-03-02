package general.datastructure;

public class Stack_LinkList<T extends Comparable<T>> implements MyStack<T>{
	private Node<T> top;
	private int size;

	public void push(T t) {
		Node<T> newtop = new Node<>(t);
		newtop.next = top;
		top = newtop;
		size++;
	}

	public T pop() {
		if (size > 0) {
			Node<T> res = top;
			top = top.next;
			size--;
			return res.data;
		} else
			throw new RuntimeException("Can't pop(), empty stack!");
	}

	public T peek() {
		if (size > 0) {
			return top.data;
		} else
			throw new RuntimeException("Can't peek(), empty stack!");
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	private static class Node<T> {
		T data;
		Node<T> next;

		public Node(T t) {
			data = t;
			next = null;
		}
	}
}
