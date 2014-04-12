package com.ttd.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author Aneesh Garg
 *
 */
public class MessagePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -3869327601949280613L;

	private static final String LOGOUT_LABEL = "Logout";
	private static final String SEND_LABEL = "Send";

	JScrollPane scrollPane;
	JTextArea messageArea;
	
	JButton send;
	JButton logout;
	
	public MessagePanel() {
		
		//Setting up message area
		JPanel messagePanel = new JPanel();
		messageArea = new JTextArea();
		messageArea.setBackground(Color.WHITE);
		scrollPane = new JScrollPane(messageArea);
		scrollPane.setVisible(true);
		messagePanel.add(scrollPane);
		messagePanel.setSize(ChatRoom.WIDTH - 200, 100);
		messagePanel.setVisible(true);
		
		//Setting up buttons area
		// Grid layout 2 rows 1 column vGap=hGap = 5
		JPanel buttonPanel = new JPanel(new GridLayout(2,1,5,5));
		buttonPanel.add(send = new JButton(SEND_LABEL));
		buttonPanel.add(logout = new JButton(LOGOUT_LABEL));
		send.addActionListener(this);
		logout.addActionListener(this);
		buttonPanel.setSize(200,100);
		buttonPanel.setVisible(true);
		
		//Setting up message panel's layout
		setLayout(new FlowLayout(FlowLayout.LEFT)); 
		add(messagePanel);
		add(buttonPanel);
		
		setSize(ChatRoom.WIDTH, 100);
		setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Button Hit");		
	}

}
