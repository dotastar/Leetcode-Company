package general.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Producer Comsumer Pattern
 * @author yazhoucao
 *
 */
public class ProducerConsumerPattern {

	public static void main(String[] args) {
		JobManager manager = new JobManager(0, 100);

		Thread c1 = new Thread(new Consumer(manager));
		Thread c2 = new Thread(new Consumer(manager));
		Thread c3 = new Thread(new Consumer(manager));
		Thread c4 = new Thread(new Consumer(manager));
		Thread c5 = new Thread(new Consumer(manager));
		Thread c6 = new Thread(new Consumer(manager));

		Thread p1 = new Thread(new Producer(manager));

		c1.start();
		c2.start();
		c3.start();
		c4.start();
		c5.start();
		c6.start();

		p1.start();
	}

	public static class Job {
		private final static long MAXLOOP = 1050;

		private int jobID;

		public Job(int idIn) {
			jobID = idIn;
		}

		public int getID() {
			return jobID;
		}

		public void calculate() {
			System.out.println("begin this job...");
			for (int i = 0; i < MAXLOOP; i++) {
				for (int j = 0; j < MAXLOOP; j++) {
					for (int k = 0; k < MAXLOOP; k++) {

					}
				}
			}

			System.out.println("finish this job!  " + jobID);
		}

	}

	public static class Producer implements Runnable {

		private JobManager manager;

		public Producer(JobManager managerIn) {
			manager = managerIn;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				manager.putJob(new Job(manager.getJobCount()));
			}
		}
	}

	public static class Consumer implements Runnable {

		private JobManager manager;

		public Consumer(JobManager managerIn) {
			manager = managerIn;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				Job job = manager.getJob();
				if (job == null)
					continue;

				job.calculate();
			}
		}
	}

	public static class JobManager {

		private BlockingQueue<Job> jobs = new LinkedBlockingQueue<Job>();
		private final int MinJobs;
		private final int MaxJobs;
		private int jobCount;

		public JobManager(int min, int max) {
			MinJobs = min;
			MaxJobs = max;
		}

		public int getJobCount() {
			return jobCount;
		}

		public synchronized void putJob(Job job) {
			while (jobs.size() > MaxJobs) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			jobs.add(job);
			++jobCount;
			notify();
			System.out.println("Create one job!  " + job.getID());
		}

		public synchronized Job getJob() {
			while (jobs.size() <= MinJobs) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Job job = jobs.poll();
			notify();
			System.out.println("Get one job!");
			return job;
		}

	}

}
