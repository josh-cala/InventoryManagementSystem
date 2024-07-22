package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import Model.MainModel;
import View.ActivityView;
import View.MainView;
import View.OrderView;
import View.ProductView;
import View.UpdateView;

public class MainController 
{
	private final MainView view;
	private final MainModel model;
	
	private final MenuButtonListener mbl;
	
	public MenuButtonListener getMenuButtonListener() { return mbl; }
	
	public MainController(final MainView view, final MainModel model)
	{
		this.view = view;
		this.model = model;
		mbl = new MenuButtonListener(view, model);
	}
}

class MenuButtonListener implements ActionListener
{
	private final MainView view;
	private final MainModel model;
	
	public MenuButtonListener(final MainView view, final MainModel model)
	{
		this.view = view;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		final String cmd = e.getActionCommand();
		
		model.dynamicPanel.removeAll();
		
		if(cmd.equals("Search For Products"))
		{
			final ProductView pdView = new ProductView();

			model.add(pdView.getContentPane());
		}
		else if(cmd.equals("Update An Order"))
		{
			model.dynamicPanel.add(new UpdateView());
		}
		else if(cmd.equals("Create An Order"))
		{
			model.dynamicPanel.add(new OrderView(model.user.employeeID));
		}
		else if(cmd.equals("View Activity"))
		{
			final ActivityView av = new ActivityView(model.user);
			
			model.add(av.getContentPane());
		}
		
		SwingUtilities.updateComponentTreeUI(view);
	}
}


