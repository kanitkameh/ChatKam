import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	Socket connection;
	public Client(String hostname,int port) {
		try {
			this.connection = new Socket(hostname,port);
			System.out.println("Successfully connected to the server");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void sendChar(char character) {
		try {
			connection.getOutputStream().write(character);
		} catch (IOException e) {
			System.out.println("Couldn't send char. Error: ");
			e.printStackTrace();
		}
	}
	
	void sendString(String str) {
		for(int i=0;i<str.length();i++) {
			sendChar(str.charAt(i));
		}
		sendChar('\n'); //Sending end line character
	}
	//TO-DO
	void receiveChar() {
		
	}
	
	void closeConnection() {
		try {
			connection.close();
		} catch (IOException e) {
			System.out.println("Can't close connection. Error: ");
			e.printStackTrace();
		}
	}
}
