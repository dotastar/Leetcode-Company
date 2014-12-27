package interview.cc150.chapter1_4;
import general.datastructure.Node;
import general.datastructure.SingLinkList;


public class PartitionLinkList{
	
	public static void main(String[] args){
		SingLinkList<Integer> list = new SingLinkList<Integer>();
		SortLinkList.randomInitializeInt(list, 10);
		Node<Integer> node = list.getHead();
		
		System.out.println(list.toString());
		list.setHead(partition2(500, node));	
		System.out.println(list.toString());
	}
	
	
	/**
	 * Version 1, only swap data
	 * Similar in the partition
	 * @param pivot
	 * @param head
	 */
	public static void partition1(int pivot, Node<Integer> head){
		
		Node<Integer> left = null;
		Node<Integer> current = head;
		
		if(current==null) return;
		
		//initial left
		while(left==null){
			if(current.getData()<pivot){
				left = head;	//head
				//swap
				int data = current.getData();
				current.setData(left.getData());
				left.setData(data);
				//current++
				current = current.next();
				break;
			}
			current = current.next();
			
			if(current==null) return;
		}
		
		//scan and swap
		while(current!=null){
			if(current.getData()<pivot){
				left = left.next();
				if(!current.equals(left)){
					//swap data only
					int data = current.getData();
					current.setData(left.getData());
					left.setData(data);
				}
			}
			current = current.next();
		}
	}
	
	/**
	 * Version 2, split the list into a big list and a small list
	 * and then merge two list
	 * @param pivot
	 * @param node
	 * @return
	 */
	public static Node<Integer> partition2(int pivot, Node<Integer> node){
		Node<Integer> smallStart = null;
		Node<Integer> smallEnd = null;
		Node<Integer> bigStart = null;
		Node<Integer> bigEnd = null;
		
		while(node!=null){
			if(node.getData()>pivot){
				//big list initial
				if(bigStart==null){
					bigStart = node;
					bigEnd = node;
				}else{
					bigEnd.setNext(node); //append to big list
					bigEnd  = node;	
				}
				
			}else{
				//small list initial
				if(smallStart==null){
					smallStart = node;
					smallEnd = node;
				}else{
					smallEnd.setNext(node); //append to small list
					smallEnd = node;
				}
			}
			
			node = node.next();	//current++
		}
		
		if(bigEnd.next()==null){
			smallEnd.setNext(bigStart);
		}else{
			bigEnd.setNext(null);
			smallEnd.setNext(bigStart);
		}
		
		return smallStart;
	}
}