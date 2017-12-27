import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class Server {
	
	ServerSocket serverSocket;
	
	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server has been initialized successfully!");
		} catch (IOException e) {
			System.out.println("Couldn't initialize server. Error is:");
			e.printStackTrace();
		}
	}
	
	void startListening() {
		Socket newConnection;
		try {
			while(true) { //server keeps waiting for connection till force stop
				newConnection = serverSocket.accept();
				System.out.println("New client has connected");
				
				new Thread(new Connection(newConnection)).start(); //starting thread for every new client
			}
		} catch (IOException e) {
			System.out.println("Client has tried to connect. Error:");
			e.printStackTrace();
		}
	}
	
	
}
