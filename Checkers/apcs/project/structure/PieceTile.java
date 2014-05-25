package apcs.project.structure;
import info.gridworld.grid.Location;
import java.awt.Color;

/**
 * This class is for display purposes within GridWorld.
 * A PieceTile acts as a colored square on the grid
 * that represents a player's available move
 * 
 * @author Darren Yang
 */
public class PieceTile extends Piece
{
	/**
	 * Super constructor params
	 * @param color color
	 * @param location location
	 * @param w world
	 */
	public PieceTile(Color color, Location location, CheckerWorld w)
	{
		super( color, location, w );
	}
}
