/*
 * manage the sockets from clients
 */
package com.server.moudle;

import java.util.HashMap;
import java.util.Iterator;

public class ManageServerToClientThread {

	//only one HashMap in server end
	public static HashMap hm=new HashMap<String, ServerConnectClientThread>();
	//add the connection thread to HashMap hm
	public static void addThread(String userId, ServerConnectClientThread scct){
		hm.put(userId, scct);
	}
	
	public static ServerConnectClientThread getClientThread(String userId){
		return (ServerConnectClientThread)hm.get(userId);
		//cannot get the ID from offline user
	}
	
	public static String getOnlineUser(){
		Iterator it=hm.keySet().iterator();
		String onlineUser="";
		while(it.hasNext()){
			onlineUser+=it.next().toString()+" ";
		}
		return onlineUser;
	}
}
