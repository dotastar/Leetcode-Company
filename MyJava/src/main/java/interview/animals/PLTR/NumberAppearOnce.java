package interview.company.palantir;

/**
 * There are two numbers in an array that only appear once,
 * others numbers appear twice.
 * Find the two numbers
 * @author Asia
 *
 */
public class NumberAppearOnce {

	public static void main(String[] args) {
		int[] input = {8,2,8,2,5,11,5,9,7,9,7,3};

        //init end  
		findAppearOnce(input);
	}
	
	/**
	 * Use XOR, same is false, different is true
	 * 1.find the sum of XOR of all which equals to 
	 * the XOR sum of these two numbers
	 * 2.find a bit = 1 which means the two numbers are
	 * different in that bit
	 * 3.Use that bit to split the original input into
	 * two arrays, and do XOR sum separately
	 * 
	 * @param array
	 */
	public static void findAppearOnce(int[] array){
	       int xor_sum = 0;
	        for(int i : array){
	            xor_sum^=i;
	        }

	        //find the index of the bit that are different
	        //in these two numbers, in order to partition
	        int bitIndex=0;
	        while((xor_sum&1)==0){
	            xor_sum = xor_sum>>1;
	            bitIndex++;
	        }
	        
	        //partition input into two groups by that bit
	        //then xor two groups separately 
	        int odd1 = 0;
	        int odd2 = 0;
	        for(int i : array){
	            if(((i>>bitIndex)&1)==0)
	                odd1^=i;
	            else
	                odd2^=i;
	        }
	        
	        System.out.println(odd1+","+odd2);
	}

}
