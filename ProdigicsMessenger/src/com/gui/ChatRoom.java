package com.gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.client.Client;

public class ChatRoom{
	private static JFrame chatRoom;
	private JPanel chatPanel;
	private JPanel messagePanel;
	private JPanel userPanel;
	private JList userList;
	private JPanel buttonPanel;
	private static String clientName;
	
	public ChatRoom(){
		setChatRoom(new JFrame("Prodigics Messenger - Chat Room"));
		getChatRoom().setSize(800,600);
		getChatRoom().setLayout(new FlowLayout());
		getChatRoom().getContentPane().setLayout(new GridBagLayout());
		getChatRoom().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setClientName(JOptionPane.showInputDialog(getChatRoom(),"Please enter user name: ","Username",JOptionPane.INFORMATION_MESSAGE));
		if(getClientName()==null){
			JOptionPane.showMessageDialog(getChatRoom(), "You have not entered username. the application will now close");
			System.exit(0);
		}
		getChatRoom().setTitle(getChatRoom().getTitle()+" : "+getClientName());
		
		chatPanel=(new ChatPanel()).getChatPanel();
		messagePanel=(new MessagePanel()).getMessagePanel();
		//userPanel=(new UserPanel()).getUserPanel();
		userList=(new UserPanel()).getList();
		buttonPanel=(new ButtonPanel()).getButtonPanel();
		
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.weightx=10.0;			gbc.weighty=10.0;
		gbc.ipadx=0;				gbc.ipady=0;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.anchor=GridBagConstraints.NORTH;
		
		Insets i=new Insets(5,5,5,5);
		gbc.insets=i;
		
		gbc.gridx=0;				gbc.gridy=0;
		gbc.gridwidth=5;			gbc.gridheight=8;
		getChatRoom().getContentPane().add(chatPanel,gbc);
		
		gbc.gridx=4;				gbc.gridy=0;
		gbc.gridwidth=1;			gbc.gridheight=8;
		getChatRoom().getContentPane().add(userList,gbc);
		
		gbc.gridx=0;				gbc.gridy=8;
		gbc.gridwidth=5;			gbc.gridheight=2;
		getChatRoom().getContentPane().add(messagePanel,gbc);
		
		gbc.gridx=5;				gbc.gridy=8;
		gbc.gridwidth=1;			gbc.gridheight=2;
		getChatRoom().getContentPane().add(buttonPanel,gbc);
		
		getChatRoom().setVisible(true);
	}
	
	public static void setChatRoom(JFrame chatRoom) {
		ChatRoom.chatRoom = chatRoom;
	}

	public static JFrame getChatRoom() {
		return chatRoom;
	}

	public static void setClientName(String clientName) {
		ChatRoom.clientName = clientName;
	}

	public static String getClientName() {
		return clientName;
	}

	@SuppressWarnings("unused")
	public static void main(String args[]){
		ChatRoom chatRoom=new ChatRoom();
		Client client=new Client(getClientName());
		client.start();		
	}


}
