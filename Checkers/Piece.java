import java.awt.Color;
import info.gridworld.grid.*;

public class Piece 
{
	private Color color;
	private boolean isKing;
	private Location location;
	
	public Piece(Color color, Location location)
	{
		this.color = color;
		this.location = location;
		isKing = false;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public void setColor( Color newColor )
	{
		color = newColor;
	}
	
	public void setLocation( Location newLocation )
	{
		location = newLocation;
	}
	
	public void setKing()
	{
		isKing = true;
	}
	
	public boolean king()
	{
		return isKing;
	}
}
