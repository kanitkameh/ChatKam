package chatClient;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI {
	Frame frame;
	TextField input;
	TextArea messageArea;
	Panel chat;
	Client me;
	public GUI(int width, int height) {
		frame = new Frame("ChatKam");
		frame.setSize(width, height);
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});
	}
	public void setUpWindowComponents() {
		frame.setLayout(new FlowLayout());
		//adding window components
		messageArea = new TextArea();
		messageArea.setRows(10);
		messageArea.setEditable(false);
		
		input = new TextField();
		input.setColumns(64);
		input.setEditable(false);
		
		setConnectionDialog();
		
		input.addKeyListener(new KeyboardInput(this));
		
		frame.add(messageArea);
		frame.add(input);
		frame.validate();
		System.out.println("Window UI set");
	}
	//dialog to get server ip and port
	void setConnectionDialog() {
		Dialog getHost = new Dialog(frame, "Server selection");
		getHost.setLayout(new FlowLayout());
		getHost.setVisible(true);
		
		Label l = new Label("Enter Server IP and port");
		getHost.add(l);
		
		TextField IP = new TextField();
		IP.setText("localhost");
		getHost.add(IP);
		
		TextField port = new TextField();
		port.setText("2704");
		getHost.add(port);
		
		Button b = new Button("Connect!");
		b.addMouseListener(new ConnectButtonListener(IP, port, this, getHost));
		getHost.add(b);
		
		getHost.pack();
	}
}
//private classes which override default UI listeners
class ConnectButtonListener extends MouseAdapter{
	String IP;
	int port;
	GUI guiController;
	Dialog toClose; //we get rid of the dialog after connecting to the server
	ConnectButtonListener(TextField IP,TextField port,GUI guiController,Dialog toClose){
		this.IP = IP.getText();
		this.port = Integer.parseInt(port.getText());
		this.guiController = guiController;
		this.toClose = toClose;
	}
	
	public void mouseClicked(MouseEvent e) {
		guiController.me = new Client(IP,port,guiController);
		guiController.input.setEditable(true); //we can write in the chat only when we are connected to a server
		toClose.dispose();
	}
	
}
class KeyboardInput extends KeyAdapter {
	GUI guiController;
	
	KeyboardInput(GUI guiController){
		this.guiController = guiController;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar()=='\n') {
			guiController.me.sendString(guiController.input.getText());
			guiController.input.setText("");
		}
	}
}