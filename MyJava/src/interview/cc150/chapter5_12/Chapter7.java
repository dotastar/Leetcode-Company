package interview.cc150.chapter5_12;

import interview.cc150.Tools;

/**
 * Mathematics and Probability
 * Only 3 questions, 7.4, 7.6, 7.7
 * And PrimeNumberUtil.java is a part of chapter7
 * before the question section
 * @author yazhoucao
 * 
 */
public class Chapter7 {

	public static void main(String[] args) {
		Tools.printSeparator(4);
		question4();

		Tools.printSeparator(6);
		question6();
		
		Tools.printSeparator(7);
		question7();
	}

	/**
	 * 7.4 Write methods to implement the multiply, subtract, and divide
	 * operations for integers. Attention: Use only the add operator. Key: use
	 * add operator to implement negate() and abs();
	 * 
	 */
	public static void question4() {
		int a = -19;
		int b = -9;
		System.out.println(a + " - " + b + " = " + subtract(a, b));
		System.out.println(a + " * " + b + " = " + multiply(a, b));
		System.out.println(a + " / " + b + " = " + divide(a, b));
	}

	private static int subtract(int a, int b) {
		return a + negate(b);
	}

	private static int negate(int a) {
		int unit = a > 0 ? -1 : 1;
		int na = 0;
		while (a != 0) {
			a += unit;
			na += unit;
		}
		return na;
	}

	private static int multiply(int a, int b) {
		boolean negative = false;
		if ((a > 0 && b < 0) || (a < 0 && b > 0))
			negative = true;

		a = abs(a);
		if (negative)
			a = negate(a);

		int unit = b > 0 ? -1 : 1;
		int res = 0;

		while (b != 0) {
			res += a;
			b += unit;
		}

		return res;
	}

	private static int abs(int a) {
		if (a >= 0)
			return a;
		else
			return negate(a);
	}

	private static int divide(int a, int b) {
		if (b == 0)
			throw new IllegalArgumentException();

		boolean negative = false;
		if ((a > 0 && b < 0) || (a < 0 && b > 0))
			negative = true;

		int unit = negative ? -1 : 1;
		a = abs(a);
		b = abs(b);
		b = negate(b);

		int res = 0;
		do {
			a += b;
			res += unit;
		} while (a >= 0);

		res += -1;
		return res;
	}

	/**
	 * 7.6 
	 * Given a two-dimensional graph with points on it, find a line which
	 * passes the most number of points
	 */
	public static void question6() {
		float[][] points = {{1,2},{1,5},{0,0},{6,4},{5,1},{2,4},{2,10},{4,4},{3,6},{-1,-1},{-1,-2},{3,2}};
		Line best = null;
		int max = -1;
		int len = points.length;
		for(int i=0; i<len-2; i++){
			float[] p1 = points[i];
			for(int j=i+1; j<len-1;j++){
				float[] p2 = points[j];
				Line l = new Line(p1, p2);
				int count = 0;
				for(int k=j+1; k<len; k++){
					float[] p3 = points[k];
					if(l.include(p3)) 
						count++;
				}
				if(count>max){
					best = l;
					max = count;
				}
			}
		}
		
		System.out.println(best+",  length:"+max);
	}
	
	public static class Line{
		//representation: y = kx + b
		private float k;	//slope
		private float b;	//intercept
		
		public Line(float slope, float intercept){
			k = slope;
			b = intercept;
		}
		
		public Line(float[] p1, float[] p2){
			if(p1.length!=2||p2.length!=2)
				throw new IllegalArgumentException();
			//k = (y1-y2)/(x1-x2), b = y1-kx1
			k = (p1[1]-p2[1])/(p1[0]-p2[0]); 
			b = p1[1] - k*p1[0];
		}
		
		public float slope(){ return k; }
		public float intercept(){ return b; }
		public String toString(){ return "y = "+k+"x + "+b; }
		public boolean include(float[] p){
			if(p.length!=2) throw new IllegalArgumentException();
			return p[1]==(k*p[0]-b)?true:false;
		}
	}

	/**
	 * 7.7 Design an algorithm to find the kth number such that the only prime
	 * factors are 3, 5, and 7.
	 */
	public static void question7() {
		int k = 2;
		int count = 0;
		int num = 105;
		while (count != k) {
			if (containsOnly357(num)) {
				count++;
			}
			num += 2;
		}

		num -= 2;
		System.out.println("The " + k + "th number contains ony 3,5,7 is "
				+ num);
	}

	private static boolean containsOnly357(int num) {
		int[] primes = { 3, 5, 7 };
		int hitCount = 0;
		for (int p : primes) {
			boolean hit = false;
			while (true) {
				if (num % p != 0)
					break;
				else {
					num = num / p;
					hit = true;
				}
			}
			if (hit)
				hitCount++;
		}

		return (num == 1 && hitCount == 3) ? true : false;
	}
}
