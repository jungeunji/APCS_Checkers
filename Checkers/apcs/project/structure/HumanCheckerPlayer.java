package apcs.project.structure;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.util.*;

/**
 * A <CODE>HumanCheckerPlayer</CODE> object represents a
 * human Checker player.
 */
public class HumanCheckerPlayer extends CheckerPlayer
{
	
	/**
	 * @param world world of the player
	 * @param s the player's name
	 * @param color the color of the player's pieces
	 * @param p the player's pieces
	 */
	public HumanCheckerPlayer(CheckerWorld world, String s, Color color, ArrayList<Piece> p )
    {
        super(world, s, color, p);
    }

	/**
	 * Retrieves the next play for the human.
	 * Postcondition: the returned move info is an allowed play.
	 * @return the move info for the next play
	 */
	public MoveInfo getPlay()
	{
		MoveInfo move;
		do
		{
			move = getWorld().getPlayerMove();
			if ( move.getNewGame() != 'z' )
			{
				return move;
			}
		} while ( !getPieces().contains( move.getPiece() ) || !getMoves().contains( move ) ); 
		return move;
	}
}
