package chatServer;

public class DatabaseUpdater implements Runnable{

	UsersDatabase database;
	int miliseconds;
	
	public DatabaseUpdater(UsersDatabase database, int miliseconds) {
		this.database=database;
		this.miliseconds=miliseconds;
	}

	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep(miliseconds);
				database.saveData();
			}
		} catch (InterruptedException e) {
			System.out.println("The thread syncing the database with the server is iterrupted!");
			e.printStackTrace();
		}
		
	}

}
