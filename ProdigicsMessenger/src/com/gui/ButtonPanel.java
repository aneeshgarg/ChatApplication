package com.gui;

import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ButtonPanel implements ActionListener{

	private static JPanel buttonPanel;
	private JButton logout;
	private JButton send;
	private TextArea messageArea;
	private static PrintWriter sendStream;
	private String clientName;

	public ButtonPanel(){
		this.clientName=ChatRoom.getClientName();
		messageArea=MessagePanel.getMessageArea();

		GridLayout grid;
		buttonPanel=new JPanel(grid=new GridLayout(2,1));
		grid.setVgap(5);
		grid.setHgap(5);

		send=new JButton("Send");
		send.addActionListener(this);

		logout=new JButton("Logout");
		logout.addActionListener(this);

		buttonPanel.add(send);
		buttonPanel.add(logout);
	}

	public JPanel getButtonPanel() {
		return buttonPanel;
	}

	public static void setSendStream(PrintWriter sendStream) {
		ButtonPanel.sendStream = sendStream;
	}

	public static PrintWriter getSendStream() {
		return sendStream;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==send){
			if(messageArea.getText()!=""){
				String temp=messageArea.getText().replaceAll("\n","commandNewLine");
				sendStream.println(clientName+": "+temp);
				messageArea.setText("");
			}
		} 
		else if(e.getSource()==logout){
			int option=JOptionPane.showConfirmDialog(buttonPanel, "Do you want to really logout??","Logout",JOptionPane.YES_NO_OPTION);
			if(option==JOptionPane.YES_OPTION){
				sendStream.println(clientName+": commandLogout");
				System.exit(0);
			}
		}
	}

}
