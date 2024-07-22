package View;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import Controller.ActivityController;
import Controller.SQL;
import Main.User;
import Model.ActivityModel;

public class ActivityView extends JFrame
{
	private final ActivityModel model;
	private final ActivityController control;
	
	private final Grid grid;

	public void setCompanyCheckBox(int index, boolean checked) { model.companyCheckBox.get(index).setSelected(checked); }
	public void setStatusCheckBox (int index, boolean checked) { model.statusCheckBox.get(index).setSelected(checked);  }

	// Checks or unchecks every company filter
	public void setAllCompanyCheckBoxes(boolean checked)
	{
		for(int i=1; i<model.companyCheckBox.size(); i++)
		{
			model.companyCheckBox.get(i).setSelected(checked);
		}
	}

	// Checks or unchecks every status filter
	public void setAllStatusCheckBoxes(boolean checked)
	{
		for(int i=1; i<model.statusCheckBox.size(); i++)
		{
			model.statusCheckBox.get(i).setSelected(checked);
		}
	}

	// Displays the filtered orders
	public void displayOrders()
	{
		model.listPanel.setLayout(new GridBagLayout());
		grid.setInset(10, 10, 10, 10);
		grid.setAnchor(Grid.NORTH);
		model.listPanel.removeAll();
		for(int i = 0; i < model.orderButtons.size(); ++i)
		{
			grid.setGrid(i%5, i/5);
			model.orderButtons.get(i).addActionListener(control.getOrderListener());
			model.listPanel.add(model.orderButtons.get(i), grid);
			
		}
		model.listScrollPane.revalidate();
		model.listScrollPane.repaint();
	}

	// Displays a detailed panel of the selected order
	public void displayOrderDetails()
	{
		model.orderIDLabel.setText("Order ID: " + model.orderID);
		model.orderTypeLabel.setText("Type: " + model.orderType);
		model.orderCompanyLabel.setText("Company: " + model.orderCompany);
		model.orderStatusLabel.setText("Status: " + model.orderStatus);
		model.orderDateLabel.setText("Date: " + model.orderDate);
		model.orderPriceLabel.setText("Total Price: $" + model.orderPrice);

		model.orderPanel.setVisible(true);
	}

//	public ActivityView(final JFrame lastFrame)
	public ActivityView(final User user)
	{
		model = new ActivityModel(this, user);
		control = new ActivityController(this, model);
		
		grid = new Grid();
		int y = 0;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("CS 442 Project");
		//this.setSize(lastFrame.getSize());
		this.setSize(1600,900);
		this.setLayout(new BorderLayout());
		//this.setLocationRelativeTo(lastFrame);
		//lastFrame.dispose();
		
		
		model.menuPanel.setLayout(new GridBagLayout());
		
		// Add Company Section
		grid.setGrid(0, y++);
		grid.setInset(10, 15, 10, 15);
		model.menuPanel.add(model.companyLabel, grid);
		
		grid.setInset(0, 15, 0, 15);
		grid.setAnchor(Grid.WEST);
		for(int i = 0; i < model.companyCheckBox.size(); ++i)
		{
			grid.setGrid(0, y++);
			model.companyCheckBox.get(i).addActionListener(control.getCompanyListener());
			model.menuPanel.add(model.companyCheckBox.get(i), grid);
		}
		model.companyCheckBox.get(0).doClick();

		
		// Add Status Section
		grid.setGrid(0, y++);
		grid.setInset(25, 15, 10, 15);
		grid.setAnchor(Grid.CENTER);
		model.menuPanel.add(model.statusLabel, grid);
		
		grid.setInset(0, 15, 0, 15);
		grid.setAnchor(Grid.WEST);
		for(int i = 0; i < model.statusCheckBox.size(); ++i)
		{
			grid.setGrid(0, y++);
			model.statusCheckBox.get(i).addActionListener(control.getStatusListener());
			model.menuPanel.add(model.statusCheckBox.get(i), grid);
		}
		model.statusCheckBox.get(0).doClick();
		
		// Add Date Section
		grid.setGrid(0, y++);
		grid.setInset(25, 15, 10, 15);
		grid.setAnchor(Grid.CENTER);
		model.menuPanel.add(model.dateLabel, grid);
		
		grid.setGrid(0, y);
		grid.setInset(0, 15, 0, 15);
		grid.setAnchor(Grid.WEST);
		model.menuPanel.add(model.startLabel, grid);
		
		grid.setGrid(0, y++);
		grid.setInset(0, 15, 0, 15);
		grid.setAnchor(Grid.EAST);
		model.menuPanel.add(model.startDateComboBox, grid);
		
		grid.setGrid(0, y);
		grid.setInset(10, 15, 0, 15);
		grid.setAnchor(Grid.WEST);
		model.menuPanel.add(model.endLabel, grid);
		
		grid.setGrid(0, y++);
		grid.setInset(10, 15, 0, 15);
		grid.setAnchor(Grid.EAST);
		model.menuPanel.add(model.endDateComboBox, grid);
		model.endDateComboBox.setSelectedIndex(model.endDateComboBox.getItemCount()-1);

		// Add Order Details Section
		model.orderPanel.setLayout(new GridBagLayout());
		y = 0;
		grid.setInset(10, 10, 10, 10);
		grid.setAnchor(Grid.NORTH);

		grid.setGrid(0, y++);
		model.orderPanel.add(model.orderIDLabel, grid);

		grid.setGrid(0, y++);
		model.orderPanel.add(model.orderTypeLabel, grid);

		grid.setGrid(0, y++);
		model.orderPanel.add(model.orderCompanyLabel, grid);

		grid.setGrid(0, y++);
		model.orderPanel.add(model.orderStatusLabel, grid);

		grid.setGrid(0, y++);
		model.orderPanel.add(model.orderDateLabel, grid);

		grid.setGrid(0, y++);
		model.orderPanel.add(model.orderPriceLabel, grid);

		grid.setGrid(0, y++);
		model.orderPanel.add(model.orderTableScrollPane, grid);
		
		model.orderPanel.setVisible(false);
		
		this.add(model.menuScrollPane, BorderLayout.WEST);
		this.add(model.listScrollPane, BorderLayout.CENTER);
		this.add(model.orderScrollPane, BorderLayout.EAST);
		
		//this.pack();
		//this.setVisible(true);
	}
}
