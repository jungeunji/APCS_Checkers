import info.gridworld.grid.Location;
import java.util.ArrayList;
import java.awt.Color;

/**
 * StupidComputerOthelloPlayer.java
 * 
 * A <CODE>StupidComputerPlayer</CODE> object represents a computer player
 * who selects a move by randomly chosing from the allowed locations.
 * 
 *  @author  Darren Yang
 *  @author  Id 5101177
 *  @version Apr 2, 2014
 *  @author  Period: 2
 *  @author  Assignment: GWOthello
 * 
 *  @author  Sources: help
 */
public class StupidComputerOthelloPlayer extends OthelloPlayer
{
	/**
	 * Constructs a stupid computer player.
	 * @param world the world
	 */
	public StupidComputerOthelloPlayer(OthelloWorld world)
	{
	    super( world, "Computer", Color.RED );
	}

	/**
	 * Determines a randomly selected allowed move.
	 * @return a random allowed more (if there is one);
	 *         null otherwise
	 */
	public Location getPlay()
	{
		ArrayList<Location> allowedMoves = getAllowedPlays();
		if ( allowedMoves.isEmpty() )
		{
		    return null;
		}
		int moveIndex = (int)( Math.random() * allowedMoves.size() );
		return allowedMoves.get( moveIndex );
	}
}
