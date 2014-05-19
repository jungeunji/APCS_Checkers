import info.gridworld.grid.Location;
import java.awt.Color;

/**
 * HumanOthelloPlayer.java
 * 
 * A <CODE>HumanOthelloPlayer</CODE> object represents a
 * human Othello player.
 */
public class HumanCheckerPlayer extends CheckerPlayer
{
	/**
	 * Constructs a human Othello player.
	 * @param world the world
	 */
	public HumanCheckerPlayer(CheckerWorld world)
	{
		super(world, "Human", Color.BLUE);
	}
	
	public HumanCheckerPlayer(CheckerWorld world, String s, Color color)
    {
        super(world, s, color);
    }

	/**
	 * Retrieves the next play for the human.
	 * Postcondition: the returned location is an allowed play.
	 * @return the location for the next play
	 */
	public MoveInfo getPlay()
	{
		Location loc;
		do
		{
			loc = getWorld().getPlayerLocation();
		}
		while (! isAllowedPlay(loc));
		return loc;
	}
}
