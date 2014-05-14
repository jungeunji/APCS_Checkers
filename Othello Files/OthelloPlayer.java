import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.util.ArrayList;
import java.awt.Color;

/**
 * OthelloPlayer.java
 *
 * This is the top-level class for all player classes.
 * 
 *  @author  Darren Yang
 *  @author  Id 5101177
 *  @version Apr 2, 2014
 *  @author  Period: 2
 *  @author  Assignment: GWOthello
 * 
 *  @author  Sources: I received help from ...
 */
public abstract class OthelloPlayer
{
	/** The world */
    private OthelloWorld world;

	/** The grid */
    private Grid<Piece> board;

	/** The name of the player ("Human" or "Computer") */
    private String name;

	/** The color of this player's game pieces */
    private Color color;


	/**
	 * Constructs an Othello player object.
	 * @param w the world
	 * @param n the name ("Human" or "Computer")
	 * @param c the color
	 */
	public OthelloPlayer(OthelloWorld w, String n, Color c)
	{
	    world = w;
	    board = w.getGrid();
	    name = n;
	    color = c;
	}

	/**
	 * Gets the next move.
	 * @return the location of the next move
	 */
	public abstract Location getPlay();

	/**
	 * Gets the player name.
	 * @return the player name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Gets the Othello world.
	 * @return the Othello world
	 */
	public OthelloWorld getWorld()
	{
		return world;
	}

	/**
	 * Determines if the player can make a play.
	 * @return true if the player can play; false otherwise
	 */
	public boolean canPlay()
	{
		return !getAllowedPlays().isEmpty();
	}

	/**
	 * Computers the list of locations that the player may play
	 * @return allowed (legal) plays (locations) for this player
	 */
	public ArrayList<Location> getAllowedPlays()
	{
	    ArrayList<Location> allowedMoves = new ArrayList<Location>();
        for ( Location empty : getEmptyLocations() )
        {
            for ( int i = 0; i < 8; i++ )
            {
                Location adjacent = empty.getAdjacentLocation( i
                    * Location.HALF_LEFT );
                Location next = getNextLocationWithColor( empty, adjacent );
                if ( next != null && !allowedMoves.contains( next )
                    && !next.equals( adjacent ) )
                {
                    allowedMoves.add( empty );
                }
            }
        }
        
        return allowedMoves;
	}

	/**
	 * Determines if this play is allowed by the rules
	 * @param loc location to be checked
	 * @return true if this location is allowed to be played;
	 *         false otherwise
	 */
	public boolean isAllowedPlay(Location loc)
	{
		return getAllowedPlays().contains( loc );
	}

	/**
	 * Make the play indicated by calling getPlay.
	 * (Place a piece and "flip" the appropriate other pieces.)
	 */
	public void play()
	{
	    Location playLoc = getPlay();
	    board.put( playLoc, new Piece( color ) );
	    
        for ( int i = 0; i < 8; i++ )
        {
            Location adjacent = playLoc.getAdjacentLocation( i
                * Location.HALF_LEFT );
            Location next = getNextLocationWithColor( playLoc, adjacent );
            if ( next != null && !next.equals( adjacent ) )
            {
                flipColorOfPieces( adjacent, next );
            }
        }
		System.out.println(name + " plays" + playLoc );
	}

	/**
	 * Determines the empty locations on the board.
	 * @return a list of the empty board locations
	 */
	private ArrayList<Location> getEmptyLocations()
	{
	    ArrayList<Location> occ = board.getOccupiedLocations();
	    ArrayList<Location> empty = new ArrayList<Location>();
	    for ( int r = 0; r < board.getNumRows(); r++ )
	    {
	        for ( int c = 0; c < board.getNumCols(); c++ )
	        {
	            Location addLoc = new Location( r, c );
	            if ( !occ.contains( addLoc ) )
	            {
	                empty.add( addLoc );
	            }
	        }
	    }
		return empty;
	}

	/**
	 * Finds the next location with this player's color
	 * beginning with second in the direction from first to second.
	 * @param first first location
	 * @param second second location
	 * @return next location with this player's color in the
	 *         direction from first to second starting with second
	 */
	private Location getNextLocationWithColor(
		Location first, Location second)
	{
	    int angle = first.getDirectionToward( second );
	    Location change = second;
	    
		while ( true )
		{
		    if ( !board.isValid( change ) )
		    {
		        return null;
		    }
		    
		    Piece piece = board.get( change );
		    if ( piece == null )
		    {
		        return null;
		    }
		    
		    if ( piece.getColor().equals( color ) )
		    {
		        return change;
		    }
		    
            change = change.getAdjacentLocation( angle );
		}
	}

	/**
	 * Changes (flips) the color of the pieces to the current player's
	 * color in the locations from start (included) to end (not included)
	 * @param start first location to color (flip)
	 * @param end first location past last location to color (flip)
	 */
	private void flipColorOfPieces(Location start, Location end)
	{
	    Location loc = start;
	    int angle = start.getDirectionToward( end );
	    
	    while ( !loc.equals( end ) )
	    {
	        board.get( loc ).setColor( color );
	        loc = loc.getAdjacentLocation( angle );
	    }
	}
}
