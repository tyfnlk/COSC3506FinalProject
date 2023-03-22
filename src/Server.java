import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	//dedclare port number
	public static final int PORT = 3191;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		//create server
		new Server();

	}
	public Server() throws IOException, ClassNotFoundException {
		ServerSocket ss = new ServerSocket(PORT);
		// running server
		System.out.println("server is running");
		//accept client socket
		Socket socket = ss.accept(); // keeps the program running
		
		//allows for sending and receiveing of serialized objects
		ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
		
		ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
		
		
		//receive request
		Request recv= (Request)is.readObject();
		System.out.println(recv.getType());
		
		//return message
		Request snd = new TestMessage(1,"bye");
		
		os.writeObject(snd);
		
		//prevent data leak
		ss.close();
		
		
	}

}
