package com.ttd.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;

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

import com.ttd.interfaces.Constants;
import com.ttd.interfaces.Context;
import com.ttd.threads.Sender;

/**
 * @author Aneesh Garg
 * 
 */
public class ChatRoom extends JFrame implements Context, Constants,
		ActionListener, ListSelectionListener {

	private static final long serialVersionUID = -3717600338345444183L;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private String chatText = "";

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

	private PrintWriter sendStream;
	private Utility utility;

	public ChatRoom() {
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setClientName(JOptionPane.showInputDialog(this,
				"Please enter your username: ", "Username",
				JOptionPane.INFORMATION_MESSAGE));
		// this.setClientName("Aneesh");
		if (!(this.getClientName() != null && this.getClientName().trim()
				.length() > 0)) {
			JOptionPane.showMessageDialog(this,
					"You have not entered username. Messenger will now close.");
			System.exit(0);
		}

		setTitle(getTitle() + " : " + this.getClientName());

		Border border = BorderFactory.createLineBorder(Color.BLACK);

		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		// Setting up chat area
		{
			setChatArea(new JEditorPane());
			getChatArea().setContentType(TEXT_HTML);
			displayMessageOnChatArea(WELCOME_MESSAGE);
			getChatArea().setEditable(false);
			getChatArea().setBackground(Color.WHITE);
			getChatArea().setBorder(border);
			getChatArea().setVisible(true);

			setChatScrollPane(new JScrollPane(getChatArea()));
			getChatScrollPane().setVisible(true);
			getChatScrollPane().setHorizontalScrollBarPolicy(
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			getChatScrollPane().setVerticalScrollBarPolicy(
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			gbc.ipadx = gbc.ipady = 5;
			gbc.weightx = 10.0;
			gbc.weighty = 7.0;
			gbc.fill = GridBagConstraints.BOTH;

			gbc.anchor = GridBagConstraints.NORTHWEST;
			getContentPane().add(getChatScrollPane(), gbc);

			// File file = new File("resources/afraid.gif");
			// String url = file.getAbsolutePath();
			// System.out.println("File exists: "+ file.exists());
			// URL url = getClass().getResource("/resources/afraid.png");
			// System.out.println(url);
			// getChatArea().setText("<html><img src=" + url +
			// "></img></html>");

		}

		// Setting up user List
		{
			setListModel(new DefaultListModel<String>());
			//getListModel().addElement(getClientName());
			setList(new JList<String>(getListModel()));
			getList().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			// list.setBorder(border);
			getList().addListSelectionListener(this);
			getList().addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {

					if (e.getClickCount() == 2) {

						/*getListModel().addElement(
								"Click" + getList().getSelectedIndex());
						System.out.println("Selected index "
								+ getList().getSelectedIndex());
						System.out.println("Selected value "
								+ getList().getSelectedValue());*/
					}
				}
			});

			setUserScrollPane(new JScrollPane(getList()));
			getUserScrollPane().setHorizontalScrollBarPolicy(
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			getUserScrollPane().setVerticalScrollBarPolicy(
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
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
			getMessageArea().setWrapStyleWord(true);
			getMessageArea().setLineWrap(true);
			getMessageArea().setEditable(true);
			getMessageArea().setVisible(true);
			// messageArea.setBorder(border);
			setMessageScrollPane(new JScrollPane(getMessageArea()));
			getMessageScrollPane().setHorizontalScrollBarPolicy(
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			getMessageScrollPane().setVerticalScrollBarPolicy(
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			getMessageScrollPane().setVisible(true);

			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.gridwidth = 1;
			gbc.gridheight = 2;
			gbc.ipadx = gbc.ipady = 5;
			gbc.weightx = 10.0;
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
		
		setUtility(new Utility() {
			
			@Override
			public void removeUser(String clientName) {
				if (clientName != null && clientName.length() > 0)
					getListModel().removeElement(clientName);
			}
			
			@Override
			public void addUser(String clientName) {
				if (clientName != null && clientName.length() > 0)
					getListModel().addElement(clientName);				
			}
		});

		new Sender(this) {
			@Override
			public void successCallback(PrintWriter sendStream) {
				System.out.println("Starting Sender Success !!!");
				setSendStream(sendStream);
			}
		}.start();
	}

	private void displayMessageOnChatArea(String text) {
		chatText += text +  NEW_LINE;
		String message = "<html><body>" + chatText + "</body></html>";
		getChatArea().setText(message);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == getSend()) {
			String text = getMessageArea().getText();
			if ( text != null && text.length() > 0) {
				String message = getUtility().processMessageToSend(
						this.getClientName(), getMessageArea().getText());
				System.out.println("Sent: " + message);
				getMessageArea().setText("");
				sendStream.println(message);
			}
		} else if (e.getSource() == getLogout()) {
			int option = JOptionPane.showConfirmDialog(this,
					"Do you want to really logout??", "Logout",
					JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				//sendStream.println(this.getClientName() + SEPERATOR+ COMMAND_LOGOUT);
				System.out.println("Exiting");
				System.exit(0);
			}
		}
	}

	@Override
	public void displayFatalError(String message, String title) {
		JOptionPane.showMessageDialog(this, message, title,
				JOptionPane.ERROR_MESSAGE);

	}

	@Override
	public void displayMessage(String message) {
		if (message != null && message.trim().length() > 0) {
			message = getUtility().processReceivedMessage(message);
			if (message != null && message.trim().length() >0 )
				displayMessageOnChatArea(message);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

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

	public PrintWriter getSendStream() {
		return sendStream;
	}

	public void setSendStream(PrintWriter sendStream) {
		this.sendStream = sendStream;
	}

	public Utility getUtility() {
		return utility;
	}

	public void setUtility(Utility utility) {
		this.utility = utility;
	}

}
