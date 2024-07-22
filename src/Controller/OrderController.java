package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Model.OrderModel;
import View.OrderView;

public class OrderController 
{
	private final OrderView view;
	private final OrderModel model;
	
	public OrderController(final OrderView view, final OrderModel model)
	{
		this.view = view;
		this.model = model;
		
		
		model.addToCartButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				view.addToCartTable(model.productIDTextField.getText(), model.quantityTextField.getText());
				
				model.productIDTextField.setText("");
				model.quantityTextField.setText("");
			}
		});
		
		model.submitOrderButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(model.cart.size() != 0)
				{
					view.setCompanyView();
				}
			}
		});
		
		model.confirmButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				final String name = model.nameTextField.getText();
				final String phone = model.phoneTextField.getText();
				final String email = model.emailTextField.getText();
				final String address = model.addressTextField.getText();
				
				if(name.length() != 0 &&
				   phone.length() != 0 &&
				   email.length() != 0 &&
				   address.length() != 0)
				{
					if(!SQL.companyExists(name))
					{
						SQL.insertCompany(name, phone, email, address);
					}
					
					SQL.confirmOrder(name, model.cart, model.userID);
				}
			}
		});
		
		model.autoFillButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				final String name = model.nameTextField.getText();
				final List<String> fields = SQL.getCompanyAutoFill(name);
				
				if(fields.size() != 0)
				{
					view.autoFill(fields);
				}
			}
		});
	}
}
