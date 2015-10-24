package interview.epi.chapter5_primitive_type;

/**
 * Five hundred closed doors along a corridor are numbered from 1 to 500. A
 * person walks through the corridor and opens each door. Another person walks
 * through the corridor and closes every alternate door. Continuing in this
 * manner, the i-th person comes and toggles the position of every i-th door
 * starting from door i. E.g. 2nd door (door #2, #4, #6), 3rd door (door #3, #6,
 * #9)
 * 
 * Problem 5.14
 * Which doors are open after the 500-th person has walked through?
 * 
 * @author yazhoucao
 * 
 */
public class Q14_The_Open_Doors_Problem {

	static String methodName = "";
	static Class<?> c = Q14_The_Open_Doors_Problem.class;

	public static void main(String[] args) {
		
	}

	public static boolean isDoorOpen(int i) {
		double sqrtI = Math.sqrt(i);
		int floorSqrtI = (int) Math.floor(sqrtI);
		return floorSqrtI * floorSqrtI == i;
	}
}
