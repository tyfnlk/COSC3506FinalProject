
public class TestMessage extends Request{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;
	
	//default constructor sets request type to 0 = null
	public TestMessage() {
		super(0);
	}
	public TestMessage(String message) {
		//default set request type to 1 (1 = test message)
		super(1);
		this.message = message;
	}
	public String getMessage() {
		return this.message;
	}
	

}
