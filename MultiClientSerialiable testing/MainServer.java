import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
	//data
	
	private ServerSocket ss;
	
	//constructor
	
	public MainServer(ServerSocket ss) {
		this.ss = ss;
	}
	//methods
	public void startServer() throws Exception {
		try {
			while(!ss.isClosed()) {
				Socket socket = ss.accept();
				
				MainClientHandler mainClientHandler = new MainClientHandler(socket);
				
				System.out.println("A new client has connected!"+ mainClientHandler.getClientUsername());
				
				Thread thread = new Thread(mainClientHandler);
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
		System.out.println("main server running");
		server.startServer();
		
	}
}
