package general.threads;


public class Consumer implements Runnable{

	private JobManager manager;
	
	public Consumer(JobManager managerIn){
		manager = managerIn;
	}
		@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			Job job = manager.getJob();
			if(job==null)
				continue;
			
			job.calculate();
		}
	}
}
