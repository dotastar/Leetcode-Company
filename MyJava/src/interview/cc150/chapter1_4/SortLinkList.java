package interview.cc150.chapter1_4;
import general.datastructure.Node;
import general.datastructure.SingLinkList;

import java.util.Random;

/*
* Sorting Algorithms for LinkedList
*
*/
public class SortLinkList{
	public static void main(String[] args) throws Exception{
		
//		int[] test = new int[]{5,2}; //,3,1,6,4,9,8,7};
//		quicksort(test,0,test.length-1);
//		System.out.println(Arrays.toString(test));
		SingLinkList<Integer> list = new SingLinkList<Integer>();
		randomInitializeInt(list, 12);
		list.add(new Node<Integer>(11));
		list.add(new Node<Integer>(22));
		list.add(new Node<Integer>(2));
		list.add(new Node<Integer>(5));
		System.out.println(list.toString());
		
		quicksort(list, list.getHead(), list.getTail());
		
		System.out.println(list.toString());
	}

	/**
	 * Regular QuickSort
	 * @param list
	 * @param first
	 * @param last
	 */
	public static void quicksort(int[] list, int first, int last){
		if(first>=last) return;
		
		int pivot = list[first];
		int left = first;	//all elements before left are done(include left)
		for(int current=first+1; current<=last; current++){
			if(list[current] < pivot){
				left++;
				if(left!=current)
					swap(list, left, current);
			}
		}
		
		swap(list, left, first); //first is pivot
		quicksort(list, first, left-1);
		quicksort(list, left+1, last);
	}
	
	/**
	 * QuickSort for Singly LinkedList
	 * @param list
	 * @param first
	 * @param last
	 */
	public static <T extends Comparable<T>> void quicksort(SingLinkList<T> list, Node<T> first, Node<T> last){
		if(first.equals(last)) return;
		
		Node<T> pivot = first; 
		Node<T> left = first;	//nodes before it are done(include)
		Node<T> prevleft = first;
		Node<T> current = first.next();

		while(!current.equals(last)){
			//swap
			if(pivot.compareTo(current)>0){
				prevleft = left;
				left = left.next();	//left++
				if(!left.equals(current))
					swap(left, current);	//swap only data
			}
			
			current = current.next(); //current++
			if(current==null) break;
			
			//System.out.println(list.toString());
		}
		
		//compare last, current = last
		if(pivot.compareTo(current)>0){
			prevleft = left;
			left = left.next();
			if(!left.equals(current))
				swap(left, current);	//swap only data
		}
		
		//swap pivot
		swap(pivot, left);
		
		//recursion
		if(!first.equals(prevleft))
			quicksort(list, first, prevleft);
		if(!left.equals(last))
			quicksort(list, left.next(), last);
	}
	
	private static <T extends Comparable<T>> void swap(Node<T> i, Node<T> j){
		//swap data
		T data = i.getData();
		i.setData(j.getData());
		j.setData(data);
	}
	
	
	public static void randomInitializeInt(SingLinkList<Integer> list, int size){
		if(list==null) list = new SingLinkList<Integer>();
		
		Random ran = new Random();
		for(int i=0; i<size; i++)
			list.add(new Node<Integer>(ran.nextInt(size*100)));
	}
	
	private static void swap(int[] list, int i, int j){
		int tmp = list[i];
		list[i] = list[j];
		list[j] = tmp;
	}
}