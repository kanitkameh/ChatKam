import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{
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
		new Thread(this).start();
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
	char receiveChar() {
		try {
			if(!connection.isClosed()) {
			char c = (char) connection.getInputStream().read();
			return c;
			}
		} catch (IOException e) {
			System.out.println("Couldn't receive the byte from the server");
			e.printStackTrace();
		}
		return 0;
	}
	
	void closeConnection() {
		try {
			connection.close();
		} catch (IOException e) {
			System.out.println("Can't close connection. Error: ");
			e.printStackTrace();
		}
	}

	//Used to get data from the server
	public void run() {
		while(!connection.isClosed()) {
		char c = receiveChar();
		System.out.print(c);
		}
	}
}
