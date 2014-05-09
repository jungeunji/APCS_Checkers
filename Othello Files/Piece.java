import java.awt.Color;

/**
 * Piece.java
 * 
 * A <CODE>Piece</CODE> object represents a piece in the Othello game.
 * 
 *  @author  Darren Yang
 *  @author  Id 5101177
 *  @version Apr 2, 2014
 *  @author  Period: 2
 *  @author  Assignment: GWOthello
 * 
 *  @author  Sources: I received help from ...
 */
public class Piece
{
	/** The color of the piece */
    private Color color;

	/**
	 * Constructs a piece.
	 * @param color the color of the piece
	 */
	public Piece(Color color)
	{
	    this.color = color;
	}

	/**
	 * Gets the color of the piece.
	 * @return the color of the piece
	 */
	public Color getColor()
	{
	    return color;
	}

	/**
	 * Sets the color of the piece.
	 * @param newColor the new color for the piece
	 */
	public void setColor(Color newColor)
	{
	    color = newColor;
	}
}
