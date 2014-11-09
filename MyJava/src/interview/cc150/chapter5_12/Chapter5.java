package interview.cc150.chapter5_12;


/**
 * Bit Manipulation
 * 
 * @author yazhoucao
 * 
 */
public class Chapter5 {

	public static void main(String[] args) {
		
		printSeparator(1);
		String m = "10000101000";
		String n = "10011";
		int M = Integer.parseInt(m, 2);
		int N = Integer.parseInt(n, 2);
		question1(M, N, 2, 6);
		
		printSeparator(2);
		question2(0.125);
	}
	

	/**
	 * 5.1
	 * You are given two 32-bit numbers,N and M, and two bit positions, i and j.
	 * Write a method to insert M into N such that M starts at bit j and ends at
	 * bit i. You can assume that the bits j through i have enough space to fit
	 * all of M. That is,if M= 10011, you can assume that there are at least 5
	 * bits between j and i. You would not, for example, have j=3 and
	 * i=2, because M could not fully fit between bit 3 and bit 2.
	 * EXAMPLE:
	 * Input:N=10000000000(binary), M=10011(binary), 
	 * i =2, j =6 (bit position, start from 0) 
	 * Output: N = 10001001100
	 */
	
	
	/**
	 * Solution
	 * 1.Set M from ith bit to jth bit all to 0
	 * 	 idea: generate sth like this: 111111^0000^11
	 * 			a.first generate 1111
	 * 			b.then shift 1111 left 2 bits, 111100
	 * 			c.use negation '~', reverse it
	 * 		1) a = 1<<j+1 bits
	 * 		2) a = a-1
	 * 		3) a = a<<i
	 * 		4) a = ~a
	 * 		5) (M&a) will set ith bit to jth bit all to 0
	 * 2.Shift N left i bits
	 * 3.M | N
	 */
	public static void question1(int M, int N, int i, int j) {
		System.out.println("M: "+Integer.toBinaryString(M));
		System.out.println("N: "+Integer.toBinaryString(N));
		
		//1.Set M from ith bit to jth bit all to 0
		int a=~(((1<<j-i)-1)<<i);
		M = M&a;	
		System.out.println(Integer.toBinaryString(M));
		
		//2.Move N left i bits
		N = N<<i;		
		System.out.println(Integer.toBinaryString(N));
		
		//3.M | N
		System.out.println("Result: "+Integer.toBinaryString(M|N));
	}
	
	
	
	/**
	 * 5.2 
	 * Given a real number between 0 and 1 (e.g., 0.72) that is passed in as a
	 * double, print the binary representation. If the number cannot be
	 * represented accurately in binary with at most 32 characters, print
	 * "ERROR."
	 */
	
	/**
	 * Solution
	 * How to represent a float/double number in binary?
	 * 0.5 = 2^-1 = 0.1 (binary)
	 * 0.25 = 2^-2 = 0.01 (binary)
	 * 
	 * 0.75 = 0.11
	 * @param num
	 */
	public static void question2(double num){
		StringBuilder binary = new StringBuilder("0.");
		int len = 32;
		double frac = 1;
		double cumulative = 0;
		while(len>=0){
			frac /= 2d;
			if(num-frac>=0){
				binary.append("1");
				cumulative += frac;
			}else
				binary.append("0");
			
			if(num==cumulative)
				break;
			
			len--;
		}
		
		if(len>=0)
			System.out.println(binary.toString());
		else
			System.out.println("Error!");
	}
	
	
	/**
	 * 5.3
	 * Given a positive integer, print the next smallest and the next largest
	 * number that have the same number of 1 bits in their binary representation.
	 * 
	 * 
	 */
	public static void question3(int num){
		
	}
	
	private static void printSeparator(int i){
		System.out.println("====================================="+i+"==========================================");
	}
}
