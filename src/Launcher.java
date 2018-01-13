import java.util.Scanner;

public class Launcher {

	public static void main(String[] args) {
		System.out.println("Do you want a server or client?");
		Scanner input = new Scanner(System.in);
		String order = input.nextLine();
		if(order.equals("server")) {
			System.out.println("Enter port number:");
			int port = input.nextInt();
			System.out.println("Starting server on port "+port);
			chatServer.Server me = new chatServer.Server(port);
			me.startListening();
		}else if(order.equals("client")) {
			chatClient.GUI clientGUI = new chatClient.GUI(640, 320);
			clientGUI.setUpWindowComponents();
		}else if(order.equals("client-cli")) {
			System.out.println("Enter IP to join: ");
			order = input.nextLine();
			System.out.println("Enter port number:");
			int port = input.nextInt();
			chatClient.Client me = new chatClient.Client(order,port,null);
			System.out.println("Welcome to the chat! Type \"EXIT\" to close conneciton)");
			do{
				order = input.nextLine();
				if(order.equals("EXIT")) {
					break;
				}
				me.sendString(order);
			}while(true);
			me.closeConnection();
		}else {
			System.out.println("You have entered invalid input.");
		}
		input.close();
	}

}
