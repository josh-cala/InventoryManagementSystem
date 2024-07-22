package View;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Controller.OrderController;
import Controller.SQL;
import Model.OrderModel;

public class OrderView extends JPanel
{
	private final OrderModel model;
	private final OrderController control;
	
	private final Grid grid;
	
	public OrderView(final String userID) 
	{
		
		
		model = new OrderModel(this, userID);
		control = new OrderController(this, model);
		
		grid = new Grid();
		grid.setInset(10, 10, 10, 10);
		
		this.setLayout(new FlowLayout());
		
		model.dynamicPanel.setLayout(new FlowLayout());
		
		model.tablePanel.setLayout(new GridBagLayout());
		
		model.cartPanel.setLayout(new GridBagLayout());
		
		model.companyPanel.setLayout(new GridBagLayout());
		
		setProductTable();	
		model.tablePanel.add(model.productScrollPane, grid);
		
		
		model.productIDLabel.setText("Enter Product ID: ");
		model.quantityLabel.setText("Enter Quantity: ");

		model.productIDTextField.setColumns(15);
		model.quantityTextField.setColumns(15);
		
		model.addPanel.setLayout(new GridBagLayout());
		grid.setGrid(0, 0);
		model.addPanel.add(model.productIDLabel, grid);
		grid.setGrid(0, 1);
		model.addPanel.add(model.quantityLabel, grid);
		grid.setGrid(1, 0);
		model.addPanel.add(model.productIDTextField, grid);
		grid.setGrid(1, 1);
		model.addPanel.add(model.quantityTextField, grid);
		grid.setGrid(1, 3);
		model.addPanel.add(model.addToCartButton, grid);
				
		grid.setGrid(0, 0);
		model.cartPanel.add(model.cartScrollPane, grid);
		grid.setGrid(0, 1);
		model.cartPanel.add(model.submitOrderButton, grid);
		
		
		model.dynamicPanel.add(model.tablePanel, BorderLayout.WEST);
		model.dynamicPanel.add(model.addPanel, BorderLayout.CENTER);
		model.dynamicPanel.add(model.cartPanel, BorderLayout.EAST);
		
		this.add(model.dynamicPanel);
	}
	
	public void setProductTable()
	{
		model.products = SQL.getAllProducts();
		
		DefaultTableModel m = (DefaultTableModel)model.productTable.getModel();
		
		m.setRowCount(0);
		
		for(int i = 0; i < model.products.length; ++i)
		{
			m.addRow(model.products[i]);
		}
		
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void addToCartTable(final String productID, final String quantity)
	{
		if(SQL.productIsAvailable(productID, quantity))
		{
			DefaultTableModel m = (DefaultTableModel)model.cartTable.getModel();
			
			Object[] row = new Object[3];
			
			row[0] = productID;
			row[1] = quantity;
			row[2] = SQL.getPrice(productID) * Integer.parseInt(quantity);
			
			m.addRow(row);
			
			SwingUtilities.updateComponentTreeUI(this);
			
			List<String> temp = new ArrayList<>();
			
			temp.add(productID);
			temp.add(quantity);
			temp.add(row[2].toString());
			
			model.cart.add(temp);
		}
	}
	
	public void setCompanyView()
	{
		model.dynamicPanel.removeAll();
		
		int x = 0;
		int y = 0;
		
		grid.setGrid(x++, y);
		model.companyPanel.add(model.nameLabel, grid);
		
		grid.setGrid(x--, y++);
		model.companyPanel.add(model.nameTextField, grid);
		
		grid.setGrid(x++, y);
		model.companyPanel.add(model.phoneLabel, grid);
		
		grid.setGrid(x--, y++);
		model.companyPanel.add(model.phoneTextField, grid);
		
		grid.setGrid(x++, y);
		model.companyPanel.add(model.emailLabel, grid);
		
		grid.setGrid(x--, y++);
		model.companyPanel.add(model.emailTextField, grid);
		
		grid.setGrid(x++, y);
		model.companyPanel.add(model.addressLabel, grid);
		
		grid.setGrid(x--, y++);
		model.companyPanel.add(model.addressTextField, grid);
		
		grid.setGrid(x--, y++);
		model.companyPanel.add(model.autoFillButton, grid);
		
		grid.setGrid(x, y);
		model.companyPanel.add(model.confirmButton, grid);
		
		
		
		model.dynamicPanel.add(model.companyPanel, grid);
		
		model.nameTextField.setColumns(35);
		model.phoneTextField.setColumns(35);
		model.emailTextField.setColumns(35);
		model.addressTextField.setColumns(35);
		
		SwingUtilities.updateComponentTreeUI(this);
		
	}

	public void autoFill(List<String> fields) 
	{
		model.phoneTextField.setText(fields.get(0));
		model.emailTextField.setText(fields.get(1));
		model.addressTextField.setText(fields.get(2));
		
	}
}
