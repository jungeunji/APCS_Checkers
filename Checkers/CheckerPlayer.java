import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.util.ArrayList;
import java.awt.Color;

/**
 * A CheckerPlayer class that contains the basic functionalities of
 * a player in the Checkers game.
 * 
 * @author Darren Yang
 * @version May 12, 2014
 */
public abstract class CheckerPlayer 
{
	//The world
	private CheckerWorld world;
	
	//The grid
	private Grid<Piece> board;
	
	//The player name
	private String name;
	
	//The player color
	private Color color;
	
	//The player's pieces
	private ArrayList<Piece> pieces;
	
	
	/**
	 * Three-parameter constructor for the Player.
	 * @param w CheckerWorld of the player
	 * @param n Name of the player
	 * @param c Color of the player's pieces
	 * @param p List of the player's pieces
	 */
	public CheckerPlayer( CheckerWorld w, String n, Color c, ArrayList<Piece> p )
	{
		world = w;
		board = w.getGrid();
		name = n;
		color = c;
		pieces = p;
	}
	
	/**
	 * Gets location of next move
	 * @return location of next move
	 */
	public abstract Location getPlay();
	
	/**
	 * Returns player name
	 * @return player name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the player world
	 * @return player world
	 */
	public CheckerWorld getWorld()
	{
		return world;
	}
	
	public ArrayList<Location> getAllowedMoves()
	{
		ArrayList<Location> moveList = new ArrayList<Location>();
		ArrayList<Location> jumpList = new ArrayList<Location>();
		for ( Piece piece : pieces )
		{
			
//			if ( piece.getColor() == Color.RED )
//			{
//				Location loc1 = new Location( piece.getLocation().getRow() - 1,
//						piece.getLocation().getCol() - 1 );
//				Location loc2 = new Location( piece.getLocation().getRow() - 1,
//						piece.getLocation().getCol() + 1 );
//				if ( board.get( loc1 ).))
//			}
			
		}
	}
	
	private boolean canJump( Piece p )
	{
		ArrayList<Location> jumps = board.getOccupiedAdjacentLocations( p.getLocation() );
		if ( jumps.isEmpty() )
		{
			return false;
		}
		else if ( p.king() )
		{
			return true;
		}
		
		for ( Location loc : jumps )
		{
			if ( p.getColor() == Color.RED )
		}
	}
}
