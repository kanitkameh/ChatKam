package chatServer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Server {
	
	ServerSocket serverSocket;
	static List<Connection> connections = new ArrayList<Connection>();
	static List<User> users = new ArrayList<User>();
	//using two maps as a replacement of bidirectional map
	static HashMap<Connection, User> connUser = new HashMap<Connection, User>();
	static HashMap<User, Connection> userConn = new HashMap<User, Connection>();
	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server has been initialized successfully!");
		} catch (IOException e) {
			System.out.println("Couldn't initialize server. Error is:");
			e.printStackTrace();
		}
	}
	
	public void startListening() {
		try {
			while(true) { //server keeps waiting for connection till force stop
				Connection newConnection = new Connection(serverSocket.accept());
				connections.add(newConnection);
				System.out.println(newConnection.socket.getInetAddress()+" has connected");
				writeToSocket(newConnection,"Welcome to ChatKam Server\n "
						+ "You must make an account in order to chat.\nTo register type /register username password\n"
						+ "To login type /login username password\nHave Fun!");
				new Thread(newConnection).start(); //starting thread for every new client
			}
		} catch (IOException e) {
			System.out.println("Client has tried to connect. Error:");
			e.printStackTrace();
		}
	}
	
//sends string as a private message
static void sendString(String str, Connection source, String username) {
		for(User entry : users) {
			if(userConn.get(entry)==null) {
				System.out.println(entry.username+" has missed a message: "+connUser.get(source).username+" has sent you a DM: "+str);
				entry.missedMessages.add(connUser.get(source).username+": "+str);
			}else if(entry.username.equals(username)) {
					writeToSocket(userConn.get(entry),(connUser.get(source).username+" has sent you a DM: "+str));
					return;
			}
		}
	}
	
//sends byte accross every client, inclunding the sender of the message
static void sendString(String str, Connection source) {
		for(User entry : users) {
			if(userConn.get(entry)==null) {
				System.out.println(entry.username+" has missed a message: "+connUser.get(source).username+": "+str);
				entry.missedMessages.add(connUser.get(source).username+": "+str);
			}else {
				writeToSocket(userConn.get(entry),(connUser.get(source).username+": "+str));
			}
		}
	}

public static void tryToRegister(String username, String password, Connection conn) {
		for(User entry : users) {
			if(entry.username.equals(username)) {
				writeToSocket(conn,"Username already taken.");
				return;
			}
		}
		//reached only if return is not issued(username not taken)
		User toAdd = new User(username,password);
		System.out.println(conn.socket.getInetAddress()+"has registered as "+username);
		writeToSocket(conn,"Successfully registered.\nNow you have to login in your account.");
		users.add(toAdd);
	}

public static void tryToLinkWithAccount(String username, String password, Connection conn) {
		//probably sorting the users and using binary search would be better TO-DO
		for(User entry : users) {
			if(entry.username.equals(username)) {
				if(entry.password.equals(password)) {
					if(connUser.containsKey(conn)) {
						writeToSocket(conn,"Account is being used at the moment.");
						return;
					}
					//Map link
					connUser.put(conn, entry);
					userConn.put(entry, conn);
					
					conn.isLinkedWithAccount = true;
					writeToSocket(conn,"Welcome back!");
					System.out.println(conn.socket.getInetAddress()+" has logged in "+username);
					Iterator<String> iter = entry.missedMessages.iterator();
					while(iter.hasNext()) {
						String missedMessage = iter.next();
						writeToSocket(userConn.get(entry),missedMessage+"\n");
						iter.remove();
					}
					
					return;
				}else {
					writeToSocket(conn,"Wrong password.");
					return;
				}
			}
		}
		
		writeToSocket(conn,"Username not found.");
	}

public static void writeToSocket(Connection conn, String str) {
		try {
			conn.socket.getOutputStream().write((str).getBytes(StandardCharsets.UTF_8.name()));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Encoding not supported");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Couldn't write byte to clients. Error: ");	
			connUser.get(conn).missedMessages.add(str);
			e.printStackTrace();
		}
	}

}
