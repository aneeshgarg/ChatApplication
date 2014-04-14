package com.ttd.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.ttd.interfaces.Constants;

/**
 * @author Aneesh Garg
 * 
 */
public class MainServer extends Thread implements Constants {

	private ServerSocket serverSocket;
	private HashSet<Socket> socketSet;
	private HashMap<Socket, PrintWriter> outputStreamMap;
	private HashMap<Socket, BufferedReader> inputStreamMap;
	private HashMap<Socket, String> clientMap;
	private Socket socket;

	public MainServer() {
		try {
			serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("Server is Started...");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception in ServerScoket");
			System.exit(0);
		}
		clientMap = new HashMap<Socket, String>();
		inputStreamMap = new HashMap<Socket, BufferedReader>();
		outputStreamMap = new HashMap<Socket, PrintWriter>();
	}

	public void run() {
		try {
			socketSet = new HashSet<Socket>();

			do {
				try {
					socket = serverSocket.accept();
					System.out.println("Connection: " + (socketSet.size() + 1)
							+ " " + socket);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Error in accepting connection");
				}
				if (socket != null) {
					socketSet.add(socket);
					String temp = "";
					if (!inputStreamMap.containsKey(socket)) {
						try {
							BufferedReader in = new BufferedReader(
									new InputStreamReader(
											socket.getInputStream()));
							temp = in.readLine();
							if (temp.split(SEPERATOR)[1].equals(COMMAND_LOGIN)) {
								clientMap.put(socket, temp.split(SEPERATOR)[0]);
								inputStreamMap.put(socket, in);
							}
						} catch (IOException e) {
							e.printStackTrace();
							System.out.println("Error in Bufferedreader");
						}
					}
					if (!outputStreamMap.containsKey(socket)) {
						try {

							if (temp.split(SEPERATOR)[1].equals(COMMAND_LOGIN)) {
								PrintWriter out = new PrintWriter(
										socket.getOutputStream(), true);
								outputStreamMap.put(socket, out);
							}
						} catch (IOException e) {
							e.printStackTrace();
							System.out.println("Error in PrintWriter");
						}
					}

					if (temp.split(SEPERATOR)[1].equals(COMMAND_LOGIN))
						new ReadAndSend(socket, inputStreamMap.get(socket)) {

							@Override
							public void send(String message) {
								if (message != null && message.trim().length() > 0) {
									Iterator<Socket> iter=socketSet.iterator();
									while(iter.hasNext()){
										Socket temp=iter.next();
										outputStreamMap.get(temp).println(message);
									}
								}

							}

							@Override
							public void logout(Socket socket) {
								if (socket != null){
									String message = clientMap.get(socket)+ SEPERATOR + COMMAND_LOGOUT;
									send(message);
									socketSet.remove(socket);
									inputStreamMap.remove(socket);
									outputStreamMap.remove(socket);
									clientMap.remove(socket);
									this.setLoggedIn(false);
								}
							}

							@Override
							public void sendUserList(Socket socket) {
								PrintWriter sendStream = outputStreamMap.get(socket);
								Iterator<Socket> iter=socketSet.iterator();
								while(iter.hasNext()){
									Socket temp=iter.next();
									if (!socket.equals(temp)) {
										String clientName = clientMap.get(temp);
										sendStream.println(clientName + SEPERATOR + COMMAND_ADD_USER);
									}
								}								
							}
						}.start(temp);
				}
			} while (!socketSet.isEmpty());
		} finally {
			if (!socketSet.isEmpty()) {
				Iterator<Socket> iter = socketSet.iterator();
				while (iter.hasNext()) {
					try {
						iter.next().close();
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("Error in closing socket");
					}
				}
			}
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Error in closing serverSocket");
				}
			}
		}
	}

	public static void main(String[] args) {
		new MainServer().start();
	}

}
