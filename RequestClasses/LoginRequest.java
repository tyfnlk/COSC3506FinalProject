
public class LoginRequest extends Request{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String password;
	private String username;

	public LoginRequest(String username, String password) {
		super("LoginRequest");
		this.username=username;
		this.password=password;
	}
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword(){
		return this.password;
	}

}
