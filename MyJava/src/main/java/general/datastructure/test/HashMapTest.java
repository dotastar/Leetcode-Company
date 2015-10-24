package general.datastructure.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import general.datastructure.HashMap_Chaining;
import general.datastructure.HashMap_Probing;
import general.datastructure.SymbolTable;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class HashMapTest {
	String type = "probe";
	
	public static void main(String[] args){
		Result res = JUnitCore.runClasses(HashMapTest.class);
		int errNo = 1;
		for(Failure fail : res.getFailures()){
			System.out.println(errNo+"."+fail.toString()+"\n"+fail.getTrace().toString());
			errNo++;
		}
		
		System.out.println("Total Tests: "+res.getRunCount());
		System.out.println("Failures : "+res.getFailureCount());
	}
	
	public <K, V> SymbolTable<K, V> createHashMap(String type){
		switch(type){
		case "chain":
			return new HashMap_Chaining<K, V>();
		case "probe":
			return new HashMap_Probing<K, V>();
		default:
			throw new IllegalArgumentException("Illegal Type: "+type);
		}
	}
	
	public <K, V> SymbolTable<K, V> createHashMap(String type, int size){
		switch(type){
		case "chain":
			return new HashMap_Chaining<K, V>(size);
		case "probe":
			return new HashMap_Probing<K, V>(size);
		default:
			throw new IllegalArgumentException("Illegal Type: "+type);
		}
	}

	@Test
	public void testContains_Int() {
		SymbolTable<Integer, Integer> map = createHashMap(type);
		assertFalse(map.contains(1));
		map.put(1, 11);
		assertTrue(map.contains(1));
	}
	
	@Test
	public void testContains_Str() {
		SymbolTable<String, String> map  = createHashMap(type);
		assertFalse("Empty map", map.contains("1"));
		map.put("1#! ", "11");
		assertTrue("After put '1#! '", map.contains("1#! "));
	}
	
	@Test
	public void testContains_Str_SpecialChar() {
		SymbolTable<String, String> map  = createHashMap(type);
		assertFalse("Test space", map.contains(" "));
		map.put(" ", "Space");
		assertTrue("Test space", map.contains(" "));
	}
	
	
	@Test
	public void testContains_Obj() {
		SymbolTable<Object, Object> map  = createHashMap(type);
		assertFalse("Empty map", map.contains("1"));
		map.put(new Integer(1), new Object());
		assertTrue("After put object(1)", map.contains(new Integer(1)));
	}

	@Test
	public void testGet_False() {
		SymbolTable<Integer, Integer> map  = createHashMap(type);
		assertNull(map.get(-9));
	}
	
	@Test
	public void testGet_Null() {
		SymbolTable<Integer, Integer> map  = createHashMap(type);
		map.put(-9, 111);
		assertNull(map.get(null));
	}
	
	@Test
	public void testGet_True() {
		SymbolTable<Integer, Integer> map  = createHashMap(type);
		map.put(-9, 111);
		assertEquals(new Integer(111), map.get(-9));
	}


	@Test
	public void testPut_Insert() {
		SymbolTable<Integer, Integer> map  = createHashMap(type);
		map.put(1, 111);
		assertEquals(new Integer(111), map.get(1));
	}
	
	@Test
	public void testPut_Update() {
		SymbolTable<Integer, Integer> map  = createHashMap(type);
		map.put(1, 111);
		map.put(1, 112);
		assertEquals(new Integer(112), map.get(1));
	}
	
	
	@Test
	public void testPut_Delete() {
		SymbolTable<Integer, Integer> map  = createHashMap(type);
		map.put(1, 111);
		map.put(1, null);
		assertNull(map.get(1));
	}

	@Test
	public void testPut_Resize() {
//		long start = System.currentTimeMillis();
		
		int capacity = 7;
		int threshold = 100;
		SymbolTable<Integer, Integer> map  =  createHashMap(type, capacity);
		for(int i=1; i<=threshold; i++){
			map.put(i, i);
		}
		assertTrue(map.capacity()>capacity);
//		long end = System.currentTimeMillis();
//		System.out.println(end-start);
	}
	
	@Test
	public void testPut_NullKey() {
		SymbolTable<Integer, Integer> map  = createHashMap(type);
		map.put(null, 1);
		assertTrue(map.size()==0);
	}
	
	@Test
	public void testPut_NullVal() {
		SymbolTable<Integer, Integer> map  = createHashMap(type);
		map.put(1, 111);
		map.put(1, null);
		assertFalse(map.contains(1));
	}

	@Test
	public void testDelete_Null() {
		SymbolTable<Integer, Integer> map  = createHashMap(type);
		map.delete(null);
	}

	@Test
	public void testDelete_NonExist() {
		SymbolTable<Integer, Integer> map  = createHashMap(type);
		map.delete(1);
	}

	@Test
	public void testDelete_Exist() {
		SymbolTable<Integer, Integer> map  = createHashMap(type);
		map.put(1, 1);
		map.delete(1);
		assert map.isEmpty();
		assertFalse(map.contains(1));
	}


	@Test
	public void testDelete_Resize() {
		int initCapacity = 7;
		SymbolTable<Integer, Integer> map  = createHashMap(type, initCapacity);
		int size = 100;
		assert map.capacity()==initCapacity;
		for(int i=0; i<size; i++)	//add number of size elements
			map.put(i, i);
		assert map.size()==size;
		int currCapacity = map.capacity();
		assert currCapacity>initCapacity;
		
		for(int i=size; i>0; i--)	//remove all the elements
			map.delete(i);
		assertTrue(map.capacity()+"!="+initCapacity, map.capacity()==initCapacity);
	}
	

	@Test
	public void testKeys() {
		SymbolTable<Integer, Integer> map = createHashMap(type);
		Set<Integer> set = new HashSet<Integer>();
		for(int i=0; i<10; i++){
			map.put(i, i);
			set.add(i);
		}
		assert set.size()==10;
		
		Iterable<Integer> keys = map.keys();
		for(Integer k : keys)
			set.remove(k);
		
		assertTrue(set.size()==0);
	}
	
	@Test
	public void testSize_Add(){
		SymbolTable<Integer, Integer> map = createHashMap(type);
		for(int i=0; i<500; i++)
			map.put(i, i);
		assertTrue(map.size()==500);
	}
	
	
	@Test
	public void testSize_AddDeleteUpdate(){
		SymbolTable<Integer, Integer> map = createHashMap(type);
		for(int i=0; i<500; i++)	//add 0~499
			map.put(i, i);
		assert map.size()==500;
		
		for(int i=0; i<100; i++)	//delete 0~99
			map.put(i, null);
		assert map.size()==400;
		
		for(int i=100; i<200; i++)	//delete 100~199
			map.delete(i);
		assert map.size()==300;
		
		for(int i=0; i<200; i++)	//check 0~199
			assert !map.contains(i);
		
		for(int i=200; i<300; i++)	//check before update
			assert map.get(i)==i;
		
		for(int i=200; i<300; i++)	//update
			map.put(i, i+100);
		
		for(int i=200; i<300; i++)	//check after update
			assertTrue(map.get(i)+"!="+(i+100), map.get(i)==(i+100));
	}
	
	/**
	 * This need to manually see the value distribution 
	 */
	public void testValueDisperse(){
		int capacity = 100;
		SymbolTable<Integer, Integer> map = createHashMap(type, capacity);
		Random ran = new Random();
		for(int i=0; i<50; i++)
			map.put(ran.nextInt(1000), i);
		assert map.size()==50;
//		Iterable<Integer> iter = map.keys();
//		int no = 1;
//		for(Integer k : iter)
//			System.out.println(no++ +"."+k+"\t"+map.get(k));
	}
}
