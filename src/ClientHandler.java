import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientHandler implements Runnable{
//DATA MEMBERS
	private Socket client;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	public static ArrayList<ClientHandler> clientList = new ArrayList<>();
	
	
//CONSTRUCTOR
	public ClientHandler(Socket clientSocket) throws IOException {
		this.client = clientSocket;
		in = new ObjectInputStream(client.getInputStream());
		out = new ObjectOutputStream(client.getOutputStream());
		
		clientList.add(this);
		System.out.println("New Client Connected created:");
		
	}
//METHOD
	
//Fundamental Methods
	@Override
	public void run() {
		
		try {
		    for(Request request = (Request)in.readObject();; request = (Request)in.readObject())
		    {
		         //listen for requests
		    	switch(request.getType()) {
		    	case "LoginRequest":
		    		System.out.println("login request");
		    		LoginRequest loginRequest = (LoginRequest)request;
		    		System.out.println("attempting to match credentials");
		    		attemptLogin(loginRequest);
		    	}

		    	
		    }
		} catch(Exception ex)
		{
		    //EOF found
		}
	
	}
	
	public void sendResponse() throws IOException {
		Request response = new Request("Youre request has been sorted");
		out.writeObject(response);
	}

//Request Response Methods
	
	public void attemptLogin(LoginRequest loginRequest) throws Exception {
		//import login credentials into a hashmap
		HashMap<String,String> lc = new HashMap<String, String>();
		lc.put("terry", "pass");
		lc.put("a", "1");
		lc.put("b", "2");
		lc.put("c", "3");
		lc.put("d", "4");
		System.out.println("attempting login");
		
		//check if user exists
		if(lc.get(loginRequest.getUsername()) == null){
			//if user does not exist, return false object
			System.out.println("attempting login invalid user");
			out.writeObject(new SuccessfulLoginRequest(loginRequest.getUsername(), false,"invalid username"));
			out.flush();
			
			
			//usermatch found in hashmap, check if password matches
		} else if(lc.get(loginRequest.getUsername()).contentEquals(loginRequest.getPassword())) {
			//if password match, return true
			System.out.println("attempting login success");
			out.writeObject(new SuccessfulLoginRequest(loginRequest.getUsername(), true,"login successful"));
			out.flush();
			System.out.println("succesfullogin packet sent");
			
		}else {
			//if password does not match, return false
			System.out.println("attempting login invalid pass");

			out.writeObject(new SuccessfulLoginRequest(loginRequest.getUsername(), false,"invalid password"));
			out.flush();
		
		}
		
		
		
	}

}
