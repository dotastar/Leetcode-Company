package general.misc;

/**
 * Garbage collection in java can not be enforced. But still sometimes, we call
 * the System.gc( ) method explicitly. System.gc() method provides just a "hint"
 * to the JVM that garbage collection should run. It is not guaranteed!
 * 
 * @author yazhoucao
 *
 */
public class ForceGC {

	public static void main(String[] args) {
		int gc = 1;
		if (gc == 1)
			tryManullyGC();
		else
			automaticGC();
		System.out.println("End program.");
	}

	private static void tryManullyGC() {
		A a = new A("white");
		System.out.println(a);
		a = null;
		Runtime.getRuntime().gc();
	}

	private static void automaticGC() {
		A a = new A("white");
		System.out.println(a);
		for (int i = 0; i < 10000000; i++) {
			if (i % 2 == 1) {
				a = new A("red");
			} else {
				a = new A("green");
			}
			a = null;
		}
	}

	static class A {
		private String color;

		public A(String color) {
			this.color = color;
		}

		@Override
		public void finalize() {
			System.out.println(this.color + " cleaned");
		}
	}
}
