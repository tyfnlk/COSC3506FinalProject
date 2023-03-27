import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
//DATA MEMBERS
	//dedclare port number and ServerSocket
	public static final int PORT = 3191;
	
	public ServerSocket ss;

//CONSTRUCTOR
	//initialize the serversocket according to the portnumber given
	public Server() throws IOException, ClassNotFoundException {
		
		this.ss = new ServerSocket(PORT);
		
	}
	
//METHODS
	//method to start the server
	
	public void startServer() throws IOException, ClassNotFoundException {
		
		System.out.println("Server Status: Running...");
		
		/*
		 * While serversocket is open, accept clients
		 * when a client connects, create a ClientHandler for that client
		 * start the ClientHandler on a new thread
		 */
		while(!ss.isClosed()) {
			
			Socket socket = this.ss.accept(); // blocking function till client connects
			
			//once client connects to server, create client handler for specific instance
			ClientHandler clientHandler = new ClientHandler(socket);
			System.out.println("handler created");
			
			Thread thread = new Thread(clientHandler);
			thread.start();
			
		}

	}
	

//MAIN METHOD
	/*
	 * executes when program runs
	 * creates a server object, and calls StartServer() method
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		//create server
		Server server = new Server();
		System.out.println("server Status: Launching...");
		server.startServer();
		

		
	}

}
