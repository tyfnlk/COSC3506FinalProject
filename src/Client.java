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
	private boolean certification = false;

	

	public static void main(String[] args) throws Exception {
		
		Client client = new Client();
		client.reciever();
		
		//login
		Scanner scanner = new Scanner(System.in);
		
		boolean result = false;
		do {
			System.out.println("enter username : ");
			String username = scanner.nextLine();
			
			System.out.println("enter Password:");
			String password = scanner.nextLine();
			
			client.login(username, password);
		} 
		while(!client.certification);
		System.out.println("login success");

		 
		

		
		


	}
	public Client() throws UnknownHostException, IOException, ClassNotFoundException {
		//connect to server
		this.socket = new Socket("127.0.0.1", Server.PORT);

		//create input/ output streams
		
		this.os = new ObjectOutputStream(socket.getOutputStream());
		
		this.is = new ObjectInputStream(socket.getInputStream()); 


	}
	public void testMessage(String hello) throws IOException, ClassNotFoundException {

		Request msg = new Request( hello);
		
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
				    for(Request incoming = (Request)is.readObject();; incoming = (Request)is.readObject())
				    {
				         //Code
				    	System.out.println("package Received");
				    	
				    	switch(incoming.getType()) {
				    	case "SuccessfulLoginRequest":
				    		System.out.println("package Received in switch case");
				    		SuccessfulLoginHandler((SuccessfulLoginRequest)incoming);
				    		System.out.println(certification);
				    	
				    	}
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
	
	
	public void login(String username, String password) throws IOException, Exception {
		// create LoginRequest object
		LoginRequest loginRequest = new LoginRequest(username, password);
		
		//send request to server
		this.os.writeObject(loginRequest);
		this.os.reset();
		//give client time to update certification
		Thread.sleep(1000);
		
	}
	public void SuccessfulLoginHandler(SuccessfulLoginRequest request) {
		if(!request.getResult()) {
			this.certification = false;
			
		}else {
			this.certification=true;
		}
		
		
		
	};

}
