package general.princeton.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class RedBlackTreeImpl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = { 12, 32, 121, 2, 52, 23, 156, 52, 21, 18, 6, 9, 51, 33, 5 };
		insertionSort(a);
		System.out.println(Arrays.toString(a));
		String s = "";
		Stack<String> test = new Stack<String>();
		List<String> l = new ArrayList<String>(test);
		System.out.println(Character.isLetterOrDigit('0'));
		StringBuilder sb = new StringBuilder();
		
	}

	public static void insertionSort(int[] a) {
		for (int i = 0; i < a.length; i++) {
			int j = i;
			while (j > 0 && a[j] < a[j - 1]) {
				swap(a, j, j - 1);
				j--;
			}
		}
	}

	private static void swap(int[] a, int i, int j) {
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}

	public static class RedBlackTree<T extends Comparable<T>> {
		private Node<T> root;
	}

	private static class Node<T extends Comparable<T>> {
		Node left;
		Node right;
		T value;

		public Node(T t) {
			value = t;
		}

	}
}
