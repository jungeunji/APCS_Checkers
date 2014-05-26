package apcs.project.structure;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.*;


//dependent on allowedMoves in CheckerPlayer

public class SmartComputerCheckerPlayer extends CheckerPlayer 
{
	
	public SmartComputerCheckerPlayer(CheckerWorld world, String name, Color color, ArrayList<Piece> pieces )
	{
		super(world, "CPU", color, pieces);
	}
	
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
	
//	public void makeMove()
//	{
//		while(!pieces.isEmpty())
//		{
//			ArrayList<Location> computerMoves = ();
//		}
//	}
}
