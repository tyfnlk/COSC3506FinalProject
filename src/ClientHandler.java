import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
//datamembers
	
	//static array list of all clients
	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
	private Socket socket;
	//get data
	private BufferedReader bufferedReader;
	//send data
	private BufferedWriter bufferedWriter;
	private String clientusername;
	
	
	//constructor
	public ClientHandler(Socket socket) {
		try {
			this.socket = socket;
			
			//buffered writer to increase efficiency (possibly just buffer the object output stream)
			
			this.bufferedWriter = new 
					BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.clientusername = bufferedReader.readLine();
			clientHandlers.add(this);
			broadcastMessage("server" + clientusername + "has entered tehj chat");
		}catch(IOException e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
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
				messageFromClient = bufferedReader.readLine();
				broadcastMessage(messageFromClient);
				
			}catch(IOException e) {
				closeEverything(socket,bufferedReader, bufferedWriter);
				break;
			}
		}
		
	}
	public void broadcastMessage(String messageToSend) {
		for(ClientHandler clientHandler : clientHandlers) {
			try {
				if(!clientHandler.clientusername.equals(clientusername)) {
					
					clientHandler.bufferedWriter.write(messageToSend);
					clientHandler.bufferedWriter.newLine();
					clientHandler.bufferedWriter.flush();
					
				}
			}catch(IOException e) {
				closeEverything(socket, bufferedReader, bufferedWriter);
			}
		}
		
	}
	public void removeClientHandler() {
		//remove from array list
		clientHandlers.remove(this);
		broadcastMessage("SERVER" + clientusername + "has left the chat");
	}
	
	public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		removeClientHandler();
		//closing bufferd... closes all readers/writers nested\
		//closing socket closes all socket inputs/outputs
		try {
			if(bufferedReader != null) {
				bufferedReader.close();
			}
			if(bufferedWriter != null) {
				bufferedWriter.close();
			}
			if(socket != null) {
				socket.close();
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	

}
