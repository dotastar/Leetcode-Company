package interview.epi.chaper7_string;

import static org.junit.Assert.*;
import interview.AutoTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Problem 7.2
 * Write a function which takes as input a string s, and removes each "b" and
 * replaces each a by "dd".
 * 
 * @author yazhoucao
 * 
 */
public class Q2_Replace_And_Remove {
	static String methodName = "replaceAndRemove";
	static Class<?> c = Q2_Replace_And_Remove.class;
	
	public static void main(String[] args) {
		AutoTestUtils.runTestClassAndPrint(c);
	}

	/**
	 * Time: O(n)
	 */
	public static String replaceAndRemove(String s) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<s.length(); i++){
			char c = s.charAt(i);
			if(c=='a')
				sb.append("dd");
			else if(c!='b')
				sb.append(c);
		}
		return sb.toString();
	}

	/****************** Unit Test ******************/

	public String invokeMethod(Method m, String s) {
		try {
			Object res = m.invoke(null, s);
			return (String) res;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return "";
		}
	}

	@Test
	public void test1() {
		String s = "bncabdad";
		String ans = "ncdddddd";
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String res = invokeMethod(m, s);
			assertTrue("Wrong: "+res, res.equals(ans));
		}
	}

	@Test
	public void test2() {
		String s = "bababa";
		String ans = "dddddd";
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String res = invokeMethod(m, s);
			assertTrue("Wrong: "+res, res.equals(ans));
		}
	}

	@Test
	public void test3() {
		String s = "adadad";
		String ans = "ddddddddd";
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String res = invokeMethod(m, s);
			assertTrue("Wrong: "+res, res.equals(ans));
		}
	}

	@Test
	public void test4() {
		String s = "bbbbbbbbb";
		String ans = "";
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String res = invokeMethod(m, s);
			assertTrue("Wrong: "+res, res.equals(ans));
		}
	}

	@Test
	public void test5() {
		String s = "bncabdad";
		String ans = "ncdddddd";
		for (Method m : AutoTestUtils.findMethod(methodName, c)) {
			String res = invokeMethod(m, s);
			assertTrue("Wrong: "+res, res.equals(ans));
		}
	}
}
