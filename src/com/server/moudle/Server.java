/*
 * Server, waiting for connection from client
 */
package com.server.moudle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.common.Message;
import com.common.User;

public class Server {
	
	private boolean isServerClose=false;
	ServerSocket ss;
	Socket s=null;
	int port=9999;
	
	public Server(){
		if(!isServerClose){
			StartServer();
		}else{
			try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void StartServer(){
		try {
			System.out.println("Server start listening port: 9999");
			ss = new ServerSocket(port);
			while(true){

				//block and waiting
				s=ss.accept();
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				User u=(User)ois.readObject();
				System.out.println("User ID : "+u.getId()+" Password: "+u.getPassword().toString());
				
				//forward
				Message msg=new Message();
				ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
				if(u.getPassword().toString().equals("1")){
				//return login success
				msg.setMsgType("1");
				oos.writeObject(msg);
				
				//when C/S connected successful, server open a new thread for communication
				ServerConnectClientThread scct=new ServerConnectClientThread(s);
				
				//add the thread into ManageClientSockets
				ManageServerToClientThread.addThread(u.getId(), scct);
				scct.start();
				
				//reflect all the online users
				scct.ReflectAllOnlineUsers(u.getId());
				
			}else{			
				msg.setMsgType("2");
				oos.writeObject(msg);
				s.close();
			}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean isServerClose() {
		return isServerClose;
	}

	public void setServerClose(boolean isServerClose) {
		this.isServerClose = isServerClose;
	}

	

}
