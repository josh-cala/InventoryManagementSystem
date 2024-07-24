package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import Controller.MainController;
import Controller.SQL;
import Main.User;
import Model.MainModel;

public class MainView extends JFrame
{
	private final MainController control;
	private final MainModel model;

	private final Grid grid;
	
	public MainView(final JFrame lastFrame, final User user)
	{
		model = new MainModel(this, user);
		control = new MainController(this, model);
		
		grid = new Grid();
		grid.setInset(5, 25, 5, 25);
		
		// Initialize Frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("CS 442 Project");
		//this.setSize(lastFrame.getSize());
		this.setSize(1600,900);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(lastFrame);
		lastFrame.dispose();
		

		model.menuPanel.setLayout(new FlowLayout(10, 25, 25));
		model.menuPanel.setBackground(Color.GRAY);
		//model.menuPanel.setBorder(BorderFactory.createEmptyBorder(25, 50, 25, 50));
		
		
		model.searchProductsButton.addActionListener(control.getMenuButtonListener());
		model.updateOrderButton.addActionListener(control.getMenuButtonListener());
		model.createOrderButton.addActionListener(control.getMenuButtonListener());
		model.viewActivityButton.addActionListener(control.getMenuButtonListener());
		
		int y = 0;
		
		model.infoPanel.setLayout(new GridBagLayout());
		model.infoPanel.setBackground(Color.LIGHT_GRAY);

		grid.setGrid(0, y++);
		model.infoPanel.add(model.nameLabel, grid);
		grid.setGrid(0, y++);
		model.infoPanel.add(model.empTypeLabel, grid);
		grid.setGrid(0, y++);
		model.infoPanel.add(model.dateLabel, grid);
		
		
		
		model.menuPanel.add(model.infoPanel);
		
		model.menuPanel.add(model.welcomeLabel);
		
		if(SQL.isAdmin(user.employeeID))
		{
			model.menuPanel.add(model.searchProductsButton);
			
			model.menuPanel.add(model.updateOrderButton);
			
			model.menuPanel.add(model.createOrderButton);
		
		}
		
		model.menuPanel.add(model.viewActivityButton);
		
		
		model.dynamicPanel.setLayout(new BorderLayout());
		
		this.add(model.menuPanel, BorderLayout.NORTH);
		
		this.add(model.dynamicPanel, BorderLayout.CENTER);

		//this.pack();
		this.setVisible(true);
	}
	
	

	
}





















