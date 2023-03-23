import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MainClient {
	//datamembers
	
	private Socket socket;
	//receive data
	private ObjectInputStream objectInputStream;
	//send data
	private ObjectOutputStream objectOutputStream;
	public String username;
	
	//constructor
	public MainClient(Socket socket, String username) {
		this.username=username;
		try {
			this.socket = socket;
			this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			this.objectInputStream = new ObjectInputStream(socket.getInputStream());
		}catch(IOException e) {
			closeEverything(socket, objectOutputStream, objectInputStream);
			
		}
	}
	
	@SuppressWarnings("resource")
	public void sendRequest() {
		try {
			objectOutputStream.writeObject(new TestMessage("messages from client"));
			//bufferedWriter.write(username);
			//bufferedWriter.newLine();
			//bufferedWriter.flush();
			
			Scanner scanner = new Scanner(System.in);
			
			while(socket.isConnected()) {
				String messageToSend = scanner.nextLine();
				
				objectOutputStream.writeObject(new TestMessage(messageToSend));
				
				//bufferedWriter.write(this.username +": " + messageToSend);
				//bufferedWriter.newLine();
				//bufferedWriter.flush();

			}
				
		}catch(IOException e) {
				closeEverything(socket, objectOutputStream, objectInputStream);
		}
		
	}
	public void listenForRequest() {
		//needs to be a thread to prevent blocking
		new Thread(new Runnable() {
			public void run() {
				Request request;
				
				while(socket.isConnected()) {
					try {
						request = (Request) objectInputStream.readObject();
						System.out.println(request.getType());
					}catch(IOException | ClassNotFoundException e) {
						closeEverything(socket, objectOutputStream, objectInputStream);
						
					}
					
				}
			}
		}).start();
	}
	
	public void closeEverything(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		try {
			if(objectOutputStream != null) {
				objectOutputStream.close();
			}
			if(objectInputStream != null) {
				objectInputStream.close();
			}
			if(socket != null) {
				socket.close();
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter username: ");
		String username = scan.nextLine();
		
		Socket socket = new Socket ("localhost", 1234);
		//create client object
		MainClient mainClient = new MainClient(socket, username);
		//run listening thread
		mainClient.listenForRequest();
		//run sending thread
		mainClient.sendRequest();
	}
}



