package interview.company.palantir;

import java.util.HashSet;

/**
 * Given an array of values, design and code 
 * an algorithm that returns whether there are 
 * two duplicates within k indices of each other? 
 * k indices and within plus or minus l (value) of 
 * each other? Do all, even the latter, in O(n) running time and O(k) space.
 * @author Asia
 *
 */
public class FindDuplicatesInKIndices {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static boolean findDuplicateWithinN(int[] array, int n) {
	    
	    HashSet<Integer> set = new HashSet<Integer>(n);
	    
	    for (int i = 0; i < array.length; i++) {
	      Integer current = new Integer(array[i]);
	      
	      // Duplicate found
	      if (set.contains(current)) {
	        return true;
	      }
	      
	      // Always keep set size as n
	      if (i >= n) {
	        set.remove(new Integer(array[i - n]));
	      }
	      set.add(current);
	    }
	    
	    return false;
	  }
}
