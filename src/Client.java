import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	private Socket socket;
	private ObjectOutputStream os;
	private ObjectInputStream is;
	private String userName;

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		//create new client
		Client client = new Client();
		//start client reciever to recieve objects
		client.reciever();
		//testing to send objects
		while(true) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("enter Message: ");
			String message = scanner.nextLine();
			
			client.testMessage(message);
		}
		
		/*
		 * sent object using 
		 * this.os.writeObject(msg);
		 * this.os.reset();
		 * or
		 * this.os.flush();
		 * not sure if reset/flush are necessary
		 */

	}
	public Client() throws UnknownHostException, IOException, ClassNotFoundException {
		//connect to server
		this.socket = new Socket("127.0.0.1", Server.PORT);

		//create input/ output streams
		
		this.os = new ObjectOutputStream(socket.getOutputStream());
		
		this.is = new ObjectInputStream(socket.getInputStream()); 


	}
	public void testMessage(String hello) throws IOException, ClassNotFoundException {

		RequestTest msg = new RequestTest(hello);
		
		this.os.writeObject(msg);
		this.os.reset();
		System.out.println("test: object sent");
		
	}
	public void reciever() {
		//create thread
		new Thread(new Runnable() {
			//thread run method
			public void run() {
				
				try {
				    for(RequestTest incoming = (RequestTest)is.readObject();; incoming = (RequestTest)is.readObject())
				    {
				         //Code
				    	System.out.println(incoming.getMessage());
				    }
				} catch(IOException | ClassNotFoundException ex)
				{
				    //EOF found
				}
				// TODO Auto-generated method stub
				
			}
			//run listener once opened
		}).start();
	}
	
	
	public void close() throws IOException {
		this.os.close();
		this.socket.close();
	}

}
