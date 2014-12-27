package general.datastructure;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class StkTest {

	public static void main(String[] args) {
		Result res = JUnitCore.runClasses(StkTest.class);
		for (Failure f : res.getFailures()) {
			System.err.println(f.toString() + "\n" + f.getTrace());
		}
		System.out.println(String.format("Finish! Total:%d Failed:%d",
				res.getRunCount(), res.getFailureCount()));
	}
	
	/***************** Stack_LinkList Test *****************/

	@Test
	public void test1_pop() {
		Stack_LinkList<Integer> stk = new Stack_LinkList<>();
		for (int i = 1; i <= 10; i++)
			stk.push(i);
		assertTrue(stk.size() == 10);

		for (int i = 10; i >= 1; i--)
			assertTrue(stk.pop() == i);
		assertTrue(stk.size() == 0);
	}

	@Test
	public void test2_peek() {
		Stack_LinkList<Integer> stk = new Stack_LinkList<>();
		for (int i = 1; i <= 10; i++)
			stk.push(i);
		assertTrue(stk.size() == 10);

		for (int i = 10; i >= 1; i--)
			assertTrue(stk.peek() == 10);
		assertTrue(stk.size() == 10);
	}

	@Test
	public void test3_isEmpty() {
		Stack_LinkList<Integer> stk = new Stack_LinkList<>();
		assertTrue(stk.size() == 0);
		assertTrue(stk.isEmpty());
		
		stk.push(1);
		assertTrue(!stk.isEmpty());

		stk.pop();
		assertTrue(stk.isEmpty());
	}
	
	
	/***************** Stack_Array Test *****************/

	@Test
	public void test11_pop() {
		Stack_Array<Integer> stk = new Stack_Array<>();
		for (int i = 1; i <= 10; i++)
			stk.push(i);
		assertTrue(stk.size() == 10);

		for (int i = 10; i >= 1; i--)
			assertTrue(stk.pop() == i);
		assertTrue(stk.size() == 0);
	}

	@Test
	public void test12_peek() {
		Stack_Array<Integer> stk = new Stack_Array<>();
		for (int i = 1; i <= 10; i++)
			stk.push(i);
		assertTrue(stk.size() == 10);

		for (int i = 10; i >= 1; i--)
			assertTrue(stk.peek() == 10);
		assertTrue(stk.size() == 10);
	}

	@Test
	public void test13_isEmpty() {
		Stack_Array<Integer> stk = new Stack_Array<>();
		assertTrue(stk.size() == 0);
		assertTrue(stk.isEmpty());
		
		stk.push(1);
		assertTrue(!stk.isEmpty());

		stk.pop();
		assertTrue(stk.isEmpty());
	}
}
