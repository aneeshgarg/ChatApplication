package com.ttd.gui;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.ttd.interfaces.Context;

/**
 * @author Aneesh Garg
 * 
 */
public class ChatRoom extends JFrame implements Context {

	private static final String TITLE = "The Tech Dork Messenger Chat Room";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	private static final long serialVersionUID = -3717600338345444183L;

	private String clientName;

	public ChatRoom() {
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//this.setClientName(JOptionPane.showInputDialog(this,"Please enter your username: ", "Username", JOptionPane.INFORMATION_MESSAGE));
		this.setClientName("Aneesh");
		if (this.getClientName() == null) {
			JOptionPane.showMessageDialog(this, "You have not entered username. Meesenger will now close.");
			System.exit(0);			
		}
		
		setTitle(getTitle() + " : " + this.getClientName());
		
		getContentPane().setLayout(new FlowLayout());
		add(new ChatPanel());
		add(new MessagePanel());
		setVisible(true);
	}

	public static void main(String[] args) {
		new ChatRoom();
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

}
