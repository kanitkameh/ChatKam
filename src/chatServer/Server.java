package chatServer;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
	
	ServerSocket serverSocket;
	static List<Connection> connections = new ArrayList<Connection>();
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
				
				new Thread(newConnection).start(); //starting thread for every new client
			}
		} catch (IOException e) {
			System.out.println("Client has tried to connect. Error:");
			e.printStackTrace();
		}
	}
	
//sends byte accross all clients except the one send as argument connection
static void sendCharAcrossAllClients(char c, Connection connection) {
		for(Connection entry : connections) {
				if(entry!=connection) {
					try {
						entry.socket.getOutputStream().write(c);
					} catch (IOException e) {
						System.out.println("Couldn't write byte to clients. Error: ");
						
						System.out.println("Removing :"+entry);
						try {
							entry.socket.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						connections.remove(entry);
						
						e.printStackTrace();
				}
			}
		}
	}
	
//sends byte accross every client, inclunding the sender of the message
static void sendCharAcrossAllClients(char c) {
		for(Connection entry : connections) {
				try {
					entry.socket.getOutputStream().write(c);
				} catch (IOException e) {
					System.out.println("Couldn't write byte to clients. Error: ");
					
					System.out.println("Removing :"+entry);
					try {
						entry.socket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					connections.remove(entry);
					
					e.printStackTrace();
			}
		}
	}
}
