package interview.cc150;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;

public class Tools {
	
	public static void main(String[] args) throws InterruptedException{
		Timer.begin();
		Thread.sleep(5);
		Timer.end();
	}
	
	/**
	 * Use reflection to automatically run all questions
	 * @param cls: the class of questions that will be ran
	 * @param num: the number of question will be ran
	 */
	public static void runAllQuestions(Class<?> cls, int num){
		try {
			for(int i=1; i<=num; i++){
				Tools.printSeparator(i);
				Method method = cls.getMethod("q"+i, new Class[]{});
				method.invoke(cls);
				System.out.println();
			}
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void printSeparator(int i){
		System.out.println("===================================== "+i+" ==========================================");
	}
	
	public static void printMat(int[][] mat){
		int n = mat.length;
		if(n==0) return;
		int m = mat[0].length;
		
		for(int i=0; i<n; i++){
			for(int j=0; j<m; j++)
				System.out.print(mat[i][j]+"\t");
			System.out.println();
		}
		System.out.println();
	}
	
	public static class Timer{
		private static Stack<Long> startTimes = new Stack<Long>();
		public static void begin(){
			startTimes.push(System.currentTimeMillis());
		}
		
		public static void end(){
			long end = System.currentTimeMillis();
			long start = startTimes.pop();
			long diff = end - start;
			if(diff>2000)
				System.out.println((float)diff/1000f+"s");
			else
				System.out.println(end-start+"ms");
		}
	}
}
