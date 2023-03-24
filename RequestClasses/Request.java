import java.io.Serializable;

public class Request implements Serializable{
	//data members
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String requestType;

	
	//constructors
	public Request(String requestType) {
		this.requestType = requestType;
	}
	
	
	
	//methods
	public String getType() {
		return this.requestType;
	}



	

}
