import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
//datamembers
	
	//static array list of all clients
	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
	private Socket socket;
	//get data
	private ObjectInputStream objectInputStream;
	//send data
	private ObjectOutputStream objectOutputStream;
	private String clientusername;
	
	
	//constructor
	public ClientHandler(Socket socket) throws Exception {
		try {
			this.socket = socket;
			
			//buffered writer to increase efficiency (possibly just buffer the object output stream)
			
			this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			
			this.objectInputStream = new ObjectInputStream(socket.getInputStream());

			Request req= (Request)objectInputStream.readObject();
			
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
		String messageFromClient;
		
		//while socket is connected , listen for message
		while(socket.isConnected()) {
			try {
				messageFromClient = objectInputStream.readLine();
				broadcastMessage(messageFromClient);
				
			}catch(IOException e) {
				closeEverything(socket,objectInputStream, objectOutputStream);
				break;
			}
		}
		
	}
	public void broadcastMessage(String messageToSend) {
		for(ClientHandler clientHandler : clientHandlers) {
			try {
				if(!clientHandler.clientusername.equals(clientusername)) {
					
					clientHandler.objectOutputStream.write(messageToSend);
					clientHandler.objectOutputStream.newLine();
					clientHandler.objectOutputStream.flush();
					
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
