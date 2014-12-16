package interview.epi;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class AutoTestUtils {
	
	private AutoTestUtils() {}
	
	/**
	 * Run test cases in the Class provided by the input
	 */
	public static String runTestClass(String classname){	
		try {
			Class<?> c = Class.forName(classname);
			return runTestClass(c);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Error! Can't find class: "+classname);
			return "Error! Can't find class: "+classname;
		}
	}
	
	/**
	 * Run test cases in that Class c, and return the results
	 */
	public static String runTestClass(Class<?> c){
		StringBuffer sb = new StringBuffer();
		Result res = JUnitCore.runClasses(c);
		for (Failure f : res.getFailures()) {
			sb.append(f.toString()+System.lineSeparator());
			sb.append(f.getTrace().toString()+System.lineSeparator());
		}
		sb.append("Total Tests: " + res.getRunCount()+System.lineSeparator());
		sb.append("Failures : " + res.getFailureCount()+System.lineSeparator());
		return sb.toString();
	}
	
	/**
	 * Run test cases in that Class c, and print the results
	 */
	public static void runTestClassAndPrint(Class<?> c){
		Result res = JUnitCore.runClasses(c);
		for (Failure f : res.getFailures()) {
			System.err.println(f.toString());
			System.err.println(f.getTrace().toString());
		}
		System.out.println("Total Tests: " + res.getRunCount());
		System.out.println("Failures : " + res.getFailureCount());
	}
	
	/**
	 * Find method by method name, it could be a fuzzy name.
	 */
	public static List<Method> findMethod(String methodName, Class<?> c){
		List<Method> mlist = new ArrayList<Method>();
		for(Method m : c.getMethods()){
			if(m.getName().contains(methodName))
				mlist.add(m);
		}
		return mlist;
	}

}
