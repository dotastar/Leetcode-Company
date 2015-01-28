package interview.epi.chapter20_parallel_computing;

/**
 * Implement a Requestor class. The execute method may take an indeterminate
 * amount of time to return; it may never return. You need to have a time-out
 * mechanism for this. Assume Requestor Objects have an Error method that you
 * can invoke.
 * 
 * A Requestor class implements a Dispatch method which takes a Requestor
 * object. The Requestor object includes a request string, a
 * ProcessResponse(string response) method, and an Execute method that takes a
 * string and returns a string. Dispatch is to create a new thread which invokes
 * Execute on request. When Execute returns, Dispatch invokes the ProcessResonse
 * method on the response.
 * 
 * @author yazhoucao
 * 
 */
public class Q5_Implement_Asynchronous_Callbacks {

	public static void main(String[] args) {
		Requestor requestor = new Requestor();
		Requestor.dispatch(requestor, "\"Request ABC\"", 1000);
	}

	public static class Requestor {
		private static final long TIMEOUT = 5000;

		/**
		 * Process response
		 */
		public void processResponse(String response) {
			System.out.println("Process Response: " + response);
		}

		/**
		 * Execute request
		 */
		public String execute(String req, long delay) {
			String response = null;
			try {
				// simulate the time taken to perform a computation
				System.out.println("Executing " + req);
				response = "'Response to " + req + "'";
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				return error(req);
			}
			return response;
		}

		/**
		 * Dispatch request to Requestor
		 */
		public static void dispatch(final Requestor r, final String req,
				final long delay) {
			Runnable task = new Runnable() {
				public void run() {
					Runnable actualTask = new Runnable() {
						public void run() { // callback
							String response = r.execute(req, delay);
							r.processResponse(response);
						}
					};
					Thread innerThread = new Thread(actualTask);
					innerThread.start(); // here asynchronous happens
					try {
						Thread.sleep(TIMEOUT);
						innerThread.interrupt();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};

			new Thread(task).start(); // dispatching starts
		}

		private String error(String s) {
			System.err.println(s);
			return s;
		}
	}
}
