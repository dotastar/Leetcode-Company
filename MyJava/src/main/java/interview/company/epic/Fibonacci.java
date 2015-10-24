package interview.company.epic;

public class Fibonacci {

	public static void main(String[] args) {
		Fibonacci o = new Fibonacci();
		o.fib(30);
	}

	public void fib(int n) {
		int a = 0;
		int b = 1;
		while (n-- > 0) {
			int fib = a + b;
			a = b;
			b = fib;
			System.out.print(fib + " ");
		}
	}
}
