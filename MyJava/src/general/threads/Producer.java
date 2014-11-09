package general.threads;


public class Producer implements Runnable{
	
	private JobManager manager;
	
	public Producer(JobManager managerIn){
		manager = managerIn;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			manager.putJob(new Job(manager.getJobCount()));
		}
	}
}
