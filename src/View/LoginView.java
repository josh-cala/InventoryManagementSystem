package View;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Controller.LoginController;
import Model.LoginModel;

public class LoginView extends JFrame
{
	private final LoginController control;
	private final LoginModel      model;
	
	private final Grid grid;
	
	private JLabel messageLabel;
	private JTextField usernameTextField;
	private JPasswordField passwordTextField;

	// Getters
	public JLabel getMessageLabel() { return messageLabel;                }
	public String getUsername()     { return usernameTextField.getText(); }
	public char[] getPassword()     { return passwordTextField.getPassword(); }
	
	// Setters
	public void setMessageLabel(String message)  { messageLabel.setText(message);       }
	public void setUsername    (String username) { usernameTextField.setText(username); }
	public void setPassword    (String password) { passwordTextField.setText(password); }
	
	public void setTextFieldWidth() 
	{
		usernameTextField.setColumns(15);
		passwordTextField.setColumns(15);
	}
	
	public LoginView()
	{
		// Initialize Controller/Model
		model = new LoginModel(this);
		control = new LoginController(this, model);

		
		// Inititialize Frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("CS 442 Project");
		this.setSize(500, 500);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		
		
		// Initialize Panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		grid = new Grid();
		grid.setInset(10, 10, 10, 10);
		
		// Initialize Message
		final JLabel enterLabel = new JLabel("Please Enter Username and Password");
		grid.setGrid(0, 0, 2, 1);
        panel.add(enterLabel, grid);
		
		// Inititalize Inputs
		final JLabel usernameLabel = new JLabel("Username: ");
		final JLabel passwordLabel = new JLabel("Password: ");
		
		usernameTextField = new JTextField(model.getUsername());
		passwordTextField = new JPasswordField(model.getPassword());
		
		usernameTextField.setColumns(15);
		passwordTextField.setColumns(15);
		
		usernameTextField.getDocument().addDocumentListener(control.getTextFieldListener());
		passwordTextField.getDocument().addDocumentListener(control.getTextFieldListener());

		
		grid.setGrid(0, 1);
		panel.add(usernameLabel, grid);
		
		grid.setGrid(1, 1);
		panel.add(usernameTextField, grid);
		
		grid.setGrid(0, 2);
		panel.add(passwordLabel, grid);
		
		grid.setGrid(1, 2);
		panel.add(passwordTextField, grid);
		
		// Initialize Button
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(control.getButtonListener());
		this.getRootPane().setDefaultButton(loginButton); // Allows enter to be used to log in.
	
		grid.setGrid(0, 3);
		panel.add(loginButton, grid);
		
		grid.setGrid(0, 4, 2, 1);
		messageLabel = new JLabel(model.getMessage());
		panel.add(messageLabel, grid);
		
		this.add(panel, BorderLayout.CENTER);
		
		this.pack();
		this.setVisible(true);
	}

}
