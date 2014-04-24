package com.ttd.threads;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.ttd.interfaces.Constants;
import com.ttd.interfaces.Context;

/**
 * @author Aneesh Garg
 * 
 */
public abstract class Sender extends Thread implements Constants {
	private Socket socket;
	private InetAddress address;
	private Context context;

	private static PrintWriter sendStream;

	public Sender(Context context) {
		System.out.println("Creating Sender Thread...");
		this.context = context;
	}
	
	public abstract void successCallback(PrintWriter sendStream);

	public void run() {
		System.out.println("Starting Sender Thread...");
		try {
			try {
				address = InetAddress.getByName(SERVER_URI);
			} catch (UnknownHostException e) {
				context.displayFatalError("Cannot find host server",
						"Connection Failed");
				e.printStackTrace();
				System.exit(0);
			}

			try {
				socket = new Socket(address, SERVER_PORT);
				System.out.println("Client Info: "+socket.toString());
			} catch (IOException e) {
				context.displayFatalError("Cannot create connection",
						"Connection Failed");
				e.printStackTrace();
				System.exit(0);
			}

			Reciever reciever = new Reciever(context, socket);
			reciever.start();
			
			try {
				sendStream = new PrintWriter(socket.getOutputStream(), true);
				sendStream.println(context.getClientName() + SEPERATOR + COMMAND_LOGIN);
				successCallback(sendStream);
			} catch (IOException e) {
				context.displayFatalError("Unable to send messages", "Failure");
				e.printStackTrace();
				System.exit(0);
			}

			while (reciever.isAlive()) {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} finally {
			if (socket != null) {
				context.displayFatalError(
						"There was some problem in closing connection",
						"Closing Failed");
			}
		}
	}
}
