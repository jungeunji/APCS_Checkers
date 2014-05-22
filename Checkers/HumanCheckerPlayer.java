import info.gridworld.grid.Location;
import java.awt.Color;
import java.util.*;

/**
 * HumanOthelloPlayer.java
 * 
 * A <CODE>HumanOthelloPlayer</CODE> object represents a
 * human Othello player.
 */
public class HumanCheckerPlayer extends CheckerPlayer
{
//	/**
//	 * Constructs a human Othello player.
//	 * @param world the world
//	 */
//	public HumanCheckerPlayer(CheckerWorld world)
//	{
//		super(world, "Human", Color.BLUE);
//	}
	
	public HumanCheckerPlayer(CheckerWorld world, String s, Color color, ArrayList<Piece> p )
    {
        super(world, s, color, p);
    }

	/**
	 * Retrieves the next play for the human.
	 * Postcondition: the returned location is an allowed play.
	 * @return the location for the next play
	 */
	public MoveInfo getPlay()
	{
		MoveInfo move;
		do
		{
			move = getWorld().getPlayerMove();
		} while ( !getPieces().contains( move.getPiece() ) && !getMoves().contains( move ) ); 
		return move;
	}
}
