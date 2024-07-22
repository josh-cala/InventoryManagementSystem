package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Controller.SQL;
import Controller.UpdateController;
import Model.Order;
import Model.UpdateModel;

public class UpdateView extends JPanel
{
	private final UpdateController control;
	private final UpdateModel model;
	
	private final Grid grid;
	
	public UpdateView()
	{
		model = new UpdateModel(this);
		control = new UpdateController(this, model);
		
		grid = new Grid();
		grid.setInset(10, 10, 10, 10);
		
		this.setLayout(new BorderLayout());
		
		
		// Search
		model.searchPanel.setLayout(new GridBagLayout());
		model.searchPanel.setBackground(Color.LIGHT_GRAY);
		model.searchPanel.setAlignmentY(CENTER_ALIGNMENT);
		model.searchLabel.setText("Enter Order ID:");
		model.searchTextField.setColumns(10);
		model.searchButton.setText("Search");
		model.searchButton.addActionListener(control.getSearchButtonListener());
		
		grid.setInset(25, 500, 25, 15);
		model.searchPanel.add(model.searchLabel, grid);
		grid.setInset(25, 15, 25, 15);
		model.searchPanel.add(model.searchTextField, grid);
		grid.setInset(25, 15, 25, 500);
		model.searchPanel.add(model.searchButton, grid);
		grid.setInset(10, 10, 10, 10);
		
		// Info
		model.infoPanel.setLayout(new FlowLayout());
		model.infoPanel.setBackground(Color.LIGHT_GRAY);
		model.infoPanel.setSize(250, 1000);
		
		model.labelPanel.setLayout(new GridBagLayout());
		model.labelPanel.setBackground(Color.LIGHT_GRAY);
		model.labelPanel.setMinimumSize(new Dimension(50, 0));
		
		model.valuePanel.setLayout(new GridBagLayout());
		model.valuePanel.setBackground(Color.LIGHT_GRAY);
		model.valuePanel.setMinimumSize(new Dimension(50, 0));
		
		model.idLabel       .setText("Order ID:");
		model.priceLabel    .setText("Total Price:");
		model.dateLabel     .setText("Date:");
		model.companyLabel  .setText("Company:");
		model.statusLabel   .setText("Status:");
		model.typeLabel     .setText("Type:");
		model.adminLabel    .setText("Admin:");
		model.associateLabel.setText("Associate:");
		
		int y = 0;
		grid.setAnchor(Grid.WEST);
		grid.setInset(50, 10, 15, 10);
		grid.setGrid(0, y++);
		model.labelPanel.add(model.idLabel, grid);
		grid.setInset(15, 10, 15, 10);
		grid.setGrid(0, y++);
		model.labelPanel.add(model.priceLabel, grid);
		grid.setInset(15, 10, 15, 10);
		grid.setGrid(0, y++);
		model.labelPanel.add(model.dateLabel, grid);
		grid.setInset(15, 10, 15, 10);
		grid.setGrid(0, y++);
		model.labelPanel.add(model.companyLabel, grid);
		grid.setInset(15, 10, 15, 10);
		grid.setGrid(0, y++);
		model.labelPanel.add(model.statusLabel, grid);
		grid.setInset(15, 10, 15, 10);
		grid.setGrid(0, y++);
		model.labelPanel.add(model.typeLabel, grid);
		grid.setInset(15, 10, 15, 10);
		grid.setGrid(0, y++);
		model.labelPanel.add(model.adminLabel, grid);
		grid.setInset(15, 10, 50, 10);
		grid.setGrid(0, y++);
		model.labelPanel.add(model.associateLabel, grid);
		grid.setInset(10, 10, 10, 10);
		
		grid.setInset(0, 0, 0, 0);
		grid.setGrid(0, 0);
		model.infoPanel.add(model.labelPanel);
		grid.setGrid(1, 0);
		model.infoPanel.add(model.valuePanel);
		grid.setInset(10, 10, 10, 10);
		
		setOrderInfo("0");
		
		// Update
		model.productIDTextField.setColumns(15);
		model.quantityTextField.setColumns(15);
		model.productIDLabel.setText("Enter ID: ");
		model.quantityLabel.setText("Enter Quantity: ");
		
		model.updateButton.addActionListener(control.getRemoveButtonListener());
		model.cancelButton.addActionListener(control.getCancelButtonListener());
		
		model.updatePanel.setLayout(new GridBagLayout());
		grid.setGrid(0, 0, 1, 4);
		model.updatePanel.add(model.productScrollPane, grid);
		grid.setGrid(1, 0);
		model.updatePanel.add(model.productIDLabel, grid);
		grid.setGrid(2, 0);
		model.updatePanel.add(model.productIDTextField, grid);
		grid.setGrid(1, 1);
		model.updatePanel.add(model.quantityLabel, grid);
		grid.setGrid(2, 1);
		model.updatePanel.add(model.quantityTextField, grid);
		grid.setGrid(1, 2);
		model.updatePanel.add(model.updateButton, grid);
		grid.setGrid(1, 3);
		model.updatePanel.add(model.cancelButton, grid);
		
		this.add(model.searchPanel, BorderLayout.NORTH);
		this.add(model.infoPanel, BorderLayout.WEST);
		this.add(model.updatePanel, BorderLayout.CENTER);
	}
	
	public void setOrderInfo(final String orderID)
	{
		final List<Object> order = SQL.getOrderInfoForUpdate(orderID);
		
		model.valuePanel.removeAll();

		if(order.size() == 0)
		{	
			model.order = null;
		}
		else
		{
			model.order = new Order(order);

			int y = 0;
			grid.setAnchor(Grid.WEST);
			grid.setInset(50, 10, 15, 10);
			grid.setGrid(0, y++);
			model.valuePanel.add(new JLabel(model.order.orderID.toString()), grid);
			grid.setInset(15, 10, 15, 10);
			grid.setGrid(0, y++);
			model.valuePanel.add(new JLabel(model.order.totalPrice.toString()), grid);
			grid.setInset(15, 10, 15, 10);
			grid.setGrid(0, y++);
			model.valuePanel.add(new JLabel(model.order.date), grid);
			grid.setInset(15, 10, 15, 10);
			grid.setGrid(0, y++);
			model.valuePanel.add(new JLabel(model.order.companyName), grid);
			grid.setInset(15, 10, 15, 10);
			grid.setGrid(0, y++);
			model.valuePanel.add(new JLabel(model.order.status), grid);
			grid.setInset(15, 10, 15, 10);
			grid.setGrid(0, y++);
			model.valuePanel.add(new JLabel(model.order.type), grid);
			grid.setInset(15, 10, 15, 10);
			grid.setGrid(0, y++);
			model.valuePanel.add(new JLabel(model.order.adminName), grid);
			grid.setInset(15, 10, 50, 10);
			grid.setGrid(0, y++);
			model.valuePanel.add(new JLabel(model.order.assocName), grid);
			grid.setInset(10, 10, 10, 10);
			
			final Object[][] table = SQL.getProductsFromOrder(orderID);
			
			DefaultTableModel m = (DefaultTableModel)model.productTable.getModel();
			
			m.setRowCount(0);
			
			for(int i = 0; i < table.length; ++i)
			{
				m.addRow(table[i]);
				model.order.productIds.add((Integer)table[i][0]);
				model.order.costPerUnit.add((Double)table[i][3]);
				model.order.quantities.add((Integer)table[i][2]);
			}
		}
		
		
		
		
		SwingUtilities.updateComponentTreeUI(this);
	}

}
