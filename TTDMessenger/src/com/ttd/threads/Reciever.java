package com.ttd.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import com.ttd.interfaces.Constants;
import com.ttd.interfaces.Context;

/**
 * @author Aneesh Garg
 * 
 */
public class Reciever extends Thread implements Constants {
	private Context context;
	private Socket socket;
	private BufferedReader recieveStream;

	public Reciever(Context context, Socket socket) {
		System.out.println("Creating Receiver Instance...");
		this.context = context;
		this.socket = socket;

		try {
			recieveStream = new BufferedReader(new InputStreamReader(
					this.socket.getInputStream()));
		} catch (IOException e) {
			context.displayFatalError(
					"Unable to recieve messages. Messenger will now close.",
					"Failure");
			System.exit(0);
		}
	}

	public void run(){
		System.out.println("Starting Receiver...");
		String message = "";
		do{
			try {
				message=recieveStream.readLine();				
				displayMessage(message);
				System.out.println("Received: " + message);
			} catch (SocketException se){
				context.displayFatalError(
						"Server Failure. Messenger will now close.",
						"Failure");
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} while(!(message.split(SEPERATOR)[0].equalsIgnoreCase(context.getClientName()) 
				&& message.split(SEPERATOR)[1].equals(COMMAND_LOGOUT)));
		System.out.println("Ending Receiver...");
	}
	
	synchronized private void displayMessage(String message){
		context.displayMessage(message);
	}

}
