import java.io.Serializable;

public class RequestTest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type ="Message";
	private String message;
	
	public RequestTest(String msg) {
		this.message = msg;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getMessage() {
		return this.message;
	}
	

}
