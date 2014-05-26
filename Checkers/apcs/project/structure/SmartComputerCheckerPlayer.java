package apcs.project.structure;

import java.awt.Color;
import java.util.*;


//dependent on allowedMoves in CheckerPlayer

/**
 * This SmartComputerCheckerPlayer extends the CheckerPlayer interface and has its own
 * getPlay() method where it determines the best move to take.
 * 
 * @author Hubert
 * @version May 26, 2014
 */
public class SmartComputerCheckerPlayer extends CheckerPlayer 
{
	/**
	 * Initializes a SmartComputerCheckerPlayer object
	 * @param world CheckerWorld of the player
	 * @param name CPU
	 * @param color Color of the pieces
	 * @param pieces List of the pieces
	 */
	public SmartComputerCheckerPlayer(CheckerWorld world, String name, Color color, ArrayList<Piece> pieces )
	{
		super(world, "CPU", color, pieces);
	}
	
	/* (non-Javadoc)
	 * @see apcs.project.structure.CheckerPlayer#getPlay()
	 */
	public MoveInfo getPlay()
	{
		try
		{
			Thread.sleep(750); //wait 0.75 seconds before moving
		}
		catch ( InterruptedException e ) //do nothing
		{}
		ArrayList<MoveInfo> moves = getMoves();
		MoveInfo nextMove = moves.get( (int)( Math.random() * moves.size() ) );
		return nextMove;
	}
}
