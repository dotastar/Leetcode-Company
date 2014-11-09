package interview.leetcode;

/**
 * Implement pow(x, n).
 * 
 * @author yazhoucao
 * 
 */
public class PowXN {

	public static void main(String[] args) {

	}

    public double pow(double x, int n) {
    	if(n<0)
    		return 1/power(x, n);
    	else
    		return power(x, n);
    }
    
    public double power(double x, int n){
    	if(n==0)
    		return 1;
    	double half = power(x, n/2);
    	
    	if(n%2==0)
    		return half*half;
    	else
    		return half*half*x;
    }
    
    
    
}
