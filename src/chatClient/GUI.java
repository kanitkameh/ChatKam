

package chatClient;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
		
		
		messageArea = new TextArea();
		messageArea.setRows(10);
		messageArea.setVisible(true);
		messageArea.setEditable(false);
		
		input = new TextField();
		input.setColumns(64);
		input.setVisible(true);
		
		String order="localhost";
		int port=2704;
		me = new Client(order,port,this);
		
		input.addKeyListener(new KeyListener() {
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==10) {
					me.sendString(input.getText());
					input.setText("");
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		frame.add(messageArea);
		frame.add(input);
		frame.validate();
		System.out.println("window set");
	}
}
