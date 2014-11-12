package general.princeton.algorithms;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class HashMapTest {
	
	public static void main(String[] args){
		Result res = JUnitCore.runClasses(HashMapTest.class);
		for(Failure fail : res.getFailures()){
			System.out.println(fail.toString());
		}
		System.out.println(res.wasSuccessful());
	}

	@Test
	public void testContains() {
		HashMap_Chaining<Integer, Integer> map  = new HashMap_Chaining<Integer, Integer>();
		assertFalse(map.contains(1));
		map.put(1, 11);
		assertTrue(map.contains(1));
		assertFalse(map.contains(11));
		
	}

	@Test
	public void testGet() {
		HashMap_Chaining<Integer, Integer> map  = new HashMap_Chaining<Integer, Integer>();
		assertNull(map.get(-9));
		map.put(24, 111);
		assertNull(map.get(-9));
		assertNotNull(map.get(24));
		map.put(-9, 1);
		assertNotNull(map.get(-9));
	}

	@Test
	public void testPut() {
		HashMap_Chaining<Integer, Integer> map  = new HashMap_Chaining<Integer, Integer>();
		map.put(1, 1);
		map.put(1, 2);
	}

	@Test
	@Ignore
	public void testKeys() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	@Ignore
	public void testDelete() {
		fail("Not yet implemented"); // TODO
	}

}
