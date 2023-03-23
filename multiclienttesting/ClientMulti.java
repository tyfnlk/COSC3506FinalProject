import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientMulti {
	//datamembers
	
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
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
	public void sendMessage() {
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
	public void listenForMessage() {
		//needs to be a thread to prevent blocking
		new Thread(new Runnable() {
			public void run() {
				String msgFromGroupChat;
				
				while(socket.isConnected()) {
					try {
						msgFromGroupChat = bufferedReader.readLine();
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
		MainClient mainClient = new MainClient(socket, username);
		//run listening thread
		mainClient.listenForMessage();
		//run sending thread
		mainClient.sendMessage();
	}
}



