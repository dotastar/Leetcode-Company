package interview.epi.chapter20_parallel_computing;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A simple web server implements part of a simple HTTP server
 * 
 * @author yazhoucao
 * 
 */
public class Q4_Implement_A_Thread_Pool {

	private static final int NTHREADS = 100;
	private static final int SERVERPORT = 8080;
	private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(SERVERPORT);
		while (true) {
			final Socket conn = server.accept();
			Runnable task = new Runnable() {
				public void run() {
					System.out.println("Executing task..." + conn);
					// Worker.handleRequest(conn);
				}
			};
			exec.execute(task);
		}

	}
}
