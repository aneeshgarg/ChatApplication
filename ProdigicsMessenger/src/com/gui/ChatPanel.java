package com.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.TextArea;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ChatPanel {

	private static JPanel chatPanel;
	private static TextArea chatArea;
	private static JScrollPane scrollPane;
	
	public ChatPanel() {
		chatPanel=new JPanel(new FlowLayout());
		chatArea=new TextArea("Welcome to Prodigics...\n");
		chatArea.setEditable(false);
		scrollPane=new JScrollPane(chatArea);
		chatArea.setBackground(Color.WHITE);
		
		chatPanel.add(scrollPane);
		scrollPane.setVisible(true);
		chatPanel.setVisible(true);
	}

	public JPanel getChatPanel() {
		return chatPanel;
	}

	public static void setChatArea(TextArea chatArea) {
		ChatPanel.chatArea = chatArea;
	}

	public static TextArea getChatArea() {
		return chatArea;
	}
}
