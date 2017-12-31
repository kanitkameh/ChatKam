package chatServer;

import java.util.ArrayList;

public class User {
	String username;
	String password;
	ArrayList<String> missedMessages;
	
	public User(String name, String password) {
		this.username = name;
		this.password = password;
		missedMessages = new ArrayList<String> ();
	}
	//returns true if password change is successful
	boolean changePassword(String oldPass,String newPass) {
		if(oldPass.equals(password)) {
			password = newPass;
			return true;
		}else{
			return false;
		}
	}
	
	boolean tryToLogin(String passTry) {
		if(passTry.equals(password)) {
			return true;
		}else {
			return false;
		}
	}
	
}
