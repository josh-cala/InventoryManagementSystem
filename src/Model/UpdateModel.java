package Model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import View.UpdateView;

public class UpdateModel 
{
	private final UpdateView view;
	
	public Order order;
	
	// Search
	public final JPanel searchPanel;
	public final JLabel searchLabel;
	public final JTextField searchTextField;
	public final JButton searchButton;
	
	// Info
	public final JPanel infoPanel;
	public final JPanel labelPanel;
	public final JPanel valuePanel;
	public final JLabel foundLabel;
	public final JLabel idLabel;
	public final JLabel priceLabel;
	public final JLabel dateLabel;
	public final JLabel companyLabel;
	public final JLabel statusLabel;
	public final JLabel typeLabel;
	public final JLabel adminLabel;
	public final JLabel associateLabel;
	
	// Update
	public final JPanel updatePanel;
	public final JButton cancelButton;
	public final JButton updateButton;
	public final JTextField productIDTextField;
	public final JTextField quantityTextField;
	public final JLabel productIDLabel;
	public final JLabel quantityLabel;
	public final JScrollPane productScrollPane;
	public final JTable productTable;
	
	
	public UpdateModel(final UpdateView view)
	{
		this.view = view;
		
		// Search
		searchPanel = new JPanel();
		searchLabel = new JLabel();
		searchTextField = new JTextField();
		searchButton = new JButton();
		
		// Info
		infoPanel = new JPanel();
		
		labelPanel = new JPanel();
		valuePanel = new JPanel();
		
		foundLabel = new JLabel();
		idLabel = new JLabel();
		priceLabel = new JLabel();
		dateLabel = new JLabel();
		companyLabel = new JLabel();
		statusLabel = new JLabel();
		typeLabel = new JLabel();
		adminLabel = new JLabel();
		associateLabel = new JLabel();
		
		//Update
		updatePanel = new JPanel();
		cancelButton = new JButton("Cancel Order");
		updateButton = new JButton("Remove Product");
		productIDTextField = new JTextField();
		quantityTextField = new JTextField();
		productIDLabel = new JLabel();
		quantityLabel = new JLabel();
		
		final DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn("ID");
		tableModel.addColumn("Product");
		tableModel.addColumn("Quantity");
		tableModel.addColumn("Cost Per Unit");
		tableModel.addColumn("Total");

		productTable = new JTable(tableModel);
		productTable.getTableHeader().setReorderingAllowed(false); // Disables column reordering
		productTable.setEnabled(false); // Disables cell editing
		productScrollPane = new JScrollPane(productTable);
		productScrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		
	}
}












