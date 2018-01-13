package chatServer;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Connection implements Runnable{
	Socket socket;
	boolean isLinkedWithAccount;
	public Connection(Socket socket) {
		this.socket = socket;
		this.isLinkedWithAccount = false; //every connection starts unlinked
	}

	@Override
	public void run() {
		printClientInput();
		System.gc(); //remove this object after closing the connection
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
						Server.connUser.remove(this);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					Server.connections.remove(this);
					break;
				}else {
					String str = new String(inputBytes, 0,len);
					System.out.println(socket.getInetAddress()+" : "+str);
					if(!isLinkedWithAccount) {
						String[] creditentials = str.split(" ");
						if(str.startsWith("/register")) {
							Server.tryToRegister(creditentials[1],creditentials[2],this);
						}else if(str.startsWith("/login")) {
							Server.tryToLinkWithAccount(creditentials[1],creditentials[2],this);
						}
						creditentials = null;
						System.gc(); //remove the creditentials string array
					}else if(isLinkedWithAccount) {
						if(str.startsWith("/")) {
							
							String [] order = str.split("/");
							if(order.length<3) {
								System.out.println(socket.getInetAddress()+" has entered an incomplete direct message command");
							}else {
								Server.sendString(order[2], this, order[1]);
							}
						}else {
							Server.sendString(str, this);
						}
					}
					len = input.read(inputBytes);
				}
			}
		} catch (IOException e) {
			System.out.println("Couldn't get input stream. Error:");
			e.printStackTrace();
		}
	}
	
}
