import java.awt.Color;

public class Piece 
{
	private Color color;
	private boolean isKing;
	
	public Piece(Color color)
	{
		this.color = color;
		isKing = false;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor( Color newColor )
	{
		color = newColor;
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
