package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.UpdateModel;
import View.UpdateView;

public class UpdateController 
{
	private final UpdateView view;
	private final UpdateModel model;
	
	private final SearchButtonListener sbl;
	private final RemoveButtonListener rbl;
	private final CancelButtonListener cbl;
	
	public SearchButtonListener getSearchButtonListener() { return sbl; }
	public RemoveButtonListener getRemoveButtonListener() { return rbl; }
	public CancelButtonListener getCancelButtonListener() { return cbl; }
	
	public UpdateController(final UpdateView view, final UpdateModel model)
	{
		this.view = view;
		this.model = model;
		
		sbl = new SearchButtonListener(view, model);
		rbl = new RemoveButtonListener(view, model);
		cbl = new CancelButtonListener(view, model);
	}
}

class SearchButtonListener implements ActionListener
{
	private final UpdateView view;
	private final UpdateModel model;
	
	public SearchButtonListener(final UpdateView view, final UpdateModel model)
	{
		this.view = view;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		final String orderID = model.searchTextField.getText();
		model.searchTextField.setText("");
		
		if(orderID.matches("(0|[1-9]\\d*)"))
		{
			view.setOrderInfo(orderID);
		}
	}
	
}

class RemoveButtonListener implements ActionListener
{
	private final UpdateView view;
	private final UpdateModel model;
	
	public RemoveButtonListener(final UpdateView view, final UpdateModel model)
	{
		this.view = view;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Integer id = Integer.parseInt(model.productIDTextField.getText());
		if(model.order.productIds.contains(id) && model.order.type.equals("INCOMING")
				&& model.order.status.equals("Recieved"))
		{
			int index = -1;
			
			for(int i = 0; i < model.order.productIds.size(); ++i)
			{
				if(model.order.productIds.get(i).equals(id))
				{
					index = i;
				}
			}
			Integer quantity = Integer.parseInt(model.quantityTextField.getText());
			Double costPerUnit = model.order.costPerUnit.get(index);
			
			if(quantity == model.order.quantities.get(index))
			{
				SQL.removeProductFromOrder(model.order.orderID, id, quantity, costPerUnit);
				view.setOrderInfo(model.order.orderID.toString());
			}
			else if(quantity < model.order.quantities.get(index) &&
					quantity > 0)
			{
				SQL.reduceProductFromOrder(model.order.orderID, id, quantity, costPerUnit);
				view.setOrderInfo(model.order.orderID.toString());
			}
			else
			{
				return;
			}
		}
		else
		{
			return;
		}
	}
}

class CancelButtonListener implements ActionListener
{
	private final UpdateView view;
	private final UpdateModel model;
	
	public CancelButtonListener(final UpdateView view, final UpdateModel model)
	{
		this.view = view;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(model.order.status.equals("Recieved") || model.order.status.equals("Sent"))
		{
			SQL.cancelOrder(model.order.orderID, model.order.productIds, model.order.quantities, model.order.type);
			view.setOrderInfo(model.order.orderID.toString());
		}
		else
		{
			return;
		}
	}
}