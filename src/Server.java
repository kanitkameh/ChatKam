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
			newConnection = serverSocket.accept();
			System.out.println("New client has connected");
			
			printClientInput(newConnection);
		} catch (IOException e) {
			System.out.println("Client has tried to connect. Error:");
			e.printStackTrace();
		}
	}
	
	void printClientInput(Socket socket) {
		InputStream input;
		try {
			input = socket.getInputStream();
			int character;
			character = input.read();
			while(character != -1){
				System.out.print((char)character);
				character = input.read();
			}
		} catch (IOException e) {
			System.out.println("Couldn't get input stream. Error:");
			e.printStackTrace();
		}
		
	}
}
