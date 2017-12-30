package chatServer;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
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
			byte [] inputBytes = new byte[64];
			int len = input.read(inputBytes);
			while(len != 0 && (!socket.isClosed())){
				if(len==-1) {
					System.out.println("Client has disconnected");
					System.out.println("Removing :"+this);
					try {
						this.socket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					Server.connections.remove(this);
				}
				String str = new String(inputBytes, 0,len);
				System.out.println(str);
				if(str.startsWith("/")) {
					String [] order = str.split("/");
					Server.sendString(order[2], this, InetAddress.getByName(order[1]));
				}else {
					Server.sendString(str, this);
				}
				len = input.read(inputBytes);
			}
		} catch (IOException e) {
			System.out.println("Couldn't get input stream. Error:");
			e.printStackTrace();
		}
		
	}
}
