package Model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Dimension;

import Controller.SQL;
import Main.User;
import View.ActivityView;

public class ActivityModel 
{
	private final ActivityView view;
	
	public final User user;
	
	public final JLabel companyLabel;
	public final JLabel statusLabel;
	public final JLabel dateLabel;
	public final JLabel startLabel;
	public final JLabel endLabel;

	public final JLabel orderIDLabel;
	public final JLabel orderTypeLabel;
	public final JLabel orderCompanyLabel;
	public final JLabel orderStatusLabel;
	public final JLabel orderDateLabel;
	public final JLabel orderPriceLabel;

	public String orderID;
	public String orderType;
	public String orderCompany;
	public String orderStatus;
	public String orderDate;
	public String orderPrice;
	
	public final List<String> selectedCompanies;
	public final List<String> selectedStatuses;

	public final List<JCheckBox> companyCheckBox;
	public final List<JCheckBox> statusCheckBox;

	public String startDate;
	public String endDate;
	
	public final JComboBox<String> startDateComboBox;
	public final JComboBox<String> endDateComboBox;
	
	public final JPanel menuPanel;
	public final JPanel listPanel;
	public final JPanel orderPanel;
	
	public final JScrollPane menuScrollPane;
	public final JScrollPane listScrollPane;
	public final JScrollPane orderScrollPane;

	public final List<JButton> orderButtons;
	
	public final JTable orderTable;
	public final JScrollPane orderTableScrollPane;
	
	public ActivityModel(final ActivityView view, final User user)
	{
		this.view = view;
		this.user = user;
		
		companyLabel = new JLabel("Company");
		statusLabel = new JLabel("Status");
		dateLabel = new JLabel("Date");
		startLabel = new JLabel("Start: ");
		endLabel = new JLabel("End: ");
		
		companyCheckBox = new ArrayList<>();
		statusCheckBox = new ArrayList<>();
		orderButtons = new ArrayList<>();
		
		companyCheckBox.add(new JCheckBox("All"));
		final List<String> companyList = SQL.getCompanyList();
		for(final String company: companyList)
		{
			companyCheckBox.add(new JCheckBox(company));
		}
		
		statusCheckBox.add(new JCheckBox("All"));
		final List<String> statusList = SQL.getOrderStatusList();
		for(final String status: statusList)
		{
			statusCheckBox.add(new JCheckBox(status));
		}

		selectedCompanies = new ArrayList<>();
		selectedStatuses = new ArrayList<>();
		
		final List<String> dateList = SQL.getDateList();
		startDateComboBox = new JComboBox<>(dateList.toArray(new String[0]));
		endDateComboBox = new JComboBox<>(dateList.toArray(new String[0]));

		startDate = startDateComboBox.getSelectedItem().toString();
		endDate = endDateComboBox.getSelectedItem().toString();
		
		menuPanel = new JPanel();
		listPanel = new JPanel();
		orderPanel = new JPanel();
		
		menuScrollPane = new JScrollPane(menuPanel);
		listScrollPane = new JScrollPane(listPanel);
		listScrollPane.setPreferredSize(new Dimension(700, 0));


		orderIDLabel = new JLabel("Order ID:");
		orderTypeLabel = new JLabel("Type:");
		orderCompanyLabel = new JLabel("Company:");
		orderStatusLabel = new JLabel("Status:");
		orderDateLabel = new JLabel("Date:");
		orderPriceLabel = new JLabel("Total Price:");

		final DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn("Product");
		tableModel.addColumn("Product ID");
		tableModel.addColumn("Quantity");
		tableModel.addColumn("Price per unit");

		orderTable = new JTable(tableModel);
		orderTable.getTableHeader().setReorderingAllowed(false); // Disables column reordering
		orderTable.setEnabled(false); // Disables cell editing
		orderTableScrollPane = new JScrollPane(orderTable);
		orderTableScrollPane.setBorder(BorderFactory.createEmptyBorder());

		orderScrollPane = new JScrollPane(orderPanel);
	}

	// Updates the list of orders to be displayed after filtering
	public void updateOrderList(List<String> orderList)
	{
		orderButtons.clear();
		for(final String orderID : orderList)
		{
			orderButtons.add(new JButton("Order #" + orderID));
		}

		view.displayOrders();
	}

	// Updates model data pertaining to the detailed view of a selected order
	public void updateOrderDetails(List<String> orderDetails, List<List<String>> itemizedDetails)
	{
		// Change values for order details
		orderID = orderDetails.get(0);
		orderType = orderDetails.get(1);
		orderCompany = orderDetails.get(2);
		orderStatus = orderDetails.get(3);
		orderDate = orderDetails.get(4);
		orderPrice = orderDetails.get(5);

		// Products are sold at a 20% markup. The purchase price for outgoing orders can be derived from selling price
		if(orderType.equals("OUTGOING"))
		{
			double price = Double.valueOf(orderDetails.get(5));
			price /= 1.2;
			orderPrice = "" + Math.round(price * 100.0) / 100.0;
		}
		
		// Change table values for itemized order details
		final DefaultTableModel tableModel = (DefaultTableModel) orderTable.getModel();
		tableModel.setRowCount(0);
		for(int i=0; i<itemizedDetails.size(); i++)
		{
			Object[] itemizedDetailsArray = itemizedDetails.get(i).toArray();
			if(orderType.equals("OUTGOING")) // 20% price reduction for outgoing orders
			{
				double price = Double.valueOf(itemizedDetailsArray[3].toString());
				price /= 1.2;
				itemizedDetailsArray[3] = Math.round(price * 100.0) / 100.0;
			}
			tableModel.addRow(itemizedDetailsArray);
		}

		view.displayOrderDetails();
	}
}
