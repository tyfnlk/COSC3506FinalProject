import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		
		new Client();


	}
	public Client() throws UnknownHostException, IOException, ClassNotFoundException {
		//connect to server
		Socket socket = new Socket("127.0.0.1", Server.PORT);
		
		//create input/ output streams
		
		ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
		
		ObjectInputStream is = new ObjectInputStream(socket.getInputStream()); 
		
		//create request to send
		
		Request msg = new TestMessage(1, "hello from client");
		
		os.writeObject(msg);
		
		Request in = (Request)is.readObject();
		
		System.out.println(in.getType());
		if(in.getType() ==1) {
			TestMessage tm = (TestMessage)in;
			System.out.println(tm.getMessage());
			
		}

		
		os.close();
		socket.close();
		
		
	}

}
