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
	//TO-DO
	void receiveChar() {
		
	}
}
