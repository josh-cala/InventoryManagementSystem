package Main;

import java.util.List;

import Controller.SQL;

public class User
{
	public final String employeeID;
	public final String firstName;
	public final String lastName;
	public final String username;
	public final String employeeType;
	
	public User(final String user)
	{
		final List<String> userInfo = SQL.getUserInfo(user);
		
		employeeID   = userInfo.get(0);
		firstName    = userInfo.get(1);
		lastName     = userInfo.get(2);
		username     = userInfo.get(3);
		employeeType = userInfo.get(4);
	}
}
