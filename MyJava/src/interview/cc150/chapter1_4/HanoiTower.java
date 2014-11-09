package interview.cc150.chapter1_4;
import java.util.Stack;



/**
 * 3.4 In the classic problem of the Towers of Hanoi, you have 3 towers and N disks 
 * of different sizes which can slide onto any tower. The puzzle starts with disks 
 * sorted in ascending order of size from top to bottom (i.e., each disk sits on top 
 * of an even larger one). You have the following constraints: 
 * (1) Only one disk can be moved at a time.
 * (2) A disk is slid off the top of one tower onto the next rod.
 * (3) A disk can only be placed on top of a larger disk.
 * Write a program to move the disks from the first tower to the last using Stacks.
 * @author yazhoucao
 *
 */
public class HanoiTower {

	
	
	public static void main(String[] args) {
		int n = 8;
		Tower[] towers = new Tower[3];
		for(int i=0; i<3; i++)
			towers[i] = new Tower();
		
		//initialize origin tower
		for(int i=n; i>=1; i--){
			towers[0].push(i);
		}
		
		printStatus(towers[0], towers[1], towers[2]);
		moveHanoiTower(n, towers[0], towers[1], towers[2]);
	}

	public static void moveHanoiTower(int n, Tower origin, Tower buffer, Tower destination){
		if(n==2){
			buffer.push(origin.pop());
			destination.push(origin.pop());
			destination.push(buffer.pop());
			return;
		}
		
		moveHanoiTower(n-1, origin, destination, buffer);
		
		moveToTop(origin, destination);
		
		moveHanoiTower(n-1, buffer, origin, destination);
		
		System.out.println("------------------moving " + (n-1) + " disks to destination tower.------------------");
		printStatus(origin, buffer, destination);
	}
	
	public static void moveToTop(Tower origin, Tower destination){
		destination.push(origin.pop());
	}
	
	public static void printStatus(Tower origin, Tower buffer, Tower destination){
		System.out.println("origin:\n"+origin.toString());
		System.out.println("buffer:\n"+buffer.toString());
		System.out.println("destination:\n"+destination.toString());
	}
	
	public static class Tower{
		private Stack<Integer> disks;
		
		public Tower(){
			disks = new Stack<Integer>();
		}
		
		public int pop(){ return disks.pop(); }
		public int peek(){ return disks.peek(); }
		
		public void push(int data){ 
			if(disks.size()==0){
				disks.push(data);
				return;
			}

			int top = peek();
			//bottom must greater than top
			if(data>top) 
				throw new IllegalArgumentException();
			else 
				disks.push(data); 
		}
		
		public String toString(){
			int length = disks.size();
			StringBuffer sb = new StringBuffer();
			for(int i=length-1; i>=0; i--){
				int data = disks.get(i);
				for(int j=0; j<data; j++)
					sb.append("#");
				sb.append("\n");
			}
			return sb.toString(); 
		}
	}
}
