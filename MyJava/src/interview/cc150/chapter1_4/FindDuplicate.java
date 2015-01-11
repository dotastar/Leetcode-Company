package interview.cc150.chapter1_4;
import general.datastructure.Node;

/*
* 2.1 Write code to remove duplicates from an unsorted linked list. 
*FOLLOW UP, How would you solve this problem if a temporary 
*buffer is not allowed?
*/
public class FindDuplicate{
	
	
	public <T extends Comparable<T>> void NestedLoopDeleteDuplicate(Node<T> node){
		Node<T> outter = node;
		while(outter.next!=null){
			Node<T> prevInner = outter;
			Node<T> inner = outter.next;
			while(inner!=null){
				if(inner.equals(outter)){
					prevInner.next = inner.next;
					inner = inner.next; //inner++
				}else{
					prevInner = inner;
					inner = inner.next; //inner++
				}
			}//end inner while
			outter = outter.next;	//outter++
			if(outter.next==null) break;
		}
	}
}