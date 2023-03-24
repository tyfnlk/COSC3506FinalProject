
public class SuccessfulLoginRequest extends Request{
	private String username;
	private boolean result;
	private String message;

	public SuccessfulLoginRequest( String username, boolean result, String message) {
		super("SuccessfulLoginRequest");
		this.username=username;
		this.result = result;
		this.message = message;
		// TODO Auto-generated constructor stub
	}
	public String getUsername() {
		return this.username;
	}
	public boolean getResult() {
		return this.result;
	}
	
	public String getMessage() {
		return this.message;
	}

}
