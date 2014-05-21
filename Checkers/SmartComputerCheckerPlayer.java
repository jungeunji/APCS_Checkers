import java.awt.Color;
import java.util.*;


public class SmartComputerCheckerPlayer extends CheckerPlayer
{
	public SmartComputerCheckerPlayer(CheckerWorld world, String name, Color color, ArrayList<Piece> pieces )
	{
		super(world, name, color, pieces);
	}
	
	public MoveInfo getPlay()
	{
		MoveInfo move = null;
		return move;
	}
}
