package Model;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.User;
import View.MainView;

public class MainModel 
{
	private final MainView view;
	
	public final User user;
	
	public final JLabel welcomeLabel;
	
	// Buttons
	public final JButton searchProductsButton;
	public final JButton updateOrderButton;
	public final JButton createOrderButton;
	public final JButton viewActivityButton;
	
	public final JPanel menuPanel;
	
	public final JPanel infoPanel;
	public final JLabel nameLabel;
	public final JLabel empTypeLabel;
	public final JLabel dateLabel;
	
	public final JPanel dynamicPanel;
	
	public void add(final Container c)
	{
		dynamicPanel.add(c);
	}
	
	public MainModel(final MainView view, final User user)
	{
		this.view = view;
		
		this.user = user;
		
		welcomeLabel = new JLabel();
		
		// Initialize Buttons
		searchProductsButton = new JButton("Search For Products");
		updateOrderButton    = new JButton("Update An Order");
		createOrderButton    = new JButton("Create An Order");
		viewActivityButton   = new JButton("View Activity");
		
		menuPanel = new JPanel();
		
		infoPanel = new JPanel();
		
		dynamicPanel = new JPanel();
		
		nameLabel = new JLabel(user.firstName + " " + user.lastName);
		empTypeLabel = new JLabel(user.employeeType + " Account");
		dateLabel = new JLabel(java.time.LocalDate.now().toString());
	}



	
}
