package interview.epi.chaper11_heap;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import org.junit.Test;

/**
 * Problem 11.4
 * How would you compute the k starts which are the closest to the Earth? 
 * You have only a few megabytes of RAM.
 * @author yazhoucao
 *
 */
public class Q4_Compute_The_K_Closest_Stars {
	static Class<?> c = Q4_Compute_The_K_Closest_Stars.class;
	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * The same as find the Kth largest number
	 * Time: O(nlg(k)), Space: O(k)
	 */
	public static List<Star> findClosestKStars(InputStream stars, int k) {
		PriorityQueue<Star> maxheap = new PriorityQueue<>(k, Collections.reverseOrder());
		try (ObjectInputStream oin = new ObjectInputStream(stars)) {
			while (true){
				maxheap.add((Star) oin.readObject());
				if(maxheap.size()>k)
					maxheap.poll();
			}
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		assert maxheap.size()==k;
		return new ArrayList<>(maxheap);
	}


	/****************** Unit Test ******************/

	@Test
	public void test0() {	//random test
		Random r = new Random();
		for (int times = 0; times < 100; ++times) {
			int num = r.nextInt(10000) + 1;
			int k = r.nextInt(num) + 1;;
			List<Star> stars = new ArrayList<>();
			// randomly generate num of stars
			for (int i = 0; i < num; ++i) {
				stars.add(new Star(r.nextInt(100001), r.nextInt(100001), r.nextInt(100001)));
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ByteArrayInputStream sin = null;
			try {
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				for (Star s : stars) {
					oos.writeObject(s);
					// System.out.println(s.distance());
				}
				oos.close();
				sin = new ByteArrayInputStream(baos.toByteArray());
			} catch (IOException e) {
				System.out.println("IOException: " + e.getMessage());
			}
			List<Star> closestStars = findClosestKStars(sin, k);
			Collections.sort(closestStars);
			Collections.sort(stars);
			System.out.println("k = " + k);
			assertTrue (stars.get(k - 1).equals(closestStars.get(closestStars.size() - 1)));
		}
	}

	public static class Star implements Comparable<Star>, Serializable {
		private static final long serialVersionUID = 1L;
		private double x, y, z;

		public Star(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public int compareTo(Star rhs) {
			double rhsDistance = rhs.x * rhs.x + rhs.y * rhs.y + rhs.z * rhs.z;
			double distance = x * x + y * y + z * z;
			return Double.valueOf(distance).compareTo(rhsDistance);
		}

		// @exclude

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Star)) {
				return false;
			}
			if (obj == this) {
				return true;
			}

			Star rhs = (Star) obj;
			double rhsDistance = rhs.x * rhs.x + rhs.y * rhs.y + rhs.z * rhs.z;
			double distance = x * x + y * y + z * z;
			return distance == rhsDistance;
		}
		// @include
	}
}
