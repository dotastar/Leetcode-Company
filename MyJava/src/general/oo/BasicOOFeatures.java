package general.oo;

public class BasicOOFeatures {
	public static void main(String[] args){
		Parent parent;
		Son son;
		son = new Son();
		parent = son;
		
		//parent.id's address is decided at compile time
		System.out.println(parent.id);
		//parent.idSelf()'s address is decided at execution time(late binding).
		System.out.println(parent.idSelf());
		
		System.out.println(son.id);
		System.out.println(son.idSelf());
	}
}


class Parent {
	int id;
	public Parent(){
		id = 99;
	}
	public String idSelf(){
		return "parent" + id;
	}
}

class Son extends Parent{
	int id;					//shadowing
	public Son(){
		id = 100;
	}
	
	public String idSelf(){	//overriding
		return "son "+id;
	}
}