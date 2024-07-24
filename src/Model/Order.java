package Model;

import java.util.ArrayList;
import java.util.List;

public class Order 
{
	public Integer orderID;
	public Double totalPrice;
	public String date;
	public String status;
	public String type;
	public Integer companyID;
	public Integer adminID;
	public Integer assocID;
	public String companyName;
	public String adminName;
	public String assocName;
	public List<Integer> productIds;
	public List<Double> costPerUnit;
	public List<Integer> quantities;
	
	public Order(final List<Object> order)
	{
		this.orderID = (Integer) order.get(0);
		totalPrice   = (Double)  order.get(1);
		date         = ((String)  order.get(2)).trim();
		status       = ((String)  order.get(3)).trim();
		type         = ((String)  order.get(4)).trim();
		companyID    = (Integer) order.get(5);
		adminID      = (Integer) order.get(6);
		assocID      = (Integer) order.get(7);
		companyName  = ((String)  order.get(8)).trim();
		adminName    = ((String)  order.get(9)).trim();
		assocName    = ((String)  order.get(10)).trim();	
		productIds = new ArrayList<>();
		costPerUnit = new ArrayList<>();
		quantities = new ArrayList<>();
	}
}
