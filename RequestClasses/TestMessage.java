
public class TestMessage extends Request{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	//default constructor sets request type to 0 = null
	public TestMessage() {
		super(null);
	}
	//constructor with parameters
	public TestMessage(String message) {
		//default set request type to "testMessage" == make same as class name
		super("TestMessage");
		this.message = message;
	}
	public String getMessage() {
		return this.message;
	}
	

}
