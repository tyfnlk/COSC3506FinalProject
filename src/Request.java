import java.io.Serializable;

public class Request implements Serializable{
	//data members
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int requestType;
	/*
	 * 0 = empty
	 * 1 = message
	 * 2 = create
	 * 3
	 * 4
	 * 5
	 * 6
	 * 7
	 */
	
	//constructors
	public Request(int requestType) {
		this.requestType = requestType;
	}
	
	
	
	//methods
	public int getType() {
		return this.requestType;
	}



	

}
