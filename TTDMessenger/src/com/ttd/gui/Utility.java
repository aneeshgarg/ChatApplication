package com.ttd.gui;

import com.ttd.interfaces.Constants;

/**
 * @author Aneesh Garg
 * 
 */
public abstract class Utility implements Constants {

	public String processMessageToSend(String clientName, String text) {
		String message = "";
		text = text.replaceAll(NEW_LINE_REGEX, NEW_LINE);
		message = clientName + SEPERATOR + text;
		return message;
	}

	public String processReceivedMessage(String text) {

		String clientName = "";
		String message = "";
		String processedMessage = "";

		String[] split = text.split(SEPERATOR);
		clientName = split[0];
		message = split[1];

		if (COMMAND_LOGIN.equals(message)) {
			processedMessage = "<b>" + clientName + " has logged in...</b>";
			add(clientName);
		} else if (COMMAND_LOGOUT.equals(message)) {
			processedMessage = "<b>" + clientName + " has logged out...</b>";
			remove(clientName);
		} else if (COMMAND_ADD_USER.equals(message)) {
			add(clientName);
		} else
			processedMessage = "<b>" + clientName + " : </b>" + message;
		return processedMessage;
	}

	synchronized public void add(String clientName) {
		addUser(clientName);
	}

	synchronized public void remove(String clientName) {
		removeUser(clientName);
	}

	public abstract void addUser(String clientName);

	public abstract void removeUser(String clientName);

}
