import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	//dedclare port number
	public static final int PORT = 3191;
	
	public ServerSocket ss;
	
	
	public Server() throws IOException, ClassNotFoundException {
		
		this.ss = new ServerSocket(PORT);
		// running server
	}
	
		
	public void startServer() throws IOException, ClassNotFoundException {
		System.out.println("Server Status: Running...");
		
		
		
		
		
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
		server.startServer();
		

		
	}

}
