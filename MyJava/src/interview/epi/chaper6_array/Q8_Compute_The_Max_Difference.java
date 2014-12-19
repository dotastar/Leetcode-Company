package interview.epi.chaper6_array;

import interview.AutoTestUtils;

/**
 * A robot needs to travel along a path that includes several ascents and
 * descents. When it goes up, it uses its battery to power the motor and when it
 * descends, it recovers the energy which is stored in the battery. The battery
 * recharging process is ideal: on descending, every Joule of gravitational
 * potential energy converts to a Joule of electrical energy which is stored in
 * the battery. The battery has a limited capacity and once it reaches this
 * capacity, the energy generated in descending is lost.
 * 
 * Problem 6.8
 * Design an algorithm that takes a sequence of n three-dimensional coordinates
 * to be traversed, and returns the minimum battery capacity needed to complete
 * the journey. The robot begins with the battery fully charged.
 * 
 * Since energy usage depends on the change in altitude of the robot, we can
 * ignore the x and y coordinates.
 * 
 * @author yazhoucao
 * 
 */
public class Q8_Compute_The_Max_Difference {

	static String methodName = "findBatteryCapacity";
	static Class<?> c = Q8_Compute_The_Max_Difference.class;

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	public static int findBatteryCapacity(int[] h) {
		int capacity = 0;
		int minHeight = h.length > 0 ? h[0] : 0;
		for (int height : h) {
			capacity = Math.max(capacity, height - minHeight);
			minHeight = Math.min(minHeight, height);
		}
		return capacity;
	}
}
