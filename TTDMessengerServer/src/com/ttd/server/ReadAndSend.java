package com.ttd.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import com.ttd.interfaces.Constants;

/**
 * @author Aneesh Garg
 * 
 */
public abstract class ReadAndSend extends Thread implements Constants {

	private static int count = 0;
	private Socket socket;
	private boolean loggedIn;
	private BufferedReader inputBuffer;

	public abstract void sendUserList(Socket socket);
	public abstract void logout(Socket socket);

	public abstract void send(String message);

	public ReadAndSend(Socket socket, BufferedReader inputBuffer) {
		this.socket = socket;
		this.inputBuffer = inputBuffer;
		this.loggedIn = true;
		/*
		 * System.out.println("SocketSet: "+this.socketSet);
		 * System.out.println("InMap: "+this.inMap);
		 * System.out.println("outMap: "+this.outMap);
		 * System.out.println("Thread Created "+(++count)+": "+this.socket);
		 */
	}

	public void run() {
		System.out.println("Active Read and Send No: " + (++count));

		String message = "";
		this.sendUserList(socket);
		while (this.loggedIn) {
			message = "";
			try {
				System.out.println("Waiting for msg");
				String temp = inputBuffer.readLine();
				message = temp;
				/*while (!COMMAND_END.equals(temp)) {
					temp = inputBuffer.readLine();
					if (!COMMAND_END.equals(temp))
						temp += NEW_LINE;
					message += temp;
				}*/
				System.out.println(message);

				send(message);

				String[] split = message.split(SEPERATOR); 
				if (split.length > 1 && COMMAND_LOGOUT.equals(split[1]))
					logout(socket);
			} catch (SocketException se) {
				logout(socket);
				System.out.println("Error in socket: " + socket);
				// str=clientMap.get(socket)+": commandLogout";
			} catch (IOException e) {
				e.printStackTrace();
				logout(socket);
				// str=clientMap.get(socket)+": commandLogout";
				System.out.println("Error in reading socket: " + socket);
			} catch (Exception exc) {
				exc.printStackTrace();
				logout(socket);
				// str=clientMap.get(socket)+": commandLogout";
				System.out.println(exc.getMessage() + " in socket: " + socket);
			}
		}

		System.out.println("Thread Ends: " + socket);
		count--;
	}

	public void start(String startMessage) {
		start();
		send(startMessage);
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

}
