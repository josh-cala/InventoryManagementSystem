package Model;

import Controller.LoginController;
import View.LoginView;
import View.MainView;

public class LoginModel 
{
	private String username;
	private String password;
	private String message;
	private final LoginView view;
	
	
	public LoginModel(LoginView view)
	{
		username = "";
		password = "";
		message = " ";
		this.view = view;
	}
	
	// Getters
	public String  getUsername() { return username; }
	public String  getPassword() { return password; }
	public String  getMessage()  { return message;  }

	// Setters
	public void setUsername(String  username) { this.username = username; }
	public void setPassword(String  password) { this.password = password; }
	public void setMessage (String  message)  { this.message  = message;  }
	
	public void reset()
	{
		this.setUsername("");
		this.setPassword("");

		
		view.setUsername("");
		view.setPassword("");
		
		view.setTextFieldWidth();
	}
	
	public void update(final String message)
	{
		reset();	
		this.message = message;
		view.setMessageLabel(message);
	}
}
