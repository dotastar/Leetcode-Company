package general.datastructure.test;

import static org.junit.Assert.assertTrue;
import general.datastructure.MyStack;
import general.datastructure.Stack_LinkList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MyStackTester {

	public static void main(String[] args) {
		Result res = JUnitCore.runClasses(StkTest.class);
		for (Failure f : res.getFailures()) {
			System.err.println(f.toString() + "\n" + f.getTrace());
		}
		System.out.println(String.format("Finish! Total:%d Failed:%d",
				res.getRunCount(), res.getFailureCount()));
	}

	/***************** Stack Implementation *****************/
	public static class MyStackImpl {

	}

	/***************** MyStack Test *****************/
	private MyStack<Integer> stk;

	@Before
	public void init() {
		stk = new Stack_LinkList<>();
	}

	@Test
	public void test01_pop() {
		for (int i = 1; i <= 10; i++)
			stk.push(i);
		assertTrue(stk.size() == 10);

		for (int i = 10; i >= 1; i--)
			assertTrue(stk.pop() == i);
		assertTrue(stk.size() == 0);
	}

	@Test
	public void test02_peek() {
		for (int i = 1; i <= 10; i++)
			stk.push(i);
		assertTrue(stk.size() == 10);

		for (int i = 10; i >= 1; i--)
			assertTrue(stk.peek() == 10);
		assertTrue(stk.size() == 10);
	}

	@Test
	public void test03_isEmpty() {
		assertTrue(stk.size() == 0);
		assertTrue(stk.isEmpty());

		stk.push(1);
		assertTrue(!stk.isEmpty());

		stk.pop();
		assertTrue(stk.isEmpty());
	}
}
