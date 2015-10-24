package interview.cc150.chapter5_12;

import interview.cc150.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Practices for prime number related operations
 * Corresponding to the knowledge part of chapter7
 * @author yazhoucao
 *
 */
public class PrimeNumberUtil {
	public static void main(String[] args){
		System.out.println(Arrays.toString(generatePrimeNums(26)));
		
		System.out.println(isPrime(100297));
		
		Tools.Timer.begin();
		System.out.println(getPrimeNum(100300));
		Tools.Timer.end();
		
		Tools.Timer.begin();
		System.out.println(getPrimeNum2(100300));
		Tools.Timer.end();
	}
	
	
	/********************************** Prime Numbers **********************************/
	
	/**
	 * Generate a prime number list, given the size of that list
	 * @param size
	 * @return
	 */
	public static int[] generatePrimeNums(int size){
		if(size<=0) return null;
		
		int[] primes = new int[size];
		primes[0] = 2;
		int count = 1;
		int num = 3;
		while(count<size){
			//if num is a prime, add to primes[]
			if(isPrime(num, primes)){
				primes[count] = num;
				count++;
			}
			
			num += 2;
		}
		
		return primes;
	}
	
	/**
	 * Given the max number(upper boundary), find the greatest prime number that
	 * less than or equal to the max number.
	 * @param max
	 * @return
	 */
	public static int getPrimeNum(int max){
		if(max<2) throw new IllegalArgumentException();
		if(max==2) return 2;
		List<Integer> primes = new ArrayList<Integer>();
		primes.add(2);
		int num = 3;
		while(num<=max){
			if(isPrime(num, primes)){
				primes.add(num);
			}
			num+=2;
		}
		return primes.get(primes.size()-1);
	}
	
	/**
	 * Faster version, calculate prime number from max to min is much faster.
	 * @param max
	 * @return
	 */
	public static int getPrimeNum2(int max){
		if(max<2) throw new IllegalArgumentException();
		if(max%2==0) max--;	//make it odd
		while(!isPrime(max)){
			max -= 2;
		}
		return max;
	}
	
	/**
	 * Use primes[] to check if it is a prime,
	 * a eligible primes[] must have all prime numbers less than the num.
	 * E.g, to check if 13 is a prime number or not, 
	 * primes[] should be {2,3,4,5,7,11} 
	 * @return
	 */
	public static boolean isPrime(int num, int[] primes){
		for(int p : primes){
			if(p==0)
				break;
			if(num%p==0)
				return false;
		}
		return true;
	}
	
	public static boolean isPrime(int num, List<Integer> primes){
		for(Integer p : primes){
			if(num%p==0)
				return false;
		}
		return true;
	}
	
	public static boolean isPrime(int num){
		if(num<2) return false;
		if(num%2==0) return false;
		
		int odd = 3;
		while(odd<num){
			if(num%odd==0)
				return false;
			
			odd += 2;
		}
		return true;
	}
}
