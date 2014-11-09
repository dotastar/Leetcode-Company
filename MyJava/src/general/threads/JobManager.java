package general.threads;

import java.util.LinkedList;
import java.util.Queue;

public class JobManager {
 
	private Queue<Job> jobs = new LinkedList<Job>();
	private final int MinJobs;
	private final int MaxJobs;
	private int jobCount;
	
	public JobManager(int min, int max){
		MinJobs = min;
		MaxJobs = max;
	}
	
	public int getJobCount(){
		return jobCount;
	}

	public synchronized void putJob(Job job){
		while(jobs.size()>MaxJobs){
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

	public synchronized Job getJob(){
		while(jobs.size()<=MinJobs){
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


	public static void main(String[] args){
		JobManager manager = new JobManager(0,100);

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

}
