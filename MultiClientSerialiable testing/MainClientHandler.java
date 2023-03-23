import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MainClientHandler implements Runnable{
//datamembers
	
	//static array list of all clients
	public static ArrayList<MainClientHandler> clientHandlers = new ArrayList<>();
	private Socket socket;
	//get data
	private ObjectInputStream objectInputStream;
	//send data
	private ObjectOutputStream objectOutputStream;
	String clientusername;
	
	
	//constructor
	public MainClientHandler(Socket socket) throws Exception {
		try {
			this.socket = socket;
			
			//buffered writer to increase efficiency (possibly just buffer the object output stream)
			
			this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			
			this.objectInputStream = new ObjectInputStream(socket.getInputStream());

			//Request req= (Request)objectInputStream.readObject();
			
		//authenticate login
		//then add user to lis
			
			clientHandlers.add(this);
			broadcastMessage("server: <" + clientusername + "> has entered the chat room");
		}catch(IOException e) {
			closeEverything(socket, objectInputStream, objectOutputStream);
		}
	}
	
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//run on separate thread
		//blocking operation
		
		//while socket is connected , send requests
		while(socket.isConnected()) {
			
			//attempt login
			
			//send requests : 
			
			
	
			
			try {
				Request incomingRequest = (Request)objectInputStream.readObject();
				//need method to deal with request instead of broadcastMessage
				
				switch(incomingRequest.getType()) {
				case "REQUEST TYPE 1":
					//cast object to requesttype
					//deal with request;
					break;
				case "REQUEST TYPE 2":
					//cast object to requesttype 2
					//deal with request
					break;
				case "REQUEST TYPE 3":
					//cast object to requesttype 3 and deal with request
					//ResolverequestType3((requestType3)incomingRequest)
					break;
				
				}
				
				//broadcastMessage(messageFromClient);
				
			}catch(IOException | ClassNotFoundException e) {
				closeEverything(socket,objectInputStream, objectOutputStream);
				break;
			}
		}
		
	}
	//need resolve function for each class action
	public void requestResolveRequest (Request request) {
		//take request and deal with it
		//break;

		
	}
	
	public void broadcastMessage(String messageToSend) {
		//for each person online
		for(MainClientHandler mainClientHandler : clientHandlers) {
			try {
				//for everyone except yourself
				if(!mainClientHandler.clientusername.equals(clientusername)) {
					//write message to output stream
					mainClientHandler.objectOutputStream.writeObject(new TestMessage(messageToSend));

				}
			}catch(IOException e) {
				closeEverything(socket, objectInputStream, objectOutputStream);
			}
		}
		
	}
	public void removeClientHandler() {
		//remove from array list
		clientHandlers.remove(this);
		broadcastMessage("SERVER" + clientusername + "has left the chat");
	}
	public String getClientUsername() {
		return this.clientusername;
	}
	
	public void closeEverything(Socket socket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
		removeClientHandler();
		//closing bufferd... closes all readers/writers nested\
		//closing socket closes all socket inputs/outputs
		try {
			if(objectOutputStream != null) {
				objectOutputStream.close();
			}
			if(objectInputStream != null) {
				objectInputStream.close();
			}
			if(socket != null) {
				socket.close();
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	

}
