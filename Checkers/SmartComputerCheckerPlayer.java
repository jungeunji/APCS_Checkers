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
		MoveInfo move = null;
		return move;
	}
	
//	public void makeMove()
//	{
//		while(!pieces.isEmpty())
//		{
//			ArrayList<Location> computerMoves = ();
//		}
//	}
}
