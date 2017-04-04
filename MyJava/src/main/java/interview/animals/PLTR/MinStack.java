package interview.company.palantir;

import java.util.ArrayList;
import java.util.List;

public class MinStack {  
	  
    //maybe we can use origin array rather than ArrayList  
    private List<Integer> dataStack;  
    private List<Integer> minStack;//store the position of MinElement  
      
    public static void main(String[] args) {  
        MinStack minStack=new MinStack();  
        minStack.push(3);  
        minStack.printStatus();  
        minStack.push(4);  
        minStack.printStatus();  
        minStack.push(2);  
        minStack.printStatus();  
        minStack.push(1);  
        minStack.printStatus();  
        minStack.pop();  
        minStack.printStatus();  
        minStack.pop();  
        minStack.printStatus();  
        minStack.pop();  
        minStack.printStatus();  
    }  
  
    public MinStack(){  
        dataStack=new ArrayList<Integer>();  
        minStack=new ArrayList<Integer>();  
    } 
    
    public void push(int item){  
        if(isEmpty()){  
            dataStack.add(item);  
            minStack.add(0);//position=0  
        }else{  
            dataStack.add(item);  
            int itemIndex = minStack.size()-1;
            int minIndex=minStack.get(itemIndex);  
            int min=dataStack.get(minIndex);  
            if(min>item){
            	//item's index which is dataStack.size-1 
            	//is the min index now
                minStack.add(itemIndex);  
            }else{
                minStack.add(minIndex);  
            }
        }
    }
    
    public int pop(){  
        int top=-1;  
        if(isEmpty()){
            System.out.println("no element,no pop");  
        }else{
            minStack.remove(minStack.size()-1);  
            top=dataStack.remove(dataStack.size()-1);  
        }
        return top;
          
    }
    
    public int min(){
        int min=-1;  
        if(!isEmpty()){  
            int minIndex=minStack.get(minStack.size()-1);  
            min=dataStack.get(minIndex);  
        }
        return min;  
    } 
    
    public boolean isEmpty(){  
        return dataStack.size()==0;  
    }  
    
    public void printStatus(){  
        System.out.println("����ջ             ����ջ                ��Сֵ");  
        for(int i=0;i<dataStack.size();i++){  
            System.out.print(dataStack.get(i)+",");  
        }  
        System.out.print("          ");  
        for(int i=0;i<dataStack.size();i++){  
            System.out.print(minStack.get(i)+",");  
        }  
        System.out.print("           ");  
        System.out.print(this.min());  
        System.out.println();  
    }  
}