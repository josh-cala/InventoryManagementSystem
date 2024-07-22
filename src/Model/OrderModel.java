package Model;

import java.util.List;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import View.OrderView;

public class OrderModel 
{
	private final OrderView view;

	public final String userID;
	
	public JPanel dynamicPanel;
	
	// CartModel
	public Object[][] products;

	public final JTable productTable;
	public final JScrollPane productScrollPane;
	public final JPanel tablePanel;
	
	public final JLabel productIDLabel;
	public final JTextField productIDTextField;
	public final JLabel quantityLabel;
	public final JTextField quantityTextField;
	public final JButton addToCartButton;
	public final JPanel addPanel;
	
	public final List<List<String>> cart;
	public final JTable cartTable;
	public final JScrollPane cartScrollPane;
	public final JPanel cartPanel;
	public final JButton submitOrderButton;
	
	// CompanyModel
	public final JPanel companyPanel;
	
	public final JLabel nameLabel;
	public final JLabel phoneLabel;
	public final JLabel emailLabel;
	public final JLabel addressLabel;
	
	public final JTextField nameTextField;
	public final JTextField phoneTextField; 
	public final JTextField emailTextField;
	public final JTextField addressTextField;
	
	public final JButton autoFillButton;
	public final JButton confirmButton;
	
	public OrderModel(final OrderView view, final String userID)
	{
		this.view = view;
		this.userID = userID;
		
		dynamicPanel = new JPanel();
		
		final DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn("ID");
		tableModel.addColumn("Product");
		tableModel.addColumn("Supplier");
		tableModel.addColumn("In Stock");
		tableModel.addColumn("Price");

		productTable = new JTable(tableModel);
		productTable.getTableHeader().setReorderingAllowed(false); // Disables column reordering
		productTable.setEnabled(false); // Disables cell editing
		productScrollPane = new JScrollPane(productTable);
		productScrollPane.setBorder(BorderFactory.createEmptyBorder());
		tablePanel = new JPanel();
		
		productIDLabel = new JLabel();
		productIDTextField = new JTextField();
		quantityLabel = new JLabel();
		quantityTextField = new JTextField();
		addToCartButton = new JButton("Add To Order");
		addPanel = new JPanel();
		
		cart = new ArrayList<>();
		
		final DefaultTableModel tableModel2 = new DefaultTableModel();
		tableModel2.addColumn("Product");
		tableModel2.addColumn("Quantity");
		tableModel2.addColumn("Cost");

		cartTable = new JTable(tableModel2);
		cartTable.getTableHeader().setReorderingAllowed(false); // Disables column reordering
		cartTable.setEnabled(false); // Disables cell editing
		cartScrollPane = new JScrollPane(cartTable);
		cartScrollPane.setBorder(BorderFactory.createEmptyBorder());
		cartPanel = new JPanel();
		submitOrderButton = new JButton("Submit Order");
		
		companyPanel = new JPanel();
		
		nameLabel = new JLabel("Company Name: ");
		phoneLabel = new JLabel("Phone #: ");
		emailLabel = new JLabel("Email: ");
		addressLabel = new JLabel("Address: ");
		
		nameTextField = new JTextField();
		phoneTextField = new JTextField();
		emailTextField = new JTextField();
		addressTextField = new JTextField();
		
		autoFillButton = new JButton("Auto Fill");
		confirmButton = new JButton("Confirm");
	}
}
