package general.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class MySocket {
	
	
	public static void main(String args[]){
		String ip = "192.168.1.74";//"127.0.0.1";//"192.168.1.42";
		int port  = 8001;						  //{'engine':'icf','params':{'k':10,'hot':1,'time':1.0E-4,'like':1,'result':8}}
//		String videoAlgoSwitch1 = "{'algorithm_switch_req':{'engine':'ucf'}}";
//		String videoAlgoSwitch2 = "{'algorithm_switch_req':{'engine':'icf'}}";
//		//ICF - Time test
//		String videoSetpara1 = "{'video_setpara_req':{'engine':'icf','params':{'k':100,'hot':1,'time':1.0E-5,'like':1,'result':25}}}";
//		String videoSetpara2 = "{'video_setpara_req':{'engine':'icf','params':{'k':100,'hot':1,'time':1.0,'like':1,'result':25}}}";	
//		//ICF - Like test 
//		String videoSetpara3 = "{'video_setpara_req':{'engine':'icf','params':{'k':100,'hot':1,'time':1.0E-5,'like':1,'candidate':12,'result':25}}}";
//		String videoSetpara4 = "{'video_setpara_req':{'engine':'icf','params':{'k':100,'hot':1,'time':1.0E-5,'like':100,'candidate':12,'result':25}}}";
//		//UCF
//		String videoSetpara5 = "{'video_setpara_req':{'engine':'ucf','params':{'k':100,'hot':1,'time':0.01,'like':1,'candidate':12,'result':25}}}";
//		String videoSetpara6 = "{'video_setpara_req':{'engine':'ucf','params':{'k':200,'hot':1,'time':0.01,'like':1,'candidate':12,'result':25}}}";
//		
		String videoPreview1 = " {'video_preview_req':{'user_id':'1011','video_id':'2fa12b61b60043fa8dfcb3005db24f55'}}";
//		String videoPreview2 = " {'video_preview_req':{'user_id':'2011','video_id':'59554159d31a403ea4464751fb97d065'}}";
//		String videoPreview3 = " {'video_preview_req':{'user_id':'695427','video_id':'c26c90a15b8d48f7964bd09fffe9244f'}}";
//		String videoguess = "{'videoguess':{'ids':[1,15,11]}}";
//		String setInterfere = "{'setVideInterfereFilter':{'scence_id':1,'type'}}";
//		String videoRecommend = " {'video_recommend_req':{'user_id':'10007','video_id':'afc76d888e7f4e9ab08cbf84a5f4a409','play_date':1370678280000}}";

		String request = null;
		
		try {
			request = videoPreview1;
			System.out.println("Begin request : " + request);
			
			Socket client = new Socket(ip, port);				
			PrintWriter writer = new PrintWriter(client.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));	
			writer.println(request);
			
			//Test
			//String data = Tools.getData_10Kb();
			//writer.println(data);
			
			writer.flush();
			//client.close();
			char[] result = new char[8570];
			reader.read(result);
			
			for(char c : result){
				System.out.print(c);
			}
				
			//writer.close();
			//reader.close();
			client.close();
			System.out.println("Socket closed!");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("End!");
		//System.out.println("End!");
	}
}
