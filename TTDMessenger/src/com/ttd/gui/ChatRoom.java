package com.ttd.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.ttd.interfaces.Context;

/**
 * @author Aneesh Garg
 * 
 */
public class ChatRoom extends JFrame implements Context, ActionListener, ListSelectionListener {

	private static final long serialVersionUID = -3717600338345444183L;

	private static final String TITLE = "The Tech Dork Messenger Chat Room";
	private static final String LOGOUT_LABEL = "Logout";
	private static final String SEND_LABEL = " Send ";
	private static final String WELCOME_MESSAGE = "<b>Welcome to The Tech Dork Messenger !!!<b>";
	private static final String TEXT_HTML = "text/html";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	private JScrollPane chatScrollPane;
	private JEditorPane chatArea;
	
	private JScrollPane userScrollPane;
	private DefaultListModel<String> listModel;
	private JList<String> list;

	private JScrollPane messageScrollPane;
	private JTextArea messageArea;

	private JButton send;
	private JButton logout;

	private String clientName;

	public ChatRoom() {
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// this.setClientName(JOptionPane.showInputDialog(this,"Please enter your username: ",
		// "Username", JOptionPane.INFORMATION_MESSAGE));
		this.setClientName("Aneesh");
		if (this.getClientName() == null) {
			JOptionPane.showMessageDialog(this,
					"You have not entered username. Messenger will now close.");
			System.exit(0);
		}

		setTitle(getTitle() + " : " + this.getClientName());
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();	
		gbc.insets = new Insets(5, 5, 5, 5);
		//Setting up chat area
		{
			setChatArea(new JEditorPane(TEXT_HTML, WELCOME_MESSAGE));
			getChatArea().setEditable(false);
			getChatArea().setBackground(Color.WHITE);
			getChatArea().setBorder(border);
			getChatArea().setVisible(true);
			
			setChatScrollPane(new JScrollPane(getChatArea()));
			getChatScrollPane().setVisible(true);
			getChatScrollPane().setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			getChatScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			gbc.ipadx = gbc.ipady = 5;
			gbc.weightx = 4.0;
			gbc.weighty = 7.0;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.anchor = GridBagConstraints.NORTHWEST;
			getContentPane().add(getChatArea(), gbc);
			
		}
		
		//Setting up user List
		{

	        setListModel(new DefaultListModel<String>());
	        getListModel().addElement(getClientName());
	        setList(new JList<String>(getListModel()));
	        getList().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        //list.setBorder(border);
	        getList().addListSelectionListener(this);
	        getList().addMouseListener(new MouseAdapter() {	        	
	        	public void mouseClicked(MouseEvent e) {
	        		
	        		if(e.getClickCount() == 2) {

	        	        getListModel().addElement("Click"+getList().getSelectedIndex());
	        			System.out.println("Selected index " + getList().getSelectedIndex());
	        			System.out.println("Selected value " + getList().getSelectedValue());
	        		}
	        	}
	        });
	        
	        setUserScrollPane(new JScrollPane(getList()));
	        getUserScrollPane().setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        getUserScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	        getUserScrollPane().setVisible(true);


			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			gbc.ipadx = gbc.ipady = 5;
			gbc.weightx = 1.0;
			gbc.weighty = 7.0;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.anchor = GridBagConstraints.NORTHWEST;
			getContentPane().add(getUserScrollPane(), gbc);
		}

		// Setting up message area
		{
			setMessageArea(new JTextArea());
			getMessageArea().setBackground(Color.WHITE);
			getMessageArea().setEditable(true);
			getMessageArea().setVisible(true);
			//messageArea.setBorder(border);
			setMessageScrollPane(new JScrollPane(getMessageArea()));
			getMessageScrollPane()
					.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			getMessageScrollPane()
					.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			getMessageScrollPane().setVisible(true);


			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.gridwidth = 1;
			gbc.gridheight = 2;
			gbc.ipadx = gbc.ipady = 5;
			gbc.weightx = 4.0;
			gbc.weighty = 2.0;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.anchor = GridBagConstraints.NORTHWEST;
			getContentPane().add(getMessageScrollPane(), gbc);
		}
		
		// Setting up Send Button
		{
			setSend(new JButton(SEND_LABEL));
			getSend().addActionListener(this);


			gbc.gridx = 1;
			gbc.gridy = 1;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			gbc.ipadx = gbc.ipady = 5;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			gbc.fill = GridBagConstraints.NONE;
			gbc.anchor = GridBagConstraints.SOUTH;
			getContentPane().add(getSend(), gbc);
		}
		
		// Setting up Logout Button
		{
			setLogout(new JButton(LOGOUT_LABEL));
			getLogout().addActionListener(this);


			gbc.gridx = 1;
			gbc.gridy = 2;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			gbc.ipadx = gbc.ipady = 5;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			gbc.fill = GridBagConstraints.NONE;
			gbc.anchor = GridBagConstraints.NORTH;
			getContentPane().add(getLogout(), gbc);

		}

		getContentPane().setSize(WIDTH, HEIGHT);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == getSend()) {
			if (getMessageArea().getText() != "") {
				String temp = getMessageArea().getText().replaceAll("\n",
						"commandNewLine");
				System.out.println(temp);
				getMessageArea().setText("");
			}
		} else if (e.getSource() == getLogout()) {
			int option = JOptionPane.showConfirmDialog(this,
					"Do you want to really logout??", "Logout",
					JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				System.out.println("Exiting");
				System.exit(0);
			}
		}
	}

    @Override
    public void valueChanged(ListSelectionEvent e) {

    	if(e.getValueIsAdjusting() == false) {
    		
    		System.out.println("hi");
    	}
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

	public JScrollPane getChatScrollPane() {
		return chatScrollPane;
	}

	public void setChatScrollPane(JScrollPane chatScrollPane) {
		this.chatScrollPane = chatScrollPane;
	}

	public JEditorPane getChatArea() {
		return chatArea;
	}

	public void setChatArea(JEditorPane chatArea) {
		this.chatArea = chatArea;
	}

	public JScrollPane getUserScrollPane() {
		return userScrollPane;
	}

	public void setUserScrollPane(JScrollPane userScrollPane) {
		this.userScrollPane = userScrollPane;
	}

	public DefaultListModel<String> getListModel() {
		return listModel;
	}

	public void setListModel(DefaultListModel<String> listModel) {
		this.listModel = listModel;
	}

	public JList<String> getList() {
		return list;
	}

	public void setList(JList<String> list) {
		this.list = list;
	}

	public JScrollPane getMessageScrollPane() {
		return messageScrollPane;
	}

	public void setMessageScrollPane(JScrollPane messageScrollPane) {
		this.messageScrollPane = messageScrollPane;
	}

	public JTextArea getMessageArea() {
		return messageArea;
	}

	public void setMessageArea(JTextArea messageArea) {
		this.messageArea = messageArea;
	}

	public JButton getSend() {
		return send;
	}

	public void setSend(JButton send) {
		this.send = send;
	}

	public JButton getLogout() {
		return logout;
	}

	public void setLogout(JButton logout) {
		this.logout = logout;
	}

}
