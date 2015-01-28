package interview.epi.chapter20_parallel_computing;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Thread T1 prints odd numbers from 1 to 100;
 * Thread T2 prints even numbers from 1 to 100;
 * Write Java code in which the two threads running concurrently, print the
 * numbers from 1 to 100 in order.
 * 
 * @author yazhoucao
 * 
 */
public class Q3_Implement_Synchronization_For_Two_Interleaving_Threads {

	public static void main(String[] args) {
		toggle = new AtomicBoolean(true);
		Thread t1 = new Thread(new OddPrinter(toggle));
		Thread t2 = new Thread(new EvenPrinter(toggle));
		t1.start();
		t2.start();
	}

	// a shared state for controlling the print process,
	// if true print odd, else false print even.
	private static AtomicBoolean toggle;

	private static class OddPrinter implements Runnable {
		AtomicBoolean toggle;
		int number;

		public OddPrinter(AtomicBoolean togl) {
			this.toggle = togl;
			number = 1;
		}

		@Override
		public void run() {
			while (number <= 100) {
				if (toggle.get()) {
					System.out.println(number);
					number += 2;
					toggle.set(false);
				}

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static class EvenPrinter implements Runnable {
		AtomicBoolean toggle;
		int number;

		public EvenPrinter(AtomicBoolean togl) {
			this.toggle = togl;
			number = 2;
		}

		@Override
		public void run() {
			while (number <= 100) {
				if (!toggle.get()) {
					System.out.println(number);
					number += 2;
					toggle.set(true);
				}

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
