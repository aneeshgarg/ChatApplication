package com.client;

import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import com.gui.ChatPanel;
import com.gui.ChatRoom;
import com.gui.UserPanel;

public class Recieve extends Thread{

	private String clientName;
	private Socket socket;
	private BufferedReader recieveStream;
	private JFrame chatRoom;
	private String message;
	private TextArea chatArea;
	private DefaultListModel listModel;
	private JList list;

	public Recieve(String clientName,Socket socket) {
		this.clientName=clientName;
		this.socket=socket;
		listModel=UserPanel.getListModel();
		chatArea=ChatPanel.getChatArea();
		chatRoom=ChatRoom.getChatRoom();
		list=UserPanel.getList();
		try{
			recieveStream=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch(IOException e){
			JOptionPane.showMessageDialog(chatRoom, "Unable to recieve messages...application will noe close...","failure",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}		
	}

	public void run(){
		do{
			try {
				message=recieveStream.readLine();
				message.replace("commandNewLine", "\n");
			} catch (SocketException se){
				JOptionPane.showMessageDialog(chatRoom, "Server Failure...application will now close...","Server Failure",JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}

			String firstPart=message.split(": ")[0];
			String secondPart=message.split(": ")[1];

			if(secondPart.equals("commandLogout")
					&& !firstPart.equalsIgnoreCase(clientName)){
				StringBuilder sb=new StringBuilder(chatArea.getText());
				sb.append(firstPart+" has logged out...").append("\n");
				chatArea.setText(sb.toString());
				chatArea.setCaretPosition(chatArea.getText().length());
			}
			else if(secondPart.equals("commandLogin")){
				StringBuilder sb=new StringBuilder(chatArea.getText());
				sb.append(firstPart+" has logged in...").append("\n");
				chatArea.setText(sb.toString());
				chatArea.setCaretPosition(chatArea.getText().length());
				listModel.addElement(firstPart);
				list.setModel(listModel);
				list.validate();
			} 
			else{
				StringBuilder sb=new StringBuilder(chatArea.getText());
				sb.append(message).append("\n");
				chatArea.setText(sb.toString());
				chatArea.setCaretPosition(chatArea.getText().length());
			}
		} while(!(message.split(": ")[0].equalsIgnoreCase(clientName) 
				&& message.split(": ")[1].equals("commandLogout")));
	}
}
