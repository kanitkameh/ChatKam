package chatClient;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{
	Socket connection;
	OutputStream out;
	InputStream in;
	public Client(String hostname,int port) {
		try {
			this.connection = new Socket(hostname,port);
			System.out.println("Successfully connected to the server");
			
			out = connection.getOutputStream();
			in = connection.getInputStream();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(this).start();
	}
	
	public void sendChar(char character) {
		try {
			out.write(character);
		} catch (IOException e) {
			System.out.println("Couldn't send char. Error: ");
			e.printStackTrace();
		}
	}
	
	public void sendString(String str) {
		for(int i=0;i<str.length();i++) {
			sendChar(str.charAt(i));
		}
		sendChar('\n'); //Sending end line character
	}
	//TO-DO
	char receiveChar() {
		try {
			if(!connection.isClosed()) {
			char c = (char) in.read();
			return c;
			}
		} catch (IOException e) {
			System.out.println("Couldn't receive the byte from the server");
			e.printStackTrace();
		}
		return 0;
	}
	
	public void closeConnection() {
		try {
			out.close();
			in.close();
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
