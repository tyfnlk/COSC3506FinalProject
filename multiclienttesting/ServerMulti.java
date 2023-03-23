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
				
				
				ClientHandlerMulti clientHandlerMulti = new ClientHandlerMulti(socket);
				
				System.out.println("A new client has connected!"+ clientHandlerMulti.getClientUsername());
				
				Thread thread = new Thread(clientHandlerMulti);
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
		ServerMulti server = new ServerMulti(ss);
		System.out.println("multi server running");
		server.startServer();
		
	}
}
