package chatServer;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Connection implements Runnable{
	Socket socket;
	public Connection(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		printClientInput();
	}
	void printClientInput() {
		InputStream input;
		try {
			input = socket.getInputStream();
			int character;
			character = input.read();
			while(character != -1 && (!socket.isClosed())){
				System.out.print((char)character);
				Server.sendCharAcrossAllClients((char)character,this);
				character = input.read();
			}
		} catch (IOException e) {
			System.out.println("Couldn't get input stream. Error:");
			e.printStackTrace();
		}
		
	}
}
