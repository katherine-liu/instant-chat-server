/*
 * function: between Server and Client communication thread
 */
package com.server.moudle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import com.common.Message;
import com.common.MessageType;

public class ServerConnectClientThread extends Thread{
	Socket s;
	

	public ServerConnectClientThread(Socket s) {
		//s is the connection between server and client
		this.s = s;
	}
	
	public void ReflectAllOnlineUsers(String ownerId){
		HashMap hm=ManageServerToClientThread.hm;
		Iterator it=hm.keySet().iterator();
		while(it.hasNext()){
			
			String onlineUserId=it.next().toString();
			Message msg=new Message();
			try {
				ObjectOutputStream oos=new ObjectOutputStream(
						ManageServerToClientThread.getClientThread(onlineUserId).s.getOutputStream());
				msg.setContent(ownerId);
				msg.setMsgType(MessageType.getResponseOnlineRequire());
				msg.setReceiverId(onlineUserId);
				oos.writeObject(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void run(){
		while(true){
			//the thread can receive from client
			try {
				ObjectInputStream ois=new ObjectInputStream(this.s.getInputStream());
				Message msg=(Message)ois.readObject();
				System.out.println(msg.getSenderId()+" sent to "+msg.getReceiverId()+" content: "+msg.getContent());
				if(msg.getMsgType().equals(MessageType.getChatContent())){
					//forward
					//to get the thread
					ServerConnectClientThread scct=ManageServerToClientThread.getClientThread(msg.getReceiverId());
					ObjectOutputStream oos=new ObjectOutputStream(scct.s.getOutputStream());
					oos.writeObject(msg);
					
				}else if(msg.getMsgType().equals(MessageType.getRequireOnline())){
					System.out.println(msg.getSenderId()+" request the list");
					String onlineUser=ManageServerToClientThread.getOnlineUser();
					Message msgRequireOnline=new Message();
					msgRequireOnline.setMsgType(MessageType.getResponseOnlineRequire());
					msgRequireOnline.setContent(onlineUser);
					msgRequireOnline.setReceiverId(msg.getSenderId());
					
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(msgRequireOnline);
					
				}
				
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
