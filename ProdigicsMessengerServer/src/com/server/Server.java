package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Server extends Thread{
	private final int PORT=9999;
	private static int count;
	private ServerSocket serverSocket;
	private Socket socket;
	private Set<Socket> socketSet;
	private Map<Socket,String> clientMap;
	private Map<Socket,BufferedReader> inMap;
	private Map<Socket,PrintWriter> outMap;
	private ReadAndSend readAndSend;

	public Server(){
		try{
			serverSocket=new ServerSocket(PORT);
			System.out.println("Server is Started...");
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Exception in ServerScoket");
		}
		clientMap=new HashMap<Socket, String>();
		inMap=new HashMap<Socket, BufferedReader>();
		outMap=new HashMap<Socket, PrintWriter>();
	}

	public void run(){
		try{
			socketSet=new HashSet<Socket>();

			do{
				try{
					socket=serverSocket.accept();
					System.out.println("Connection: "+(++count)+socket);
				}
				catch(IOException e){
					e.printStackTrace();
					System.out.println("Error in accepting");
				}
				socketSet.add(socket);
				String temp="";
				if(!inMap.containsKey(socket)){
					try{
						BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
						temp=in.readLine();
						if(temp.split(": ")[1].equals("commandLogin"))
							clientMap.put(socket,temp.split(": ")[0]);
						inMap.put(socket, in);
					} 
					catch (IOException e){
						e.printStackTrace();
						System.out.println("Error in Bufferedreader");
					}
				}
				if(!outMap.containsKey(socket)){
					try{
						PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
						outMap.put(socket, out);
					}
					catch (IOException e){
						e.printStackTrace();
						System.out.println("Error in PrintWriter");
					}
				}
				readAndSend=new ReadAndSend(socket,clientMap,socketSet,inMap,outMap);
				readAndSend.start(temp);
			}while(!socketSet.isEmpty());
		}
		finally{
			if(!socketSet.isEmpty()){
				Iterator<Socket> iter=socketSet.iterator();
				while(iter.hasNext()){
					try {
						iter.next().close();
					} 
					catch (IOException e) {
						e.printStackTrace();
						System.out.println("Error in closing socket");
					}
				}
			}
			if(serverSocket!=null){
				try {
					serverSocket.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
					System.out.println("Error in closing serverSocket");
				}
			}
		}
	}
	public static void main(String args[]){
		Server server=new Server();
		server.start();
	}
}