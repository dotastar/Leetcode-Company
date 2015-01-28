package interview.epi.chapter20_parallel_computing;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

/**
 * Consider a web-based calendar in which the server hosting the calendar has to
 * perform a task when the next calendar events takes place. (The task could be
 * sending an email or a SMS.) Your job is to design a facility that manages the
 * execution of such tasks.
 * 
 * Develop a Timer class that manages the execution of deferred tasks. The Timer
 * constructor takes as its argument an object which includes a Run method and a
 * name field, which is a string. Timer must support
 * 1). start a thread, identified by name, at a given time in the future;
 * 2). canceling a thread, identified by name (the cancel request is to be
 * ignored if the thread has already started).
 * 
 * @author yazhoucao
 * 
 */
public class Q6_Implement_A_Timer_Class {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static class Timer extends Thread {
		private Map<String, Entry<Date, Runnable>> events;
		private PriorityQueue<Entry<Date, Runnable>> minHeap;

		public Timer(String name) {
			events = new HashMap<>();
			minHeap = new PriorityQueue<>();
		}

		public void addEvent(String name, Date date, Runnable e) {
			Entry<Date, Runnable> entry = new KV<Date, Runnable>(date, e);
			minHeap.add(entry);
			events.put(name, entry);
		}

		public void cancel(String name) {
			if (events.containsKey(name)) {
				Entry<Date, Runnable> event = events.get(name);
				minHeap.remove(event);
				events.remove(name);
			}
		}

		@Override
		public void run() {
			// 
		}
	}

	private static class KV<K, V> implements Entry<K, V> {
		private K key;
		private V val;

		public KV(K kIn, V vIn) {
			key = kIn;
			val = vIn;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return val;
		}

		@Override
		public V setValue(V value) {
			return val = value;
		}

		@Override
		public int hashCode() {
			return key == null ? 0 : key.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof KV) {
				@SuppressWarnings("unchecked")
				KV<K, V> kv = (KV<K, V>) o;
				return key.equals(kv.getKey()) && val.equals(kv.getValue());
			} else
				return false;
		}
	}
}
