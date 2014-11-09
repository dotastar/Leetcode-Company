package general.oo;

public class BasicOOFeatures {
	public static void main(String[] args){
		Parent parent;
		Son son;
		son = new Son();
		parent = son;
		
		System.out.println(parent.id);
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
	int id;
	public Son(){
		id = 100;
	}
	
	public String idSelf(){
		return "son "+id;
	}
}