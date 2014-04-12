package com.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.gui.ButtonPanel;
import com.gui.ChatRoom;

public class Client extends Thread {

	private JFrame chatRoom;
	private final String LOCALHOST="localhost";
	private final int PORT=9999;
	private String clientName;
	private Socket socket;
	private InetAddress address;

	private static PrintWriter sendStream;

	public Client(String clientName) {
		this.clientName=clientName;
		chatRoom=ChatRoom.getChatRoom();
	}

	public void run(){
		try{
			try {
				address=InetAddress.getByName(LOCALHOST);
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(chatRoom, "Cannot find host server","Connection Failed",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}

			try {
				socket=new Socket(address,PORT);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(chatRoom, "Cannot create connection","Connection Failed",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}

			try {
				sendStream=new PrintWriter(socket.getOutputStream(),true);
				sendStream.println(clientName+": commandLogin");
				ButtonPanel.setSendStream(sendStream);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(chatRoom, "Unable to send messages","failure",JOptionPane.ERROR_MESSAGE);	
				e.printStackTrace();
				System.exit(0);
			}

			Recieve recieve=new Recieve(clientName,socket);
			recieve.start();

			while(recieve.isAlive()){
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		} finally{
			if(socket!=null){
				JOptionPane.showMessageDialog(chatRoom, "There was some problem in closing connection","Closing Failed",JOptionPane.ERROR_MESSAGE);	
			}
		}
	}
	
	public static PrintWriter getSendStream(){
		return Client.sendStream;
	}

}
