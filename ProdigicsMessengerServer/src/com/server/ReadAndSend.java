package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ReadAndSend extends Thread {

	private Socket socket;
	private Map<Socket, String> clientMap;
	private Set<Socket> socketSet;
	private Map<Socket, BufferedReader> inMap;
	private Map<Socket, PrintWriter> outMap;
	private BufferedReader in;
	private PrintWriter out;
	private String str;

	public ReadAndSend(Socket socket,
			Map<Socket, String> clientMap,
			Set<Socket> socketSet,
			Map<Socket, BufferedReader> inMap, 
			Map<Socket, PrintWriter> outMap)
	{
		this.socket=socket;
		this.clientMap=clientMap;
		this.socketSet=socketSet;
		this.inMap=inMap;
		this.outMap=outMap;
		/*System.out.println("SocketSet: "+this.socketSet);
		System.out.println("InMap: "+this.inMap);
		System.out.println("outMap: "+this.outMap);
		System.out.println("Thread Created "+(++count)+": "+this.socket);*/
	}

	public void run(){
		if(inMap.containsKey(socket))
			in=inMap.get(socket);

		while(socketSet.contains(socket)){
			try {
				System.out.println("Waiting for msg");
				str=in.readLine();
				System.out.println(str);
			} catch(SocketException se){
				str=clientMap.get(socket)+": commandLogout";
			} catch (IOException e){ 
				e.printStackTrace();
				str=clientMap.get(socket)+": commandLogout";
				System.out.println("Error in reading socket: "+socket);
			} catch (Exception exc){
				exc.printStackTrace();
				str=clientMap.get(socket)+": commandLogout";
				System.out.println(exc.getMessage()+" in socket: "+socket);				
			}

			Iterator<Socket> iter=socketSet.iterator();
			while(iter.hasNext()){
				Socket temp=iter.next();
				out=outMap.get(temp);
				out.println(str);
			}

			if(str.split(": ")[1].equals("commandLogout")){
				socketSet.remove(socket);
				inMap.remove(socket);
				outMap.remove(socket);
				clientMap.remove(socket);
			}
		}
		System.out.println("Thread Ends: "+socket);
	}
	
	public void start(String str){
		start();
		Iterator<Socket> iter=socketSet.iterator();
		while(iter.hasNext()){
			Socket temp=iter.next();
			out=outMap.get(temp);
			out.println(str);
		}
	}
}
