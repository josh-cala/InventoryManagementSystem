package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Model.ActivityModel;
import View.ActivityView;

public class ActivityController 
{
	private final ActivityView view;
	private final ActivityModel model;

	private final CompanyCheckBoxListener companyCheckBoxListener;
	private final StatusCheckBoxListener statusCheckBoxListener;
	private final OrderButtonListener orderButtonListener;

	public CompanyCheckBoxListener getCompanyListener() { return companyCheckBoxListener; }
	public StatusCheckBoxListener getStatusListener()	{ return statusCheckBoxListener;  }
	public OrderButtonListener getOrderListener()		{ return orderButtonListener;	  }

	public ActivityController(final ActivityView view, final ActivityModel model)
	{
		this.view = view;
		this.model = model;
		this.companyCheckBoxListener = new CompanyCheckBoxListener(view, model);
		this.statusCheckBoxListener = new StatusCheckBoxListener(view, model);
		this.orderButtonListener = new OrderButtonListener(view, model);

		// Event handler for the Start Date combo box filter
		model.startDateComboBox.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {
				model.startDate = model.startDateComboBox.getSelectedItem().toString();
				final List<String> orderList = SQL.getOrderIDList(model.selectedCompanies, model.selectedStatuses, model.startDate, model.endDate, model.user.employeeID);
				model.updateOrderList(orderList);
		   }
		});

		// Event handler for the End Date combo box filter
		model.endDateComboBox.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {
				model.endDate = model.endDateComboBox.getSelectedItem().toString();
				final List<String> orderList = SQL.getOrderIDList(model.selectedCompanies, model.selectedStatuses, model.startDate, model.endDate, model.user.employeeID);
				model.updateOrderList(orderList);
		   }
		});
	}

	// Event handler for the Company checkbox filters
	class CompanyCheckBoxListener implements ActionListener
	{
		private final ActivityView view;
		private final ActivityModel model;
		
		public CompanyCheckBoxListener(ActivityView view, ActivityModel model)
		{
			this.view = view;
			this.model = model;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			model.selectedCompanies.clear();
			String selection = e.getActionCommand();
			if(selection.equals("All")) // Add all companies to filter
			{
				view.setAllCompanyCheckBoxes(model.companyCheckBox.get(0).isSelected());
			}

			for(int i=1; i<model.companyCheckBox.size(); i++)
			{
				if(model.companyCheckBox.get(i).isSelected()) // Add company to filter
				{
					model.selectedCompanies.add(model.companyCheckBox.get(i).getActionCommand());
				}
				else // If any company is not selected, uncheck "All"
				{
					view.setCompanyCheckBox(0, false);
				}
			}

			if(model.selectedCompanies.size() == model.companyCheckBox.size()-1) // If all companies are selected, check "All"
			{
				view.setCompanyCheckBox(0, true);
			}

			final List<String> orderList = SQL.getOrderIDList(model.selectedCompanies, model.selectedStatuses, model.startDate, model.endDate, model.user.employeeID);
			model.updateOrderList(orderList);
		}
	}

	// Event handler for the Status checkbox filters
	class StatusCheckBoxListener implements ActionListener
	{
		private final ActivityView view;
		private final ActivityModel model;
		
		public StatusCheckBoxListener(ActivityView view, ActivityModel model)
		{
			this.view = view;
			this.model = model;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			model.selectedStatuses.clear();
			String selection = e.getActionCommand();
			if(selection.equals("All")) // Add all statuses to filter
			{
				view.setAllStatusCheckBoxes(model.statusCheckBox.get(0).isSelected());
			}

			for(int i=1; i<model.statusCheckBox.size(); i++)
			{
				if(model.statusCheckBox.get(i).isSelected()) // Add status to filter
				{
					model.selectedStatuses.add(model.statusCheckBox.get(i).getActionCommand());
				}
				else // If any status is not selected, uncheck "All"
				{
					view.setStatusCheckBox(0, false);
				}
			}

			if(model.selectedStatuses.size() == model.statusCheckBox.size()-1) // If all statuses are selected, check "All"
			{
				view.setStatusCheckBox(0, true);
			}

			final List<String> orderList = SQL.getOrderIDList(model.selectedCompanies, model.selectedStatuses, model.startDate, model.endDate, model.user.employeeID);
			model.updateOrderList(orderList);
		}
	}

	// Event handler for Order buttons
	class OrderButtonListener implements ActionListener
	{
		private final ActivityView view;
		private final ActivityModel model;
		
		public OrderButtonListener(ActivityView view, ActivityModel model)
		{
			this.view = view;
			this.model = model;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			String orderID = e.getActionCommand().substring(7); // Extract Order ID from the name of the button
			model.updateOrderDetails(SQL.getOrderDetailsList(orderID), SQL.getItemizedOrderList(orderID));
		}
	}
}
