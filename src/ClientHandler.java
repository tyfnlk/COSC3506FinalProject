import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
	private Socket client;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	public static ArrayList<ClientHandler> clientList = new ArrayList<>();
	
	
	
	//constructor
	public ClientHandler(Socket clientSocket) throws IOException {
		//set the client socket
		this.client = clientSocket;
		//initialize object input and output streams
		in = new ObjectInputStream(client.getInputStream());
		out = new ObjectOutputStream(client.getOutputStream());
		
		// add current client to the static client list
		clientList.add(this);
		//notify server that a new client has connected
		System.out.println("New Client Connected created:");
		
	}

//thread process
	@Override
	public void run() {
		//constantly read input objects on different thread so blocking does not affect client
		try {
		    for(RequestTest request = (RequestTest)in.readObject();; request = (RequestTest)in.readObject())
		    {
		         // deal with requests.(switch case methods for each request)
		    	switch(request.getType()){
		    	case "Message":
		    		message(request);
		    		sendResponse();
		    		break;
		    		
		    	case "case2":
		    		//case2(request);
		    		break;
		    		//repeat for all different types of request and create equivalent function
		    	
		    	
		    	
		    	
		    	}
		    	
		    }
		} catch(IOException | ClassNotFoundException ex)
		{
		    //EOF found
		}
	
	}
	//example of handling 
	public void message(RequestTest request) {
		System.out.println(request.getMessage());
	}
	//test of sending Request object back to client
	public void sendResponse() throws IOException {
		RequestTest response = new RequestTest("Youre request has been sorted");
		out.writeObject(response);
		
	}

}
