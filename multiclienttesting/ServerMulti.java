import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMulti {
	//data
	
	private ServerSocket ss;
	
	//constructor
	
	public ServerMulti(ServerSocket ss) {
		this.ss = ss;
	}
	//methods
	public void startServer() {
		try {
			while(!ss.isClosed()) {
				Socket socket = ss.accept();
				
				
				ClientHandler clientHandler = new ClientHandler(socket);
				
				System.out.println("A new client has connected!"+ clientHandler.getClientUsername());
				
				Thread thread = new Thread(clientHandler);
				thread.start();
			}
		}catch(IOException e) {
			
		}
		
	}
	public void closeServerSocket() {
		try {
			if(ss != null) {
				ss.close();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(1234);
		MainServer server = new MainServer(ss);
		System.out.println("multi server running");
		server.startServer();
		
	}
}
