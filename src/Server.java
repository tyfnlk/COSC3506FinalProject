import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	//dedclare port number
	public static final int PORT = 3191;
	
	public ServerSocket ss;
	
	//constructor
	public Server() throws IOException, ClassNotFoundException {
		//initalize server socket
		this.ss = new ServerSocket(PORT);
		// running server
	}
	
	//method to start server	
	public void startServer() throws IOException, ClassNotFoundException {
		System.out.println("Server Status: Running...");
		
		//continuously allows clients to connect
		//when a client connects, a thread is created to handle their specific requests (clientHandler)
		while(!ss.isClosed()) {
			
			Socket socket = this.ss.accept(); // blocking function till client connects
			
			//once client connects to server, create client handler for specific instance
			ClientHandler clientHandler = new ClientHandler(socket);
			System.out.println("handler created");
			
			Thread thread = new Thread(clientHandler);
			thread.start();
			
		}
		
	
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		//create server
		Server server = new Server();
		System.out.println("server Status: Launching...");
		//start server
		server.startServer();
		

		
	}

}
