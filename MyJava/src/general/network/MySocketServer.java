package general.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MySocketServer {

	private ServerSocket server;
	private final static Logger logger = Logger.getLogger(MySocketServer.class.getName());

	private final static int BufferRcvSize = 10240;
	
	public static void setLogingProperties(String logPath, Level showLevel){
		try {		
			FileHandler fileHandler = new FileHandler(logPath);
			fileHandler.setFormatter(MyLogFormatter.getInstance());
			logger.addHandler(fileHandler);
			logger.setLevel(showLevel);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Initialize fileHandler exception!", e);
		}
	}
		
	public MySocketServer(int port){
		setLogingProperties("./server.log", Level.INFO);
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Initialize server exception!", e);
		}
	}
	
	public void start(){
		logger.info("Begin listening...");
		while(true){
			try {
				Socket client = server.accept();
				logger.info("Socket accepted...");
				invoke_multiThread(client);
				//invoke(socket);	//single thread
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Accept socket exception!", e);
			}
		}
	}
	
	private void invoke_multiThread(final Socket socket){
		new Thread(new Runnable(){
			@Override
			public void run() {
				invoke(socket);
			}
		}).start();
	}
	
	private void invoke(Socket socket){
		BufferedReader reader = null;
		PrintWriter writer = null;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream());
			if(socket.isConnected()&&!socket.isInputShutdown()&&!socket.isOutputShutdown()){
				char[] bufferRcv = new char[BufferRcvSize];
				reader.read(bufferRcv);
				//processing msg...
				logger.info(String.valueOf(bufferRcv));
				writer.println("Msg received!");
				writer.println(Tools.getData_10Kb());
				writer.flush();
			}
			
			logger.info("Socket disconnected!\n");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Invoke socket client exception!", e);
		}
		finally {  
            try {  
                writer.close();  
            } catch (Exception e) {}
            try {  
                reader.close();  
            } catch (Exception e) {}
            try {  
                socket.close();  
            } catch (Exception e) {}
        }
	}
	
	
	
	public static void main(String[] args){
		if(args.length<=1){
			int port = 8001;	//default
			try{
				if(args.length==1)
					port = Integer.valueOf(args[0]);
			}catch(Exception e){
				System.out.println(e.toString());
			}
			
			MySocketServer myserver = new MySocketServer(port);
			myserver.start();
		}else{
			logger.log(Level.SEVERE, "Number of arguments is wrong!");
		}
		
	}
}
