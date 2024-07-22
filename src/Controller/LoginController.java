package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import Main.User;
import Model.LoginModel;
import View.LoginView;
import View.MainView;

public class LoginController
{
	private final LoginView lv;
	private final LoginModel lm;
	
	private final LoginButtonListener loginButtonListener;
	private final TextFieldListener textFieldListener;
	
	public LoginButtonListener getButtonListener()
	{
		return loginButtonListener;
	}
	
	public TextFieldListener getTextFieldListener()
	{
		return textFieldListener;
	}
	
	
	public LoginController(LoginView lv, LoginModel lm)
	{
		this.lv = lv;
		this.lm = lm;
		this.loginButtonListener = new LoginButtonListener(lv, lm);
		this.textFieldListener = new TextFieldListener(lv, lm);
	}
}

class LoginButtonListener implements ActionListener
{
	private final LoginModel lm;
	private final LoginView lv;
	
	public LoginButtonListener(LoginView lv, LoginModel lm)
	{
		this.lv = lv;
		this.lm = lm;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		/* Possible Query Results
		 *
		 *	"VALID"        -> Valid username and password
		 *	"BAD_USERNAME" -> Username was not found
		 *	"BAD_PASSWORD" -> Incorrect Password
		 *	"ERROR"		   -> SQL Error
		 */

		final String result = SQL.checkLogin(lm.getUsername(), lv.getPassword());
		
		if(result.equals("VALID"))
		{
			//lv.dispose();
			
			final User user = new User(lm.getUsername());
			
			final MainView mv = new MainView(lv, user);
		}
		else if(result.equals("BAD_USERNAME"))
		{
			lm.update("Invalid Username");
		}
		else if(result.equals("BAD_PASSWORD"))
		{
			lm.update("Invalid Password");
		}
		else //result.equals("ERROR")
		{
			lm.update("Error");
		}
		
	}
}

class TextFieldListener implements DocumentListener
{
	private final LoginModel lm;
	private final LoginView lv;
	
	public TextFieldListener(LoginView lv, LoginModel lm)
	{
		this.lv = lv;
		this.lm = lm;
	}

	@Override
	public void insertUpdate(DocumentEvent e) 
	{	
		updateModel();
	}

	@Override
	public void removeUpdate(DocumentEvent e) 
	{
		updateModel();
	}

	@Override
	public void changedUpdate(DocumentEvent e)
	{
		updateModel();
	}
	
	private void updateModel()
	{
		lm.setUsername(lv.getUsername());
		lm.setPassword(lv.getPassword().toString());
	}
}
