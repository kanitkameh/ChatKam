import java.io.IOException;
import java.util.Scanner;

public class Launcher {

	public static void main(String[] args) {
		System.out.println("Do you want a server or client?");
		Scanner input = new Scanner(System.in);
		String order = input.nextLine();
		if(order.equals("server")) {
			System.out.println("Starting server...");
			Server me = new Server(2704);
			me.startListening();
		}else if(order.equals("client")) {
			System.out.println("Enter IP to join: ");
			order = input.nextLine();
			Client me = new Client(order,2704);
			me.sendChar('1');
			try {
				me.connection.close();
			} catch (IOException e) {
				System.out.println("Can't close connection. Error: ");
				e.printStackTrace();
			}
		}else {
			System.out.println("You have entered invalid input.");
		}
		input.close();
	}

}
