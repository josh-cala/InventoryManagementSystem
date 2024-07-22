package View;

import javax.swing.JButton;

public class CartButton extends JButton
{
	final String productID;
	
	public CartButton(final String productID)
	{
		this.productID = productID;
	}
}
