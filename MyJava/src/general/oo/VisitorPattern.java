package general.oo;

public class VisitorPattern {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

interface Visitor {
	public void visit(AssignNode an);

	public void visit(BinOpNode bn);

	public void visit(IfNode in);
}

class TypeCheckVisitor implements Visitor {
	public void visit(AssignNode an) { /**logic...**/}

	public void visit(BinOpNode  bn){ /**logic...**/ }

	public void visit(IfNode in) { /**logic...**/ }
}

class PrettyPrintVisitor implements Visitor {
	public void visit(AssignNode an) { /**logic...**/}

	public void visit(BinOpNode  bn){ /**logic...**/ }

	public void visit(IfNode in) { /**logic...**/ }
}

abstract class Node {
	public Node() { /**logic...**/}

	public void accept(Visitor v) {
	}
}

class AssignNode extends Node {
	public AssignNode() { /**logic...**/ }

	public void accept(Visitor v) {
		v.visit(this);
	}
}

class BinOpNode extends Node {
	public BinOpNode() { /**logic...**/ }

	public void accept(Visitor v) {
		v.visit(this);
	}
}

class IfNode extends Node {
	public IfNode() { /**logic...**/ }

	public void accept(Visitor v) {
		v.visit(this);
	}
}
