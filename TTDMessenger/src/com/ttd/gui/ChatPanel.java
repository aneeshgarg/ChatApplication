package com.ttd.gui;

import java.awt.Color;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author Aneesh Garg
 * 
 */
public class ChatPanel extends JPanel {
	private static final long serialVersionUID = 4511946731990922100L;

	private static final String WELCOME_MESSAGE = "<b>Welcome to The Tech Dork Messenger !!!<b>";
	private static final String TEXT_HTML = "text/html";

	private JScrollPane scrollPane;
	private JEditorPane chatArea;

	public ChatPanel() {
		setChatArea(new JEditorPane(TEXT_HTML, WELCOME_MESSAGE));
		getChatArea().setEditable(false);
		getChatArea().setBackground(Color.WHITE);
		getChatArea().setVisible(true);
		
		setScrollPane(new JScrollPane(getChatArea()));
		getScrollPane().setSize(ChatRoom.WIDTH-200, ChatRoom.HEIGHT-100);
		getScrollPane().setVisible(true);
		
		add(getScrollPane());
		setVisible(true);
		
		setSize(ChatRoom.WIDTH-200, ChatRoom.HEIGHT-100);
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JEditorPane getChatArea() {
		return chatArea;
	}

	public void setChatArea(JEditorPane chatArea) {
		this.chatArea = chatArea;
	}

}
