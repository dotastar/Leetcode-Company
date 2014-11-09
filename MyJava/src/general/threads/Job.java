package general.threads;

public class Job {
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

	public static void main(String[] args) {
		Job job = new Job(1);
		long time1 = System.currentTimeMillis();
		job.calculate();
		long time2 = System.currentTimeMillis();
		System.out.println("Cost time:" + (time2 - time1));
	}
}
