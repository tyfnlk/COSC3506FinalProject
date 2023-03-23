import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientMulti {
	//datamembers
	
	private Socket socket;
	//receive data
	private ObjectInputStream objectInputStream;
	//send data
	private ObjectOutputStream objectOutputStream;
	public String username;
	
	//constructor
	public ClientMulti(Socket socket, String username) {
		this.username=username;
		try {
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch(IOException e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
			
		}
	}
	
	@SuppressWarnings("resource")
	public void sendRequest() {
		try {
			bufferedWriter.write(username);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			
			Scanner scanner = new Scanner(System.in);
			
			while(socket.isConnected()) {
				String messageToSend = scanner.nextLine();
				
				bufferedWriter.write(this.username +": " + messageToSend);
				bufferedWriter.newLine();
				bufferedWriter.flush();

			}
				
		}catch(IOException e) {
				closeEverything(socket, bufferedReader, bufferedWriter);
		}
		
	}
	public void listenForRequest() {
		//needs to be a thread to prevent blocking
		new Thread(new Runnable() {
			public void run() {
				Request request;
				
				while(socket.isConnected()) {
					try {
						request = (Request) bufferedReader.readLine();
						System.out.println(msgFromGroupChat);
					}catch(IOException e) {
						closeEverything(socket, bufferedReader, bufferedWriter);
						
					}
					
				}
			}
		}).start();
	}
	
	public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter username: ");
		String username = scan.nextLine();
		
		Socket socket = new Socket ("localhost", 1234);
		//create client object
		ClientMulti clientMulti = new ClientMulti(socket, username);
		//run listening thread
		clientMulti.listenForRequest();
		//run sending thread
		clientMulti.sendRequest();
	}
}



