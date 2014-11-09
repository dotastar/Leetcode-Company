package general.oo;
/**
 * CSE 505, use delegation implement inheritance.
 * 
 * An important technique in the study of Design Patterns is the use of
 * `delegation' to replace class inheritance'. A systematic way for doing this
 * transformation was sketched in Lecture 14.
 * 
 * Extend this technique so that the inheritance of attributes and methods, and
 * also method over-riding are handled. Apply the resulting technique to the
 * program in Delegation.java which defines four classes, C1, C11, C111, and
 * C112, where the classes C111 and C112 extend C11 which in turn extends C1.
 * The result of your transformation should be four classes called D1, D11,
 * D111, and D112 which correspond to C1, C11, C111, and C112 respectively, but
 * do not make use of class inheritance. Make the classes D1, D11, D111, and
 * D112 implement interfaces I1, I11, I111 and I112 respectively, where the
 * interfaces I111 and I112 should inherit from I11 which in turn should inherit
 * from I1. Define these interfaces suitably.
 * 
 * @author yazhoucao
 * 
 */
public class Delegation {
	public static void main(String args[]) {
		C111 c111 = new C111();
		System.out.println(c111.m111());

		C112 c112 = new C112();
		System.out.println(c112.m112());

		D111 d111 = new D111();
		System.out.println(d111.m111());

		D112 d112 = new D112();
		System.out.println(d112.m112());
	}
}

class C1 {
	protected int a1 = 1;

	public int m1() {
		return a1 + p1(100) + q1(100);
	}

	public int p1(int m) {
		return m;
	}

	public int q1(int m) {
		return m;
	}
}

class C11 extends C1 {
	protected int a11 = 11;

	public int m11() {
		return m1() + q1(200);
	}

	public int p1(int m) {
		return m * a1;
	}

	public int q1(int m) {
		return m + a11;
	}
}

class C111 extends C11 {
	protected int a111 = 111;

	public int m111() {
		return m1() + m11() + a111;
	}

	public int p1(int m) {
		return m * a1 * a11;
	}
}

class C112 extends C11 {
	protected int a112 = 112;

	public int m112() {
		return m1() + m11() + a112;
	}

	public int p1(int m) {
		return m * a1 * a11 * a112;
	}
}

// -------SIMULATING CLASS INHERITANCE BY DELEGATION ---------

interface I1 {
	public int m1();

	public int p1(int m);

	public int q1(int m);
}

interface I11 extends I1 {
	public int m11();
}

interface I111 extends I11 {
	public int m111();
}

interface I112 extends I11 {
	public int m112();
}

class D1 implements I1 {
	protected int a1 = 1;
	private I1 i1; // overriding

	public D1(I1 child) {
		//polymorphism, 
		//the behavior of D1 is bind with i1, can be decided at runtime.
		this.i1 = child;
	}
		
	public int m1() {
		return a1 + i1.p1(100) + i1.q1(100);
	}

	public int p1(int m) {
		return i1.p1(m);	
	}

	public int q1(int m) {
		return i1.q1(m);
	}
	
	public int get_a1(){
		return a1;
	}
}

class D11 implements I11 {
	protected int a11 = 11;
	protected D1 parent;// extends
	private I11 i11; // overriding

	public D11(I11 i11) {
		this.i11 = i11;
		parent = new D1(i11);
	}

	public int m1() {
		//extending parent's method
		return parent.m1();
	}

	public int p1(int m) {
		//override parent's method
		return m * parent.get_a1();
	}

	public int q1(int m) {
		//override parent's method
		return m + a11;
	}

	public int m11() {
		//new method
		return i11.m1() + i11.q1(200);
	}
	
	public int get_a11(){
		return a11;
	}
}

class D111 implements I111 {
	protected D11 parent;	//extending
	protected int a111 = 111;

	public D111() {
		parent = new D11(this);
	}

	public int m1() {
		return parent.m1();
	}

	public int q1(int m) {
		return parent.q1(m);
	}

	public int m11() {
		return parent.m11();
	}

	public int m111() {
		return m1() + m11() + a111;
	}

	public int p1(int m) {
		return m * parent.parent.get_a1() * parent.get_a11();
	}
	
	public int get_a111(){
		return a111;
	}
}

class D112 implements I112 {
	protected D11 parent;
	protected int a112 = 112;

	public D112() {
		this.parent = new D11(this);
	}

	public int m1() {
		return parent.m1();
	}

	public int q1(int m) {
		return parent.q1(m);
	}

	public int m11() {
		return parent.m11();
	}

	public int m112() {
		return m1() + m11() + a112;
	}

	public int p1(int m) {
		return m * parent.parent.get_a1() * parent.get_a11() * a112;
	}
	
	public int get_a112(){
		return a112;
	}
}
