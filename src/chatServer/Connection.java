package chatServer;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Connection implements Runnable{
	Socket socket;
	boolean isLinkedWithAccount;
	Server serverObj;
	public Connection(Socket socket,Server serverObj) {
		this.serverObj = serverObj;
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
						serverObj.connUser.remove(this);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					serverObj.connections.remove(this);
					break;
				}else {
					String str = new String(inputBytes, 0,len);
					System.out.println(socket.getInetAddress()+" : "+str);
					if(!isLinkedWithAccount) {
						String[] creditentials = str.split(" ");
						if(creditentials.length<3) {
							System.out.println(socket.getInetAddress()+" has entered an incomplete register command");
						}else if(str.startsWith("/register")) {
							serverObj.tryToRegister(creditentials[1],creditentials[2],this);
						}else if(str.startsWith("/login")) {
							serverObj.tryToLinkWithAccount(creditentials[1],creditentials[2],this);
						}
						creditentials = null;
						System.gc(); //remove the creditentials string array
					}else if(isLinkedWithAccount) {
						if(str.startsWith("/")) {
							
							String [] order = str.split("/");
							if(order.length<3) {
								System.out.println(socket.getInetAddress()+" has entered an incomplete direct message command");
							}else {
								serverObj.sendString(order[2], this, order[1]);
							}
						}else {
							serverObj.sendString(str, this);
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
