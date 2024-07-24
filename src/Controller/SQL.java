package Controller;


import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL 
{
	 private final static String url = "jdbc:sqlite:Warehouse.db";
	 
	 /**
	  * Returns a String indicating the result of checking
	  * the database for a username and password.
	  * 
	  * @param username username submitted by the user
	  * @param password password submitted by the user
	  * @return a string based on outcome of query
	  */
	 public static String checkLogin(final String username, final char[] password)
	 {
		 final String[] result = new String[]
		 {
			"VALID",		// Valid username and password	0
			"BAD_USERNAME",	// Username was not found		1
			"BAD_PASSWORD", // Incorrect Password			2
			"ERROR"			// SQL Error					3
		 };
		 
		 try(final Connection conn = DriverManager.getConnection(url))
		 {
			 final String query = "select password " +
					 			  "from user " +
					 			  "where username = \'" + username + "\';";
			 
			 final Statement stmt = conn.createStatement();

			 final ResultSet rs =  stmt.executeQuery(query);
			 
			 final char[] realPassword = rs.getString("password").toCharArray();

			 // Username was not found
			 if(realPassword == null)
			 {
				 return result[1];
			 }
			 // Valid username and password
			 else if(Arrays.equals(realPassword, password))
			 {
				 return result[0];
			 }
			 // Incorrect Password
			 else
			 {
				 return result[2];
			 }
		 }
		 catch (SQLException e) 
		 {
			// SQL Error
			return result[3];
		 }
		 catch (NullPointerException e)
		 {
			 return result[1];
		 }
	 }

	 public static boolean isAdmin(final String userID)
	 {
		 try(final Connection conn = DriverManager.getConnection(url))
		 {
			 final String query = "select type " +
					 			  "from user " +
					 			  "where user_id = " + userID + ";";
			 
			 final Statement stmt = conn.createStatement();

			 final ResultSet rs =  stmt.executeQuery(query);

			 
			 if(rs.getString("type").equals("Admin"))
			 {
				 return true;
			 }
			 else
			 {
				 return false;
			 }
		 }
		 catch (SQLException e) 
		 {
			// SQL Error
			return false;
		 }
	 }
	 
	 /**
	  * Returns a list of all companies on file in the database
	  * 
	  * @return list of strings of company names
	  */
	 public static List<String> getCompanyList()
	 {
		 List<String> result = new ArrayList<>();
		 
		 try(final Connection conn = DriverManager.getConnection(url))
		 {
			 final String query = "select distinct(c_name) " +
					 			  "from company ";
			 
			 final Statement stmt = conn.createStatement();

			 final ResultSet rs =  stmt.executeQuery(query);
			 
			 while(rs.next())
			 {
				 result.add(rs.getString("c_name"));
			 }
			 
			 return result;
		 }
		 catch (SQLException e) 
		 {
			// SQL Error
			result.add("ERROR");
			return result;
		 }
	 }

	 /**
	  * Returns a list of unique status's of all orders in the database
	  * 
	  * @return list of strings of order status
	  */
	 public static List<String> getOrderStatusList() 
	 {
		 List<String> result = new ArrayList<>();
		 
		 try(final Connection conn = DriverManager.getConnection(url))
		 {
			 final String query = "select distinct(status) " +
					 			  "from p_order";
			 
			 final Statement stmt = conn.createStatement();

			 final ResultSet rs =  stmt.executeQuery(query);
			 
			 while(rs.next())
			 {
				 result.add(rs.getString("status"));
			 }
			 
			 return result;
		 }
		 catch (SQLException e) 
		 {
			 e.printStackTrace();
			// SQL Error
			result.add("ERROR");
			return result;
		 }
	 }

	 /**
	  * Returns a list of unique dates for all orders in the database
	  * 
	  * @return list of string of dates
	  */
	 public static List<String> getDateList()
	 {
		 final List<String> result = new ArrayList<>();
		 
		 try(final Connection conn = DriverManager.getConnection(url))
		 {
			 final String query = "select distinct(strftime('%m/%d/%Y', date)) from p_order order by date;";
			 
			 final Statement stmt = conn.createStatement();

			 final ResultSet rs =  stmt.executeQuery(query);
			 
			 while(rs.next())
			 {
				 result.add(rs.getString("(strftime('%m/%d/%Y', date))"));
			 }
			 
			 return result;
		 }
		 catch (SQLException e) 
		 {
			 e.printStackTrace();
			 result.add("ERROR");
			return result;
		 }
	 }
	
	 /**
	  * Returns a list of info in the database for the
	  * user who has just succesfully signed in. The list will
	  * contain the user id, first name, last name, username, and 
	  * employee type.
	  * 
	  * @param username username submitted by the user
	  * @return list of string of user info
	  */
	 public static List<String> getUserInfo(final String username) 
	 {
		 List<String> result = new ArrayList<>();
		 
		 try(final Connection conn = DriverManager.getConnection(url))
		 {
			 final String query = "select * from user where username = \'" + username + "\';";
			 
			 final Statement stmt = conn.createStatement();

			 final ResultSet rs =  stmt.executeQuery(query);
			 
			 result.add(rs.getString("user_id"));
			 result.add(rs.getString("first_name"));
			 result.add(rs.getString("last_name"));
			 result.add(rs.getString("username"));
			 result.add(rs.getString("type"));
			 
			 return result;
		 }
		 catch (SQLException e) 
		 {
			 e.printStackTrace();
			 result.add("ERROR");
			 result.add("ERROR");
			 result.add("ERROR");
			 result.add("ERROR");
			 result.add("ERROR");
			return result;
		 }
	 }

	 public static List<Object> getOrderInfoForUpdate(final String orderID)	 
	 {
		 try(final Connection conn = DriverManager.getConnection(url))
		 {
			 final String query = "select o.*, "
					 	+ "strftime('%m/%d/%Y', date), "
				 		+ "c.c_name, "
				 		+ "admin.first_name as admin_first, "
				 		+ "admin.last_name as admin_last, "
				 		+ "assoc.first_name as assoc_first, "
				 		+ "assoc.last_name as assoc_last "
				 		+ "from "
				 		+ "    p_order as o, "
				 		+ "	   (select first_name, last_name, user_id from user where type = 'Admin') as admin, "
				 		+ "	   (select first_name, last_name, user_id from user where type = 'Associate') as assoc "
				 		+ "	   join (select company_id, c_name from company) as c "
				 		+ "	   on o.company_id = c.company_id "
				 		+ "where "
				 		+ "    o.order_id = " + orderID + ";";
			 
			 final Statement stmt = conn.createStatement();

			 final ResultSet rs =  stmt.executeQuery(query);
			 
			 if(rs.getInt("order_id") == 0)
			 {
				 return new ArrayList<>();
			 }
			 
			 final List<Object> result = new ArrayList<>();
			 
			 result.add(rs.getInt("order_id"));
			 result.add(rs.getDouble("total_price"));
			 result.add(rs.getString("strftime('%m/%d/%Y', date)"));
			 result.add(rs.getString("status"));
			 result.add(rs.getString("type"));
			 result.add(rs.getInt("company_id"));
			 result.add(rs.getInt("admin_id"));
			 result.add(rs.getInt("associate_id"));
			 result.add(rs.getString("c_name"));
			 result.add(rs.getString("admin_first") + " " + rs.getString("admin_last"));
			 result.add(rs.getString("assoc_first") + " " + rs.getString("assoc_last"));
			 
			 return result;
		 }
		 catch (SQLException e) 
		 { 
			 // SQL Error
			 e.printStackTrace();
			 return new ArrayList<>();
		 }
	 }
	 
	// Query to select order ID's filtered by company, order status, and date range
	public static List<String> getOrderIDList(List<String> selectedCompanies, List<String> selectedStatuses, String startDate, String endDate, String userID)
	{
		final List<String> result = new ArrayList<>();
		
		try(final Connection conn = DriverManager.getConnection(url))
		{
			
			String query = "select order_id from p_order"
							+ " join company on company.company_id = p_order.company_id"
							+ " where (strftime('%m/%d/%Y', date) BETWEEN '" + startDate + "' and '" + endDate + "')"; // Filter by date
			
			if(!isAdmin(userID))
			{
				query += " and associate_id = " + userID + " ";
			}
			
			if(selectedCompanies.size() > 0) // Filter by company
			{
				query += " and (c_name = '" + selectedCompanies.get(0) + "'";
				for(int i=1; i<selectedCompanies.size(); i++)
				{
					query += " or c_name = '" + selectedCompanies.get(i) + "'";
				}
				query += ")";
			}
			if(selectedStatuses.size() > 0) // Filter by status
			{
				query += " and (status = '" + selectedStatuses.get(0) + "'";
				for(int i=1; i<selectedStatuses.size(); i++)
				{
					query += " or status = '" + selectedStatuses.get(i) + "'";
				}
				query += ")";
			}
			query += " order by date;";
				
			final Statement stmt = conn.createStatement();

			final ResultSet rs =  stmt.executeQuery(query);
				
			while(rs.next())
			{
				result.add(rs.getString("order_id"));
			}
				
			return result;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			result.add("ERROR");
			return result;
		}
	}

	// Query to retrieve relevant attributes of an order
	public static List<String> getOrderDetailsList(String orderID)
	{
		final List<String> result = new ArrayList<>();
		
		try(final Connection conn = DriverManager.getConnection(url))
		{
			final String query = "select type, c_name, status, strftime('%m/%d/%Y', date), total_price"
								+ " from p_order, company"
								+ " where p_order.company_id = company.company_id and order_id = " + orderID + ";";
				
			final Statement stmt = conn.createStatement();

			final ResultSet rs =  stmt.executeQuery(query);

			if(rs.next())
			{
				result.add(orderID);
				result.add(rs.getString("type"));
				result.add(rs.getString("c_name"));
				result.add(rs.getString("status"));
				result.add(rs.getString("strftime('%m/%d/%Y', date)"));
				result.add(rs.getString("total_price"));
			}
				
			return result;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			result.add("ERROR");
			return result;
		}
	}

	// Query to retrieve the details of each product within an order
	public static List<List<String>> getItemizedOrderList(String orderID)
	{
		final List<List<String>> result = new ArrayList<>();
		
		try(final Connection conn = DriverManager.getConnection(url))
		{
			final String query = "select p_name, product_id, quantity, selling_price"
								+ " from product join itemized_order on product_id = product"
								+ " where order_id = " + orderID + ";";
				
			final Statement stmt = conn.createStatement();

			final ResultSet rs =  stmt.executeQuery(query);
				
			for(int i=0; rs.next(); i++)
			{
				result.add(new ArrayList<>());
				result.get(i).add(rs.getString("p_name"));
				result.get(i).add(rs.getString("product_id"));
				result.get(i).add(rs.getString("quantity"));
				result.get(i).add(rs.getString("selling_price"));
			}
				
			return result;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			result.add(new ArrayList<>());
			result.get(0).add("ERROR");
			return result;
		}
	}
	
	public static Object[][] getProductsFromOrder(final String orderID)
	{
		try(final Connection conn = DriverManager.getConnection(url))
		 {
			 final String query = "select *, quantity*selling_price as total " +
					 			  "from itemized_order join product on product_id = product " +
					 			  "where order_id = " + orderID + ";";
			 
			 final Statement stmt = conn.createStatement();

			 final ResultSet rs =  stmt.executeQuery(query);
			 
			 List<List<Object>> table = new ArrayList<>();
				
			while(rs.next())
			{
				List<Object> temp = new ArrayList<>();
				
				temp.add(rs.getInt("product_id"));
				temp.add(rs.getString("p_name"));
				temp.add(rs.getInt("quantity"));
				temp.add(rs.getDouble("selling_price"));
				temp.add(rs.getDouble("total"));
				
				table.add(temp);
			}
			
			Object[][] array = new Object[table.size()][];
			Object[] blankArray = new Object[0];
			for(int i=0; i < table.size(); i++) 
			{
				array[i] = table.get(i).toArray(blankArray);
			}
			
			return array;
		 }
		 catch (SQLException e) 
		 {
			// SQL Error
			 e.printStackTrace();
			return new Object[0][0];
		 }
	}
	
	public static Object[][] getOrders(final List<String> companyList,
				final List<String> statusList,
				final String	   startDate,
				final String	   endDate,
				final String       minPrice,
				final String       maxPrice)
	{

		final StringBuilder c = new StringBuilder("");
		for(int i = 0; i < companyList.size() - 1; ++i) 
		{
			c.append("\'" +companyList.get(i) + "\', ");
		}
		c.append("\'" + companyList.get(companyList.size() - 1) + "\'");
		
		final StringBuilder s = new StringBuilder("");
		for(int i = 0; i < statusList.size() - 1; ++i) 
		{
			s.append("\'" + statusList.get(i) + "\', ");
		}
		s.append("\'" + statusList.get(statusList.size() - 1) + "\'");
		
		
		try(final Connection conn = DriverManager.getConnection(url))
		{
			final String query = "select o.order_id, o.total_price, o.date, o.status, o.type, c.c_name, admin.first_name as admin_first, admin.last_name as admin_last, assoc.first_name as assoc_first, assoc.last_name as assoc_last "
			+ "from "
			+ "    p_order as o, "
			+ "	   (select first_name, last_name, user_id from user where type = 'Admin') as admin, "
			+ "	   (select first_name, last_name, user_id from user where type = 'Associate') as assoc "
			+ "	   join (select company_id, c_name from company) as c "
			+ "	   on o.company_id = c.company_id "
			+ "where "
			+ "		admin.user_id = o.admin_id "
			+ "	and	assoc.user_id = o.associate_id "
			+ "	and	c.c_name in (" + c.toString() + ") "
			+ "	and	o.status in (" + s.toString() + ") "
			+ "	and	date >= date(\'" + startDate + "\') " 
			+ " and date <= date(\'" + endDate + "\') "
			+ " ;";
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs =  stmt.executeQuery(query);
			
			List<List<Object>> table = new ArrayList<>();
			
			while(rs.next())
			{
				List<Object> temp = new ArrayList<>();
				
				temp.add(rs.getInt("order_id"));
				temp.add(rs.getFloat("total_price"));
				temp.add(rs.getDate("date"));
				temp.add(rs.getString("status"));
				temp.add(rs.getString("type"));
				temp.add(rs.getString("c_name"));
				temp.add(rs.getString("admin_first") + " " + rs.getString("admin_last"));
				temp.add(rs.getString("assoc_first") + " " + rs.getString("assoc_last"));
				
				table.add(temp);
			}
			
			Object[][] array = new Object[table.size()][];
			Object[] blankArray = new Object[0];
			for(int i=0; i < table.size(); i++) 
			{
				array[i] = table.get(i).toArray(blankArray);
			}
			
			return array;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			return new Object[0][0];
		}
	}
		
	public static void reduceProductFromOrder(final Integer orderID, final Integer productID, final Integer quantity, final Double costPerUnit)
	{
		try(final Connection conn = DriverManager.getConnection(url))
		{
			final Statement stmt = conn.createStatement();
			
			
			final String update1 = "update p_order " + 
							       "set total_price = total_price - " + quantity * costPerUnit +
							       " where order_id = " + orderID + ";";
			stmt.executeUpdate(update1);
			
			
			final String update2 = "update itemized_order " + 
							       "set quantity = quantity - " + quantity + 
							       " where product = " + productID + ";";
			stmt.executeUpdate(update2);
			
			
			final String update3 = "update product " + 
								   "set total_in_stock = total_in_stock + " + quantity + 
								   " where product_id = " + productID;
			stmt.executeUpdate(update3);
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void removeProductFromOrder(final Integer orderID, final Integer productID, final Integer quantity, final Double costPerUnit)
	{
		try(final Connection conn = DriverManager.getConnection(url))
		{
			final Statement stmt = conn.createStatement();
			
			
			final String update1 = "update p_order " + 
							       "set total_price = total_price - " + quantity * costPerUnit +
							       " where order_id = " + orderID + ";";
			stmt.executeUpdate(update1);
			
			
			final String update2 = "delete from itemized_order " + 
							       "where product = " + productID + ";";
			stmt.executeUpdate(update2);
			
			
			final String update3 = "update product " + 
								   "set total_in_stock = total_in_stock + " + quantity + 
								   " where product_id = " + productID;
			stmt.executeUpdate(update3);
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void cancelOrder(final Integer orderID, final List<Integer> products, final List<Integer> quantities, final String type)
	{
		try(final Connection conn = DriverManager.getConnection(url))
		{
			final Statement stmt = conn.createStatement();
			
			final String update = "update p_order " + 
								  "set status = \'Cancelled\' " +
								  "where order_id = " + orderID;
			stmt.executeUpdate(update);
			
			int n = (type.equals("INCOMING")) ? 1 : -1;
			
			for(int i = 0; i < products.size(); ++i)
			{
				final String temp = "update product " + 
								    "set total_in_stock = total_in_stock + " + quantities.get(i) * n + 
								    " where product_id = " + products.get(i);
				stmt.executeUpdate(temp);
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static Object[][] getAllProducts()
	{
		try(final Connection conn = DriverManager.getConnection(url))
		{
			 final String query = "select * from product " + 
					 			  "join company on supplier_id = company_id;";
			 
			 final Statement stmt = conn.createStatement();

			 final ResultSet rs =  stmt.executeQuery(query);
			 
			 List<List<Object>> table = new ArrayList<>();
				
			while(rs.next())
			{
				List<Object> temp = new ArrayList<>();
				
				temp.add(rs.getInt("product_id"));
				temp.add(rs.getString("p_name"));
				temp.add(rs.getString("c_name"));
				temp.add(rs.getInt("total_in_stock"));
				temp.add(rs.getDouble("selling_price"));
				
				table.add(temp);
			}
			
			Object[][] array = new Object[table.size()][];
			Object[] blankArray = new Object[0];
			for(int i=0; i < table.size(); i++) 
			{
				array[i] = table.get(i).toArray(blankArray);
			}
			
			return array;
		 }
		 catch (SQLException e) 
		 {
			// SQL Error
			 e.printStackTrace();
			return new Object[0][0];
		 }
	}
	
	public static double getPrice(final String productID)
	{
		try(final Connection conn = DriverManager.getConnection(url))
		{
			 final String query = "select selling_price from product " + 
					 			  "where product_id = " + productID + ";";
			 
			 final Statement stmt = conn.createStatement();

			 final ResultSet rs =  stmt.executeQuery(query);
			 
			 return rs.getDouble("selling_price");
			 
		 }
		 catch (SQLException e) 
		 {
			// SQL Error
			 e.printStackTrace();
			return 0.0;
		 }
	}
	
	public static boolean productIsAvailable(final String productID, final String quantity)
	{
		try(final Connection conn = DriverManager.getConnection(url))
		{
			final String query = "select product_id, total_in_stock " +
								 "from product " + 
								 "where product_id = " + productID + ";";
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(query);
			
			if(rs.getInt("product_id") == 0)
			{
				return false;
			}
			else if(Integer.parseInt(quantity) <= rs.getInt("total_in_stock"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static String[][] getProductPopulation(final String[] Filter, boolean populateFlag) {

		ArrayList<String[]> tableArray = new ArrayList<String[]>();
		ArrayList<String> getConditions = new ArrayList<String>();

		// Attempt to populate tableArray.
		try(final Connection conn = DriverManager.getConnection(url)) {
			
			// Create query from filters.
			String query = "SELECT PRODUCT.*, COMPANY.c_name FROM PRODUCT JOIN COMPANY ON PRODUCT.supplier_id = COMPANY.company_id WHERE";

			// Check filters. Create query from filters.
			if (populateFlag) {
				query = "SELECT PRODUCT.*, COMPANY.c_name FROM PRODUCT JOIN COMPANY ON PRODUCT.supplier_id = COMPANY.company_id;";
			}
			else {
				if(!Filter[0].isEmpty()) {
					getConditions.add(" p_name LIKE \"%" + Filter[0] + "%\"");
				}
				if(!Filter[1].isEmpty() || !Filter[2].isEmpty()) {
					if (!Filter[1].isEmpty() && !Filter[2].isEmpty())
					{
						getConditions.add(" selling_price BETWEEN " + Filter[1] + " AND " + Filter[2]);
					}
					else if (!Filter[1].isEmpty())
					{
						getConditions.add(" selling_price > " + Filter[1]);
					}
					else 
					{
						getConditions.add(" selling_price < " + Filter[2]);
					}
				}
				if(!Filter[3].isEmpty()) {
					getConditions.add(" supplier_id = " + Filter[3]);
				}
				if(!Filter[4].isEmpty()) {
					getConditions.add(" total_in_stock >= " + Filter[4]);
				}
				if(!Filter[5].isEmpty()) {
					getConditions.add(" product_id = " + Filter[5] );
				}
				if(!Filter[6].isEmpty()) {
					getConditions.add(" supplier_id IN (SELECT COMPANY.company_id FROM COMPANY WHERE COMPANY.c_name LIKE \"%" + Filter[6] + "%\")");
				}

				if (!getConditions.isEmpty()) {
					query += getConditions.get(0);
					for ( int c = 1 ; c < getConditions.size(); c++) {
						query += (" AND " + getConditions.get(c));
					}
					query += ";";
				}
				else {
					query = "SELECT PRODUCT.*, COMPANY.c_name FROM PRODUCT JOIN COMPANY ON PRODUCT.supplier_id = COMPANY.company_id;";
				}
			}

			// Create statement object.
			final Statement statement = conn.createStatement();

			// Obtain result set from query.
			final ResultSet resultSet = statement.executeQuery(query);

			String[] tempArray = new String[6];
			while(resultSet.next()) // Returns a boolean.
			{
				for (int c = 0; c < 6; c++) {
					tempArray[c] = resultSet.getString(c + 1);
				}
				tableArray.add(tempArray.clone());
				Arrays.fill(tempArray, "");
			}
		}
		 catch (SQLException e) 
		{
			e.printStackTrace();
			return new String[0][0];
		}

		// Return tableArray even if it is empty.

		String[][] convertedArray = new String[tableArray.size()][];
		for (int c = 0; c < tableArray.size(); c++) {
			convertedArray[c] = tableArray.get(c);
		}
		
		return convertedArray;
	 }

	public static void confirmOrder(String name, List<List<String>> cart, final String adminID) 
	{
		double total = 0;
		
		for(final List<String> product: cart)
		{
			total += Double.parseDouble(product.get(2));
		}
		
		Random r = new Random();
		
		int orderID = r.nextInt(99999-10000)+10000;
		
		String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
		
		String status = "Recieved";
		
		String type = "INCOMING";
		
		int companyID = getCompanyID(name);
		
		int assocID = assignAssociate();
		
		try(final Connection conn = DriverManager.getConnection(url))
		{
			final String update = "insert into p_order " +
								  "values("
								  + orderID + ", "
								  + total + ", \'"
								  + date + "\', \'"
								  + status + "\', \'"
								  + type + "\', "
								  + companyID + ", "
								  + adminID + ", "
								  + assocID
								  + ")";
			
			final Statement stmt = conn.createStatement();
			
			stmt.executeUpdate(update);
		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		try(final Connection conn = DriverManager.getConnection(url))
		{
			for(final List<String> item: cart)
			{
				final String update = "insert into itemized_order values( " + orderID + ", " +  item.get(0) + ", " + item.get(1) + ");";
	
				final Statement stmt = conn.createStatement();
				
				stmt.executeUpdate(update);
				
				final String delete = "update product set total_in_stock = total_in_stock - " + item.get(1) + " where product_id = " + item.get(0);
				
				stmt.executeUpdate(delete);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static int getCompanyID(final String companyName)
	{
		try(final Connection conn = DriverManager.getConnection(url))
		{
			final String query = "select company_id from company where c_name = \'" + companyName + "\';";
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(query);
			
			return rs.getInt("company_id");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	public static int assignAssociate() 
	{
		try(final Connection conn = DriverManager.getConnection(url))
		{
			final String query = "select associate_id "
					+ "		  		     from p_order "
					+ "		  		     group by associate_id "
					+ "		  		     having count(*) = (select min(c) "
					+ "		  		     			from (select count(*) c "
					+ "		  		     			      from p_order "
					+ "		  		     			      group by associate_id)) "
					+ "		  		     order by random() "
					+ "		  		     limit 1;";
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(query);
			
			return rs.getInt("associate_id");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
 	public static List<String> getCompanyAutoFill(String name) 
	{
		try(final Connection conn = DriverManager.getConnection(url))
		{
			final String query = "select phone, email, address " + 
								 "from company " +
								 "where c_name = \'" + name + "\' " +
								 "and company_id not in (select supplier_id from product);";
			
			final Statement stmt = conn.createStatement();
			
			final ResultSet rs = stmt.executeQuery(query);
			
			List<String> result = new ArrayList<>();
			
			result.add(rs.getString("phone"));
			result.add(rs.getString("email"));
			result.add(rs.getString("address"));
			
			return result;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	
 	public static boolean companyExists(String name) 
 	{
 		try(final Connection conn = DriverManager.getConnection(url))
		{
			final String query = "select company_id from company where c_name = \'" + name + "\';";
			
			final Statement stmt = conn.createStatement();

			final ResultSet rs = stmt.executeQuery(query);
			
			if(rs.getInt("company_id") == 0)
			{
				return false;
			}
			
			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public static void insertCompany(String name, String phone, String email, String address) 
	{
		Random r = new Random();
		
		int id = r.nextInt(99999-10000)+10000;
		
		try(final Connection conn = DriverManager.getConnection(url))
		{
			final String update = "insert into company values( " +
								  id + ", \'" + 
								  name + "\', \'" +
								  address + "\', \'" + 
								  phone + "\', \'" +
								  email + 
								  "\');";
			
			final Statement stmt = conn.createStatement();

			stmt.executeUpdate(update);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}


}

















