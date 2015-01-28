package interview.epi.chapter20_parallel_computing;

import java.util.Date;

/**
 * Implement a read-write lock in an object like a cache.
 * 
 * @author yazhoucao
 * 
 */
public class Q7_The_Readers_Writers_Problem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

class Reader extends Thread {
	public void run() {
		while (true) {
			synchronized(Cache.ReadLock){
				Cache.ReadCount++;
			}
			System.out.println(Cache.data);
			synchronized(Cache.ReadLock){
				Cache.ReadCount--;
				Cache.ReadLock.notify();
			}

			// do something else
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Writer extends Thread {
	public void run() {
		while (true) {
			synchronized (Cache.WriteLock) {
				boolean done = false;
				while(!done){
					synchronized(Cache.ReadLock){
						if (Cache.ReadCount == 0) {
							Cache.data = new Date().toString();
							done = true;
						} else {
							try { // use wait/notify to avoid busy waiting
								// protect against spurious notify, see
								// stackoverflow do-spurious-wakeups-actually-happen
								while (Cache.ReadCount != 0) {
									Cache.ReadLock.wait();
								}
							} catch (InterruptedException e) {
								System.out.println("InterruptedException in Writer wait");
							}
						}
					}
				}
			}
		}
	}
}

class Cache {
	static Object ReadLock = new Object();
	static Object WriteLock = new Object();
	static int ReadCount = 0;
	static String data = null;
}