package general.datastructure.test;

import general.datastructure.MyQueue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MyQueueTester {
	public static void main(String[] args) {
		Result res = JUnitCore.runClasses(StkTest.class);
		for (Failure f : res.getFailures()) {
			System.err.println(f.toString() + "\n" + f.getTrace());
		}
		System.out.println(String.format("Finish! Total:%d Failed:%d",
				res.getRunCount(), res.getFailureCount()));
	}

	/***************** Implementation *****************/
	public static class MyQueueImpl implements MyQueue<Integer> {

		@Override
		public void offer(Integer ele) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Integer poll() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Integer peek() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int size() {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	/***************** Test *****************/
	private MyQueue<Integer> q;

	@Before
	public void init() {
		q = new MyQueueImpl();
	}

	@Test
	public void test1() {
		q.offer(1);
	}

}
