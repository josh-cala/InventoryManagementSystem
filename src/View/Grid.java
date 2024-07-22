package View;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Grid extends GridBagConstraints
{
	public Grid()
	{
		this.insets = new Insets(0,0,0,0);
	}
	
	public void setGrid(final int x, final int y)
	{
		setGrid(x, y, 1, 1);
	}
	
	public void setGrid(final int x, final int y, final int w, final int h)
	{
		this.gridx = x;
		this.gridy = y;
		this.gridwidth = w;
		this.gridheight = h;
	}
	
	public void setAnchor(final int a)
	{
		this.anchor = a;
	}
	
	public void setInset(final int top, final int left, final int bottom, final int right)
	{
		this.insets.top = top;
		this.insets.left = left;
		this.insets.bottom = bottom;
		this.insets.right = right;
	}
}
