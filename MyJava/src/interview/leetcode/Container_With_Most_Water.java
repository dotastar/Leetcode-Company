package interview.leetcode;

/**
 * Given n non-negative integers a1, a2, ..., an, where each represents a point
 * at coordinate (i, ai). n vertical lines are drawn such that the two endpoints
 * of line i is at (i, ai) and (i, 0). Find two lines, which together with
 * x-axis forms a container, such that the container contains the most water.
 * 
 * Note: You may not slant the container.
 * 
 * 
 * @author yazhoucao
 * 
 */
public class Container_With_Most_Water {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * The problem is to find the maximun value of (i-j)*min(a_i, a_j).
	 */
    public int maxArea(int[] height) {
        int l=0;
        int r=height.length-1;
        int max = 0;
        while(l<r){
            int h = height[l]<height[r] ? height[l] : height[r];
            int currArea = (r-l)*h;
            max = currArea>max ? currArea : max;
            if(height[l]<height[r])
                l++;
            else
                r--;
        }
        return max;
    }

}
