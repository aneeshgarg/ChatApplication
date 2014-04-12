package com.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.TextArea;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MessagePanel {

	private static JPanel messagePanel;
	private static TextArea messageArea;
	private static JScrollPane scrollPane;
	
	public MessagePanel(){
		messagePanel=new JPanel(new FlowLayout());
		messageArea=new TextArea();
		scrollPane=new JScrollPane(messageArea);
		
		messageArea.setBackground(Color.WHITE);
		
		messagePanel.add(scrollPane);
		messagePanel.setSize(50,50);
		
		scrollPane.setVisible(true);
		messagePanel.setVisible(true);
	}

	public JPanel getMessagePanel() {
		return messagePanel;
	}

	public static void setMessageArea(TextArea messageArea) {
		MessagePanel.messageArea = messageArea;
	}

	public static TextArea getMessageArea() {
		return messageArea;
	}

}
