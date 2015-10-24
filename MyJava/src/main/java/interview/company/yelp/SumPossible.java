package interview.company.yelp;

/**
 * From a given integer array values, find if 
 * a Total value is possible or not? The numbers 
 * in the array can be used more than once. 
 * example  
 * int[] points = {3, 7}; 
 * isScorePossible(points, 10) => true 
 * isScorePossible(points, 9) => true
 * @author yazhoucao
 *
 */
public class SumPossible {
	
	public static void main(String[] args){
		int[] points = {3, 7};
		
		System.out.println(iterative(points, 20));
	}
	
	
	
	
	
	public static boolean recursion(int[] points, int sum){
		if(sum<0) return false;
		if(sum==0) return true;

		for(int i=0; i<points.length; i++){
			if(recursion(points, sum-points[i]))
				return true;
		}

		return false;
	}
	
	
	
	/**
	 * Dynamic programming
	 * 
	 * @param points
	 * @param sum
	 * @return
	 */
	public static boolean iterative(int[] points, int sum){
		int[] status = new int[sum+1];
		status[0] = 1;
		for (int i=0;i<points.length;++i){
			int currVal = points[i];
			for (int j=currVal;j<=sum;++j){
				status[j]+=status[j-currVal];
				System.out.println("status "+j+" +="+status[j-points[i]]+";  j-points[i] "+j+"-" + points[i] + ";  i:"+i);
			}
		}
		System.out.println(status[sum]);
		return status[sum]>0;
	}
}
