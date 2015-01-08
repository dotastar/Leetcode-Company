package interview.company.epic;

/**
 * Given a number find whether the digits in the number can be used to form an
 * equation with + and '='. That is if the number is 123, we can have a equation
 * of 1+2=3. But even the number 17512 also forms the equation 12+5=17.
 * 
 * @author yazhoucao
 * 
 */
public class NumberSplitSum {

	public static void main(String[] args) {
		NumberSplitSum o = new NumberSplitSum();
		System.out.println(o.canSplitSum(17512)); // true 12+5=17
		System.out.println(o.canSplitSum(6321)); // false
		System.out.println(o.canSplitSum(123446)); // true 12+34=46
	}

	public boolean canSplitSum(int num) {
		String n = Integer.toString(num);
		int half = n.length() / 2;
		// split and check
		for (int i = 1; i <= half; i++) {
			for (int j = i + 1; j < half + i; j++) {
				int n1 = Integer.valueOf(n.substring(0, i));
				int n2 = Integer.valueOf(n.substring(i, j));
				int n3 = Integer.valueOf(n.substring(j, n.length()));
				if (check(n1, n2, n3)) {
					System.out.println(n1 + "\t" + n2 + "\t" + n3);
					return true;
				}
			}
		}
		return false;
	}

	private boolean check(int n1, int n2, int n3) {
		if (n1 + n2 == n3)
			return true;
		if (n1 + n3 == n2)
			return true;
		if (n3 + n2 == n1)
			return true;

		return false;
	}

}
