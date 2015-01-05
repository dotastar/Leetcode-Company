package interview.company.epic;

import interview.AutoTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Given a array
 * {{ 4,7,3,6,7}}
 * 
 * construct a triangle like
 * {{81}}
 * {{40,41}}
 * {{21,19,22}}
 * {{11,10,9,13}}
 * {{4,7,3,6,7}}
 * 
 * @author yazhoucao
 * 
 */
public class ContructTriangle {

	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(ContructTriangle.class);
	}

	/**
	 * Bottom up construct the triangle
	 * Time: O(n^2), n = base.length
	 */
	public List<int[]> construactTriangle(int[] base) {
		List<int[]> res = new ArrayList<>();
		res.add(base);
		for (int len = base.length - 1; len >= 1; len--) {
			int[] layer = new int[len];
			int[] prev = res.get(res.size() - 1);
			for (int i = 0; i < prev.length - 1; i++)
				layer[i] = prev[i] + prev[i + 1];
			res.add(layer);
		}
		reverse(res);
		return res;
	}

	private <T> void reverse(List<T> list) {
		int l = 0, r = list.size() - 1;
		while (l < r) {
			T temp = list.get(l);
			list.set(l, list.get(r));
			list.set(r, temp);
			l++;
			r--;
		}
	}

	@Test
	public void test1() {
		int[] base = { 4, 7, 3, 6, 7 };
		List<int[]> res = construactTriangle(base);
		for (int[] layer : res)
			System.out.println(Arrays.toString(layer));
	}
}
