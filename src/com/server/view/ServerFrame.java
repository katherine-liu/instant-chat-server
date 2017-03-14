/*
 * Server Control pad
 */
package com.server.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.server.moudle.Server;

public class ServerFrame implements ActionListener{
	
	public static void main(String[] args) {
		new ServerFrame();

	}
	
	private JFrame jframe=new JFrame();
	private JPanel jpanel=null;
	private JButton jbStart=null, jbClose=null;
	private Server server=null;
	public ServerFrame(){
		jpanel=new JPanel();
		jbStart=new JButton("Start");
		jbStart.addActionListener(this);
		jbClose=new JButton("Close");
		jbClose.addActionListener(this);
		jpanel.add(jbStart);
		jpanel.add(jbClose);
		
		jframe.add(jpanel);
		jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
		jframe.setSize(400, 300);
		jframe.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getSource()==jbStart){
			server=new Server();
		}
		else if(arg0.getSource()==jbClose){
			
			server.setServerClose(true);
			
//			server.ss.close();
			System.out.println("ServerFrame close Server");
		}
		
	}

}
