import java.awt.Color;

/**
 * 
 * This class is for display purposes within GridWorld
 * 
 * @author Darren Yang
 */
public class PieceTile 
{
	private final Color color;
	private Piece piece;
	
	public PieceTile( Color c )
	{
		color = c;
		piece = null;
	}
	
	public void setPiece( Piece p )
	{
		piece = p;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public Piece getPiece()
	{
		return piece;
	}
}
