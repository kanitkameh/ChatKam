package chatClient;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Client implements Runnable{
	Socket connection;
	OutputStream out;
	InputStream in;
	GUI gui;
	public Client(String hostname,int port,GUI gui) {
		this.gui=gui;
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
	
	public void sendString(String str) {
		try {
			out.write(str.getBytes(StandardCharsets.UTF_8.name()));
		} catch (IOException e) {
			System.out.println("Couldn't send char. Error: ");
			e.printStackTrace();
		}
	}
	
	//TO-DO
	String receiveString() {
		try {
			if(!connection.isClosed()) {
				byte [] inputBytes = new byte[64];
				int len = in.read(inputBytes);
				String str = new String(inputBytes,0,len);
				return str;
			}
		} catch (IOException e) {
			System.out.println("Couldn't receive the byte from the server");
			e.printStackTrace();
		}
		return "empty";
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
		String str = receiveString();
		gui.messageArea.append(str+'\n');
		System.out.println(str);
		}
	}
}
